package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.Entity.PayEngine;
import com.wenna4.ppp.Intf.Enum.EngineStatusEnum;
import com.wenna4.ppp.Intf.Enum.EngineTypeEnum;
import com.wenna4.ppp.Intf.Enum.OrderStatusEnum;
import com.wenna4.ppp.Intf.Mapper.PayEngineMapper;
import com.wenna4.ppp.Intf.PayEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PayEngineServiceImpl implements PayEngineService {
    private static final Logger log = LoggerFactory.getLogger(PayEngineServiceImpl.class);

    @Resource
    private PayEngineMapper payEngineMapper;

    @Override
    public int insert(PayEngine payEngine) {
        return payEngineMapper.insert(payEngine);
    }

    @Override
    public PayEngine loadById(int id) {
        return payEngineMapper.loadById(id);
    }

    @Override
    public int update(PayEngine payEngine) {
        return 0;
    }

    @Override
    public List<PayEngine> getSubEngineList(int mainEngineId) {
        return payEngineMapper.getSubEngineList(mainEngineId);
    }

    @Override
    public int updateStatusByMainEngineId(int engineId, OrderStatusEnum orderStatusEnum, String userIp) {
        List<PayEngine> engineList = getSubEngineList(engineId);
        if (engineList != null) {
            for (PayEngine tempEngine : engineList) {
                tempEngine.setStatus(orderStatusEnum.getId());
                tempEngine.setDesc(getDesc(tempEngine.getEngineType(), orderStatusEnum, tempEngine.getAmount()));
                if (tempEngine.getEngineType() != EngineTypeEnum.CouponCommand.getId()) { //只要使用券，前面插入的时候就已经说明券已使用
                    update(tempEngine);
                }
            }
        }
        //更新主引擎的状态
        PayEngine engine = new PayEngine();
        engine.setId(engineId);
        engine.setDesc(getDesc(0, orderStatusEnum, null));
        engine.setStatus(orderStatusEnum.getId());
        return update(engine);
    }

    private String getDesc(int engineType, OrderStatusEnum orderStatusEnum, BigDecimal amount) {
        String desc = orderStatusEnum.getName();
        if (orderStatusEnum == OrderStatusEnum.PaySuccess) {
            switch (engineType) {
                case 0:
                    desc = orderStatusEnum.getName();
                    break;
                case 1:
                    desc = EngineStatusEnum.CouponUsed.getName();
                    break;
                case 2:
                    desc = EngineStatusEnum.AmountReduce.getName();
                    break;
                case 3:
                    desc = EngineStatusEnum.ThirdPartPaySuccess.getName();
                    break;
                default:
                    desc = orderStatusEnum.getName();
                    break;
            }
        } else if (orderStatusEnum == OrderStatusEnum.PayFail) {
            switch (engineType) {
                case 0:
                    desc = orderStatusEnum.getName();
                    break;
                case 1:
                    desc = EngineStatusEnum.CouponUsed.getName();
                    break;
                case 2:
                    desc = EngineStatusEnum.AmountResume.getName();
                    break;
                case 3:
                    desc = EngineStatusEnum.ThirdPartPayFail.getName();
                    break;
                default:
                    desc = orderStatusEnum.getName();
                    break;
            }
        }
        if (amount != null) {
            desc = desc.replace("%s", amount.toString());
        }
        return desc;
    }
}
