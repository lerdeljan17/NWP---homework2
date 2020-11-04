package com.company.exceptions;

public class MissingAnnotationException extends Exception {

    public MissingAnnotationException(String message) {
        super("Class initialization required because of an autowired filed but the class" + message + " does not have a Bean,Service nor Component annotation");
    }
}
