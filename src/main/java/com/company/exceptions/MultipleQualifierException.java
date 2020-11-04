package com.company.exceptions;


public class MultipleQualifierException  extends Exception {
    public MultipleQualifierException(String message) {
        super("There are multiple bean classes with the samo qualifier" + message + "qualifier must be unique");
    }
}
