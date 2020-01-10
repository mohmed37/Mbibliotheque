package com.microservicelibrairie.dao;


import com.microservicelibrairie.entities.UserReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReservationDao extends JpaRepository<UserReservation,Long> {

   UserReservation findByIdClient(Long id);


}
