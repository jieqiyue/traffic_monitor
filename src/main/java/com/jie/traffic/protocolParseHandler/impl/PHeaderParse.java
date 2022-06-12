package com.jie.traffic.protocolParseHandler.impl;



import com.jie.traffic.obj.Packet;
import com.jie.traffic.protocolParseHandler.ProtocolParse;
import com.jie.traffic.utils.ConverseUtil;


import java.nio.MappedByteBuffer;

/**
 * @Classname PHeaderParse
 * @Description TODO
 * @Date 2022/5/16 15:45
 * @Created by Jieqiyue
 */
public class PHeaderParse implements ProtocolParse {
    @Override
    public boolean canParse(MappedByteBuffer mpb,Packet packet) {
        return true;
    }

    @Override
    public boolean parse(Packet packet, MappedByteBuffer mpb,int times) throws Exception {
        byte[] buffer = new byte[4];
        mpb.get(buffer);
        packet.timeStampSec = ConverseUtil.bytesToIntS(buffer, 0);
        mpb.get(buffer);
        packet.timeStampMil = ConverseUtil.bytesToIntS(buffer,0);
        mpb.get(buffer);
        packet.capLen = ConverseUtil.bytesToIntS(buffer,0);
        mpb.get(buffer);
        packet.offLen = ConverseUtil.bytesToIntS(buffer,0);
        return true;
    }
}
