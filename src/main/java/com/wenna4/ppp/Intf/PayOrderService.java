package com.wenna4.ppp.Intf;


import com.wenna4.ppp.Intf.Entity.PayOrder;
import com.wenna4.ppp.Intf.Enum.BizEnum;

public interface PayOrderService {

    PayOrder loadById(int id);

    int insert(PayOrder order);

    int update(PayOrder order);

    PayOrder loadByBizIdAndBizOrderId(BizEnum bizEnum, int bizOrderId);
}
