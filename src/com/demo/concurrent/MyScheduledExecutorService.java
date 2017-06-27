package com.demo.concurrent;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by heyboy on 6/27/17.
 */
public class MyScheduledExecutorService {

  public static void main(String[] args){
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    final Runnable beeper = new Runnable() {
      int count=0;
      @Override
      public void run() {
        System.out.println(new Date()+" beeper "+(++count));
      }
    };

    final ScheduledFuture handle1 = scheduler.scheduleAtFixedRate(beeper, 1, 2, TimeUnit.SECONDS);
    final ScheduledFuture handle2 = scheduler.scheduleAtFixedRate(beeper, 2, 5, TimeUnit.SECONDS);

    scheduler.schedule(new Runnable(){
      @Override
      public void run() {
        handle1.cancel(true);
        handle2.cancel(true);
        scheduler.shutdown();
      }
    } , 30, TimeUnit.SECONDS);
  }
}
