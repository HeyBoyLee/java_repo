package com.demo.nio.channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by heyboy on 2017/6/24.
 */
public class ChannelTest {
  public static void main(String[] args) {
    try {
      RandomAccessFile raf = new RandomAccessFile("/Users/apple/repo/java_repo/doc/1.0.txt", "rw");
      FileChannel fc = raf.getChannel();

      ByteBuffer bb = ByteBuffer.allocate(1024);

      int bytesRead = fc.read(bb);
      while (bytesRead != -1) {
        bb.flip();

        while (bb.hasRemaining()) {
          System.out.print((char) bb.get());
        }
        bb.clear();
        bytesRead = fc.read(bb);
      }
      System.out.print((char) bb.get());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
