package com.driver;

public class NoIdExistException extends Exception {
    public NoIdExistException(){
        super("id's not found");
    }
}
