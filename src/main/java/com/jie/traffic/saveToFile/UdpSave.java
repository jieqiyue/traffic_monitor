package com.jie.traffic.saveToFile;

import com.jie.traffic.obj.Packet;
import com.jie.traffic.utils.ConverseUtil;
import com.jie.traffic.utils.TimeUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Classname UdpSave
 * @Description [时间] 数据包长度 源MAC地址 目的MAC地址 网络层协议名 源IP地址 目的IP地址 源端口 目的端口
 * @Date 2022/5/28 11:57
 * @Created by Jieqiyue
 */
public class UdpSave extends SavePacketToFile{
    public UdpSave(String filePath) throws FileNotFoundException {
        super(filePath);
        fmtFile.format("num  [time]\t\t\t\tpackage len\t\t\tsource mac\t\t\tdes mac\t\t\tnet proname\t\tsource IP\t\tDes ip\t\tsource port\t\tdes port\n");
    }

    @Override
    public void save(Packet packet, int num) throws IOException {
        fmtFile.format("%7d [%s]  %dBytes %02x-%02x-%02x-%02x-%02x-%02x %02x-%02x-%02x-%02x-%02x-%02x\t\t%s\t\t %15s   %15s  %5d\t %5d \n",num,
                TimeUtil.TimeStamp2Date(packet.timeStampSec),packet.capLen,packet.ethSourceMac[0],packet.ethSourceMac[1],packet.ethSourceMac[2],
                packet.ethSourceMac[3],packet.ethSourceMac[4],packet.ethSourceMac[5],packet.ethDestMac[0],packet.ethDestMac[1],packet.ethDestMac[2]
                ,packet.ethDestMac[3],packet.ethDestMac[4],packet.ethDestMac[5],packet.ipVersion,ConverseUtil.ip4ToString(packet.ipv4SourceIpAddress),
                ConverseUtil.ip4ToString(packet.ipv4DestinationIpAddress),ConverseUtil.twoByteToInt(packet.udpSourcePort),ConverseUtil.twoByteToInt(packet.udpDestPort)
        );
    }
}
