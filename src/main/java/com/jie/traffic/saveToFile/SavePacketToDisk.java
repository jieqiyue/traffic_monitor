package com.jie.traffic.saveToFile;

import com.jie.traffic.obj.Packet;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Classname SavePacketToDisk
 * @Description TODO
 * @Date 2022/5/28 11:55
 * @Created by Jieqiyue
 */
public class SavePacketToDisk {
    public static final String arpFilePath = "arp.txt";
    public static final String ipFilePath = "ip.txt";
    public static final String udpFilePath = "udp.txt";
    public static final String dnsFilePath = "dns.txt";

    public static ArpSave arpSave;
    public static IpSave ipSave;
    public static UdpSave udpSave;
    public static DnsSave dnsSave;
    static {
        try {
            arpSave = new ArpSave(arpFilePath);
            ipSave = new IpSave(ipFilePath);
            udpSave = new UdpSave(udpFilePath);
            dnsSave = new DnsSave(dnsFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void savePacketToDisk(Packet packet,int num) throws IOException {
        if ((packet.packetType & 4) == 4) {
            arpSave.save(packet,num);
        }
        if ((packet.packetType & 2) == 2) {
            ipSave.save(packet,num);
        }
        if ((packet.packetType & 1) == 1) {
            udpSave.save(packet,num);
        }

        if ((packet.packetType & 8) == 8) {
            dnsSave.save(packet,num);
        }
    }

    public static void close() {
        arpSave.close();
        ipSave.close();
        udpSave.close();
        dnsSave.close();
    }
}
