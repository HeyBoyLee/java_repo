package com.demo.volatileTest;

/**
 * Created by heyboy on 6/26/17.
 */
public class Counter {

  private volatile static int count =0;

  public static void inc(){
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    count ++;
  }

  public static void main(String[] args){
    for(int i=0;i<1000;i++){
      new Thread(new Runnable() {
        @Override
        public void run() {
          Counter.inc();
        }
      }).start();
    }
    System.out.println("count:"+count);
  }
}
