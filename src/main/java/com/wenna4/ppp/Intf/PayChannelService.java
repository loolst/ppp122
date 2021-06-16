package com.wenna4.ppp.Intf;

import com.wenna4.ppp.Intf.DO.PayChannelDo;
import com.wenna4.ppp.Intf.Entity.PayChannelAudit;
import com.wenna4.ppp.Intf.Enum.PayChannelEnum;
import com.wenna4.ppp.Intf.Enum.PayMethodEnum;

public interface PayChannelService {

    public PayChannelAudit loadById(int id);

    public int insert(PayChannelAudit channelAudit);

    public int update(PayChannelAudit channelAudit);

    /**
     * 支付方式不同,返回类型不同
     */
    public String pay(PayChannelDo payChannelDo, PayChannelEnum channelEnum, PayMethodEnum methodEnum, String userIp);


}
