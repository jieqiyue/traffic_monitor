package com.jie.traffic.emnus;

/**
 * @Classname DnsType
 * @Description TODO
 * @Date 2022/5/28 13:11
 * @Created by Jieqiyue
 */
public enum DnsType {


    HostAddress(1,"Host Address"),
    CNAME(5,"CNAME");




    DnsType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    public static String getDnsType(Integer code) {
        if(code==null){
            return null;
        }
        for (DnsType dnsType : DnsType.values()) {
            if (dnsType.getCode().equals(code)) {
                return dnsType.getName();
            }
        }
        return null;
    }
    private String name;

    private Integer code;
}
