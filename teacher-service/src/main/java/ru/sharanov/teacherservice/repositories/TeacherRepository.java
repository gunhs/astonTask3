package ru.sharanov.teacherservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sharanov.teacherservice.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}