package com.eayesiltas.bookApp.repository;

import com.eayesiltas.bookApp.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
}
