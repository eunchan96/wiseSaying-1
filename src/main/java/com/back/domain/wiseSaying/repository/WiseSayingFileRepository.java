package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.standard.util.Util;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class WiseSayingFileRepository extends WiseSayingRepository {
    @Override
    public void save(WiseSaying wiseSaying) {
        if (wiseSaying.getId() == 0) {
            int lastId = getLastID();
            wiseSaying.setId(++lastId);
            Util.file.set("db/wiseSaying/lastId.txt", String.valueOf(lastId));
        }
        String filePath = "db/wiseSaying/%d.json".formatted(wiseSaying.getId());
        String wiseSayingJson = Util.json.toString(wiseSaying.toMap());
        Util.file.set(filePath, wiseSayingJson);
    }

    @Override
    public boolean delete(int id) {
        String filePath = "db/wiseSaying/%d.json".formatted(id);

        return Util.file.delete(filePath);
    }

    @Override
    public List<WiseSaying> findAll() {
        return Util.file.walkRegularFiles("db/wiseSaying", "\\d+\\.json")
                .map(path -> Util.file.get(path.toString(), ""))
                .map(Util.json::toMap)
                .map(WiseSaying::new)
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .toList();
    }

    @Override
    public Optional<WiseSaying> findById(int id) {
        String filePath = "db/wiseSaying/%d.json".formatted(id);

        String wiseSayingJson = Util.file.get(filePath, "");
        if (wiseSayingJson.isEmpty()) {
            return Optional.empty();
        }
        WiseSaying wiseSaying = new WiseSaying(Util.json.toMap(wiseSayingJson));
        return Optional.of(wiseSaying);
    }

    private int getLastID(){
        return Integer.parseInt(Util.file.get("db/wiseSaying/lastId.txt", "0"));
    }
}
