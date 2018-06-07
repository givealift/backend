package com.agh.givealift.util;

public class NotUniqueLoginException extends RuntimeException {

    public NotUniqueLoginException() {
        super("Podany Email jest już w bazie");
    }
}
