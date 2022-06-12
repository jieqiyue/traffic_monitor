package com.jie.traffic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname mmapFile
 * @Description 使用mmap的方式读取一个文件
 * @Date 2022/5/12 19:13
 * @Created by Jieqiyue
 */
public class MmapFile {
    public FileChannel fileChannel;

    /**
     * 对一个文件进行内存映射。
     *
     * @param file 要映射的文件。
     * @param channelMode 以什么模式打开通道。
     * @param mmapMode 以什么模式进行内存映射。
     * @return
     * @throws FileNotFoundException
     */
    public MappedByteBuffer mappingFile(File file, String channelMode, FileChannel.MapMode mmapMode, long start, long len) throws IOException {
        if (start < 0 || len < 0 || start > file.length() - len) {
            throw new IllegalArgumentException("read invidied file position,check ");
        }

        fileChannel = new RandomAccessFile(file, channelMode).getChannel();
        MappedByteBuffer map = fileChannel.map(mmapMode, start, len);
        return map;
    }

    /**
     * 对channel进行关闭。
     *
     * @throws IOException
     */
    public void closeChannel() throws IOException {
        fileChannel.close();
    }

    public MappedByteBuffer mappingFile(String filePath, String channelMode, FileChannel.MapMode mmapMode, long start, long len) throws Exception {
        return mappingFile(new File(filePath), channelMode, mmapMode, start, len);
    }

    public MappedByteBuffer mappingFile(String filePath, String channelMode, FileChannel.MapMode mmapMode) throws Exception {
        File file = new File(filePath);
        return mappingFile(file, channelMode, mmapMode, 0,  file.length());
    }
}