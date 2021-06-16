package com.wenna4.ppp.controller.pay;

import com.wenna4.ppp.Intf.DO.PayChannelDo;
import com.wenna4.ppp.Intf.DO.PayUnipayDO;
import com.wenna4.ppp.Intf.DO.ResponseDO;
import com.wenna4.ppp.Intf.Entity.PayOrder;
import com.wenna4.ppp.Intf.Enum.OrderStatusEnum;
import com.wenna4.ppp.Intf.Enum.PayChannelEnum;
import com.wenna4.ppp.Intf.Enum.PayMethodEnum;
import com.wenna4.ppp.Intf.PayChannelService;
import com.wenna4.ppp.Intf.PayOrderService;
import com.wenna4.ppp.controller.BaseController;
import com.wenna4.ppp.controller.utils.IPUtil;
import org.apache.commons.lang.StringUtils;
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

/**
 * @author qinsong
 */
@Controller
@RequestMapping("/pay/channel")
public class PayChannelController extends BaseController {

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private PayChannelService payChannelService;

    private Logger log = LoggerFactory.getLogger(PayChannelController.class);

    @ResponseBody
    @PostMapping("/ajax/toPay")
    public ResponseDO toPay(PayChannelDo paychannelDO, HttpServletRequest request, HttpServletResponse response) {
        log.info("channel to pay");
        //1.参数校验
        String ret = validate(paychannelDO);
        log.info("渠道支付参数校验结果：" + ret);
        if (!StringUtils.equals(ret, SUCCESS)) {
            return new ResponseDO(false, ret, FAIL_ID, null);
        }
        //2.支付校验
        String useIP = IPUtil.getRequestIP(request);
        String url = payChannelService.pay(paychannelDO, PayChannelEnum.getById(paychannelDO.getPayChannel()), PayMethodEnum.getById(paychannelDO.getPayMethod()), useIP);
        return new ResponseDO(true, SUCCESS, SUCCESS_ID, url);
    }

    @ResponseBody
    @PostMapping("/alipayNotify")
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        log.info("channel to notify");
        String status = request.getParameter("trade_status");
        String outTradeNO = request.getParameter("trade_no");
        String tradeNo = request.getParameter("out_trade_no");
        String price = request.getParameter("price");
        log.info("channel to notify.status:" + status + "," + tradeNo);

        //参数校验
        log.info("参数校验");

        return null;
    }

    private String validate(PayChannelDo unipayDO) {
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
        if (payOrder == null || payOrder.getUserId()!=unipayDO.getUserId()||
                payOrder.getStatus() != OrderStatusEnum.NotPay.getId()) {
            return "参数不正确";
        }
        return SUCCESS;

    }


}
