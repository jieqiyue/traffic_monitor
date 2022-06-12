package com.jie.traffic.utils;

import java.util.List;

/**
 * @Classname converseUtil
 * @Description TODO
 * @Date 2022/5/12 20:37
 * @Created by Jieqiyue
 */
public class ConverseUtil {

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToIntB(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后  小端法)的顺序。
     *
     * @param ary
     *            byte数组
     * @param offset
     *            从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToIntS(byte[] ary, int offset) throws Exception {
        if ((ary[offset + 3] & 0x80000000) > 0) {
            throw new Exception("input array is error "+ary[offset + 3]);
        }
        int value;
        value = (int) ((ary[offset]&0xFF)
                | ((ary[offset+1]<<8) & 0xFF00)
                | ((ary[offset+2]<<16)& 0xFF0000)
                | ((ary[offset+3]<<24) & 0x7F000000));
        return value;
    }

    public static String byteToProto(byte[] arr) {
        if (arr[1] == -35) {
            return "IPV6";
        }else if(arr[1] == 53) {
            return "RARP";
        }else if (arr[1] == 0) {
            return "IPV4";
        }else if (arr[1] == 6) {
            return "ARP";
        }
        return "UNKNOW";
    }

    public static int twoByteToInt(byte [] arr) {
        return (arr[0] & 0xff) * 256 + (arr[1] & 0xff);
    }


    public static String parseDnsName(List<Byte> list) {
        StringBuilder sb = new StringBuilder();
        for (int i =0 ;i < list.size();) {
            int temp = list.get(i);
            if (temp < 64) {
                sb.append(".");

                while (temp > 0) {
                    i++;
                    sb.append((char)list.get(i).byteValue());
                    temp--;
                }
                i++;
            }
        }
        String string = sb.toString();
        return string.substring(1);
    }
    public static String parseDnsName(byte[] list) {
        StringBuilder sb = new StringBuilder();
        for (int i =0 ;i < list.length;) {
            int temp = list[i];
            if (temp < 64) {
                sb.append(".");

                while (temp > 0) {
                    i++;
                    sb.append((char)list[i]);
                    temp--;
                }
                i++;
            }
        }
        String string = sb.toString();
        return string.substring(1);
    }
    public static String ipToString(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf((arr[0]) & 0xff) + ".");
        sb.append(String.valueOf((arr[1]) & 0xff) + ".");
        sb.append(String.valueOf((arr[2]) & 0xff) + ".");
        sb.append(String.valueOf((arr[3]) & 0xff));
        return sb.toString();
    }
    public static String ip4ToString(byte[]arr) {
        StringBuilder sb = new StringBuilder();
        return sb.
                append(arr[0]&0xff).append(".").
                append(arr[1]&0xff).append(".").
                append(arr[2]&0xff).append(".").
                append(arr[3]&0xff).
                toString();
    }
}
