package com.demo.nio.selector;

import sun.rmi.server.InactiveGroupException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by heyboy on 2017/6/24.
 */
public class Client {

  private ByteBuffer buf = ByteBuffer.allocate(512);
  private String host;
  private Integer port;

  public Client(String host, Integer port) {
    this.host = host;
    this.port = port;
  }

  private void query() throws IOException{
    SocketChannel sc = null;
    try {
      byte[] bytes = new byte[512];
      System.in.read(bytes);
      sc = SocketChannel.open();
      sc.connect(new InetSocketAddress(InetAddress.getByName(host), port));
      buf.clear();
      buf.put(bytes);

      buf.flip();
      sc.write(buf);
      buf.clear();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (sc != null)
        sc.close();
    }
  }

  public static void main(String[] args) {
    try {
      new Client("localhost", 8099).query();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
