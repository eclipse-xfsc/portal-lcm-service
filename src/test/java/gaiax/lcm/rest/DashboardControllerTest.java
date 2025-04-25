package gaiax.lcm.rest;

import gaiax.lcm.model.DashboardServiceResponse;
import gaiax.lcm.model.DatasetResponse;
import gaiax.lcm.model.Statistics;
import gaiax.lcm.model.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
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
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DashboardController service;

    @Test
    void getTransactionsUnauthorized() throws Exception {
        ResponseEntity<TransactionResponse> rs =
                ResponseEntity.ok(new TransactionResponse(
                        Collections.singletonList(
                                new TransactionResponse.
                                        TransactionItem("id", "title", "subtitle", "2022-08-08"))));
        when(service.getTransactions(any())).thenReturn(rs);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lcm-service/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")));
    }

    @Test
    void getDatasets() throws Exception {
        ResponseEntity<DatasetResponse> rs =
                ResponseEntity.ok(new DatasetResponse(
                        Collections.singletonList(
                                new DatasetResponse
                                        .DatasetItem(
                                                "id",
                                        "name",
                                        "previewImg",
                                        "provider.url",
                                        "logo",
                                        "description",
                                        true,
                                        true,
                                        "deployed"))));
        when(service.getDatasets(any())).thenReturn(rs);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lcm-service/datasets"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("deployed")));
    }

    @Test
    void getSP() throws Exception {
        ResponseEntity<DashboardServiceResponse> rs =
                ResponseEntity.ok(new DashboardServiceResponse(
                        Collections.singletonList(
                                new DashboardServiceResponse.ServiceItem(
                                        "id",
                                        "name",
                                        "previewImg",
                                        "provider.url",
                                        "logo",
                                        "description",
                                        true,
                                        true,
                                        "deployed"))));
        when(service.getSP(any())).thenReturn(rs);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lcm-service/sp"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("deployed")));
    }

    @Test
    void getServices() throws Exception {
        ResponseEntity<DashboardServiceResponse> rs =
                ResponseEntity.ok(new DashboardServiceResponse(
                        Collections.singletonList(
                                new DashboardServiceResponse.ServiceItem(
                                        "id",
                                        "name",
                                        "previewImg",
                                        "provider.url",
                                        "logo",
                                        "description",
                                        true,
                                        true,
                                        "deployed"))));
        when(service.getServices(any())).thenReturn(rs);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lcm-service/services"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("deployed")));
    }

    @Test
    void getStatistics() throws Exception {
        ResponseEntity<Statistics> rs =
                ResponseEntity.ok(new Statistics());
        when(service.getStatistics(any())).thenReturn(rs);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lcm-service/statistics"))
                .andExpect(status().isOk());
    }
}
