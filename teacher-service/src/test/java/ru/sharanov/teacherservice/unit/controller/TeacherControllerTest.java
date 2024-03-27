package ru.sharanov.teacherservice.unit.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.sharanov.teacherservice.controllers.TeacherController;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.repositories.TeacherRepository;
import ru.sharanov.teacherservice.services.TeacherService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController controller;


    @Test
    void fetchTeacherById_ReturnsTeacherEntity() {

        var teacher = Teacher.builder()
                .name("Иван Петрович")
                .direction("Русский")
                .age(28)
                .salary(45000)
                .schoolId(1)
                .build();
//        Mockito.doReturn(teacher).when(teacherRepository.findById(1L));
        Mockito.when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        var responseEntity = controller.fetchTeacherById(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(teacher, responseEntity.getBody());


//        List<FioResponse> fioResponses =
//                List.of(FioResponse.builder().fullName("������").gender(Gender.MALE).build(),
//                        FioResponse.builder().fullName("��������").gender(Gender.FEMALE).build());
//        when(teacherService.getFioEntity("����", 2)).thenReturn(fioResponses);
//        ResultActions response = mockMvc.perform(post("/api/name")
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("fio", "����")
//                .param("count", String.valueOf(2)));
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.[0].fullName").value("������"))
//                .andExpect(jsonPath("$.[0].gender").value("MALE"))
//                .andExpect(jsonPath("$.[1].fullName").value("��������"))
//                .andExpect(jsonPath("$.[1].gender").value("FEMALE"));
    }


}
