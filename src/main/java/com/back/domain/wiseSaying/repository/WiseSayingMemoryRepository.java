package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WiseSayingMemoryRepository extends WiseSayingRepository{
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>();

    @Override
    public void save(WiseSaying wiseSaying) {
        if(wiseSaying.getId() == 0) {
            wiseSaying.setId(++lastId);
            wiseSayings.add(wiseSaying);
        }
    }

    @Override
    public Optional<WiseSaying> findById(int id) {
        return wiseSayings.stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst();
    }

    @Override
    public boolean delete(int id){
        WiseSaying wiseSaying = findById(id).orElse(null);
        return wiseSayings.remove(wiseSaying);
    }

    @Override
    public List<WiseSaying> findAll() {
        return wiseSayings.reversed();
    }
}