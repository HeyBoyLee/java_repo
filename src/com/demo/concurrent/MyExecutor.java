package com.demo.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by heyboy on 2017/6/24.
 */
public class MyExecutor implements Runnable {
  private int index;
  public MyExecutor(int i){
    index =i;
  }
  @Override
  public void run() {
    try {
      System.out.println("["+this.index+"] start....");
      Thread.sleep((int)(Math.random()*100000));
      System.out.println("["+this.index+"] end.");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args){
    try {
      ExecutorService service = Executors.newFixedThreadPool(4);
      for(int i=0;i<10;i++){
        service.execute(new MyExecutor(i));
      }
      service.shutdown();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
