import java.io.*;
import java.util.Formatter;
import java.util.function.Consumer;

/**
 * @Classname TestRandomAccessFIle
 * @Description 测试
 * @Date 2022/5/9 19:00
 * @Created by Jieqiyue
 */
public class TestRandomAccessFIle {

    public static void main(String[] args) throws FileNotFoundException {
        Formatter fmtFile;
        fmtFile = new Formatter(new FileOutputStream("test.txt"));
        // write to the file.
        fmtFile.format("[时间]\t\t数据包长度\t\t源MAC地址\t\t目的MAC地址\t\tARP请求内容/ARP响应内容\n\n");

        fmtFile.close();

        if (fmtFile.ioException() != null) {

        }
    }
    /**
     * 每次固定从文件中读取指定数量的字节，并通过 Consumer 接口对象进行处理
     *
     * @param file 指定文件
     * @param bytesCount 每次读取的固定字节数，如果为 -1 表示读取全部的
     * @param consumer Consumer 接口对象
     */
    public static void fixRateReadBytes(File file, int bytesCount, Consumer<byte[]> consumer) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            long dataSize = randomAccessFile.length();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
