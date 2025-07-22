package com.willeylee.remember_this.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.willeylee.remember_this.entities.CloudNode;

public interface CloudNodeRepository extends JpaRepository<CloudNode, Integer>{
    Optional<CloudNode> findByUserUserId(Integer userId);
    Optional<CloudNode> findByNodeId(Integer nodeId);
}
