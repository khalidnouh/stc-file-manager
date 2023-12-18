package com.stc.STCFilesBackend.repository;

import com.stc.STCFilesBackend.domain.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long>, JpaSpecificationExecutor<Permissions> {

    List<Permissions> findAllByGroup_Id(Long groupId);
}
