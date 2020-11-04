package com.company.exceptions;

public class MissingImplementation extends Exception {
    public MissingImplementation(String message) {
        super("The field annotated with qualifier" + message + "can not be initialized because that implementation is missing or that class does not have the qualifier" + message);
    }
}
