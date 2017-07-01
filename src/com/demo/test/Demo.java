package com.demo.test;

/**
 * Created by heyboy on 2017/7/1.
 */
public class Demo {
  static int i = 0;

  public static void main(String[] args) {

    if (++i == 0 && add()) {
      System.out.println("xxx:" + i);
    }
    System.out.println(i);
  }

  public static boolean add() {
    System.out.println(i);
    i++;
    return true;
  }
}
