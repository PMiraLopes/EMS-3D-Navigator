package com.ems3DNavigator.exceptions;

public class RoomNotFoundException extends RuntimeException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public RoomNotFoundException(){ 
        super();
    }
    
    public RoomNotFoundException(String message) {
        super(message);
    }
    
    public RoomNotFoundException(String message, Throwable cause) { 
        super(message, cause); 
        }
    public RoomNotFoundException(Throwable cause) { 
        super(cause); 
    }

}
