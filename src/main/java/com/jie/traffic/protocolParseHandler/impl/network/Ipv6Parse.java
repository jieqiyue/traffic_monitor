package com.jie.traffic.protocolParseHandler.impl.network;

import com.jie.traffic.obj.Packet;
import com.jie.traffic.protocolParseHandler.ProtocolParse;

import java.nio.MappedByteBuffer;

/**
 * @Classname Ipv6Parse
 * @Description TODO
 * @Date 2022/5/19 22:15
 * @Created by Jieqiyue
 */
public class Ipv6Parse implements ProtocolParse {
    @Override
    public boolean canParse(MappedByteBuffer mpb, Packet packet) {
        if (packet.ethNetType[0] == -122 && packet.ethNetType[1] ==-35) {
            return true;
        }
        return false;
    }

    @Override
    public boolean parse(Packet packet, MappedByteBuffer mpb,int times) throws Exception {
        // ipv6的就直接跳过了
        mpb.position(mpb.position() + packet.capLen - 14);
        return false;
    }
}
