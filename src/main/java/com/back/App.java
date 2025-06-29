package com.back;

import com.back.domain.wiseSaying.controller.WiseSayingController;
import com.back.global.rq.Rq;

import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    private final WiseSayingController wiseSayingController;

    public void run(){
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();
            Rq rq = new Rq(cmd);

            switch (rq.getActionName()) {
                case "종료" -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                case "등록" -> wiseSayingController.actionWrite();
                case "목록" -> wiseSayingController.actionList();
                case "삭제" -> wiseSayingController.actionDelete(rq);
                case "수정" -> wiseSayingController.actionModify(rq);
                case "빌드" -> wiseSayingController.actionArchive();
                default -> System.out.println("알 수 없는 명령어입니다.");
            }
        }
    }

    public App(String mode) {
        wiseSayingController = new WiseSayingController(scanner, mode);
    }
}