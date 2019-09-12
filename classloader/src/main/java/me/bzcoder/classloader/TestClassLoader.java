package me.bzcoder.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author : BaoZhou
 * @date : 2019/9/11 8:59
 */
public class TestClassLoader extends ClassLoader {
  private String name;

  public TestClassLoader(ClassLoader parent, String name) {
    super(parent);
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public Class<?> findClass(String name) {

    InputStream is = null;
    byte[] data = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      is = new FileInputStream(new File("d:/Test.class"));
      int c = 0;
      while (-1 != (c = is.read())) {
        baos.write(c);
      }
      data = baos.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
        baos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return this.defineClass(name, data, 0, data.length);
  }

  public static void main(String[] args) {
    TestClassLoader loader = new TestClassLoader(
        TestClassLoader.class.getClassLoader(), "TestLoader");
    Class clazz;
    try {
      clazz = loader.loadClass("test.classloader.Test");
      Object object = clazz.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

}
