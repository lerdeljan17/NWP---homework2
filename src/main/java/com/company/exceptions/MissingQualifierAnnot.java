package com.company.exceptions;

public class MissingQualifierAnnot extends Exception {
    public MissingQualifierAnnot(String message) {
        super("Field" + message + "that is interface is autowired and an interface but does not have a qualifier annotation to specify implementation");
    }
}
