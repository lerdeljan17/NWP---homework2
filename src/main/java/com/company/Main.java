package com.company;

import com.company.annotations.Autowired;
import com.company.exceptions.MissingAnnotationException;
import com.company.exceptions.MissingImplementation;
import com.company.exceptions.MissingQualifierAnnot;
import com.company.exceptions.MultipleQualifierException;

public class Main {


    public static void main(String[] strings) {

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//       @SuppressWarnings("rawtypes")
//        Class cl;
//        Field f;
//        try {
//            cl = Class.forName("com.company.annotations.Autowired");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            cl = Class.forName("com.company.Test");
//            Object o = cl.newInstance();
//            f = cl.getDeclaredField("a");
//            boolean a =  f.getAnnotation(Autowired.class).verbose();
//            if(a){
//                System.out.println("Initialized" + " " +  f.getType() + " " + f.getName() +" in " + cl.getName() + " " + LocalDateTime.now().format(formatter) + " " + o.hashCode());
//            }else {
//                System.out.println("nema");
//            }
//        } catch (ClassNotFoundException | NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }


        Test t = new Test();
        try {
            DI_Engine di_engine = new DI_Engine(t);
        } catch (MissingAnnotationException | MissingQualifierAnnot | MissingImplementation | MultipleQualifierException e) {
            e.printStackTrace();
        }
        //System.out.println(t.getPredmet().getS());


    }

}

class Test {
    @Autowired(verbose = true)
    private Predmet predmet;

    public Predmet getPredmet() {
        return predmet;
    }
}
