package ru.sharanov.teacherservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.services.TeacherService;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchTeacherById(@PathVariable Long id){
        return teacherService.fetchTeacherById(id);
    }
    @GetMapping
    public ResponseEntity<?> fetchTeacher(){
        return teacherService.fetchTeacher();
    }
    @PostMapping
    public ResponseEntity<?> createTeacher(@RequestBody Teacher teacher){
        return teacherService.createTeacher(teacher);
    }

}