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

class Oeufs2 {

    int doX(Short x, Short y) { return 1; }
    int doX(Integer x, Integer y) { return 2; }
    int doX(Number n, Number m) { return 3; }
    int doX(short x, short y) { return 4; }
    int doX(int x, int y) { return 5; }
    int doX(long x, long y) { return 10; }
    int doX(float x, float y) { return 11; }
    int doX(double x, double y) { return 12; }
    int doX(Short... x) { return 6; }
    int doX(Integer... x) { return 7; }

    public static void main(String[] args) {
        new Oeufs2().go();

    }
    void go() {
        Short s = 7;
        System.out.print(doX(s,s) + " ");

        /*Short
        Number (class parent)
        short*/
    }
}

enum turn {
    ON("20"), OFF("50");
    private String s;
    turn(String s) {this.s = s;}
    String getS() {return s;}
}

class enumm {
    public static void main(String[] args) {
        System.out.println(turn.ON.getS());

    }
}


class tt {
    public void t() {
        throw new RuntimeException();
    }
    public static void main(String[] args) {
       try {
           tt t = new tt();
           t.t();
       } catch (RuntimeException e) {
           System.out.println("OK");
       } finally {
           System.out.println("OKK");
       }

    }
}

class Weather {
    public static void main(String[] args) {
        Forecaster fc = new Forecaster();
        new Listener(fc);
        new Listener(fc);
        new Listener(fc);
    }

    static class Forecaster extends Thread {
        private int tomorrowsTemperature;

        public Forecaster() {
            start();
        }

        public synchronized int getTomorrowsTemperature() {
            return tomorrowsTemperature;
        }

        public void run() {
            try {
                for(;;) {
                    sleep(1000);
                    synchronized (this) {
                        tomorrowsTemperature = (int)(40*Math.random()-10);
                        notifyAll();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Listener extends Thread {
        private final Forecaster forecaster;

        public Listener(Forecaster forecaster) {
            this.forecaster = forecaster;
            start();
        }

        public void run() {
            try {
                for(;;) {
                    synchronized (forecaster) {
                        forecaster.wait();
                    }

                    System.out.println("I hear tomorrow's temperature will be " +
                    + forecaster.getTomorrowsTemperature() + " degress Celsius");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}





