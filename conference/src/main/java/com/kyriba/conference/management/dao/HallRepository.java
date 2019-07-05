package com.kyriba.conference.management.dao;

import com.kyriba.conference.management.domain.Hall;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HallRepository extends JpaRepository<Hall, Long>
{
}
