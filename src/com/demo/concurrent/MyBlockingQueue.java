package com.demo.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by heyboy on 6/27/17.
 */
public class MyBlockingQueue implements Runnable {
  private int index;
  public static BlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);

  public MyBlockingQueue(int i){this.index = i;}

  @Override
  public void run() {
    try {
      queue.put(String.valueOf(this.index));
      System.out.println("{"+this.index+"} in queue");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args){
    ExecutorService service = Executors.newCachedThreadPool();
    for(int i=0;i<10;i++){
      service.submit(new MyBlockingQueue(i));
    }
    Thread thread = new Thread(new Runnable(){
      @Override
      public void run() {
        while(true){
          try {
            if(MyBlockingQueue.queue.isEmpty()) break;
            String str = MyBlockingQueue.queue.take();
            System.out.println(str+" has take!");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    service.submit(thread);
    service.shutdown();
  }
}