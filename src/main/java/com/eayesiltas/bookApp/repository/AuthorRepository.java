package com.eayesiltas.bookApp.repository;

import com.eayesiltas.bookApp.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
