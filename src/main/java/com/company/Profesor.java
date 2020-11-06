package com.company;

import com.company.annotations.Autowired;
import com.company.annotations.Bean;
import com.company.annotations.Qualifier;
import com.company.annotations.Service;

@Service
public class Profesor {
    @Qualifier(value = "Car")
    @Autowired(verbose = true)
    Vozilo v;

    @Qualifier(value = "Car")
    @Autowired(verbose = true)
    Vozilo v1;
}
