package com.wenna4.ppp.Intf;

import com.wenna4.ppp.Intf.Entity.VipOrder;

public interface VipOrderService {

    VipOrder loadById(int id);

    int insert(VipOrder order);

    int update(VipOrder order);
}
