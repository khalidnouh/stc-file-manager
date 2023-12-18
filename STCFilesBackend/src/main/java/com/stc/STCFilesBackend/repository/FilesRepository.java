package com.stc.STCFilesBackend.repository;

import com.stc.STCFilesBackend.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long>, JpaSpecificationExecutor<Files> {
}
