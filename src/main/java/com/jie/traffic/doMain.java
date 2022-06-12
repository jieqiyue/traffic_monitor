package com.jie.traffic;

import com.jie.traffic.obj.Packet;
import com.jie.traffic.saveToFile.SavePacketToDisk;
import com.jie.traffic.utils.ConverseUtil;
import com.jie.traffic.utils.TimeUtil;

import java.io.IOException;
import java.util.Scanner;

/**
 * @Classname doMain
 * @Description TODO
 * @Date 2022/5/16 16:09
 * @Created by Jieqiyue
 */
public class doMain {
    public static String URI = "D:\\Desktop\\ddd\\traffic_monitor\\src\\main\\resources\\day8.pcap";
    //各个包解析的路径定义在SavePacketToDisk类中

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("input the pcap file path:");
        Scanner sc = new Scanner(System.in);
        URI = sc.nextLine();
        int num = 1;

        ParsePcapFile parsePcapFile = new ParsePcapFile(URI);
        long beginTime = System.currentTimeMillis();
        while (parsePcapFile.hasRemaining()) {

            Packet packet = parsePcapFile.parsePcapBody(num);

            SavePacketToDisk.savePacketToDisk(packet,num);
            packet.packetType = 0;
            num++;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("sum time: "+ (endTime - beginTime));
        System.out.println("total " + num  + " packages！");
        parsePcapFile.close();
        SavePacketToDisk.close();
    }

}
