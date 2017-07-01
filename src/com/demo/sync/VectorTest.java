package com.demo.sync;

import java.util.Vector;//线程安全

/**
 * Created by heyboy on 6/23/17.
 * 运行报错：　java.lang.ArrayIndexOutOfBoundsException:
 * synchronized 互斥同步
 * vector的线程安全是指读操作或写操作是线程安全的，
 * 但在多线程里分别读，写的话是不能保证同步的
 */
public class VectorTest {
  private static Vector<Integer> vector = new Vector<Integer>();

  public static void main(String[] args) {
    while (true) {
      for (int i = 0; i < 10; i++) {
        vector.add(i);
      }

      Thread removeThread = new Thread(new Runnable() {
        @Override
        public void run(){
          synchronized (vector) {  // + synchronized 后可以保证正常输出
            try {
              throw new Exception("my excpetion");// 同步块中抛异常时，会释放锁
//              for (int i = 0; i < vector.size(); i++) {
//                vector.remove(i);
//              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      });

      Thread printThread = new Thread(new Runnable() {
        @Override
        public void run() {
          synchronized (vector) {
            for (int i = 0; i < vector.size(); i++) {
              System.out.println(vector.get(i));
            }
          }
        }
      });

      removeThread.start();
      printThread.start();

      while (Thread.activeCount() > 20) ;//防止过多线程，内存溢出
    }
  }
}
