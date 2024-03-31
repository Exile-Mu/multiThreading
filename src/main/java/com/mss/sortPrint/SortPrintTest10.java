package com.mss.sortPrint;

import com.mss.utils.PrintHelper;

public class SortPrintTest10 {

    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";

    static class Str {
        public static String s = C;
    }

    private static int print(String prev, String cur, int i) {
        synchronized (Str.class) {
            if (prev.equals(Str.s)) {
                i++;
                Str.s = cur;
                if (A.equals(Str.s)) {
                    System.out.println(PrintHelper.printThreadMark() + "==>" + i);
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
                    PrintHelper.printExceptionMark(e);
                }
            }
            if (i == 10) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; ) {
                i = print(C, A, i);
                wait(C, i);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; ) {
                i = print(A, B, i);
                wait(A, i);
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; ) {
                i = print(B, C, i);
                wait(B, i);
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
