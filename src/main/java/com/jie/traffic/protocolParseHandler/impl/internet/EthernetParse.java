package com.jie.traffic.protocolParseHandler.impl.internet;



import com.jie.traffic.obj.Packet;
import com.jie.traffic.protocolParseHandler.ProtocolParse;

import java.nio.MappedByteBuffer;

/**
 * @Classname EthernetParse
 * @Description TODO
 * @Date 2022/5/16 15:28
 * @Created by Jieqiyue
 */
public class EthernetParse implements ProtocolParse {

    @Override
    public boolean canParse(MappedByteBuffer mpb,Packet packet) {
        return true;
    }

    @Override
    public boolean parse(Packet packet, MappedByteBuffer mpb,int times) {
        mpb.get(packet.ethDestMac);
        mpb.get(packet.ethSourceMac);
        mpb.get(packet.ethNetType);

        // 如果Ethernet中的第三个部分小于1500，那么就直接丢弃这个报文

        int temp = (0xff & packet.ethNetType[0]) * 256;
        temp += (0xff & packet.ethNetType[1]);

        if (temp < 1500) {
            mpb.position(mpb.position() + packet.capLen - 14);
            return false;
        }
        //todo 如果上层有解析了一些其它协议，此处需要放开。
        //这里分别是2048 ipv4 ， 2054 arp ，34525 ipv6
        if ( temp != 2048 && temp != 2054 && temp!=34525){
            mpb.position(mpb.position() + packet.capLen - 14);
            //System.out.println("error :"+ times);
            return false;
        }
//        if ( ((( (0 | packet.netType[0]) << 8)&0xff00) | (packet.netType[1] &0xff) ) < 1500) {
//            System.out.println();
//            mpb.position(mpb.position() + packet.capLen - 14);
//            return false;
//        }
        return true;
    }
}
