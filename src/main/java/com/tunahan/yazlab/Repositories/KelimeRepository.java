package com.tunahan.yazlab.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tunahan.yazlab.Models.Kelime;

public interface KelimeRepository extends MongoRepository<Kelime, Integer>{

    Optional<Kelime> findById(Long employeeId);
    
}
