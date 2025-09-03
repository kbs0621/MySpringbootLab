package com.rookies4.myspringbootlab.service;

import com.rookies4.myspringbootlab.controller.dto.PublisherDTO;
import com.rookies4.myspringbootlab.entity.Publisher;
import com.rookies4.myspringbootlab.exception.BusinessException;
import com.rookies4.myspringbootlab.exception.ErrorCode;
import com.rookies4.myspringbootlab.repository.PublisherRepository;
import com.rookies4.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    /**
     * 모든 출판사 조회 (각 출판사의 도서 수 포함)
     */
    public List<PublisherDTO.SimpleResponse> getAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(PublisherDTO.SimpleResponse::fromEntity)
                .toList();
    }

    /**
     * ID로 특정 출판사 조회 (출판사의 모든 도서 포함)
     */
    public PublisherDTO.Response getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Publisher", "id", id));
        return PublisherDTO.Response.fromEntity(publisher);
    }

    /**
     * 이름으로 특정 출판사 조회
     */
    public PublisherDTO.Response getPublisherByName(String name) {
        Publisher publisher = publisherRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Publisher", "name", name));
        return PublisherDTO.Response.fromEntity(publisher);
    }

    /**
     * 새로운 출판사 생성 (이름 중복 검증)
     */
    @Transactional
    public PublisherDTO.Response createPublisher(PublisherDTO.Request request) {
        if (publisherRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.PUBLISHER_NAME_DUPLICATE,
                    request.getName());
        }

        Publisher publisher = Publisher.builder()
                .name(request.getName())
                .build();

        Publisher savedPublisher = publisherRepository.save(publisher);
        return PublisherDTO.Response.fromEntity(savedPublisher);
    }

    /**
     * 기존 출판사 수정 (이름 중복 검증: 자기 자신 제외)
     */
    @Transactional
    public PublisherDTO.Response updatePublisher(Long id, PublisherDTO.Request request) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Publisher", "id", id));

        if (!publisher.getName().equals(request.getName()) &&
                publisherRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.PUBLISHER_NAME_DUPLICATE,
                    request.getName());
        }

        publisher.setName(request.getName());

        Publisher updatedPublisher = publisherRepository.save(publisher);
        return PublisherDTO.Response.fromEntity(updatedPublisher);
    }

    /**
     * 출판사 삭제 (도서 존재 시 삭제 거부)
     */
    @Transactional
    public void deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Publisher", "id", id);
        }

        Long bookCount = bookRepository.countByPublisherId(id);
        if (bookCount > 0) {
            throw new BusinessException(ErrorCode.PUBLISHER_HAS_BOOKS,
                    id, bookCount);
        }

        publisherRepository.deleteById(id);
    }
}
