package com.back;

import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    private int lastId = 0;

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
                case "등록" -> actionWrite();
                default -> System.out.println("알 수 없는 명령어입니다.");
            }
        }
    }

    private void actionWrite() {
        System.out.print("명언 : ");
        String wiseSaying = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        System.out.println("%d번 명언이 등록되었습니다.".formatted(++lastId));
    }
}