package com.kartik.backend_microservices.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username); // this method we don't have to implement, spring will do it for us
    UserEntity getUserByUsername(String username);

}
