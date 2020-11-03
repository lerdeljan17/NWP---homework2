package com.company;

import com.company.annotations.Bean;
import com.company.annotations.Qualifier;

@Qualifier(value = "Car")
@Bean
public class Auto implements Vozilo {
}
