package com.wenna4.ppp.controller;

import com.wenna4.ppp.Intf.DO.ResponseDO;
import com.wenna4.ppp.Intf.Entity.PayOrder;
import com.wenna4.ppp.Intf.Entity.User;
import com.wenna4.ppp.Intf.Entity.VipOrder;
import com.wenna4.ppp.Intf.Enum.BizEnum;
import com.wenna4.ppp.Intf.Enum.OrderStatusEnum;
import com.wenna4.ppp.Intf.Enum.PlatformEnum;
import com.wenna4.ppp.Intf.UserService;
import com.wenna4.ppp.Intf.VipOrderService;
import com.wenna4.ppp.Intf.constant.KeyConstant;
import com.wenna4.ppp.Intf.utils.HttpClientUtils;
import com.wenna4.ppp.Intf.utils.JsonUtils;
import com.wenna4.ppp.Intf.utils.ParameterUtil;
import com.wenna4.ppp.Intf.utils.security.MD5Signature;
import com.wenna4.ppp.controller.utils.CookieUtil;
import com.wenna4.ppp.controller.utils.IPUtil;
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
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/vip/order")
public class VipOrderController extends BaseController {

    private Logger log = LoggerFactory.getLogger(VipOrderController.class);

    @Resource
    private VipOrderService vipOrderService;


    @GetMapping("/toPay")
    public String toPay(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        int userId = UserUtils.dealuser(DOMAIN, request);
        if (userId == 0) {
            //跳到登录
            return "redirect:/u/login?callback=/vip/order/toPay";
        }

        model.addAttribute("goods", "美国总统府一日游锤票");
        model.addAttribute("amount", new BigDecimal("300"));

        return "vipOrder";

        //1 找开一个页面 2 ，点击支付能在vip 支付记录  3 调用支付中心PayOrder层的下单
    }

    @PostMapping("/ajax/toPay")
    @ResponseBody
    public ResponseDO pay(BigDecimal amount, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        log.info("vip order to pay, amount=:" + amount.toString());
        //String userName, String password

        int userId = UserUtils.dealuser(DOMAIN, request);
        if (userId == 0) {
            //跳到登录
            return new ResponseDO(false, "用户未登录", 0, null);
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseDO(false, "金额不正确", 1, null);
        }

        VipOrder vipOrder = new VipOrder();
        vipOrder.setAmount(amount);
        vipOrder.setUserId(userId);
        vipOrder.setStatus(0);
        vipOrderService.insert(vipOrder);

        // 1存到数据库里

        //50-100, http, dubbo, socket

        PayOrder order = new PayOrder();
        order.setUserId(userId);
        order.setAmount(vipOrder.getAmount());
        order.setBizId(BizEnum.VIP.getId());
        order.setBizOrderId(vipOrder.getId());
        order.setStatus(OrderStatusEnum.NotPay.getId());
        order.setCallbackUrl("/vip/order/orderCallback");
        order.setReturnUrl("/vip/order/toPay");
        order.setPlatformId(PlatformEnum.PC.getId());
        order.setTitle("VIP订单" + vipOrder.getId());
        order.setMsg("发起支付");
        order.setUserIp(IPUtil.getRequestIP(request));

        String json = JsonUtils.toStr(order);
        Map<String, String> map = JsonUtils.convert2Map(json);

        //map = ParameterUtil.getObjectToMap(order);

        String ret = HttpClientUtils.executePostHttpClientUTF("http://127.0.0.1:8080/pay/order/create", map);
        ret.toUpperCase().hashCode();

        return (ResponseDO) JsonUtils.convert(ret, ResponseDO.class);
      /*  Map<String,String> map = new HashMap<>();
        map.put("userId",userId + "" );
        map.put("bizOrderId",  order.getId() +"");
        map.put("bizId", BizEnum.Course.getId() + "");
        map.put("amount", amount.toString() );
        map.put("status", "0" );
        map.put("msg","发起支付" );
        map.put("platformId", PlatformEnum.PC.getId() + "");
        map.put("callbackUrl","/class/order/orderCallback" );
        map.put("returnUrl","" );
        map.put("title", "课程订单" + classOrderId );
        map.put("userIp", IPUtil.getRequestIP(request));
        String ret = HttpClientUtils.executePostHttpClientUTF("http://127.0.0.1:8080/pay/order/create", map);
        logger.info("ret =:" + ret);*/

    }


}
