package com.wenna4.ppp.Intf.Enum;

public enum PayMethodEnum {

    AccountPay(1, "H5支付"),
    DirectPay(2, "快捷支付"),
    ScanCodePay(3, "扫码支付"),
    FenQiPay(4, "分期支付");

    private int id;
    private String name;

    PayMethodEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PayMethodEnum getById(int id) {
        for (PayMethodEnum payMethodEnum : PayMethodEnum.values()) {
            if (payMethodEnum.getId() == id) {
                return payMethodEnum;
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
