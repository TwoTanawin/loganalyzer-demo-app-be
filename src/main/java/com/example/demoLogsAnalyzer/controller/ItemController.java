package com.example.demoLogsAnalyzer.controller;

import com.example.demoLogsAnalyzer.model.Item;
import com.example.demoLogsAnalyzer.repository.InMemoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private InMemoryItemRepository repository;

    @GetMapping
    public Flux<Item> getAllItems() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Item> getItemById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping
    public Mono<Item> createItem(@RequestBody Item item) {
        return repository.save(item);
    }

    @PutMapping("/{id}")
    public Mono<Item> updateItem(@PathVariable String id, @RequestBody Item item) {
        item.setId(id);
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteItem(@PathVariable String id) {
        return repository.deleteById(id);
    }
}
