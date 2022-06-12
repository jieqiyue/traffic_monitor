package com.jie.traffic.protocolParseHandler.impl.application;

import com.jie.traffic.obj.DnsAnswer;
import com.jie.traffic.obj.DnsQuestion;
import com.jie.traffic.obj.Packet;
import com.jie.traffic.protocolParseHandler.ProtocolParse;
import com.jie.traffic.utils.ConverseUtil;

import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DnsParse
 * @Description 解析dns协议包
 * @Date 2022/5/26 15:43
 * @Created by Jieqiyue
 */
public class DnsParse implements ProtocolParse {
    //todo 此处直接使用端口号来进行应用层协议的判断。可能会出现差错。
    @Override
    public boolean canParse(MappedByteBuffer mpb, Packet packet) {
        if (packet.udpSourcePort[1] == 53 || packet.udpDestPort[1] == 53) {
            return true;
        }
        return false;
    }

    @Override
    public boolean parse(Packet packet, MappedByteBuffer mpb, int num) throws Exception {
        //先保存一下dns报文头部的position，后面用来计算偏移，并且在最后用来跳过多余字段
        int beginPosition = mpb.position();

        //定义一个Map，后续因为有可能是通过偏移指针来引用的,key作为偏移量。并且这个map在这个方法结束时就会被清理了，对于下一次解析没有影响。
        Map<Integer,String> map = new HashMap<>();

        //先解析dns报文头部的固定的12个字节
        byte [] buffer = new byte[2];
        mpb.get(packet.dnsTransactionId);
        mpb.get(packet.dnsFlags);
        if ((packet.dnsFlags[0] & 0x80) == 128) {
            packet.dnsReqRespType = "response";
        }else {
            packet.dnsReqRespType = "request";
        }
        mpb.get(buffer);
        packet.dnsQuestionNum = ConverseUtil.twoByteToInt(buffer);
        mpb.get(buffer);
        packet.dnsAnswerRRsNum = ConverseUtil.twoByteToInt(buffer);
        mpb.get(buffer);
        packet.dnsAuthorityRRsNum = ConverseUtil.twoByteToInt(buffer);
        mpb.get(buffer);
        packet.dnsAdditionalNum = ConverseUtil.twoByteToInt(buffer);
        //根据上面得到的请求和相应的报文长度创建对应长度的请求或者响应数组
        packet.dnsQues = new DnsQuestion[packet.dnsQuestionNum];
        packet.dnsAns = new DnsAnswer[packet.dnsAnswerRRsNum];

        // 解析请求或者是响应部分
        //解析请求部分
        int beginIndex;
        for (int i = 0;i < packet.dnsQuestionNum;i++) {
            DnsQuestion question = new DnsQuestion();
            packet.dnsQues[i] = question;

//            byte temp;
//            beginIndex = mpb.position();
//            while ((temp = mpb.get()) != 0) {
//                //此处并没有把最后的0加入到List中
//                packet.dnsQues[i].qNameArr.add(temp);
//            }
            beginIndex = mpb.position();
            packet.dnsQues[i].qName = readName(mpb, beginPosition, beginIndex, map);

            //转化完了之后将它加入到map中，以便后续进行偏移引用。
            //map.put(beginIndex -  beginPosition,packet.dnsQues[i].qName);

            mpb.get(packet.dnsQues[i].qType);
            mpb.get(packet.dnsQues[i].qClass);
        }
        // 解析响应部分
        for (int i =0 ;i < packet.dnsAnswerRRsNum;i++) {
            DnsAnswer answer = new DnsAnswer();
            packet.dnsAns[i] = answer;
//           // 解析响应中的name字段
//            byte temp = mpb.get();
//            if ((temp & 0xc0) == 192) {
//                //如果是最高两位是11的话，那么表示这个一个指针偏移
//                packet.dnsAns[i].rNameArr.add(temp);
//                int offset = (int)mpb.get();
//                packet.dnsAns[i].rName = map.get(offset);
//            }else {
//                //是一个完整的字符串表示，就要用刚刚读取的temp转化为第一个长度。
//                while (temp != 0) {
//                    packet.dnsAns[i].rNameArr.add(temp);
//                    temp = mpb.get();
//                }
//                packet.dnsAns[i].rName = ConverseUtil.parseDnsName(packet.dnsAns[i].rNameArr);
//            }
            beginIndex = mpb.position();
            packet.dnsAns[i].rName = readName(mpb, beginPosition, beginIndex, map);

            //解析响应中的其它字段
            mpb.get(packet.dnsAns[i].aType);
            mpb.get(packet.dnsAns[i].aClass);
            mpb.get(packet.dnsAns[i].aTimeToLive);
            mpb.get(buffer);
            packet.dnsAns[i].aDataLength = ConverseUtil.twoByteToInt(buffer);
            packet.dnsAns[i].aData = new byte[packet.dnsAns[i].aDataLength];

//            //解析响应中的data段
//            beginIndex = mpb.position();
//            for (int j = 0;j < packet.dnsAns[i].aDataLength;j++) {
//                packet.dnsAns[i].aData[j] = mpb.get();
//            }
            if (packet.dnsAns[i].aType[1] !=28) {
                beginIndex = mpb.position();
                //由于此处可能读取的是ip地址
                readIpOrName(packet.dnsAns[i],mpb, beginPosition, beginIndex, map);
            }else{
                mpb.position(mpb.position() + packet.dnsAns[i].aDataLength);
            }

            //packet.dnsAns[i].aDataString = readName(mpb, beginPosition, beginIndex, map);
           // map.put(beginIndex - beginPosition ,packet.dnsAns[i].aDataString);
        }
        packet.packetType = packet.packetType | 8;
        //跳过结尾可能未解析的部分
        mpb.position(mpb.position() + packet.capLen - 14 - packet.ipHeaderLen - 8 - (mpb.position() - beginPosition) );
        return false;
    }

    public void readIpOrName(DnsAnswer dnsAnswer,MappedByteBuffer mpb, int beginPosition,int beginIndex,Map<Integer,String> map) {
        //如果是读取ip地址的话
        if (dnsAnswer.aType[1] == 1) {
            mpb.get(dnsAnswer.aData);
            dnsAnswer.aDataString = ConverseUtil.ip4ToString(dnsAnswer.aData);
            return ;
        }
        //如果是CNAME之类的查询的话
        dnsAnswer.aDataString = readName(mpb,beginPosition,beginIndex,map);
    }
    public String readName(MappedByteBuffer mpb, int beginPosition,int beginIndex,Map<Integer,String> map) {
        //如果是要解析ip地址的话

        StringBuilder sb = new StringBuilder();
        boolean isFirst = false;
        //解析name，当读取到0字节或者是读取到指针偏移就退出循环
        while (true) {
            byte temp = mpb.get();
            //如果是0，代表到了结尾
            if (temp == 0) {
                break;
            }
            //判断是指针偏移还是普通的长度
            if ((temp & 0xc0) == 192) {
                if (mpb.position() == beginIndex + 1) {
                    isFirst = true;
                }
                //如果是最高两位是11的话，那么表示这个一个指针偏移,那么就计算偏移量,并且如果碰到了指针就直接退出循环
                int offset = (temp & 0x3f) * 256 + (mpb.get() & 0xff);
                sb.append(".").append(map.get(offset));
                break;
            }else{
                sb.append(".");
                //如果是读取长度的话，就读取相应的长度
                int length = temp & 0xff;
                while (length > 0) {
                    sb.append((char)mpb.get());
                    length--;
                }
            }
        }
        String ret = sb.toString().substring(1);
        // 将刚刚解析得到的字符串加入到map中
        if (!isFirst) {
            map.put(beginIndex - beginPosition,ret);
            for (int i = 0;i < ret.length();i++) {
                if (ret.charAt(i) == '.') {
                    map.put(beginIndex - beginPosition + 1,ret.substring(i + 1));
                }
                beginIndex++;
            }
        }
        return ret;
    }
}
