package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingFileRepository;
import com.back.domain.wiseSaying.repository.WiseSayingMemoryRepository;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.List;
import java.util.Optional;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(String mode) {
        switch (mode) {
            case "file" -> wiseSayingRepository = new WiseSayingFileRepository();
            case "memory" -> wiseSayingRepository = new WiseSayingMemoryRepository();
            default -> throw new IllegalArgumentException("Invalid mode: " + mode);
        }
    }

    public void write(WiseSaying wiseSaying) {
        wiseSayingRepository.save(wiseSaying);
    }

    public void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);

        wiseSayingRepository.save(wiseSaying);
    }

    public boolean delete(int id) {
        return wiseSayingRepository.delete(id);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findAll();
    }

    public Optional<WiseSaying> findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void archive() {
        wiseSayingRepository.archive();
    }
}
