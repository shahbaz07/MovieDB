package com.careem.moviedb.communication.exception;

public class ServerException extends Exception {

    private int code;
    private String message;

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
