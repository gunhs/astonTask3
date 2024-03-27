//package ru.sharanov.teacherservice.Integration.controllers;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class TeacherControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//
//
//    @Test
//    void fetchTeacherById() throws Exception {
//        mvc.perform(get("http://localhost:8088/teachers")).andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    void fetchTeacher() {
//    }
//
//    @Test
//    void createTeacher() {
//    }
//}