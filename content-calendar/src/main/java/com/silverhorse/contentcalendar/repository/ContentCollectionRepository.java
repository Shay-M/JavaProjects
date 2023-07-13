package com.silverhorse.contentcalendar.repository;

import com.silverhorse.contentcalendar.model.Content;
import com.silverhorse.contentcalendar.model.Status;
import com.silverhorse.contentcalendar.model.Type;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentCollectionRepository {

    private final List<Content> contents = new ArrayList<>();

    public ContentCollectionRepository() {

    }

    public List<Content> findAll() {
        return contents;
    }

    public Optional<Content> findById(Integer id) {
        return contents.stream().filter(c -> c.id().equals(id)).findFirst();
    }

    @PostConstruct
    private void init() {
        Content content = new Content(1, "first",
                "f",
                Status.IDEA,
                Type.ARTICLE,
                LocalDateTime.now(),
                null,
                "");


        contents.add(content);
    }

    public void save(final Content content) {
        contents.add(content);
    }

    public boolean existsByID(final Integer id) {
        return contents.stream().filter(c -> c.id().equals(id)).count() == 1;

    }
}
