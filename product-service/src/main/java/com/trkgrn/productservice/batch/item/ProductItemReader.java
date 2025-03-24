package com.trkgrn.productservice.batch.item;

import com.trkgrn.common.model.enums.ApprovalStatus;
import com.trkgrn.productservice.model.entity.VariantProduct;
import com.trkgrn.productservice.repository.VariantProductRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Component("productItemReader")
public class ProductItemReader extends RepositoryItemReader<VariantProduct> {

    public ProductItemReader(VariantProductRepository variantProductRepository) {
        setRepository(variantProductRepository);
        setMethodName("findByApprovalStatus");
        setArguments(Collections.singletonList(ApprovalStatus.APPROVED));
        setPageSize(10);
        Map<String, Sort.Direction> sortMap = new LinkedHashMap<>();
        sortMap.put("id", Sort.Direction.ASC);
        setSort(sortMap);
    }

}
