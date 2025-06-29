package com.back;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    private int lastId = 0;
//    private final List<WiseSaying> wiseSayings = new ArrayList<>();

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
                case "등록" -> actionWrite();
                case "목록" -> actionList();
                case "삭제" -> actionDelete(rq);
                case "수정" -> actionModify(rq);
                default -> System.out.println("알 수 없는 명령어입니다.");
            }
        }
    }

    private void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
//        wiseSayings.add(wiseSaying);

        Map<String, Object> wiseSayingMap = wiseSaying.toMap();
        String wiseSayingJson = Util.json.toString(wiseSayingMap);
        Util.file.set("db/wiseSaying/%d.json".formatted(wiseSaying.getId()), wiseSayingJson);
        Util.file.set("db/wiseSaying/lastId.txt", String.valueOf(lastId));

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId()));
    }

    private void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        List<WiseSaying> wiseSayings = Util.file.walkRegularFiles("db/wiseSaying", "\\d+\\.json")
                .map(path -> Util.file.get(path.toString(), ""))
                .map(Util.json::toMap)
                .map(WiseSaying::new)
                .toList();

        for (WiseSaying wiseSaying : wiseSayings.reversed()) {
            System.out.println(wiseSaying);
        }
    }

    private void actionDelete(Rq rq) {
        int id = rq.getParamInt("id", -1);

        if (id == -1) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

//        WiseSaying wiseSaying = wiseSayings.stream()
//                .filter(e -> e.getId() == id)
//                .findFirst()
//                .orElse(null);
//
//        if (wiseSaying == null){
//            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
//            return;
//        }
//
//        wiseSayings.remove(wiseSaying);

        String filePath = "db/wiseSaying/%d.json".formatted(id);

        if (Util.file.notExists(filePath)) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }
        Util.file.delete(filePath);
        System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
    }

    private void actionModify(Rq rq) {
        int id = rq.getParamInt("id", -1);

        if (id == -1) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

//        WiseSaying wiseSaying = wiseSayings.stream()
//                .filter(e -> e.getId() == id)
//                .findFirst()
//                .orElse(null);
//
//        if (wiseSaying == null) {
//            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
//            return;
//        }

        String filePath = "db/wiseSaying/%d.json".formatted(id);
        if (Util.file.notExists(filePath)) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }
        String wiseSayingJson = Util.file.get(filePath, "");
        Map<String, Object> wiseSayingMap = Util.json.toMap(wiseSayingJson);
        WiseSaying wiseSaying = new WiseSaying(wiseSayingMap);

        System.out.println("명언(기존) : %s".formatted(wiseSaying.getContent()));
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);

        wiseSayingMap = wiseSaying.toMap();
        wiseSayingJson = Util.json.toString(wiseSayingMap);
        Util.file.set(filePath, wiseSayingJson);

        System.out.println("%d번 명언이 수정되었습니다.".formatted(id));
    }
}