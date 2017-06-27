package com.demo.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * Created by heyboy on 2017/6/24.
 */
public class Server implements Runnable {
  private Integer port1 = 8099;
  private Integer port2 = 9099;

  private ServerSocketChannel serverChannel1;
  private ServerSocketChannel serverChannel2;

  private SocketChannel clientChannel1;
  private SocketChannel clientChannel2;

  private Selector selector;

  private ByteBuffer buf = ByteBuffer.allocate(512);

  public Server() {
    init();
  }

  private void init() {
    try {
      selector = SelectorProvider.provider().openSelector();

      serverChannel1 = ServerSocketChannel.open();
      serverChannel1.configureBlocking(false);
      serverChannel1.bind(new InetSocketAddress("localhost", port1));
      serverChannel1.register(selector, SelectionKey.OP_ACCEPT);

      serverChannel2 = ServerSocketChannel.open();
      serverChannel2.configureBlocking(false);
      serverChannel2.bind(new InetSocketAddress("localhost", port2));
      serverChannel2.register(selector, SelectionKey.OP_ACCEPT);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void accept(SelectionKey key) {
    try {
      ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
      if (ssc.equals(serverChannel1)) {
        clientChannel1 = ssc.accept();
        clientChannel1.configureBlocking(false);
        clientChannel1.register(selector, SelectionKey.OP_READ);
      } else if (ssc.equals(serverChannel2)) {
        clientChannel2 = serverChannel2.accept();
        clientChannel2.configureBlocking(false);
        clientChannel2.register(selector, SelectionKey.OP_READ);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void read(SelectionKey key) {
    try {
      buf.clear();
      SocketChannel channel = (SocketChannel) key.channel();
      int count = channel.read(buf);
      if (count == -1) {
        channel.close();
        key.cancel();
        return;
      }

      String info = new String(buf.array()).trim();
      if (channel.equals(clientChannel1)) {
        System.out.println("clinet1 message:" + info);
      } else if (channel.equals(clientChannel2)) {
        System.out.println("client2 message:" + info);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        System.out.println("running...");
        selector.select();

        Iterator keys = selector.selectedKeys().iterator();
        while (keys.hasNext()) {
          System.out.println("running2....");

          SelectionKey key = (SelectionKey) keys.next();
          keys.remove();

          if (!key.isValid()) continue;

          if (key.isAcceptable()) {
            accept(key);
          } else if (key.isReadable()) {
            read((key));
          }
        }

      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public static void main(String[] args) {
    try {
      Thread t = new Thread(new Server());
      t.start();
      t.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
