package com.game.view;

/**
 * Console class that actually outputs to the terminal by calling System.out.println
 * and passing the data that was sent to it from the ConsoleView object
 */

class Console {

    // print method without a color selector
    public static void print(String message){
        System.out.println(message);
    }

    // print method overloaded with a color selector
    public static void print(String text, AnsiTextColor textColor, AnsiBackgroundColor backgroundColor) {
        System.out.println(backgroundColor.getColor() + textColor.getColor() + text + AnsiTextColor.RESET.getColor());
    }

}