package com.demo.loader;

/**
 * Created by heyboy on 2017/6/24.
 */
import java.lang.reflect.Method;

public class ClassIdentity {

  public static void main(String[] args) {
    new ClassIdentity().testClassIdentity();
  }

  public void testClassIdentity() {
    String classDataRootPath = "/Users/apple/repo/java_repo/classpath";
    FileSystemClassLoader fscl1 = new FileSystemClassLoader(classDataRootPath);
    FileSystemClassLoader fscl2 = new FileSystemClassLoader(classDataRootPath);
    String className = "com.demo.loader.Sample";
    try {
      Class<?> class1 = fscl1.loadClass(className);  // 加载Sample类
      Object obj1 = class1.newInstance();  // 创建对象
      Class<?> class2 = fscl2.loadClass(className);
      Object obj2 = class2.newInstance();
      Method setSampleMethod = class1.getMethod("setSample", java.lang.Object.class);
      setSampleMethod.invoke(obj1, obj2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
