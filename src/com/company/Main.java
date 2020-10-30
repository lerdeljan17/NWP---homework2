package com.company;

import com.company.annotations.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Main {

    public static void main(String[] strings){
        @SuppressWarnings("rawtypes")
        Class cl;
        Field f;

        try {
            cl = Class.forName("com.company.Test");
            f = cl.getDeclaredField("a");
            boolean a =  f.getAnnotation(Autowired.class).verbose();
            if(a){
                System.out.println("autowired");
            }else {
                System.out.println("nema");
            }
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }


}

}
class Test{
    @Autowired(verbose = true)
    int a;
}
