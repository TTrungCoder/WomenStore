package com.web.service.impl;

import com.web.domain.Account;
import com.web.respository.AccountRepository;
import com.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
        String fullname = oauth2.getPrincipal().getAttribute("name");
        String password = Long.toHexString(System.currentTimeMillis());

        UserDetails user = User.withUsername(fullname)
                .password(bCryptPasswordEncoder.encode(password)).roles("CUST").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    //login
    @Override
    public Account login(String username, String password) {
        // Tìm kiếm thông tin username
        Optional<Account> optExist = findById(username);

        // Nếu tìm thấy username và so sánh password nếu trùng nhau
        if (optExist.isPresent()) {
            // Không cần mã hóa mật khẩu trong phương thức login
            if (password.equals(optExist.get().getPassword())) {
                // Xóa trắng password
                optExist.get().setPassword("");
                return optExist.get();
            }
        }

        // Login thất bại
        return null;
    }



    @Override
    public <S extends Account> S save(S entity) {
        Optional<Account> optExist = findById(entity.getUsername());

        // Kiểm tra nếu người dùng đã nhập mật khẩu mới
        if (!StringUtils.isEmpty(entity.getPassword())) {
            // Mã hóa mật khẩu mới
            entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        } else {
            // Sử dụng mật khẩu cũ từ tài khoản hiện tại
            if (optExist.isPresent()) {
                entity.setPassword(optExist.get().getPassword());
            }
        }

        return accountRepository.save(entity);
    }


    @Override
    public <S extends Account> Optional<S> findOne(Example<S> example) {
        return accountRepository.findOne(example);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public List<Account> findAll(Sort sort) {
        return accountRepository.findAll(sort);
    }

    @Override
    public List<Account> findAllById(Iterable<String> ids) {
        return accountRepository.findAllById(ids);
    }

    @Override
    public Optional<Account> findById(String username) {
        return accountRepository.findById(username);
    }


    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return accountRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        accountRepository.flush();
    }

    @Override
    public boolean existsById(String id) {
        return accountRepository.existsById(id);
    }

    @Override
    public <S extends Account> S saveAndFlush(S entity) {
        return accountRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
        return accountRepository.saveAllAndFlush(entities);
    }

    @Override
    public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
        return accountRepository.findAll(example, pageable);
    }

    @Override
    public void deleteInBatch(Iterable<Account> entities) {
        accountRepository.deleteInBatch(entities);
    }

    @Override
    public <S extends Account> long count(Example<S> example) {
        return accountRepository.count(example);
    }

    @Override
    public void deleteAllInBatch(Iterable<Account> entities) {
        accountRepository.deleteAllInBatch(entities);
    }

    @Override
    public long count() {
        return accountRepository.count();
    }

    @Override
    public <S extends Account> boolean exists(Example<S> example) {
        return accountRepository.exists(example);
    }

    @Override
    public void deleteById(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> ids) {
        accountRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void delete(Account entity) {
        accountRepository.delete(entity);
    }

    @Override
    public <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return accountRepository.findBy(example, queryFunction);
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        accountRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAllInBatch() {
        accountRepository.deleteAllInBatch();
    }

    @Override
    public Account getOne(String id) {
        return accountRepository.getOne(id);
    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {
        accountRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
    }

    @Override
    public Account getById(String id) {
        return accountRepository.getById(id);
    }

    @Override
    public Account getReferenceById(String id) {
        return accountRepository.getReferenceById(id);
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        return accountRepository.findAll(example);
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        return accountRepository.findAll(example, sort);
    }
}