package ru.sharanov.teacherservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sharanov.teacherservice.model.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
