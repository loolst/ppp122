package com.wenna4.ppp.Intf.Enum;

public enum PaySourceEnum {

    Out(1, "向第三方请求"),
    SynIn(2, "第三方同步返回"),
    AsynIn(3, "第三方异步返回");

    private int id;
    private String name;

    PaySourceEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PaySourceEnum getById(int id) {
        for (PaySourceEnum PaySourceEnumEnum : PaySourceEnum.values()) {
            if (PaySourceEnumEnum.getId() == id) {
                return PaySourceEnumEnum;
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
