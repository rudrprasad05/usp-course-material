package com.assignment3.cs241.Group6.repository;

import com.assignment3.cs241.Group6.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE s.teacher IS NULL")
    List<Subject> findSubjectsWithoutTeacher();

    @Query("SELECT s FROM Subject s WHERE s.subjectName = :name")
    Subject findByName(@Param("name") String name);
}
