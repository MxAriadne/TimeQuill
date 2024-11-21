package com.hedgehog.timequill.repo;

import com.hedgehog.timequill.entities.AssignmentEntity;
import com.hedgehog.timequill.entities.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AssignmentRepository extends CrudRepository<AssignmentEntity, Integer> {
    Set<AssignmentEntity> findByProject(ProjectEntity project);
}
