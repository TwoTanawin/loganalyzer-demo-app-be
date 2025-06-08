package com.example.demoLogsAnalyzer.repository;

import com.example.demoLogsAnalyzer.model.Item;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class InMemoryItemRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryItemRepository.class);
    private final Map<String, Item> store = new ConcurrentHashMap<>();

    public Flux<Item> findAll() {
        logger.info("Fetching all items");
        return Flux.fromIterable(store.values());
    }

    public Mono<Item> findById(String id) {
        logger.info("Fetching item by ID: {}", id);
        return Mono.justOrEmpty(store.get(id))
                .doOnNext(item -> logger.debug("Found item: {}", item))
                .switchIfEmpty(Mono.defer(() -> {
                    logger.warn("Item not found for ID: {}", id);
                    return Mono.empty();
                }));
    }

    public Mono<Item> save(Item item) {
        if (item.getId() == null || item.getId().isBlank()) {
            item.setId(UUID.randomUUID().toString());
        }
        store.put(item.getId(), item);
        return Mono.just(item);
    }

    public Mono<Void> deleteById(String id) {
        logger.info("Deleting item by ID: {}", id);
        store.remove(id);
        return Mono.empty();
    }
}
