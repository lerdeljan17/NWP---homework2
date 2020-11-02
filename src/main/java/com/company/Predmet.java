package com.company;

import com.company.annotations.Autowired;
import com.company.annotations.Bean;

@Bean
public class Predmet {
    @Autowired(verbose = true)
    public Student s;

    public int getS() {
        return s.hashCode();
    }
}
