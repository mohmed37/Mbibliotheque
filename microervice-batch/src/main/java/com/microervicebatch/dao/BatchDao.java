package com.microervicebatch.dao;

import com.microervicebatch.entities.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BatchDao extends JpaRepository<Batch,Long> {

}
