package ru.sharanov.teacherservice.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import ru.sharanov.teacherservice.AbstractAPI;

@AutoConfigureMockMvc(addFilters = false)
class TeacherControllerTest extends AbstractAPI {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private TeacherService teacherService;
//
//    @Test
//    @DisplayName("���������� ��������� ��������� �����")
//    void getSuggestionsNameTest() throws Exception {
//
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
//    }
//
//    @Test
//    @DisplayName("���������� ��������� ��������� ������")
//    void getSuggestionsAddressTest() throws Exception {
//        List<AddressResponse> addressResponses =
//                List.of(
//                        AddressResponse.builder().fullAddress("� ������, �-� ���������, �� �����������").city("������")
//                                .country("������").postalCode("108813").build(),
//                        AddressResponse.builder().fullAddress("108813, � ������, ��������� ����������, � ����������," +
//                                        " �������������� �����, �� ��������")
//                                .city("����������").country("������").postalCode("108813").build());
//        when(teacherService.getAddressEntity("������ �����", 2)).thenReturn(addressResponses);
//        ResultActions response = mockMvc.perform(get("/api/address")
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("address", "������ �����")
//                .param("count", String.valueOf(2)));
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.[0].fullAddress")
//                        .value("� ������, �-� ���������, �� �����������"))
//                .andExpect(jsonPath("$.[0].country").value("������"))
//                .andExpect(jsonPath("$.[0].city").value("������"))
//                .andExpect(jsonPath("$.[0].postalCode").value("108813"))
//                .andExpect(jsonPath("$.[1].fullAddress").value("108813, � ������," +
//                        " ��������� ����������, � ����������, �������������� �����, �� ��������"))
//                .andExpect(jsonPath("$.[1].country").value("������"))
//                .andExpect(jsonPath("$.[1].city").value("����������"))
//                .andExpect(jsonPath("$.[1].postalCode").value("108813"));
//    }
}
