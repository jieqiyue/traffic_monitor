package com.jie.traffic.saveToFile;

import com.jie.traffic.obj.Packet;
import com.jie.traffic.utils.ConverseUtil;
import com.jie.traffic.utils.TimeUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Classname IpSave
 * @Description TODO
 * @Date 2022/5/28 11:37
 * @Created by Jieqiyue
 */
public class IpSave extends SavePacketToFile{
    public IpSave(String filePath) throws FileNotFoundException {
        super(filePath);
       // fmtFile.format("序号  \t[时间]\t\t\t数据包长度\t\t\t源MAC地址\t\t\t目的MAC地址\t\t\t源IP地址\t\t\t目的IP地址\n");
    }

    @Override
    public void save(Packet packet, int num) throws IOException {
        fmtFile.format("%7d [%s]  %dBytes %02x-%02x-%02x-%02x-%02x-%02x %02x-%02x-%02x-%02x-%02x-%02x    %s    %s \n",num,
                TimeUtil.TimeStamp2Date(packet.timeStampSec),packet.capLen,packet.ethSourceMac[0],packet.ethSourceMac[1],packet.ethSourceMac[2],
                packet.ethSourceMac[3],packet.ethSourceMac[4],packet.ethSourceMac[5],packet.ethDestMac[0],packet.ethDestMac[1],packet.ethDestMac[2]
                ,packet.ethDestMac[3],packet.ethDestMac[4],packet.ethDestMac[5], ConverseUtil.ip4ToString(packet.ipv4SourceIpAddress),ConverseUtil.ip4ToString(packet.ipv4DestinationIpAddress)
                );
    }
    // [时间] 数据包长度 源MAC地址 目的MAC地址 源IP地址 目的IP地址

}
