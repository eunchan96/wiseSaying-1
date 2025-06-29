package com.back.domain.wiseSaying.controller;

import com.back.AppTestRunner;
import com.back.standard.util.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WiseSayingControllerTest {
    @Test
    @DisplayName("등록")
    void actionWrite1() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("등록 시 명언 번호 증가")
    void actionWrite2() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                내 사전에 불가능이란 없다.
                나폴레옹
                """);

        assertThat(out)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void actionList() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                내 사전에 불가능이란 없다.
                나폴레옹
                목록
                """);

        assertThat(out)
                .contains("2 / 나폴레옹 / 내 사전에 불가능이란 없다.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제")
    void actionDelete1() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                내 사전에 불가능이란 없다.
                나폴레옹
                삭제?id=1
                목록
                """);

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("2 / 나폴레옹 / 내 사전에 불가능이란 없다.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제 시 존재하지 않는 명언")
    void  actionDelete2() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=1
                삭제?id=1
                목록
                """);

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("수정")
    void actionModify() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=3
                수정?id=1
                현재를 사랑하세요.
                아리스토텔레스
                목록
                """);

        assertThat(out)
                .contains("3번 명언은 존재하지 않습니다.")
                .contains("명언(기존) : 현재를 사랑하라.")
                .contains("작가(기존) : 작자미상")
                .contains("1 / 아리스토텔레스 / 현재를 사랑하세요.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("빌드")
    void actionArchive() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                내 사전에 불가능이란 없다.
                나폴레옹
                빌드
                """);

        assertThat(out)
                .contains("data.json 파일의 내용이 갱신되었습니다.");

        String json = """
                [
                    {
                        "id": 1,
                        "content": "현재를 사랑하라.",
                        "author": "작자미상"
                    },
                    {
                        "id": 2,
                        "content": "내 사전에 불가능이란 없다.",
                        "author": "나폴레옹"
                    }
                ]""";
        assertThat(Util.file.get("db/wiseSaying/data.json", "").trim())
                .isEqualTo(json);
    }
}