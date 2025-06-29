package com.back;

import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);

    public void run(){
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            switch (cmd) {
                case "종료" -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("알 수 없는 명령어입니다.");
            }
        }
    }
}