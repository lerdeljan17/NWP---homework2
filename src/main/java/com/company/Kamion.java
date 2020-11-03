package com.company;

import com.company.annotations.Bean;
import com.company.annotations.Qualifier;

@Qualifier(value = "Truck")
@Bean
public class Kamion implements Vozilo{
}
