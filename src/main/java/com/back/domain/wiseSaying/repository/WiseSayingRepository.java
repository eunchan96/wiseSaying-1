package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.standard.util.Util;

import java.util.List;
import java.util.Optional;

public abstract class WiseSayingRepository {
    public abstract void save(WiseSaying wiseSaying);
    public abstract boolean delete(int id);
    public abstract List<WiseSaying> findAll();
    public abstract Optional<WiseSaying> findById(int id);

    public void archive() {
        String json = Util.json.toString(findAll().stream()
                .map(WiseSaying::toMap)
                .toList());

        Util.file.set("db/wiseSaying/data.json", json);
    }
}
