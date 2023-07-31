package com.web.respository;


import com.web.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    Page<Product> findAllByNameLike(String name, Pageable pageable);


    Page<Product> findProductByCategoryId(Long id, Pageable pageable);

}
