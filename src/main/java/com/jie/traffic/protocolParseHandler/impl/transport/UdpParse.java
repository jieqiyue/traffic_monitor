package com.jie.traffic.protocolParseHandler.impl.transport;

import com.jie.traffic.obj.Packet;
import com.jie.traffic.protocolParseHandler.ProtocolParse;

import java.nio.MappedByteBuffer;

/**
 * @Classname UdpParse
 * @Description TODO
 * @Date 2022/5/23 17:24
 * @Created by Jieqiyue
 */
public class UdpParse implements ProtocolParse {
    //todo 如果是没有udp头的udp数据应该怎么处理？
    @Override
    public boolean canParse(MappedByteBuffer mpb, Packet packet) {
        if (packet.ipv4TransProtocolCH == "UDP") {
            return true;
        }
        return false;
    }

    @Override
    public boolean parse(Packet packet, MappedByteBuffer mpb, int num) throws Exception {
        mpb.get(packet.udpSourcePort);
        mpb.get(packet.udpDestPort);
        mpb.get(packet.udpLen);
        mpb.get(packet.udpCheckSum);

        packet.packetType = packet.packetType | 1;

        //TODO 后续不解析了，所以将文件指针跳过，如果后续有解析上层协议，需要将这里放开
        if (packet.udpSourcePort[1] == 53 || packet.udpDestPort[1] == 53) {
            return true;
        }else{
            mpb.position(mpb.position() + packet.capLen - 14 - packet.ipHeaderLen - 8);
            return false;
        }
    }
}
