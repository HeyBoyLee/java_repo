package com.demo.loader;

/**
 * Created by heyboy on 2017/6/24.
 */
public class Sample {

  private Sample instance;

  public void setSample(Object instance) {
    System.out.println(instance.toString());
    //this.instance = (Sample) instance;
  }
}
