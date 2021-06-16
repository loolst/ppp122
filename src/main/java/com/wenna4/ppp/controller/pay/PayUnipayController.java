package com.wenna4.ppp.controller.pay;

import com.wenna4.ppp.Intf.CouponUserService;
import com.wenna4.ppp.Intf.DO.CouponUserDO;
import com.wenna4.ppp.Intf.DO.PayUnipayDO;
import com.wenna4.ppp.Intf.DO.ResponseDO;
import com.wenna4.ppp.Intf.Entity.CouponUser;
import com.wenna4.ppp.Intf.Entity.PayOrder;
import com.wenna4.ppp.Intf.Entity.PayUnipay;
import com.wenna4.ppp.Intf.Enum.OrderStatusEnum;
import com.wenna4.ppp.Intf.Enum.PayChannelEnum;
import com.wenna4.ppp.Intf.Enum.PayMethodEnum;
import com.wenna4.ppp.Intf.PayOrderService;
import com.wenna4.ppp.Intf.PayUnipayService;
import com.wenna4.ppp.Intf.utils.HttpClientUtils;
import com.wenna4.ppp.Intf.utils.JsonUtils;
import com.wenna4.ppp.controller.BaseController;
import com.wenna4.ppp.controller.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pay/unipay")
public class PayUnipayController extends BaseController {

    @Resource
    private PayUnipayService payUnipayService;
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private CouponUserService couponUserService;

    private Logger log = LoggerFactory.getLogger(PayUnipayController.class);


    @GetMapping("/{orderId}")
    public String show(@PathVariable int orderId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.orderId存不存在，status, userId
        if (orderId <= 0) {
            return "redirect:/index";
        }
        int userId = UserUtils.dealuser(DOMAIN, request);
        if (userId == 0) {
            //跳到登录
            return "redirect:/u/login?callback=/class/order/toPay";
        }
        PayOrder payOrder = payOrderService.loadById(orderId);
        if (payOrder == null || payOrder.getUserId() != userId || payOrder.getStatus() != OrderStatusEnum.NotPay.getId()) {
            log.warn("fail to validateOrder");
            return "redirect:/index";
        }
        //分页，只查第一页
        //下面是把参数传给页面
        model.addAttribute("order", payOrder);
        List<CouponUserDO> couponUserDOList = couponUserService.loadListByUserIdAndBizId(userId, payOrder.getBizId(), payOrder.getAmount(), 1, 10);
        model.addAttribute("couponList", couponUserDOList);
        return "cashier";
    }

    @PostMapping("/ajax/toPay")
    @ResponseBody
    public ResponseDO pay(PayUnipayDO unipayDO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1 参数校验 2 入库 3 调用 Channel的接口，进行对外的渠道调用
        log.info("uniPay to pay ,unipaydo = : " + unipayDO.toString());
        //1. channeDo的参数校验
        String ret = validate(unipayDO);
        log.info("支付参数校验结果：" + ret);
        if (!StringUtils.equals(ret, SUCCESS)) {
            return new ResponseDO(false, ret, FAIL_ID, null);
        }

        CouponUser coupon = null;
        if (!StringUtils.isEmpty(unipayDO.getCouponNum())) {
            coupon = couponUserService.loadByCouponNum(unipayDO.getCouponNum());
            if (coupon == null || coupon.getUserId() != unipayDO.getUserId()) {
                return new ResponseDO(false, "券不存在", FAIL_ID, null);
            }
        }
        //入库到unipay
        PayUnipay unipay = new PayUnipay();
        unipay.setCouponId(0);
        unipay.setCouponId(coupon == null ? 0 : coupon.getId());
        unipay.setAmount(unipayDO.getAmount());
        unipay.setOrderId(unipayDO.getOrderId());
        unipay.setPayChannel(unipayDO.getPayChannel());
        unipay.setPayMethod(unipayDO.getPayMethod());
        unipay.setUserId(unipayDO.getUserId());

        log.info(unipay.toString());

        payUnipayService.insert(unipay);

        //("#userId").val() ,"orderId":$("#orderId").val(), "payMethod":method,"payChannel":channel, "payPlatform":1000,"amount":$("#amount").val(), "couponNum":""}

        Map<String, String> map = new HashMap<>();
        map.put("userId", unipayDO.getUserId() + "");
        map.put("orderId", unipayDO.getOrderId() + "");
        map.put("payChannel", unipayDO.getPayChannel() + "");
        map.put("payMethod", unipayDO.getPayMethod() + "");
        map.put("payPlatform", unipayDO.getPayPlatform() + "");
        map.put("amount", unipayDO.getAmount().toString());
        map.put("couponNum", unipayDO.getCouponNum());
        String ret1 = HttpClientUtils.executePostHttpClientUTF("http://127.0.0.1:8080/pay/engine/ajax/topay", map);
        log.info("ret =:" + ret1);
        return (ResponseDO) JsonUtils.convert(ret1, ResponseDO.class);
    }

    //放入bizType
    private String validate(PayUnipayDO unipayDO) {
        log.info("开始校验渠道支付参数");
        if (unipayDO == null) {
            return "参数不存在";
        }
        if (unipayDO.getOrderId() <= 0
                || unipayDO.getAmount().compareTo(BigDecimal.ZERO) == -1) {
            return "参数不正确";
        }
        // 支付渠道和支付方式是否存在
        PayChannelEnum payChannel = PayChannelEnum.getById(unipayDO.getPayChannel());
        PayMethodEnum payMethod = PayMethodEnum.getById(unipayDO.getPayMethod());
        if (payChannel == null || payMethod == null) {
            return "支付渠道或者支付方式不存在";
        }
        //增加BeanUtils.

        PayOrder payOrder = payOrderService.loadById(unipayDO.getOrderId());
        //到这一步早在业务层就已经调用PayOrder的createOrder将其状态修改为NotPay了，这里如果不是NotPay,都是错误的
        //还有这里注意对payOrder的归属的校验
        if (payOrder == null || payOrder.getUserId() != unipayDO.getUserId() ||
                payOrder.getStatus() != OrderStatusEnum.NotPay.getId()) {
            return "参数不正确";
        }
        return SUCCESS;

    }


}
