package com.mss.sortPrint;

import com.mss.utils.PrintHelper;

public class SortPrintTest10 {

    static class Str {
        public static String s = "c";
    }

    private static int print(String prev, String cur, int i) {
        synchronized (Str.class) {
            if (prev.equals(Str.s)) {
                Str.s = cur;
                if ("a".equals(Str.s)) {
                    System.out.println(PrintHelper.printThreadMark() + "==>" + ++i);
                }
                System.out.println(PrintHelper.printThreadMark() + Str.s);
            }
        }
        return i;
    }

    private static void wait(String cur, int i) {
        boolean flag = true;
        while (flag) {
            if (cur.equals(Str.s)) {
                flag = false;
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (i == 10) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Runnable run1 = () -> {
            for (int i = 0; i < 10; ) {
                i = print("c", "a", i);
                wait("c", i);
            }
        };
        Runnable run2 = () -> {
            for (int i = 0; i < 10; ) {
                i = print("a", "b", i);
                wait("a", i);
            }
        };
        Runnable run3 = () -> {
            for (int i = 0; i < 10; ) {
                i = print("b", "c", i);
                wait("b", i);
            }
        };
        Thread t1 = new Thread(run1);
        Thread t2 = new Thread(run2);
        Thread t3 = new Thread(run3);

        t1.start();
        t2.start();
        t3.start();
    }
}
