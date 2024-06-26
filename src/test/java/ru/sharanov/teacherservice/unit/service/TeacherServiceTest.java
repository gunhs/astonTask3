package ru.sharanov.teacherservice.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;
import ru.sharanov.teacherservice.configurations.RestConfigure;
import ru.sharanov.teacherservice.dto.StudentsResponse;
import ru.sharanov.teacherservice.dto.TeacherResponse;
import ru.sharanov.teacherservice.dto.TeachersResponse;
import ru.sharanov.teacherservice.mapper.TeacherMapper;
import ru.sharanov.teacherservice.model.School;
import ru.sharanov.teacherservice.model.Student;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.repositories.StudentRepository;
import ru.sharanov.teacherservice.repositories.TeacherRepository;
import ru.sharanov.teacherservice.services.TeacherService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestConfigure restConfigure;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher teacher1;
    private Teacher teacher2;
    private Student student1;
    private Student student2;
    private TeacherResponse teacherResponse;
    private List<Teacher> teachers;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        teachers = List.of(teacher1, teacher2);
        student1 = new Student(1L, teachers);
        student2 = new Student(2L, List.of(teacher1));
        teacher1.setStudent(List.of(student1, student2));
        teacher2.setStudent(List.of(student1));
        teacherResponse = TeacherMapper.convertTeachertoTeacherResponse(teacher1);
     }

    @Test
    public void testCreateTeacher_Success() {
        when(teacherRepository.save(teacher1)).thenReturn(teacher1);
        Optional<TeacherResponse> teacherResponse = teacherService.createTeacher(teacher1);

        assertTrue(teacherResponse.isPresent());
        assertEquals(this.teacherResponse, teacherResponse.get());
        verify(teacherRepository, times(1)).save(teacher1);
    }

    @Test
    public void testCreateTeacher_InternalServerError() {
        when(teacherRepository.save(teacher1)).thenThrow(RuntimeException.class);

        Optional<TeacherResponse> teacherResponse = teacherService.createTeacher(teacher1);

        assertFalse(teacherResponse.isPresent());
        verify(teacherRepository, times(1)).save(teacher1);
    }

    @Test
    public void testFetchTeacherById_Success() {
        int teacherId = 1;
        teacher1.setId(teacherId);
        School school = School.builder().schoolName("First").principalName("First School").location("Moscow").build();
        List<Student> students = List.of(student1, student2);
        StudentsResponse studentsResponse = StudentsResponse.builder().students(students).build();
        this.teacherResponse.setId(teacherId);
        this.teacherResponse.setSchool(school);
        this.teacherResponse.setStudents(students);
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher1));
        when(restTemplate.getForObject(restConfigure.getSchool() + teacher1.getSchoolId(), School.class)).thenReturn(school);
        when(restTemplate.getForObject(restConfigure.getStudent() + teacher1.getId(), StudentsResponse.class))
                .thenReturn(studentsResponse);

        Optional<TeacherResponse> teacherResponse = teacherService.fetchTeacherById(teacherId);

        assertTrue(teacherResponse.isPresent());
        assertEquals(this.teacherResponse, teacherResponse.get());
        assertEquals(school, teacherResponse.get().getSchool());
        assertEquals(studentsResponse.getStudents(), teacherResponse.get().getStudents());
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(restTemplate, times(1))
                .getForObject(restConfigure.getSchool() + teacher1.getSchoolId(), School.class);
        verify(restTemplate, times(1))
                .getForObject(restConfigure.getStudent() + teacher1.getId(), StudentsResponse.class);
    }

    @Test
    public void testFetchTeacherById_NotFound() {
        int teacherId = 1;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        Optional<TeacherResponse> teacherResponse = teacherService.fetchTeacherById(teacherId);

        assertFalse(teacherResponse.isPresent());
        verify(teacherRepository, times(1)).findById(teacherId);
        verifyNoInteractions(restTemplate);
    }

    @Test
    public void testFetchTeacher_Success() {
        when(teacherRepository.findAll()).thenReturn(teachers);

        Optional<TeachersResponse> teachersResponse = teacherService.fetchTeacher();

        assertTrue(teachersResponse.isPresent());
        assertEquals(teachers, teachersResponse.get().getTeachers());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testFetchTeacher_Empty() {
        when(teacherRepository.findAll()).thenReturn(Collections.emptyList());

        Optional<TeachersResponse> teachersResponse = teacherService.fetchTeacher();

        assertFalse(teachersResponse.isPresent());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testCreateStudentAndAssignTeachers_Success() {
        Long studentId = 1L;
        List<Integer> teacherIds = List.of(1, 2);
        List<Teacher> teachers = List.of(teacher1, teacher2);
        when(teacherRepository.findAllById(teacherIds)).thenReturn(teachers);
        when(studentRepository.save(student1)).thenReturn(student1);

        teacherService.createStudentAndAssignTeachers(studentId, teacherIds);

        assertEquals(studentId, student1.getId());
        assertEquals(teachers, student1.getTeacher());
        verify(teacherRepository, times(1)).findAllById(teacherIds);
        verify(studentRepository, times(1)).save(ArgumentMatchers.any());
    }
}
