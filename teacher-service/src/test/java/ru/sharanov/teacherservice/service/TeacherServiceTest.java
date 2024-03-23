package ru.sharanov.teacherservice.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@RequiredArgsConstructor
class TeacherServiceTest extends AbstractAPI {

    private final TeacherService teacherService;
    @MockBean
    private final TeacherRepository teacherRepository;
    @MockBean
    private final RestTemplate restTemplate;

    @Test
    @DisplayName("Method fetch teacher")
    void fetchTeacherByIdTest() {
        Teacher teacher = Teacher.builder()
                .id(1L)
                .age(40)
                .direction("Математика")
                .name("Сергей Викторович Олейнин")
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
        when(restTemplate.getForObject("http://localhost:8080/school/" + teacher.getSchoolId(), School.class))
                .thenReturn(school);
        TeacherResponse teacherResponse = TeacherResponse.builder()
                .id(1L)
                .age(40)
                .direction("Математика")
                .name("Сергей Викторович Олейнин")
                .school(school)
                .salary(35000)
                .build();

        Assertions.assertEquals(teacherResponse, teacherService.fetchTeacherById(1L));
    }

//    @Test
//    @DisplayName("Метод получения подсказок адресов из сервиса")
//    void getAddressEntityTest() {
//        List<AddressResponse> addressResponses =
//                List.of(
//                        AddressResponse.builder().fullAddress("г Москва, р-н Гольяново, ул Хабаровская").city("Москва")
//                                .country("Россия").postalCode(null).build(),
//                        AddressResponse.builder().fullAddress("108813, г Москва, поселение Московский, г Московский, " +
//                                        "Новомосковский округ, ул Хабарова")
//                                .city("Московский").country("Россия").postalCode("108813").build());
//        DadataResponseAddress dadataResponse =
//                DadataResponseAddress.builder()
//                        .suggestions(
//                                List.of(SuggestionAddress.builder()
//                                                .unrestrictedValue("г Москва, р-н Гольяново, ул Хабаровская").data(
//                                                        AddressDadadataResponse.builder().country("Россия").city("Москва")
//                                                                .postalCode(null).build()).build(),
//                                        SuggestionAddress.builder().unrestrictedValue("108813, г Москва, поселение Московский, " +
//                                                        "г Московский, Новомосковский округ, ул Хабарова")
//                                                .data(
//                                                        AddressDadadataResponse.builder().country("Россия").city("Московский")
//                                                                .postalCode("108813").build()).build()
//                                )).build();
//        when(dadadataService.getAddressSuggestions("москва хабар", 2)).thenReturn(dadataResponse);
//        List<AddressResponse> addressResponseFromMethod = teacherService.getAddressEntity("москва хабар", 2);
//        Assertions.assertThat(addressResponseFromMethod).isNotNull();
//        org.junit.jupiter.api.Assertions.assertEquals(addressResponses,
//                teacherService.getAddressEntity("москва хабар", 2));
//    }
}
