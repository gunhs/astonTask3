package ru.sharanov.teacherservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.client.RestTemplate;
import ru.sharanov.teacherservice.AbstractAPI;
import ru.sharanov.teacherservice.dto.TeacherResponse;
import ru.sharanov.teacherservice.model.School;
import ru.sharanov.teacherservice.model.Teacher;
import ru.sharanov.teacherservice.repositories.TeacherRepository;
import ru.sharanov.teacherservice.services.TeacherService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc(addFilters = false)
class TeacherServiceTest extends AbstractAPI {

    @InjectMocks
    private TeacherService teacherService = new TeacherService();
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private RestTemplate restTemplateBean;

    @Test
    @DisplayName("Method fetch teacher")
    void fetchTeacherByIdTest() {
        Teacher teacher = Teacher.builder()
                .id(1L)
                .age(40)
                .direction("����������")
                .name("������ ���������� �������")
                .schoolId(1)
                .salary(35000)
                .build();
        when(teacherRepository.findById(1L)).thenReturn(Optional.ofNullable(teacher));
        School school = School.builder()
                .schoolName("Moscow Principail School")
                .id(1L)
                .location("Moscow")
                .schoolName("First School")
                .build();
        when(restTemplateBean.getForObject("http://localhost:8080/school/" + teacher.getSchoolId(), School.class))
                .thenReturn(school);
                TeacherResponse teacherResponse = TeacherResponse.builder()
                .id(1L)
                .age(40)
                .direction("����������")
                .name("������ ���������� �������")
                .school(school)
                .salary(35000)
                .build();

        Assertions.assertEquals(teacherResponse, teacherService.fetchTeacherById(1L).getBody());
    }

//    @Test
//    @DisplayName("����� ��������� ��������� ������� �� �������")
//    void getAddressEntityTest() {
//        List<AddressResponse> addressResponses =
//                List.of(
//                        AddressResponse.builder().fullAddress("� ������, �-� ���������, �� �����������").city("������")
//                                .country("������").postalCode(null).build(),
//                        AddressResponse.builder().fullAddress("108813, � ������, ��������� ����������, � ����������, " +
//                                        "�������������� �����, �� ��������")
//                                .city("����������").country("������").postalCode("108813").build());
//        DadataResponseAddress dadataResponse =
//                DadataResponseAddress.builder()
//                        .suggestions(
//                                List.of(SuggestionAddress.builder()
//                                                .unrestrictedValue("� ������, �-� ���������, �� �����������").data(
//                                                        AddressDadadataResponse.builder().country("������").city("������")
//                                                                .postalCode(null).build()).build(),
//                                        SuggestionAddress.builder().unrestrictedValue("108813, � ������, ��������� ����������, " +
//                                                        "� ����������, �������������� �����, �� ��������")
//                                                .data(
//                                                        AddressDadadataResponse.builder().country("������").city("����������")
//                                                                .postalCode("108813").build()).build()
//                                )).build();
//        when(dadadataService.getAddressSuggestions("������ �����", 2)).thenReturn(dadataResponse);
//        List<AddressResponse> addressResponseFromMethod = teacherService.getAddressEntity("������ �����", 2);
//        Assertions.assertThat(addressResponseFromMethod).isNotNull();
//        org.junit.jupiter.api.Assertions.assertEquals(addressResponses,
//                teacherService.getAddressEntity("������ �����", 2));
//    }
}
