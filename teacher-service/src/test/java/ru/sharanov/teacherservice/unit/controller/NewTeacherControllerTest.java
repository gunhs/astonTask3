package ru.sharanov.teacherservice.unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.sharanov.teacherservice.controllers.TeacherController;
import ru.sharanov.teacherservice.dto.TeacherResponse;
import ru.sharanov.teacherservice.model.School;
import ru.sharanov.teacherservice.model.Student;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.services.TeacherService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class NewTeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    private Teacher teacher;
    private TeacherResponse teacherResponse;

    @BeforeEach
    public void init(){
        teacher = Teacher.builder()
                .name("Павел Артемович")
                .age(56)
                .direction("Английский")
                .schoolId(1)
                .studentList(List.of(1 ,2))
                .build();
        teacherResponse = TeacherResponse.builder()
                .name("Павел Артемович")
                .age(56)
                .direction("Английский")
                .school(new School())
                .students(List.of(new Student(1L, "Олег", 12, "MALE", 1),
                        new Student(2L, "Саша", 11, "MALE", 1)))
                .build();
    }

    @Test
    void fetchTeacherById_ReturnsTeacherEntity() throws Exception {
        given(teacherService.createTeacher(ArgumentMatchers.any())).willAnswer(i->i.getArgument(0));
        ResultActions response = mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper))

    }

}
