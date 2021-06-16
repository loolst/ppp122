package com.wenna4.ppp.Intf.Enum;

public enum BizEnum {
    VIP(11, "会员卡"),
    Course(2, "在线课程");

    private int id;
    private String name;

    BizEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BizEnum getById(int id) {
        for (BizEnum bizEnum : BizEnum.values()) {
            if (bizEnum.getId() == id) {
                return bizEnum;
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
