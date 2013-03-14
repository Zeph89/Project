package com.excilys.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

class example {
    int x;
    int y;
    String name;
    public static void main(String args[]) {
        example pnt = new example();
        int z;
        System.out.println("pnt is " + pnt.name +
                " " + pnt.x + " " + pnt.y);

        //System.out.println(z);
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

class SomeClass {
    public static void main(String args[]) {
        LinkedList<String> list = new LinkedList<String>();
        list.add("BbB1");
        list.add("bBb2");
        list.add("bbB3");
        list.add("BBb4");
        Collections.sort(list);
        for (String str : list) {
            System.out.print(str + ":");
        }
    }
}

class Empty {
    public void test() {
        System.out.println("1");
    }
}


class Extended extends Empty {

    public static void main(String args[]) {

    }

    public void test() {
        super.test();
        System.out.println("1");
    }
}

class TryMe {
    public static void doStuff1(List<Empty> list) {
        // some code
    }
    public static void doStuff2(List list) {
        // some code
    }
    public static void doStuff3(List<? extends Empty> list) {
        // some code
    }

    public static void main(String args[]) {
        List<Empty> list1 = new LinkedList<Empty>();
        List<Extended> list2 = new LinkedList<Extended>();

        doStuff3(list2);
    }
}

class Oeufs {
    /*int doX(Long x, Long y) { return 1; }
    int doX(long... x) { return 2; }
    int doX(Integer x, Integer y) { return 3; }
    int doX(Number n, Number m) { return 4; }
    int doX(int x, int y) { return 5; }
    int doX(Short x, Short y) { return 6; }*/
    int doX(Short... x) { return 7; }
    int doX(Integer... x) { return 8; }
    //int doX(float... x) { return 9; }

    public static void main(String[] args) {
        new Oeufs().go();
    }
    void go() {
        short s = 7;
        System.out.print(doX(s,s) + " ");
        System.out.println(doX(7,7));

        Short ss = 8;
        Integer i = ss.intValue();
        i = (int) ss;
    }
}

