package ru.sharanov.teacherservice.dto;

import lombok.*;
import ru.sharanov.teacherservice.model.School;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherResponse {
    private Long id;
    private String name;
    private int age;
    private String direction;
    private int salary;
    private School school;
}