package com.company;

import com.company.annotations.Autowired;
import com.company.annotations.Bean;
import com.company.annotations.Qualifier;

@Bean
public class Profesor {
    @Qualifier(value = "Car")
    @Autowired(verbose = true)
    Vozilo v;
}
