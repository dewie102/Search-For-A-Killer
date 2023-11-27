package com.game.view;

/**
 * ConsoleText class that creates text objects with a text color, background color,
 * and the text string. There are three constructors that can be called (text only,
 * text and text color, text and background color, text and text color and background color.
 */

class ConsoleText {

    // INSTANCE VARIABLES
    String text;
    AnsiBackgroundColor backgroundColor;
    AnsiTextColor textColor;

    // CONSTRUCTOR
    // builds a new ConsoleText object with the given text, text color, and background color.
    ConsoleText(String text, AnsiTextColor textColor, AnsiBackgroundColor backgroundColor) {
        setText(text);
        setBackgroundColor(backgroundColor);
        setTextColor(textColor);
    }

    // builds a new ConsoleText object with the given text and text color.
    ConsoleText(String text, AnsiTextColor textColor) {
        setText(text);
        setTextColor(textColor);
        setBackgroundColor(AnsiBackgroundColor.NONE);
    }

    // builds a new ConsoleText object with the given text and background color.
    ConsoleText(String text, AnsiBackgroundColor backgroundColor) {
        setText(text);
        setTextColor(AnsiTextColor.NONE);
        setBackgroundColor(backgroundColor);
    }

    // builds a new ConsoleText object with the given text.
    ConsoleText(String text) {
        setText(text);
        setTextColor(AnsiTextColor.NONE);
        setBackgroundColor(AnsiBackgroundColor.NONE);
    }

    // GETTERS AND SETTERS
    void setText(String text) {
        this.text = text;
    }

    void setBackgroundColor(AnsiBackgroundColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    void setTextColor(AnsiTextColor textColor) {
        this.textColor = textColor;
    }
}