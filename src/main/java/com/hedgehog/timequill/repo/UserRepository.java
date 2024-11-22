package com.hedgehog.timequill.repo;

import com.hedgehog.timequill.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
