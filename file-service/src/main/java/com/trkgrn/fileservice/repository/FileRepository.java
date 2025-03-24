package com.trkgrn.fileservice.repository;

import com.trkgrn.fileservice.model.FileMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends MongoRepository<FileMetadata, String> {
}
