package ru.sharanov.teacherservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sharanov.teacherservice.dto.StudentResponse;
import ru.sharanov.teacherservice.dto.TeacherResponse;
import ru.sharanov.teacherservice.model.School;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> createTeacher(Teacher teacher) {
        try {
            return new ResponseEntity<>(teacherRepository.save(teacher), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> fetchTeacherById(Integer id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            School school = restTemplate.getForObject("http://localhost:8080/school/" + teacher.get().getSchoolId(), School.class);
            StudentResponse students = restTemplate
                    .getForObject("http://localhost:8082/student/teacher/" + teacher.get().getId(), StudentResponse.class);
            TeacherResponse teacherResponse = new TeacherResponse(
                    teacher.get().getId(),
                    teacher.get().getName(),
                    teacher.get().getAge(),
                    teacher.get().getDirection(),
                    teacher.get().getSalary(),
                    school,
                    students.getStudents()
            );
            return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Student Found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> fetchTeacher() {
        List<Teacher> teachers = teacherRepository.findAll();
        if (!teachers.isEmpty()) {
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No teachers", HttpStatus.NOT_FOUND);
        }
    }

    public List<Teacher> getTeachersByStudentId(Long studentId) {
        return teacherRepository.findByStudentsId(studentId);
    }

}