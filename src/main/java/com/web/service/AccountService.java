package com.web.service;

import com.web.domain.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface AccountService {
    //login
    Account login(String username, String password);
    <S extends Account> S save(S entity);

    <S extends Account> Optional<S> findOne(Example<S> example);

    List<Account> findAll();

    Page<Account> findAll(Pageable pageable);

    List<Account> findAll(Sort sort);

    List<Account> findAllById(Iterable<String> ids);

    Optional<Account> findById(String username);

    <S extends Account> List<S> saveAll(Iterable<S> entities);

    void flush();

    boolean existsById(String id);

    <S extends Account> S saveAndFlush(S entity);

    <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities);

    <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable);

    void deleteInBatch(Iterable<Account> entities);

    <S extends Account> long count(Example<S> example);

    void deleteAllInBatch(Iterable<Account> entities);

    long count();

    <S extends Account> boolean exists(Example<S> example);

    void deleteById(String id);

    void deleteAllByIdInBatch(Iterable<String> ids);

    void delete(Account entity);

    <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    void deleteAllById(Iterable<? extends String> ids);

    void deleteAllInBatch();

    Account getOne(String id);

    void deleteAll(Iterable<? extends Account> entities);

    void deleteAll();

    Account getById(String id);

    Account getReferenceById(String id);

    <S extends Account> List<S> findAll(Example<S> example);

    <S extends Account> List<S> findAll(Example<S> example, Sort sort);
}
