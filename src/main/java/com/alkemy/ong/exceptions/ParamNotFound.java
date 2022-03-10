package com.alkemy.ong.exceptions;

public class ParamNotFound extends RuntimeException{

    public ParamNotFound(String error) {
        super(error);
    }
}
