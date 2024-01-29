package com.koobing.koobing.search.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HostelJpaRepository extends JpaRepository<HostelEntity, UUID> {
    List<HostelEntity> findByZipcode(String zipcode);
}
