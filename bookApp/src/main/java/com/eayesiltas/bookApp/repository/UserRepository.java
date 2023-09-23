package com.eayesiltas.bookApp.repository;

import com.eayesiltas.bookApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
