package com.jie.traffic.protocolParseHandler.impl.network;

import com.jie.traffic.emnus.TransType;
import com.jie.traffic.obj.Packet;
import com.jie.traffic.protocolParseHandler.ProtocolParse;

import java.nio.MappedByteBuffer;

/**
 * @Classname IpParse
 * @Description TODO
 * @Date 2022/5/17 15:28
 * @Created by Jieqiyue
 */
public class Ipv4Parse implements ProtocolParse {
    /**
     * 判断是不是ipv4协议解析的。读取一个字节判断前四位的值。如果是’0100‘就是ipv4，如果是‘0110’就是ipv6
     * @param mpb
     * @return
     */
    @Override
    public boolean canParse(MappedByteBuffer mpb,Packet packet) {
        if (packet.ethNetType[0] == 8 && packet.ethNetType[1] ==0) {
            return true;
        }
        return false;

       // return true;
    }

    @Override
    public boolean parse(Packet packet, MappedByteBuffer mpb,int times) throws Exception {
        //version和ip数据包长度
        byte vl = mpb.get();
        if (vl >> 4 == 4) {
            packet.ipVersion = "ipv4";
        }else {
            packet.ipVersion = "ipv6";
        }

        packet.ipHeaderLen = (vl & 0xf) * 4;
        mpb.get(packet.typeOfService);
        mpb.get(packet.totalLength);
        mpb.get(packet.identification);

        byte flags = mpb.get();
        packet.flags = (byte) (flags >> 5);

        packet.fragmentOffset[0] = (byte) (((flags << 3) &0xff) >> 3);
        packet.fragmentOffset[1] = mpb.get();
        packet.timeToLive =  mpb.get();
        packet.ipv4TransProtocol = mpb.get();
        packet.ipv4TransProtocolCH = TransType.getProtocol((int)packet.ipv4TransProtocol);

        mpb.get(packet.ipv4HeaderChecksum);
        mpb.get(packet.ipv4SourceIpAddress);
        mpb.get(packet.ipv4DestinationIpAddress);

        if (packet.ipHeaderLen - 20 > 0) {
            packet.ipv4Options = new byte[packet.ipHeaderLen - 20];
            mpb.get(packet.ipv4Options);
        }
        packet.packetType = packet.packetType | 2;
        //todo 由于后续的不解析了，所以跳过,后续如果补齐了上层的协议，需要将这里放开，让上层的类进行解析。
        if (packet.ipv4TransProtocolCH != "UDP") {
            mpb.position(mpb.position() + packet.capLen - 14 - packet.ipHeaderLen);
            return false;
        }
        //此处返回true是当上层协议为UDP时返回的。
        return true;
    }
}
