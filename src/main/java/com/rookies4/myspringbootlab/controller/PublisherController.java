package com.rookies4.myspringbootlab.controller;

import com.rookies4.myspringbootlab.controller.dto.PublisherDTO;
import com.rookies4.myspringbootlab.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    /**
     * 모든 출판사 조회 (각 출판사의 도서 수 포함)
     */
    @GetMapping
    public ResponseEntity<List<PublisherDTO.SimpleResponse>> getAllPublishers() {
        List<PublisherDTO.SimpleResponse> publishers = publisherService.getAllPublishers();
        return ResponseEntity.ok(publishers);
    }

    /**
     * ID로 특정 출판사 조회 (출판사의 모든 도서 포함)
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO.Response> getPublisherById(@PathVariable Long id) {
        PublisherDTO.Response publisher = publisherService.getPublisherById(id);
        return ResponseEntity.ok(publisher);
    }

    /**
     * 이름으로 특정 출판사 조회
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<PublisherDTO.Response> getPublisherByName(@PathVariable String name) {
        PublisherDTO.Response publisher = publisherService.getPublisherByName(name);
        return ResponseEntity.ok(publisher);
    }

    /**
     * 새로운 출판사 생성
     */
    @PostMapping
    public ResponseEntity<PublisherDTO.Response> createPublisher(
            @Valid @RequestBody PublisherDTO.Request request) {
        PublisherDTO.Response createdPublisher = publisherService.createPublisher(request);
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    /**
     * 기존 출판사 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO.Response> updatePublisher(
            @PathVariable Long id,
            @Valid @RequestBody PublisherDTO.Request request) {
        PublisherDTO.Response updatedPublisher = publisherService.updatePublisher(id, request);
        return ResponseEntity.ok(updatedPublisher);
    }

    /**
     * 출판사 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}