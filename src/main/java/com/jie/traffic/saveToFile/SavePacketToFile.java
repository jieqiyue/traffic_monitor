package com.jie.traffic.saveToFile;

import com.jie.traffic.obj.Packet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Formatter;

/**
 * @Classname SavePacketToFile
 * @Description TODO
 * @Date 2022/5/28 11:38
 * @Created by Jieqiyue
 */
public abstract class SavePacketToFile {
    public String filePath;
    public Formatter fmtFile;

    public SavePacketToFile(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        fmtFile = new Formatter(new FileOutputStream(filePath));

    }

    public abstract void save(Packet packet, int num) throws IOException;

    public void close() {
        fmtFile.close();
    }
}
