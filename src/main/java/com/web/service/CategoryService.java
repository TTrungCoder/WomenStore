package com.web.service;

import com.web.domain.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Page<Category> findByNameContaining(String name, Pageable pageable);

    List<Category> findByNameContaining(String name);

    <S extends Category> S save(S entity);

    List<Category> findAll();

    Page<Category> findAll(Pageable pageable);

    List<Category> findAll(Sort sort);

    List<Category> findAllById(Iterable<Long> ids);

    Optional<Category> findById(Long id);

    <S extends Category> List<S> saveAll(Iterable<S> entities);

    void flush();

    boolean existsById(Long id);

    <S extends Category> S saveAndFlush(S entity);

    <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteInBatch(Iterable<Category> entities);

    void deleteAllInBatch(Iterable<Category> entities);

    long count();

    <S extends Category> boolean exists(Example<S> example);

    void deleteById(Long id);

    void deleteAllByIdInBatch(Iterable<Long> ids);

    void delete(Category entity);

    void deleteAllById(Iterable<? extends Long> ids);

    void deleteAllInBatch();

    void deleteAll(Iterable<? extends Category> entities);

    void deleteAll();

    Category getById(Long id);
}
