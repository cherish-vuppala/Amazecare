package com.hexaware.Amazecare.repo;

import com.hexaware.Amazecare.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {

}
