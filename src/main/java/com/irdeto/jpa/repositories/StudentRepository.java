package com.irdeto.jpa.repositories;

import com.irdeto.jpa.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
