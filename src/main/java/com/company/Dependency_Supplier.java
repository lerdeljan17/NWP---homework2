package com.company;

import com.company.annotations.Bean;
import com.company.annotations.Component;
import com.company.annotations.Qualifier;
import com.company.annotations.Service;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Dependency_Supplier {
    private ArrayList<String> classNames = new ArrayList<>();
    private HashMap<String, HashMap<String,String>> interfaces = new HashMap<>();

    public Dependency_Supplier(ArrayList<String> classNames) {
        this.classNames = classNames;
        Class cl = null;
        for (String className : classNames) {
            try {
                cl = Class.forName(className);
                if(cl.isInterface() && (cl.getAnnotation(Bean.class) !=null || cl.getAnnotation(Service.class) !=null || cl.getAnnotation(Component.class) !=null)){
                    String interFace = className;
                    HashMap<String,String> classesToAdd = new HashMap<>();
                    for (String name : classNames) {
                        Class c = Class.forName(name);
                        if(c.getAnnotation(Qualifier.class) != null && Arrays.toString(c.getInterfaces()).contains(cl.getName())){
                            //System.out.println(Arrays.toString(c.getInterfaces()));
                            String key = ((Qualifier)c.getAnnotation(Qualifier.class)).value();
                            //System.out.println("kkey" + key);

                            Object o = classesToAdd.putIfAbsent(key,c.getName());
                            if(o != null){
                                // TODO: 3.11.2020. exception vise beanova sa istim qualifierom
                                }
                        }
                    }

                    interfaces.putIfAbsent(cl.getName(),classesToAdd);


                }
            } catch (ClassNotFoundException  e) {
                e.printStackTrace();
            }

        }
        //System.out.println(interfaces.toString());

    }

    public HashMap<String, HashMap<String, String>> getInterfaces() {
        return interfaces;
    }
}
