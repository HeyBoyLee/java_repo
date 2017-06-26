package com.demo.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by heyboy on 2017/6/26.
 */
public class MyCyclicBarrior {
  //徒步需要的时间: Shenzhen, Guangzhou, Shaoguan, Changsha, Wuhan
  private static int[] timeWalk = {5, 8, 15, 15, 10};
  private static int[] timeSelf = {1, 3, 4, 4, 5};
  private static int[] timeBus = {2, 4, 6, 6, 7};

  static String now() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    return sdf.format(new Date()) + ":";
  }

  public static void main(String[] args){
    CyclicBarrier barrier = new CyclicBarrier(3);
    ExecutorService exec = Executors.newFixedThreadPool(3);
    exec.submit(new Tour(timeWalk, barrier, "WalkTime"));
    exec.submit(new Tour(timeSelf, barrier, "SelfTime"));
    exec.submit(new Tour(timeBus, barrier, "BusTime"));
    exec.shutdown();
  }

  static class Tour implements Runnable {
    private int[] times;
    private CyclicBarrier cb;
    private String tourName;

    Tour(int[] t, CyclicBarrier cb, String name) {
      this.times = t;
      this.cb = cb;
      this.tourName = name;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(times[0] * 1000);
        System.out.println(now() + tourName + "reached shenzhen");
        cb.await();
        Thread.sleep(times[1] * 1000);
        System.out.println(now() + tourName + "reached guangzhou");
        cb.await();
        Thread.sleep(times[2] * 1000);
        System.out.println(now() + tourName + "reached shaoguan");
        cb.await();
        Thread.sleep(times[3] * 1000);
        System.out.println(now() + tourName + "reached changsha");
        cb.await();
        Thread.sleep(times[4] * 1000);
        System.out.println(now() + tourName + "reached wuhan");
        cb.await();
      } catch (InterruptedException e) {

      } catch (BrokenBarrierException e) {

      }
    }
  }
}
