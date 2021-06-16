package com.wenna4.ppp.Intf;

import com.wenna4.ppp.Intf.Entity.PayUnipay;

public interface PayUnipayService {

    public PayUnipay loadById(int id);

    public int insert(PayUnipay payUnipay);

    public int update(PayUnipay payUnipay);


}
