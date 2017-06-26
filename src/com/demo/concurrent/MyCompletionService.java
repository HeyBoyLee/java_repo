package com.demo.concurrent;

import java.util.concurrent.*;

/**
 * Created by heyboy on 2017/6/26.
 */
public class MyCompletionService implements Callable<String> {
  private int id;
  MyCompletionService(int i){
    id = i;
  }
  @Override
  public String call() throws Exception {
    Integer time = (int)(Math.random()*1000);
    System.out.println("id:"+id+" start");
    Thread.sleep(time);
    System.out.println("id:"+id+" end");
    return id+":"+id+", time:"+time;
  }

  public static void main(String[] args){
    try {
      ExecutorService service = Executors.newCachedThreadPool();
      CompletionService<String> completion = new ExecutorCompletionService<String>(service);
      for(int i=0;i<10;i++){
        completion.submit(new MyCompletionService(i));
      }
      for(int i=0;i<10;i++){
        System.out.println(completion.take().get());
      }
      service.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }
}
