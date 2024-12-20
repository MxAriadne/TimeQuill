package com.hedgehog.timequill.repo;

import com.hedgehog.timequill.entities.ProjectEntity;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProjectRepository extends CrudRepository<ProjectEntity, Integer> {
    ProjectEntity findByName(String projName);
}
