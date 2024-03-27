package ru.sharanov.teacherservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sharanov.teacherservice.dto.StudentResponse;
import ru.sharanov.teacherservice.dto.TeacherResponse;
import ru.sharanov.teacherservice.model.School;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.repositories.TeacherRepository;
import ru.sharanov.teacherservice.services.TeacherService;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/teachers")
@Tag(name = "Teacher controller", description = "the controller giving information about all teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;

    @GetMapping("/{id}")
    @Operation(
            summary = "ont teacher method",
            description = "method that returns information about one teacher"
    )
    public ResponseEntity<?> fetchTeacherById(@PathVariable @Parameter(description = "id teacher") Long id) {
        Optional<TeacherResponse> teacherResponse = teacherService.fetchTeacherById(id);
        if (teacherResponse.isPresent()) {
                       return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
        Optional<TeacherResponse> teacherResponse = teacherService.createTeacher(teacher);
        if (teacherResponse.isPresent()) {
            return new ResponseEntity<>(teacherResponse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}