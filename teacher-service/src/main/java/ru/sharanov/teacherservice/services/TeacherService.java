package ru.sharanov.teacherservice.services;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sharanov.teacherservice.dto.TeacherResponse;
import ru.sharanov.teacherservice.model.School;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> createTeacher(Teacher student){
        try{
            return new ResponseEntity<>(teacherRepository.save(student), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> fetchTeacherById(long id){
        Optional<Teacher> student =  teacherRepository.findById(id);
        if(student.isPresent()){
            School school = restTemplate.getForObject("http://localhost:8080/school/" + student.get().getSchoolId(), School.class);
            TeacherResponse studentResponse = new TeacherResponse(
                    student.get().getId(),
                    student.get().getName(),
                    student.get().getAge(),
                    student.get().getGender(),
                    school
            );
            return new ResponseEntity<>(studentResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Student Found",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> fetchTeacher(){
        List<Teacher> teachers = teacherRepository.findAll();
        if(!teachers.isEmpty()){
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No Students",HttpStatus.NOT_FOUND);
        }
    }
}