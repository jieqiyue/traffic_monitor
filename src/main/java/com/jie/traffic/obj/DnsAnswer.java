package com.jie.traffic.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DnsAnswer
 * @Description TODO
 * @Date 2022/5/26 17:43
 * @Created by Jieqiyue
 */
public class DnsAnswer {
    //由于qname是不定长的，所以直接先用一个List放着。
    public List<Byte> rNameArr = new ArrayList<>();
    public String rName;

    public byte[] aType = new byte[2];
    public byte[] aClass = new byte[2];
    public byte[] aTimeToLive = new byte[4];
   // public byte[] aDataLength = new byte[2];
    //进行了一下转化，本来是2个字节的，将这两个字节转化为了int值
    public int aDataLength;
    public byte[] aData;
    //由于aData域可能是ip地址，也可能是一个域名（只要响应类型是CNAME），如果是域名的话可能会被其它地方引用，所以用一个字段表示转换后的
    public String aDataString;
}
