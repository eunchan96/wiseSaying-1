package com.back;

import java.util.Scanner;

public class AppContext {
    public static Scanner scanner;

    public static void renew(Scanner _Scanner) {
        scanner = _Scanner;
    }

    public static void renew() {
        renew(new Scanner(System.in));
    }
}
