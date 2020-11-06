package com.company;

import com.company.annotations.Bean;
import com.company.annotations.Component;
import com.company.annotations.Qualifier;
import com.company.annotations.Service;
import com.company.exceptions.MultipleQualifierException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Dependency_Supplier {
    private ArrayList<String> classNames = new ArrayList<>();
    private final HashMap<String, HashMap<String, String>> interfaces = new HashMap<>();

    public Dependency_Supplier(ArrayList<String> classNames) throws MultipleQualifierException {
        this.classNames = classNames;
        Class cl = null;
        for (String className : classNames) {
            try {
                cl = Class.forName(className);
                //odavde uklonio bean,service...
                if (cl.isInterface()) {
                    String interFace = className;
                    HashMap<String, String> classesToAdd = new HashMap<>();
                    for (String name : classNames) {
                        Class c = Class.forName(name);
                        if (c.getAnnotation(Qualifier.class) != null && Arrays.toString(c.getInterfaces()).contains(cl.getName())) {
                            //System.out.println(Arrays.toString(c.getInterfaces()));
                            String key = ((Qualifier) c.getAnnotation(Qualifier.class)).value();
                            //System.out.println("kkey" + key);

                            Object o = classesToAdd.putIfAbsent(key, c.getName());
                            if (o != null) {
                                throw new MultipleQualifierException(key);
                            }
                        }
                    }

                    interfaces.putIfAbsent(cl.getName(), classesToAdd);


                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        //System.out.println(interfaces.toString());

    }

    public HashMap<String, HashMap<String, String>> getInterfaces() {
        return interfaces;
    }
}
