package com.wenna4.ppp.Intf.Enum;

public enum PayChannelEnum {
    Alipay(1, "支付宝"),
    WeiXin(2, "微信支付"),
    JD(3, "京东支付"),
    HuaBei(4, "花呗");

    private int id;
    private String name;

    PayChannelEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PayChannelEnum getById(int id) {
        for (PayChannelEnum payChannelEnum : PayChannelEnum.values()) {
            if (payChannelEnum.getId() == id) {
                return payChannelEnum;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
