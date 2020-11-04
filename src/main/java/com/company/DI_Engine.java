package com.company;

import com.company.annotations.*;
import com.company.exceptions.MissingAnnotationException;
import com.company.exceptions.MissingImplementation;
import com.company.exceptions.MissingQualifierAnnot;
import com.company.exceptions.MultipleQualifierException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

public class DI_Engine {
    //    private String projectPath;
    private ArrayList<String> classNames = new ArrayList<>();
    private ArrayList<String> myClasses = new ArrayList<>();
    private HashMap<String, Object> singletons = new HashMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    Dependency_Supplier dependency_supplier;
    private Object rootInstace;

    public DI_Engine(Object o) throws MissingAnnotationException, MissingQualifierAnnot, MissingImplementation, MultipleQualifierException {
        this.rootInstace = o;
//        projectPath = System.getProperty("user.dir");
//        System.out.println(projectPath);
//        File f = new File(projectPath+"\\src\\main\\java\\com\\company");
//        System.out.println(Arrays.toString(f.listFiles()));
        myClasses.addAll(Arrays.asList("Autowired.class", "Bean.class", "Component.class", "Qualifier.class", "Scope.class", "Service.class", "Dependency_Supplier.class", "DI_Engine.class", "Main.class"));

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            Enumeration<URL> roots = classLoader.getResources("");
            System.out.println();
            File root = new File(roots.nextElement().getPath());
            getClasses(root);
            System.out.println(classNames);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dependency_supplier = new Dependency_Supplier(classNames);
        scanFile("com.company.Test", rootInstace);


//        try {
//            cl = Class.forName(classNames.get(3));
//            Object o = cl.newInstance();
//            singletons.putIfAbsent(classNames.get(3),o);
//            f = cl.getDeclaredField("a");
//            boolean a =  f.getAnnotation(Autowired.class).verbose();
//            if(a){
//                System.out.println("Initialized" + " " +  f.getType() + " " + f.getName() +" in " + cl.getName() + " " + LocalDateTime.now().format(formatter) + " " + o.hashCode());
//            }else {
//                System.out.println("nema");
//            }
//        } catch (ClassNotFoundException | NoSuchFieldException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }

    }

    public Object scanFile(String className, Object o) throws MissingAnnotationException, MissingQualifierAnnot, MissingImplementation {
        @SuppressWarnings("rawtypes")
        Class cl = null;
        HashMap<String, Object> fieldMap = null;
        Field[] fields = new Field[0];
        try {
            cl = Class.forName(className);
            //o = cl.newInstance();
            Object value = null;
            fieldMap = new HashMap<>();
            fields = cl.getDeclaredFields();
            for (Field f : fields) {
                if (f.getAnnotation(Autowired.class) != null) {
                    if (!singletons.containsKey(f.getType().getName())) {
                        String type = f.getType().getName();
                        //f.set(o, );
                        if (f.getType().isInterface()) {
                            if (f.getAnnotation(Qualifier.class) != null) {
                                String qualifier = ((Qualifier) f.getAnnotation(Qualifier.class)).value();
//                                System.out.println(f.getType().getName());
                                if(dependency_supplier.getInterfaces().get(f.getType().getName()).containsKey(qualifier)){
                                    type = dependency_supplier.getInterfaces().get(f.getType().getName()).get(qualifier);
                                }else {
                                    throw new MissingImplementation(qualifier);
                                }

                            } else {
                                // TODO: 3.11.2020. exception
                                System.out.println("missing qualifier annot");
                                throw new MissingQualifierAnnot( f.getType() + " " + f.getName());
                            }

                        }
                        value = scanFile(type, o);
                        System.out.println("------------------------");
                        System.out.println("value " + value.getClass().getName() + " " + value.hashCode());
                        System.out.println("object " + o.getClass().getName() + " " + o.hashCode());
                        System.out.println("------------------------");
                        fieldMap.putIfAbsent(f.getName(), value);
                        if (f.getAnnotation(Autowired.class).verbose()) printAutowired(f, value, cl);
                        //f.set(o,value);
                    } else {
                        fieldMap.putIfAbsent(f.getName(), singletons.get(f.getType().getName()));
                        //f.set(o, singletons.get(f.getType().getName()));
                    }
                }


            }

//            System.out.println(cl.getName());
//            System.out.println(rootInstace.getClass().getName());
            if (!this.rootInstace.getClass().getName().equals(cl.getName()) ) {
                if (cl.getAnnotation(Bean.class) != null || cl.getAnnotation(Service.class) != null || cl.getAnnotation(Component.class) != null) {
                    if (cl.getAnnotation(Service.class) != null || ((Bean) cl.getAnnotation(Bean.class)).scope() == Scope.Singleton) {
                        singletons.putIfAbsent(cl.getName(), cl.newInstance());
                        o = singletons.get(cl.getName());
                    } else if (cl.getAnnotation(Component.class) != null || ((Bean) cl.getAnnotation(Bean.class)).scope() == Scope.Prototype) {
                        o = cl.newInstance();
                    } else {

                    }
                } else {

                    // TODO: 3.11.2020. exception
                    //System.out.println("ovde treba izuzetak da ispali posto nema bean ni nista drugo");
                    throw new MissingAnnotationException(cl.getName());
                }
            }

            for (Field field : fields) {
                field.setAccessible(true);
                if (fieldMap.containsKey(field.getName())) {
                    field.set(o, fieldMap.get(field.getName()));
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //System.out.println(((Test)o).predmet.getS());
        //System.out.println("poslednji " +o.getClass().getName() +" " + o.hashCode());
        return o;
    }

    public void getClasses(File root) {
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                // Loop through its listFiles() recursively.
                getClasses(file);

            } else {
                String name = file.getPath();
                String rName = file.getName();
                if (name.contains(".class") && !myClasses.contains(rName)) {
                    classNames.add(name.replace(".class", "").replaceAll("\\\\", ".").substring(name.indexOf("classes")).replace("classes.", ""));
                }

                // Check if it's a .class file or a .jar file and handle accordingly.

            }
        }
    }

    public void printAutowired(Field f, Object object, Class cl) {
        System.out.println("Initialized" + " " + f.getType() + " " + f.getName() + " in " + cl.getName() + " " + LocalDateTime.now().format(formatter) + " with " + object.hashCode());

    }
}
