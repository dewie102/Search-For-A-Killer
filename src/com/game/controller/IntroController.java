package com.game.controller;

import org.w3c.dom.ls.LSOutput;

class IntroController {

    private static void printIntro(){
        printStory();
        printObjective();
        printHowToWin();
    }

    private static void printStory(){
        System.out.println(
"       ================The Story================\n" +
"You are the newest detective to join Cool Town Detective Agency \n"+
"and you’ve just been called out to investigate a reported murder \n" +
"at the Old Manor in town. It seems that the town’s founder, \n" +
"Mr. Cool, has been killed.\n"
        );
    }

    private static void printObjective(){
        System.out.println(
"       ================Your Mission================\n" +
"Your Mission: Solve the case by figuring out who the murderer is, \n" +
"and what the murder weapon was. Investigate the house, talk to \n" +
"witnesses and suspects, and find evidence to piece together what \n" +
"happened.\n"
        );
    }

    private static void printHowToWin(){
        System.out.println();
        System.out.println(
"       ================Winning================\n" +
"Once you think you know the answer, call in to the Police Department \n" +
"to report your findings. If you solve the case correctly – you win! \n" +
"If not, your first case at the agency might be your last. \n"
        );
    }


    public static void main(String[] args) {
        printIntro();
    }

}