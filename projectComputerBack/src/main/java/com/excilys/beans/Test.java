package com.excilys.beans;

import static java.lang.Math.*;

class Test {
    public static void main(String[] args) {
        /*String test = "Test A. Test B. Test C.";

        String regex = "\\.\\s*";

        String[] result = test.split(regex);

        for (int i=0; i<result.length; i++)
            System.out.println(result[i]);

        System.out.println(PI);*/

        int k = 5;
        System.out.println(k=3*k++);

        k = 5;
        if (k++ == 5)
            System.out.println("OK");

        System.out.println(k+++ ++k);

    }
}

abstract class Ball {
    static void method() {
        int value = 10;
        System.out.println("value = " + value);
        }}

class Football extends Ball {
}

class Sample2 {
    public static void main(String args[]) {
        Football a = new Football();
        a.method();
    }
}