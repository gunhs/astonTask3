package ru.sharanov.teacherservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.sharanov.teacherservice.dto.StudentResponse;
import ru.sharanov.teacherservice.dto.TeacherResponse;
import ru.sharanov.teacherservice.dto.TeachersResponse;
import ru.sharanov.teacherservice.model.School;
import ru.sharanov.teacherservice.model.Student;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.repositories.StudentRepository;
import ru.sharanov.teacherservice.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;

import static ru.sharanov.teacherservice.mapper.TeacherMapper.convertTeachertOptionalToTeacherResponse;
import static ru.sharanov.teacherservice.mapper.TeacherMapper.convertTeachertoTeacherResponse;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;

    public Optional<TeacherResponse> createTeacher(Teacher teacher) {
        Optional<TeacherResponse> teacherResponse;
        try {
            Teacher savedTeacher = teacherRepository.save(teacher);
            teacherResponse = Optional.of(convertTeachertoTeacherResponse(savedTeacher));
        } catch (Exception e) {
            return Optional.empty();
        }
        return teacherResponse;
    }

    public Optional<TeacherResponse> fetchTeacherById(Integer id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            School school = restTemplate.getForObject("http://localhost:8080/school/" + teacher.get().getSchoolId(), School.class);
            StudentResponse students = restTemplate
                    .getForObject("http://localhost:8082/student/teacher/" + teacher.get().getId(), StudentResponse.class);
            TeacherResponse teacherResponse = convertTeachertOptionalToTeacherResponse(teacher);
            teacherResponse.setSchool(school);
            teacherResponse.setStudents(students.getStudents());
            return Optional.of(teacherResponse);
        } else {
            return Optional.empty();
        }
    }

    public Optional<TeachersResponse> fetchTeacher() {
        List<Teacher> teachers = teacherRepository.findAll();
        TeachersResponse teachersResponse = new TeachersResponse();
        if (!teachers.isEmpty()) {
            teachersResponse.getTeachers().addAll(teachers);
            return Optional.of(teachersResponse);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public void createStudentAndAssignTeachers(Long studentId, List<Integer> teacherIds) {
        Student student = new Student();
        student.setId(studentId);

        List<Teacher> teachers = teacherRepository.findAllById(teacherIds);

        student.setTeacher(teachers);
        studentRepository.save(student);
    }
}