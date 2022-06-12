package com.jie.traffic;


import com.jie.traffic.obj.Packet;
import com.jie.traffic.obj.PacpConstant;
import com.jie.traffic.protocolParseHandler.ProtocolParse;
import com.jie.traffic.protocolParseHandler.impl.*;
import com.jie.traffic.protocolParseHandler.impl.application.DnsParse;
import com.jie.traffic.protocolParseHandler.impl.internet.EthernetParse;
import com.jie.traffic.protocolParseHandler.impl.network.Ipv4Parse;
import com.jie.traffic.protocolParseHandler.impl.network.Ipv6Parse;
import com.jie.traffic.protocolParseHandler.impl.network.ArpParse;
import com.jie.traffic.protocolParseHandler.impl.transport.UdpParse;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname parsePacpFile
 * @Description 解析pcap文件
 * @Date 2022/5/12 18:59
 * @Created by Jieqiyue
 */
public class ParsePcapFile {
    private final String URI;
    private File file;
    private MappedByteBuffer mpb = null;
    private MmapFile mmapfile = null;

    public static Packet packet = new Packet();
    /**
     *  map中存放解析器
     */
    public static Map<Integer, List<ProtocolParse>> parseHandle = new ConcurrentHashMap<>();
    static {
        parseHandle.put(0,Arrays.asList(new PHeaderParse()));
        parseHandle.put(1, Arrays.asList(new EthernetParse()));
        parseHandle.put(2,Arrays.asList(new Ipv4Parse(),new Ipv6Parse(),new ArpParse()));
        parseHandle.put(3, Arrays.asList(new UdpParse()));
        parseHandle.put(4, Arrays.asList(new DnsParse()));
    }

    /*
        定义pacp文件的Global Header中的字段。
     */

    private final byte[] magic = new byte[4];
    private final byte[] majorVersion = new byte[2];
    private final byte[] minorVersion = new byte[2];
    private final byte[] timeZone = new byte[4];
    private final byte[] signFigs = new byte[4];
    private final byte[] snapLen = new byte[4];
    private final byte[] linkType = new byte[4];

    public ParsePcapFile(String URI) throws IOException {
        if (URI == null) {
            throw new NullPointerException();
        }
        this.URI = URI;
        init();
    }

    /**
     * 在init方法中对pacp文件的文件头进行处理。
     */
    private void init() throws IOException {
        file = new File(URI);

        if (file.length() < PacpConstant.PCAPHEADERLEN) {
            throw new RuntimeException("file length is wrong");
        }

        mmapfile = new MmapFile();
        mpb = mmapfile.mappingFile(file, "rw", FileChannel.MapMode.READ_WRITE,0,file.length());

        mpb.get(magic);
        mpb.get(majorVersion);
        mpb.get(minorVersion);
        mpb.get(timeZone);
        mpb.get(signFigs);
        mpb.get(snapLen);
        mpb.get(linkType);
    }

    public Packet parsePcapBody(int num) throws EOFException {
        if (!hasRemaining()) {
            throw new EOFException("pcap file has read over.");
        }
        // 不再每次都创建一个新的Packet对象，而是在一开始创建一个。进行对象的复用。
        //Packet packet = new Packet();

        try {
            boolean parseRet = true;
            for (int i = 0;i < parseHandle.size();i++){
                for (ProtocolParse toParse:parseHandle.get(i)) {
                    if (toParse.canParse(mpb,packet)) {
                        parseRet = toParse.parse(packet, mpb,num);

                        break;
                    }
                }
                if (!parseRet) {
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        //由于没有完全解析数据包，这里还需要跳过一些字段
       // mpb.position(mpb.position() + packet.capLen - 14 - packet.ipHeaderLen);
        return packet;
    }

    /**
     * 是否将pacp文件数据包解析完成。
     * @return
     */
    public boolean hasRemaining() {
        return mpb.position() < file.length();
    }

    public void close() throws IOException {
        mmapfile.closeChannel();
    }
}
