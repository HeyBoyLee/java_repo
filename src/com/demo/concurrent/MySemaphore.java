package com.demo.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by heyboy on 2017/6/25.
 */
public class MySemaphore implements Runnable{

  private Semaphore position;
  private Integer id;

  MySemaphore(Semaphore s, Integer index){
    position = s;
    id = index;
  }

  @Override
  public void run() {
    try {
      position.acquire();
      System.out.println("顾客["+this.id+"]获得坑位");
      Thread.sleep((int)(Math.random()*1000));
      System.out.println("顾客["+this.id+"]使用完毕");
      position.release();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args){

    Semaphore p = new Semaphore(2);
    ExecutorService service = Executors.newCachedThreadPool();
    for(int i=0;i<10;i++){

      service.submit(new MySemaphore(p , i));
    }
    service.shutdown();
    System.out.println("service 关闭");
    p.acquireUninterruptibly();

    System.out.println("使用完毕，需要清扫了");
    p.release(2);
  }
}
