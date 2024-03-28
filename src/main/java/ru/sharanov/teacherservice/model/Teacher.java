package ru.sharanov.teacherservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int age;
    private String direction;
    private Integer salary;
    private Integer schoolId;
    @ManyToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Student> student;
}