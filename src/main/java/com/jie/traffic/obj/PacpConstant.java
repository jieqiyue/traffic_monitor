package com.jie.traffic.obj;

/**
 * @Classname pacpConstant
 * @Description TODO
 * @Date 2022/5/12 19:00
 * @Created by Jieqiyue
 */
public class PacpConstant {
    public static final int PCAPHEADERLEN = 24;

    //packet header中的秒级时间戳
    public static final int TIMESTAMPSELEN = 4;

    // packet header中的微妙时间戳（基于TIMESTAMPSE）
    public static final int TIMESTAMPSELMI = 4;

    public static final int capDataLen = 4;

}
