package pl.wojciechkostecki.revel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.repository.LocalRepository;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class LocalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocalRepository localRepository;

    @Test
    void createLocal() {
    }

    @Test
    void getAllLocals() {
    }

    @Test
    void getLocal() throws Exception {
        //given
        Local local = new Local();
        local.setName("Bue");
        localRepository.save(local);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/"+local.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Local local2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local.class);
        org.assertj.core.api.Assertions.assertThat(local2).isNotNull();
        org.assertj.core.api.Assertions.assertThat(local2.getId()).isEqualTo(local.getId());
        org.assertj.core.api.Assertions.assertThat(local2.getName()).isEqualTo("Bue");
    }

    @Test
    void getLocalsByName() {
    }

    @Test
    void updateLocal() {
    }

    @Test
    void deleteLocal() {
    }
}