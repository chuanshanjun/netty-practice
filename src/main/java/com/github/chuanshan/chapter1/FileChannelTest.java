package com.github.chuanshan.chapter1;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author:ChuanShanJun
 * @date:2020/11/14
 * @description:
 */
public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        // 从文件获取一个FileChannel
        FileChannel fileChannel =
                new RandomAccessFile(
                        "...",
                        "rw")
                        .getChannel();

        // 声明一个Byte类型的Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 将FileChannel中的数据读出到buffer中，-1表示读取完毕
        // buffer默认为写方法
        // read()方法是相对channel而言的，相对buffer就是写
        while (fileChannel.read(buffer) != -1) {
            // buffer切换为读
            buffer.flip();
            // buffer中是否有未读数据
            while (buffer.hasRemaining()) {
                // 未读数据长度
                int remaining = buffer.remaining();
                // 声明一个字节数组
                byte[] bytes = new byte[remaining];
                // 将buffer中数据读出到字节数组
                buffer.get(bytes);
                // 打印出来
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }
            // 清空buffer，为下一次写入数据做准备
            // clear()会将buffer再次切换为写模式
            buffer.clear();
        }
    }
}
