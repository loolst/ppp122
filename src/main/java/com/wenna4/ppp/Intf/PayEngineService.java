package com.wenna4.ppp.Intf;

import com.wenna4.ppp.Intf.Entity.PayEngine;
import com.wenna4.ppp.Intf.Enum.OrderStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PayEngineService {
    public int insert(PayEngine payEngine);

    public PayEngine loadById(int id);

    public int update(PayEngine payEngine);

    public List<PayEngine> getSubEngineList(int mainEngineId);

    public int updateStatusByMainEngineId(int engineId, OrderStatusEnum orderStatusEnum, String userIp);
}
