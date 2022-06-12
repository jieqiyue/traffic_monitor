package com.jie.traffic.saveToFile;

import com.jie.traffic.emnus.DnsType;
import com.jie.traffic.obj.Packet;
import com.jie.traffic.utils.ConverseUtil;
import com.jie.traffic.utils.TimeUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Classname DnsSave
 * @Description [时间] 数据包长度 源MAC地址 目的MAC地址 网络层协议名 源IP地址 目的IP地址 源端口 目的端口 DNS包类型 请求内容/响应内容
 * @Date 2022/5/28 11:58
 * @Created by Jieqiyue
 */
public class DnsSave extends SavePacketToFile{
    public DnsSave(String filePath) throws FileNotFoundException {
        super(filePath);
        //fmtFile.format("序号 [时间]\t\t\t数据包长度\t\t\t源MAC地址\t\t\t目的MAC地址\t\t\t网络层协议名\t\t\t源IP地址\t\t\t目的IP地址\t\t源端口\t\t目的端口\t\tDNS包类型\t\t请求响应/响应内容\n");
    }

    @Override
    public void save(Packet packet, int num) throws IOException {

        //如果是1类
        if (packet.dnsReqRespType.equals("request")){
            for (int i = 0;i < packet.dnsQuestionNum;i++) {
                fmtFile.format("%7d [%s]  %dBytes\t%02x-%02x-%02x-%02x-%02x-%02x\t%02x-%02x-%02x-%02x-%02x-%02x\t\t%s\t\t\t\t%s\t\t%s \t\t%5d\t %d  \t\tDNS请求 %s  查询%s的地址\n",num,
                        TimeUtil.TimeStamp2Date(packet.timeStampSec),packet.capLen,packet.ethSourceMac[0],packet.ethSourceMac[1],packet.ethSourceMac[2],
                        packet.ethSourceMac[3],packet.ethSourceMac[4],packet.ethSourceMac[5],packet.ethDestMac[0],packet.ethDestMac[1],packet.ethDestMac[2]
                        ,packet.ethDestMac[3],packet.ethDestMac[4],packet.ethDestMac[5],packet.ipVersion, ConverseUtil.ip4ToString(packet.ipv4SourceIpAddress),
                        ConverseUtil.ip4ToString(packet.ipv4DestinationIpAddress),ConverseUtil.twoByteToInt(packet.udpSourcePort),ConverseUtil.twoByteToInt(packet.udpDestPort),
                        DnsType.getDnsType(ConverseUtil.twoByteToInt(packet.dnsQues[i].qType)),packet.dnsQues[i].qName
                );
            }

        }else if (packet.dnsReqRespType.equals("response") ){
            //&& packet.dnsAns[0].aType[1] == 1
            for (int i = 0;i < packet.dnsAnswerRRsNum;i++){
                if (packet.dnsAns[i].aType[1] == 1){
                    fmtFile.format("%7d [%s]  %dBytes\t%02x-%02x-%02x-%02x-%02x-%02x\t%02x-%02x-%02x-%02x-%02x-%02x\t\t%s\t\t\t\t%s\t\t%s \t\t%5d\t %d  \t\tDNS响应 %s  域名%s的IP地址是%s\n",num,
                            TimeUtil.TimeStamp2Date(packet.timeStampSec),packet.capLen,packet.ethSourceMac[0],packet.ethSourceMac[1],packet.ethSourceMac[2],
                            packet.ethSourceMac[3],packet.ethSourceMac[4],packet.ethSourceMac[5],packet.ethDestMac[0],packet.ethDestMac[1],packet.ethDestMac[2]
                            ,packet.ethDestMac[3],packet.ethDestMac[4],packet.ethDestMac[5],packet.ipVersion, ConverseUtil.ip4ToString(packet.ipv4SourceIpAddress),
                            ConverseUtil.ip4ToString(packet.ipv4DestinationIpAddress),ConverseUtil.twoByteToInt(packet.udpSourcePort),ConverseUtil.twoByteToInt(packet.udpDestPort),
                            DnsType.getDnsType(ConverseUtil.twoByteToInt(packet.dnsAns[i].aType)),packet.dnsAns[i].rName,packet.dnsAns[i].aDataString
                    );
                }else if (packet.dnsAns[i].aType[1] == 5) {
                    fmtFile.format("%7d [%s]  %dBytes\t%02x-%02x-%02x-%02x-%02x-%02x\t%02x-%02x-%02x-%02x-%02x-%02x\t\t%s\t\t\t\t%s\t\t%s \t\t%5d\t %d  \t\tDNS响应 %s 域名%s的CNAME是%s\n",num,
                            TimeUtil.TimeStamp2Date(packet.timeStampSec),packet.capLen,packet.ethSourceMac[0],packet.ethSourceMac[1],packet.ethSourceMac[2],
                            packet.ethSourceMac[3],packet.ethSourceMac[4],packet.ethSourceMac[5],packet.ethDestMac[0],packet.ethDestMac[1],packet.ethDestMac[2]
                            ,packet.ethDestMac[3],packet.ethDestMac[4],packet.ethDestMac[5],packet.ipVersion, ConverseUtil.ip4ToString(packet.ipv4SourceIpAddress),
                            ConverseUtil.ip4ToString(packet.ipv4DestinationIpAddress),ConverseUtil.twoByteToInt(packet.udpSourcePort),ConverseUtil.twoByteToInt(packet.udpDestPort),
                            DnsType.getDnsType(ConverseUtil.twoByteToInt(packet.dnsAns[i].aType)),packet.dnsAns[i].rName,packet.dnsAns[i].aDataString
                    );
                }

            }

        }

    }
}
