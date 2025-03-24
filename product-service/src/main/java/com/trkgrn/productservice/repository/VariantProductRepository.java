package com.trkgrn.productservice.repository;

import com.trkgrn.common.model.enums.ApprovalStatus;
import com.trkgrn.productservice.model.entity.VariantProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantProductRepository extends JpaRepository<VariantProduct, Long> {
    Page<VariantProduct> findByApprovalStatus(ApprovalStatus approvalStatus, Pageable pageable);
}