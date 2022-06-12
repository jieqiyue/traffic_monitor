package com.jie.traffic.protocolParseHandler;

import com.jie.traffic.obj.Packet;

import java.nio.MappedByteBuffer;

/**
 * @Classname ProtocolParse
 * @Description TODO
 * @Date 2022/5/16 15:24
 * @Created by Jieqiyue
 */
public interface ProtocolParse {
    /**
     * 由各个协议实现类判断是否能够解析
     * @return
     */
    boolean canParse(MappedByteBuffer mpb,Packet packet);

    /**
     *
     * @param packet 将解析的结果丢入这个数据包中
     * @param mpb 文件指针
     */
    boolean parse(Packet packet, MappedByteBuffer mpb,int num) throws Exception;
}
