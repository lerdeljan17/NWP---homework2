package com.company;

import com.company.annotations.Autowired;
import com.company.annotations.Bean;
import com.company.annotations.Scope;

@Bean(scope = Scope.Prototype)
public class Student {
    @Autowired(verbose = true)
    private Profesor p;
}
