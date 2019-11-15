package com.microservicelibrairie.dao;

import com.microservicelibrairie.entities.Librairie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrairieRepository extends JpaRepository<Librairie,Long> {

}
