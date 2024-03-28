package ru.sharanov.teacherservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.sharanov.teacherservice.dto.TeacherResponse;
import ru.sharanov.teacherservice.dto.TeachersResponse;
import ru.sharanov.teacherservice.mapper.TeacherMapper;
import ru.sharanov.teacherservice.model.Student;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.repositories.TeacherRepository;
import ru.sharanov.teacherservice.services.TeacherService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@EmbeddedKafka(topics = "test-topic")
@ActiveProfiles({"test"})
@TestPropertySource(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
public class TeacherControllerIntegrationTest {

    private int port = 8086;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeacherService teacherService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher teacher1;
    private Teacher teacher2;
    private TeacherResponse teacherResponse;
    private TeachersResponse teachersResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Student student1 = new Student(1);
        Student student2 = new Student(2);
        List<Student> students = List.of(student1, student2);
        teacher1 = Teacher.builder()
                .name("Дмитрий Иванов")
                .age(35)
                .salary(45000)
                .direction("Математика")
                .schoolId(1)
                .build();
        teacher2 = Teacher.builder()
                .name("Анна Викторовна")
                .age(46)
                .salary(51000)
                .direction("Физика")
                .schoolId(2)
                .build();
        teacherResponse = TeacherMapper.convertTeachertoTeacherResponse(teacher1);
        teachersResponse = new TeachersResponse();
        teachersResponse.getTeachers().add(teacher1);
        teacherRepository.deleteAll();
    }

    @Test
    public void testFetchTeacherById_Success() throws Exception {
        teacher1 = teacherRepository.save(teacher1);
        when(teacherService.fetchTeacherById(teacher1.getId()))
                .thenReturn(Optional.of(teacherResponse));

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                new URI("http://localhost:" + port + "/teachers/" + teacher1.getId()), String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(objectMapper.writeValueAsString(teacherResponse), responseEntity.getBody());
    }

    @Test
    public void testFetchTeacherById_NotFound() throws Exception {
        int teacherId = 1;
        when(teacherService.fetchTeacherById(teacherId))
                .thenReturn(Optional.empty());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                new URI("http://localhost:" + port + "/teachers/" + teacherId), String.class);

        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testFetchTeacher_Success() throws Exception {
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        TeachersResponse teachersResponse = new TeachersResponse();
        teachersResponse.getTeachers().add(teacher1);
        teachersResponse.getTeachers().add(teacher2);
        when(teacherService.fetchTeacher())
                .thenReturn(Optional.of(teachersResponse));

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                new URI("http://localhost:" + port + "/teachers"), String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(objectMapper.writeValueAsString(teachersResponse), responseEntity.getBody());
    }

    @Test
    public void testFetchTeacher_Empty() throws Exception {
        when(teacherService.fetchTeacher())
                .thenReturn(Optional.empty());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                new URI("http://localhost:" + port + "/teachers"), String.class);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals("[]", responseEntity.getBody());
    }

    @Test
    public void testCreateTeacher_Success() throws Exception {
        when(teacherService.createTeacher(teacher1)).thenReturn(Optional.of(teacherResponse));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(teacher1), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                new URI("http://localhost:" + port + "/teachers"),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(objectMapper.writeValueAsString(teacherResponse), responseEntity.getBody());
    }

    @Test
    public void testCreateTeacher_InternalServerError() throws Exception {
        when(teacherService.createTeacher(teacher1)).thenReturn(Optional.empty());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(teacher1), headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(
                new URI("http://localhost:" + port + "/teachers"),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        assertEquals(500, responseEntity.getStatusCodeValue());
    }
}