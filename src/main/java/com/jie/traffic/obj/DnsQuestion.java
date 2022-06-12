package com.jie.traffic.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DnsQuestion
 * @Description TODO
 * @Date 2022/5/26 17:32
 * @Created by Jieqiyue
 */
public class DnsQuestion {
    //由于qname是不定长的，所以直接先用一个List放着。
    public List<Byte> qNameArr = new ArrayList<>();
    public String qName;
    //这两个字段暂时就存byte类型就行，到时候如果要有它的含义的时候在来进行含义的解析。
    public byte[] qType = new byte[2];
    public byte[] qClass = new byte[2];
}
