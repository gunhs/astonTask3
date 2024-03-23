package ru.sharanov.teacherservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.services.TeacherService;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
@Tag(name = "Teacher controller", description = "the controller giving information about all teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/{id}")
    @Operation(
            summary = "ont teacher method",
            description = "method that returns information about one teacher"
    )
    public ResponseEntity<?> fetchTeacherById(@PathVariable @Parameter(description = "id teacher") Long id) {
        return teacherService.fetchTeacherById(id);
    }

    @GetMapping
    @Operation(
            summary = "all teacher method",
            description = "method that returns information about all teachers"
    )
    public ResponseEntity<?> fetchTeacher() {
        return teacherService.fetchTeacher();
    }

    @PostMapping
    @Operation(
            summary = "create teacher method",
            description = "method that create new teacher"
    )
    public ResponseEntity<?> createTeacher(
            @RequestBody @Parameter(description = " info about new teacher") Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }
}