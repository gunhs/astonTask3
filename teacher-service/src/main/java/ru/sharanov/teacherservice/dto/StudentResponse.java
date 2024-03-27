package ru.sharanov.teacherservice.dto;

import lombok.Data;
import ru.sharanov.teacherservice.model.Student;

import java.util.List;

@Data
public class StudentResponse {
    List<Student> students;
}
