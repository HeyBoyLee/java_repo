package com.demo.loader;

import sun.misc.Launcher;

/**
 * Created by heyboy on 2017/6/23.
 */


public class LoaderTest {
  public static void main(String[] args) {
    System.out.println(ClassLoader.getSystemClassLoader());//系统类加载器 AppClassLoader
    System.out.println(ClassLoader.getSystemClassLoader().getParent());//标准扩展类加载器 ExtClassLoader
    System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());//启动类加载器 bootstrap 无法获取
//    Launcher.AppClassLoader.getSystemClassLoader();
    String[] path = System.getProperty("java.class.path").split(":");
    for (int i = 0; i < path.length; i++) {
      System.out.println(path[i]);
    }
    System.out.println(System.getProperty("java.ext.dirs"));
    try {
      Class<?> type = Class.forName("com.demo.loader.ClassLoaderTest");
      System.out.println(type.getClassLoader());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
