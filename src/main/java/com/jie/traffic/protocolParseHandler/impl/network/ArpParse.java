package com.jie.traffic.protocolParseHandler.impl.network;

import com.jie.traffic.obj.Packet;
import com.jie.traffic.protocolParseHandler.ProtocolParse;

import java.nio.MappedByteBuffer;

/**
 * @Classname ArpParse
 * @Description TODO
 * @Date 2022/5/18 19:25
 * @Created by Jieqiyue
 */
public class ArpParse implements ProtocolParse {

    @Override
    public boolean canParse(MappedByteBuffer mpb, Packet packet) {
        if (packet.ethNetType[0] == 8 && packet.ethNetType[1] == 6)
            return true;
        return false;
    }

    @Override
    public boolean parse(Packet packet, MappedByteBuffer mpb,int times) throws Exception {
        mpb.get(packet.arpHardWareType);
        mpb.get(packet.arpUpProtocolType);
        packet.arpMacAddressLen = mpb.get();
        packet.arpIpProtocolLen = mpb.get();
        mpb.get(packet.arpType);
        mpb.get(packet.arpSourceMacAddress);
        mpb.get(packet.arpSourceIpAddress);
        mpb.get(packet.arpDesMacAddress);
        mpb.get(packet.arpDesIpAddress);

        // apr协议是没有上层协议的，已经到了协议栈的顶部了，这里的跳过是因为有些arp包是不足64字节的，所以会在结尾补一些0，所以
        // 当这个包还有剩余的时候就需要跳过了。
        if (packet.capLen - 28 - 14 > 0) {
            mpb.position(mpb.position() + packet.capLen - 42);
        }
        packet.packetType = packet.packetType | 4;
        return false;
    }
}
