package com.demo.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by heyboy on 2017/6/25.
 */
public class MyReentrantLock implements Runnable {
  private ReentrantLock lock;
  private Integer id;

  MyReentrantLock(Integer index, ReentrantLock l){
    lock = l;
    id = index;
  }

  @Override
  public void run() {
    try {
      lock.lock();
      System.out.println(id+":获得");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
      System.out.println(id + ":释放");
    }
  }

  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    ReentrantLock lock = new ReentrantLock();
    for(int i=0;i<10;i++){
      service.submit(new MyReentrantLock(i, lock));
    }
    service.shutdown();

  }
}
