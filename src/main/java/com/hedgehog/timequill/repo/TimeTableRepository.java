package com.hedgehog.timequill.repo;

import com.hedgehog.timequill.entities.AssignmentEntity;
import com.hedgehog.timequill.entities.ProjectEntity;
import com.hedgehog.timequill.entities.TimeTableEntity;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TimeTableRepository extends CrudRepository<TimeTableEntity, Integer> {
    TimeTableEntity findByProject(ProjectEntity project);

    /**
     * Finds all TimeTableEntities associated with the given AssignmentEntity.
     *
     * @param task the AssignmentEntity to search for
     * @return a Set of TimeTableEntities associated with the given AssignmentEntity
     */
    Set<TimeTableEntity> findAllByAssignment(AssignmentEntity task);
}
