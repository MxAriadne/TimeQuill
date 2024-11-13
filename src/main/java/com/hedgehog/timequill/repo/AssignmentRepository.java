package com.hedgehog.timequill.repo;

import com.hedgehog.timequill.config.entities.AssignmentEntity;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AssignmentRepository extends CrudRepository<AssignmentEntity, Integer> {
}