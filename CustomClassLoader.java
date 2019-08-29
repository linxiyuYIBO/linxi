package com.linxi;

import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends ClassLoader{
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //双亲委派模型
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        //暂时指定Controller
        //"D:\\httpd\\webapp"+ name +".class";
        String filename = "D:\\httpd\\webapp\\" + name + ".class";
        byte[] buf = new byte[8192];
        int len;
        try {
            len = new FileInputStream(filename).read(buf);
        } catch (IOException e) {
            throw new ClassNotFoundException("没找到相应的文件",e);
        }
        return defineClass(name, buf,0, len);
    }
}
