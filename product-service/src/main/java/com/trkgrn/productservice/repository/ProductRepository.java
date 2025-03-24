package com.trkgrn.productservice.repository;

import com.trkgrn.common.model.enums.ApprovalStatus;
import com.trkgrn.productservice.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByApprovalStatus(ApprovalStatus approvalStatus, Pageable pageable);
}