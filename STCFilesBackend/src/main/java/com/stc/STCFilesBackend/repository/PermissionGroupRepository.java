package com.stc.STCFilesBackend.repository;

import com.stc.STCFilesBackend.domain.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long>, JpaSpecificationExecutor<PermissionGroup> {
}
