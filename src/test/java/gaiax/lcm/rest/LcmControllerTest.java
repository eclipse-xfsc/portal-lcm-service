package gaiax.lcm.rest;

import gaiax.lcm.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unused")
@SpringBootTest(properties = {
        "check-keycloak-token=false"
})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
class LcmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LcmController service;

    @Test
    void serviceOverview() throws Exception {
        ResponseEntity rs = ResponseEntity.ok().build();
        when(service.serviceOverview(any(), any())).thenReturn(rs);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lcm-service/service/overview?serviceId=1"))
                .andExpect(status().isOk());
    }

    @Test
    void lcmConfiguration() throws Exception {
        ResponseEntity rs = ResponseEntity.ok().build();
        GetServicesConfigRq rq = new GetServicesConfigRq();
        String content = JsonUtil.asJsonString(rq);
        when(service.lcmConfiguration(any(), any())).thenReturn(rs);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/lcm-service/service/configuration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void updateConfiguration() throws Exception {
        ResponseEntity rs = ResponseEntity.ok().build();
        UpdateConfigRq rq = new UpdateConfigRq();
        String content = JsonUtil.asJsonString(rq);
        when(service.updateConfiguration(any(), any())).thenReturn(rs);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/lcm-service/service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void templates() throws Exception {
        ResponseEntity rs = ResponseEntity.ok().build();
        when(service.templates(any(), any(), any())).thenReturn(rs);
        GetServicesConfigRq rq = new GetServicesConfigRq();
        String content = JsonUtil.asJsonString(rq);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/lcm-service/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void createServices() throws Exception {
        ResponseEntity rs = ResponseEntity.ok().build();
        when(service.createServices(any(), any())).thenReturn(rs);
        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart("/api/lcm-service/configuration")
                        .file(firstFile))
                .andExpect(status().isOk());
    }

    @Test
    void deleteService() throws Exception {
        ResponseEntity rs = ResponseEntity.ok().build();
        when(service.deleteService(any())).thenReturn(rs);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lcm-service/service/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getLogs() throws Exception {
        ResponseEntity rs = ResponseEntity.ok().build();
        when(service.getLogs(any(), any())).thenReturn(rs);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lcm-service/service/2/logs"))
                .andExpect(status().isOk());
    }
}
