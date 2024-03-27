//package ru.sharanov.teacherservice.unit.repository;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import ru.sharanov.teacherservice.model.Teacher;
//import ru.sharanov.teacherservice.repositories.TeacherRepository;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//public class TeacherRepositoryTest {
//
//    @Autowired
//    private TeacherRepository teacherRepository;
//
//    @Test
//    public void teacherSaveTest() {
//
//        Teacher teacher = Teacher.builder()
//                .name("Дмитрий Иванов")
//                .age(40)
//                .direction("Математика")
//                .salary(35000)
//                .build();
//
//        Teacher savedTeacher = teacherRepository.save(teacher);
//        Assertions.assertThat(savedTeacher).isNotNull();
//        Assertions.assertThat(savedTeacher.getId()).isGreaterThan(0);
//    }
//
//}
