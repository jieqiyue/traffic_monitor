package com.jie.traffic.obj;

/**
 * @Classname Packet
 * @Description TODO
 * @Date 2022/5/16 14:59
 * @Created by Jieqiyue
 */
public class Packet {
    public int packetType;
    // Packet header
    public long timeStampSec;
    public long timeStampMil;
    public int capLen;
    public int offLen;
    //==============================================
    // Ethernet
    public byte[] ethDestMac = new byte[6];
    public byte[] ethSourceMac = new byte[6];
    public byte[] ethNetType = new byte[2];
    //===============================================
    // ipv4
    public String ipVersion;
    public int ipHeaderLen;
    public byte[] typeOfService = new byte[1];
    public byte[] totalLength = new byte[2];
    public byte[] identification = new byte[2];
    /**
     * 这个是flags，只有低3位有用。
      */
    public byte flags;
    /**
     * 因为fragmentOffset是13bit的，但是把它*8了，
     */
    public byte []fragmentOffset = new byte[2];
    public byte timeToLive, ipv4TransProtocol;
    public String ipv4TransProtocolCH;
    public byte[] ipv4HeaderChecksum = new byte[2];
    public byte[] ipv4SourceIpAddress = new byte[4];
    public byte[] ipv4DestinationIpAddress = new byte[4];
    public byte[] ipv4Options;
    //====================================
    // ARP
    public byte[] arpHardWareType = new byte[2];
    public byte[] arpUpProtocolType = new byte[2];
    public byte arpMacAddressLen, arpIpProtocolLen;
    public byte[] arpType = new byte[2];

    public byte[] arpSourceMacAddress = new byte[6];
    public byte[] arpSourceIpAddress = new byte[4];

    public byte[] arpDesMacAddress = new byte[6];
    public byte[] arpDesIpAddress = new byte[4];
    //====================================
    //UDP
    public byte[] udpSourcePort = new byte[2];
    public byte[] udpDestPort = new byte[2];
    public byte[] udpLen = new byte[2];
    public byte[] udpCheckSum = new byte[2];
    //==========================================
    // DNS
    // 表示这个dns报文解析了多长
    public int dnsLen = 0;
    // dns headers这个header是必须要存在的，为12个字节。
    public byte[] dnsTransactionId = new byte[2];
    public byte[] dnsFlags = new byte[2];
    public String dnsReqRespType; // 这个dnsType就是标识是请求报文还是响应报文
    //表示这个dns报文中有多少个问题，问题就是要服务器回答的问题。这个字段决定了下面可选字段的个数。
//    public byte[] dnsQuestions = new byte[2];
//    public byte[] dnsAnswerRRs = new byte[2];
//    public byte[] dnsAuthorityRRs = new byte[2];
//    public byte[] dnsAdditional = new byte[2];
    public int dnsQuestionNum,dnsAnswerRRsNum,
            dnsAuthorityRRsNum,dnsAdditionalNum;
    // 接下来为dns报文长度可变的四个字段
    public DnsQuestion[] dnsQues;
    public DnsAnswer[] dnsAns;
    //下面这两个在解析DNS的时候直接跳过了，如果后续要用到了，再去解析它。
    public DnsAnswer[] dnsAuthority;
    public DnsAnswer[] dnsAdditional;
    //=============================================
}
