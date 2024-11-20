package com.hedgehog.timequill.repo;

import com.hedgehog.timequill.config.entities.TimeTableEntity;
import com.hedgehog.timequill.config.entities.UserEntity;

import org.springframework.data.repository.CrudRepository;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TimeTableRepository extends CrudRepository<TimeTableEntity, Integer> {
}