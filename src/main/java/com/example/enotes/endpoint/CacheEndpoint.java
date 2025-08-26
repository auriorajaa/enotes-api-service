package com.example.enotes.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Caching", description = "Caching related API's")
@RequestMapping("/api/v1/cache")
public interface CacheEndpoint {

    @GetMapping("/")
    ResponseEntity<?> getAllCache();

    @GetMapping("/{cache_name}")
    ResponseEntity<?> getCache(@PathVariable String cache_name);

    @DeleteMapping("/")
    ResponseEntity<?> removeAllCache();
}
