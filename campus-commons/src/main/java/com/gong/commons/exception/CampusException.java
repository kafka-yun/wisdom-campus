package com.gong.commons.exception;

public class CampusException extends RuntimeException{

    private String errMeassage;

    public CampusException(){

    }
    public CampusException(String message) {
        super(message);
        this.errMeassage = message;
    }

    public static void cast(String message){
        throw new CampusException(message);
    }
    public static void cast(CommonError error){
        throw new CampusException(error.getErrMessage());
    }

}
