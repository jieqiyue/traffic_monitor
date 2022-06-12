package com.jie.traffic.saveToFile;

import com.jie.traffic.obj.Packet;
import com.jie.traffic.utils.ConverseUtil;
import com.jie.traffic.utils.TimeUtil;

import java.io.*;

/**
 * @Classname ArpSave
 * @Description TODO
 * @Date 2022/5/28 10:39
 * @Created by Jieqiyue
 */
public class ArpSave extends SavePacketToFile{

    public ArpSave(String filePath) throws FileNotFoundException {
        super(filePath);
        //fmtFile.format("序号  [时间]\t\t\t数据包长度\t\t\t源MAC地址\t\t\t目的MAC地址\t\t\tARP请求内容/ARP响应内容\n");
    }

    public void save(Packet packet,int num) throws IOException {
        // write to the file.
        if (packet.arpType[1] == 2){
            fmtFile.format("%7d [%s]  %dBytes %02x-%02x-%02x-%02x-%02x-%02x %02x-%02x-%02x-%02x-%02x-%02x 响应 %s 的MAC地址\n",num,
                    TimeUtil.TimeStamp2Date(packet.timeStampSec),packet.capLen,packet.ethSourceMac[0],packet.ethSourceMac[1],packet.ethSourceMac[2],
                    packet.ethSourceMac[3],packet.ethSourceMac[4],packet.ethSourceMac[5],packet.ethDestMac[0],packet.ethDestMac[1],packet.ethDestMac[2]
                    ,packet.ethDestMac[3],packet.ethDestMac[4],packet.ethDestMac[5], ConverseUtil.ip4ToString(packet.arpSourceIpAddress)
            );
        }else{
            fmtFile.format("%7d [%s]  %dBytes %02x-%02x-%02x-%02x-%02x-%02x %02x-%02x-%02x-%02x-%02x-%02x 查询 %s 的MAC地址\n",num,
                    TimeUtil.TimeStamp2Date(packet.timeStampSec),packet.capLen,packet.ethSourceMac[0],packet.ethSourceMac[1],packet.ethSourceMac[2],
                    packet.ethSourceMac[3],packet.ethSourceMac[4],packet.ethSourceMac[5],packet.ethDestMac[0],packet.ethDestMac[1],packet.ethDestMac[2]
                    ,packet.ethDestMac[3],packet.ethDestMac[4],packet.ethDestMac[5],ConverseUtil.ip4ToString(packet.arpDesIpAddress)

            );
        }

        if (fmtFile.ioException() != null) {
            throw new IOException("IoException in arp");
        }
    }
}
