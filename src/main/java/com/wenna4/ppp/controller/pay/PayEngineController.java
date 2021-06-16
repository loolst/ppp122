package com.wenna4.ppp.controller.pay;

import com.wenna4.ppp.Intf.CouponService;
import com.wenna4.ppp.Intf.CouponUserService;
import com.wenna4.ppp.Intf.DO.PayDO;
import com.wenna4.ppp.Intf.DO.PayUnipayDO;
import com.wenna4.ppp.Intf.DO.ResponseDO;
import com.wenna4.ppp.Intf.Entity.Coupon;
import com.wenna4.ppp.Intf.Entity.CouponUser;
import com.wenna4.ppp.Intf.Entity.PayEngine;
import com.wenna4.ppp.Intf.Enum.EngineStatusEnum;
import com.wenna4.ppp.Intf.Enum.EngineTypeEnum;
import com.wenna4.ppp.Intf.Enum.OrderStatusEnum;
import com.wenna4.ppp.Intf.PayEngineService;
import com.wenna4.ppp.Intf.utils.HttpClientUtils;
import com.wenna4.ppp.Intf.utils.JsonUtils;
import com.wenna4.ppp.controller.BaseController;
import com.wenna4.ppp.controller.utils.IPUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay/engine")
public class PayEngineController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(PayEngineController.class);

    @Resource
    private PayEngineService payEngineService;
    @Resource
    private CouponUserService couponUserService;
    @Resource
    private CouponService couponService;

    @ResponseBody
    @PostMapping("/ajax/topay")
    public ResponseDO topay(PayUnipayDO payUnipayDO, HttpServletRequest request, HttpServletResponse response) {
        log.info("pay engine do:" + payUnipayDO.toString());

        String userIP = IPUtil.getRequestIP(request);

        //插总指令
        PayEngine totalPayEngine = generatePayEngine(payUnipayDO, userIP, payUnipayDO.getAmount(), EngineTypeEnum.TotalCommand,
                null, OrderStatusEnum.NotPay, 0);
        payEngineService.insert(totalPayEngine);

        //判断是否支付完成
        if (payUnipayDO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return new ResponseDO(true, "success", SUCCESS_ID, totalPayEngine);
        }

        //插入其他指令
        CouponUser couponDO = couponUserService.loadByCouponNum(payUnipayDO.getCouponNum());
        ResponseDO ret = parse(payUnipayDO, userIP, couponDO, totalPayEngine);
        Coupon coupon = null;
        if (couponDO != null) {
            coupon = couponService.loadById(couponDO.getCouponId());
        }

        //调用 channel 支第三方，支付
        if (StringUtils.equals(ret.getMsg(), "发起第三方支付")) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", payUnipayDO.getUserId() + "");
            map.put("orderId", payUnipayDO.getOrderId() + "");
            map.put("payChannel", payUnipayDO.getPayChannel() + "");
            map.put("payMethod", payUnipayDO.getPayMethod() + "");
            map.put("payPlatform", payUnipayDO.getPayPlatform() + "");
            map.put("amount", payUnipayDO.getAmount().toString());
            map.put("couponNum", "");

            String ret1 = HttpClientUtils.executePostHttpClientUTF("http://127.0.0.1:8080/pay/channel/ajax/toPay", map);
            log.info("ret =:" + ret1);
            return (ResponseDO) JsonUtils.convert(ret1, ResponseDO.class);
        } else {
            return ret;
        }

    }

    private PayEngine generatePayEngine(PayUnipayDO unipayDO, String userIp,
                                        BigDecimal amount, EngineTypeEnum engineTypeEnum, EngineStatusEnum engineStatusEnum,
                                        OrderStatusEnum orderStatusEnum, int mainEngineId) {
        String desc = orderStatusEnum.getName();
        if (engineStatusEnum != null) {
            desc = String.format(engineStatusEnum.getName(), amount);
        }
        PayEngine payEngine = new PayEngine();
        payEngine.setAmount(amount);
        payEngine.setDesc(desc);
        payEngine.setEngineType(engineTypeEnum.getId());
        payEngine.setMainEngineId(mainEngineId);
        payEngine.setOrderId(unipayDO.getOrderId());
        payEngine.setStatus(engineTypeEnum == EngineTypeEnum.CouponCommand ? 1 : orderStatusEnum.getId());//若使用券，则把券的状态设为已用
        payEngine.setUserId(unipayDO.getUserId());
        payEngine.setUserIp(userIp);
        return payEngine;
    }

    private ResponseDO parse(PayUnipayDO unipayDO, String userIp,
                             CouponUser couponDO, PayEngine totalPayEngine) {
        ResponseDO responseDO = parseCouponPay(unipayDO, userIp, couponDO, totalPayEngine);
        if (responseDO.isSuccess()) {
            return responseDO;
        }
        //如果支付没有完成
        PayEngine thirdPayEngine = generatePayEngine(unipayDO, userIp, unipayDO.getAmount(), EngineTypeEnum.ThirdPartPayCommand,
                EngineStatusEnum.ThirdPartPayStart, OrderStatusEnum.NotPay, totalPayEngine.getId());
        payEngineService.insert(thirdPayEngine);
        return new ResponseDO(true, "发起第三方支付", SUCCESS_ID, totalPayEngine);
    }

    private ResponseDO parseCouponPay(PayUnipayDO unipayDO, String userIp,
                                      CouponUser couponDO, PayEngine totalPayEngine) {
        if (StringUtils.isEmpty(unipayDO.getCouponNum()) || couponDO == null) {
            return new ResponseDO(false, "不使用券支付", FAIL_ID, null);
        }
        couponDO.setStatus(1);
        couponUserService.update(couponDO);

        Coupon coupon = couponService.loadById(couponDO.getCouponId());

        PayEngine couponPayEngine = generatePayEngine(unipayDO, userIp, coupon.getAmount(), EngineTypeEnum.CouponCommand,
                EngineStatusEnum.CouponUsed, OrderStatusEnum.NotPay, totalPayEngine.getId());
        payEngineService.insert(couponPayEngine);
        //判断券支付金额是否满足
        if (unipayDO.getAmount().compareTo(coupon.getAmount()) <= 0) {
            unipayDO.setAmount(BigDecimal.ZERO);
            return new ResponseDO(true, SUCCESS, SUCCESS_ID, totalPayEngine);
        } else {
            unipayDO.setAmount(unipayDO.getAmount().subtract(coupon.getAmount()));
        }
        return new ResponseDO(false, "成功使用券支付", SUCCESS_ID, null);
    }

    @ResponseBody
    @PostMapping("/ajax/callback")
    public ResponseDO orderCallback(int engineId, OrderStatusEnum orderStatusEnum, String userIp, String desc) {
        PayEngine totalEngine = payEngineService.loadById(engineId);
        if (totalEngine == null) {
            return new ResponseDO(false, "支付引擎为空", FAIL_ID, null);
        }
        //执行对engine命令的更新
        int ret = payEngineService.updateStatusByMainEngineId(engineId, orderStatusEnum, userIp);
        if (ret <= 0) {
            return new ResponseDO(false, "支付引擎更新失败", FAIL_ID, null);
        }

        PayDO payDO = new PayDO();
        payDO.setAmount(totalEngine.getAmount());
        payDO.setMsg(desc);
        payDO.setOrderId(totalEngine.getOrderId());
        payDO.setOrderStatus(orderStatusEnum.getId());
        if (true) {//payOrderService.orderCallback(payDO)){ //unipay callback;
            return new ResponseDO(false, "支付异常,请重新支付", FAIL_ID, null);
        } else {
            return new ResponseDO(true, "支付完成", SUCCESS_ID, null);
        }

    }
}
