package com.meidl.mongodb.repository;

import com.meidl.mongodb.domain.File;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * File 存储库.
 */
public interface FileRepository extends MongoRepository<File, String> {
}
