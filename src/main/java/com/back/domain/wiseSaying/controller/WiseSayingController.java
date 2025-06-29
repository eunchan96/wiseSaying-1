package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;
import com.back.global.rq.Rq;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner scanner, String mode) {
        this.scanner = scanner;
        wiseSayingService = new WiseSayingService(mode);
    }

    public void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        WiseSaying wiseSaying = new WiseSaying(content, author);
        wiseSayingService.write(wiseSaying);
        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId()));
    }

    public void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        List<WiseSaying> wiseSayings = wiseSayingService.findAll();

        for (WiseSaying wiseSaying : wiseSayings) {
            System.out.println(wiseSaying);
        }
    }

    public void actionDelete(Rq rq) {
        int id = rq.getParamInt("id", -1);

        if (id == -1) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

        boolean deleted = wiseSayingService.delete(id);
        if(deleted) System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        else System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
    }

    public void actionModify(Rq rq) {
        int id = rq.getParamInt("id", -1);

        if (id == -1) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

        WiseSaying wiseSaying = wiseSayingService.findById(id).orElse(null);

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.println("명언(기존) : %s".formatted(wiseSaying.getContent()));
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        wiseSayingService.modify(wiseSaying, content, author);

        System.out.println("%d번 명언이 수정되었습니다.".formatted(id));
    }

    public void actionArchive() {
        wiseSayingService.archive();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
