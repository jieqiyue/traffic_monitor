package com.jie.traffic.protocolParseHandler.impl.transport;

import com.jie.traffic.obj.Packet;
import com.jie.traffic.protocolParseHandler.ProtocolParse;

import java.nio.MappedByteBuffer;

/**
 * @Classname TcpParse
 * @Description TODO
 * @Date 2022/5/23 17:23
 * @Created by Jieqiyue
 */
public class TcpParse implements ProtocolParse {
    @Override
    public boolean canParse(MappedByteBuffer mpb, Packet packet) {
        return false;
    }

    @Override
    public boolean parse(Packet packet, MappedByteBuffer mpb, int num) throws Exception {
        return false;
    }
}
