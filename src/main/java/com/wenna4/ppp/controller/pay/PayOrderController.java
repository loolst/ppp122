package com.wenna4.ppp.controller.pay;

import com.wenna4.ppp.Intf.DO.ResponseDO;
import com.wenna4.ppp.Intf.Entity.PayOrder;
import com.wenna4.ppp.Intf.Enum.BizEnum;
import com.wenna4.ppp.Intf.Enum.OrderStatusEnum;
import com.wenna4.ppp.Intf.Enum.PlatformEnum;
import com.wenna4.ppp.Intf.PayOrderService;
import com.wenna4.ppp.controller.BaseController;
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


@Controller
@RequestMapping("/pay/order")
public class PayOrderController extends BaseController {

    private Logger log = LoggerFactory.getLogger(PayOrderController.class);

    @Resource
    private PayOrderService payOrderService;


    @PostMapping("/create")
    @ResponseBody
    public ResponseDO pay(PayOrder order, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("pay order =:" + order.toString());

        //Order 进行判断，  order null , null pointer exception
        String ret = verify(order);
        if (!StringUtils.equals(ret, SUCCESS)) {
            return new ResponseDO(false, ret, FAIL_ID, null);
        }

        PayOrder tempOrder = payOrderService.loadByBizIdAndBizOrderId(BizEnum.getById(order.getBizId()), order.getBizOrderId());
        if (tempOrder != null) {
            if (tempOrder.getStatus() == OrderStatusEnum.PaySuccess.getId()) {
                return new ResponseDO(false, "已经支付完成", FAIL_ID, null);
            } else {
                if (order.getAmount().compareTo(tempOrder.getAmount()) != 0) {
                    tempOrder.setAmount(order.getAmount());
                    payOrderService.update(tempOrder);
                    return new ResponseDO(true, SUCCESS, SUCCESS_ID, null);
                }
            }
        }
        try {
            payOrderService.insert(order);

        } catch (Exception e) {
            log.error("11", e);
        }
        return new ResponseDO(true, SUCCESS, SUCCESS_ID, order.getId());

    }

    private String verify(PayOrder order) {
        if (order == null) {
            log.warn("");
            return "订单异常";
        }

        if (BizEnum.getById(order.getBizId()) == null || order.getBizOrderId() <= 0) {
            return "订单异常";
        }

        if (PlatformEnum.getById(order.getPlatformId()) == null) {
            return "访问平台不正确";
        }

        if (StringUtils.isEmpty(order.getCallbackUrl()) || StringUtils.isEmpty(order.getReturnUrl())) {
            return "回调参数不能为空";
        }

        return SUCCESS;
    }


}
