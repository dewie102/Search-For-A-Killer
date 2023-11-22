package com.game.view.framework;

/*
 * A custom Exception to throw when the user enters invalid input
 */
public class InvalidInputException extends Exception{
    public InvalidInputException(String statement){
        super(statement);
    }
}
