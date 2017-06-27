package com.demo.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by heyboy on 6/27/17.
 */
public class MyCountDownLatch {
  public static void main(String[] args) throws InterruptedException{
    final CountDownLatch begin = new CountDownLatch(1);
    final CountDownLatch end = new CountDownLatch(1);

    final ExecutorService exec = Executors.newFixedThreadPool(10);

    for(int i=0;i<10;i++){
      final int No = i;
      Runnable run = new Runnable() {
        @Override
        public void run() {
          try {
            begin.await();
            //Thread.sleep((long)Math.random()*1000);
            System.out.println("No."+No+" arrived");
          } catch (InterruptedException e) {
            e.printStackTrace();
          } finally {
            end.countDown();
          }
        }
      };
      exec.submit(run);
    }
    System.out.println("Game Start");
    begin.countDown();
    end.await();
    System.out.println("Game Over");
    exec.shutdown();
  }
}
