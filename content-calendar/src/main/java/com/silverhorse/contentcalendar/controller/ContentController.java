package com.silverhorse.contentcalendar.controller;

import com.silverhorse.contentcalendar.model.Content;
import com.silverhorse.contentcalendar.model.Status;
import com.silverhorse.contentcalendar.model.Type;
import com.silverhorse.contentcalendar.repository.ContentCollectionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/content")
/*
@CrossOrigin() Because:
Access to fetch at 'http://localhost:8080/api/content' from origin 'http://localhost:5173' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource. If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with CORS disabled.
*/
@CrossOrigin()
public class ContentController {

    private final ContentCollectionRepository repository;


    public ContentController(final ContentCollectionRepository repository) {
        this.repository = repository;
    }

    // @GetMapping("/api/content")
    @GetMapping("")
    public List<Content> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Content findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "content not fond!"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody Content content) {
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Content content, @PathVariable Integer id) {
        if (!repository.existsByID(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "content not fond!");
        }
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        if (!repository.existsByID(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "content not fond!");
        }
        repository.delete(id);
    }
}
