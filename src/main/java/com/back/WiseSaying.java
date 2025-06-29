package com.back;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WiseSaying {
    private int id;
    private String content;
    private String author;

    @Override
    public String toString() {
        return "%d / %s / %s".formatted(id, author, content);
    }
}