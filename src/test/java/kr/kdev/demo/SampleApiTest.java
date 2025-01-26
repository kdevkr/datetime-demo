package kr.kdev.demo;

import kr.kdev.demo.api.SampleApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(SampleApi.class)
class SampleApiTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenYear_whenGetStatOfYear_thenEqualsFromTo() {
        Assertions.assertDoesNotThrow(() -> {
            // NOTE: SampleApi.getStatOfYear
            mockMvc.perform(get("/api/stat/{year}", 2025))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.from").value("2025.01.01"))
                    .andExpect(jsonPath("$.to").value("2025.12.31"));
        });
    }

    @Test
    void givenYear_whenGetStatOfYearMonth_thenEqualsFromTo() {
        Assertions.assertDoesNotThrow(() -> {
            // NOTE: SampleApi.getStatOfYearMonth
            mockMvc.perform(get("/api/stat/{year}/{month}", 2025, 2))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.from").value("2025.02.01"))
                    .andExpect(jsonPath("$.to").value("2025.02.28"));
        });
    }
}
