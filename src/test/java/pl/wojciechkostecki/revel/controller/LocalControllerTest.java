package pl.wojciechkostecki.revel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.repository.LocalRepository;

import javax.transaction.Transactional;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LocalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocalRepository localRepository;

    @Test
    void getLocalTest() throws Exception {
        //given
        Local newLocal = new Local();
        newLocal.setName("Pijalnia");
        newLocal.setOpeningTime(LocalTime.of(16, 0));
        newLocal.setClosingTime(LocalTime.of(23, 30));
        localRepository.save(newLocal);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/" + newLocal.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Local local = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local.class);
        org.assertj.core.api.Assertions.assertThat(local).isNotNull();
        org.assertj.core.api.Assertions.assertThat(local.getId()).isEqualTo(newLocal.getId());
        org.assertj.core.api.Assertions.assertThat(local.getName()).isEqualTo("Pijalnia");
        org.assertj.core.api.Assertions.assertThat(local.getOpeningTime()).isEqualTo(LocalTime.parse("16:00:00"));
        org.assertj.core.api.Assertions.assertThat(local.getClosingTime()).isEqualTo(LocalTime.parse("23:30:00"));
    }

    @Test
    void getAllLocalsTest() throws Exception {
        //given
        Local local = new Local();
        local.setName("Bue");
        localRepository.save(local);
        Local local2 = new Local();
        local2.setName("Bue Bue");
        localRepository.save(local2);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/locals"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        Local[] locals = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local[].class);
        Assertions.assertThat(locals).isNotNull();
        Assertions.assertThat(locals).hasSize(2);
        Assertions.assertThat(locals[0].getName()).isEqualTo(local.getName());
        Assertions.assertThat(locals[1].getName()).isEqualTo(local2.getName());
    }

    @Test
    void getLocalsByNameTest() throws Exception {
        //given
        Local local = new Local();
        local.setName("Bue");
        localRepository.save(local);
        Local local2 = new Local();
        local2.setName("Pijalnia");
        localRepository.save(local2);
        Local local3 = new Local();
        local3.setName("Bue Bue");
        localRepository.save(local3);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/search?name=bue"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        Local[] locals = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local[].class);
        Assertions.assertThat(locals).isNotNull();
        Assertions.assertThat(locals).hasSize(2);
        Assertions.assertThat(locals[0].getName()).containsIgnoringCase("bue");
        Assertions.assertThat(locals[1].getName()).containsIgnoringCase("bue");
    }

    @Test
    void getLocalsByNameWhenDoesNotExistTest() throws Exception {
        Local local = new Local();
        local.setName("Kuźnia");
        localRepository.save(local);
        Local local2 = new Local();
        local2.setName("Pijalnia");
        localRepository.save(local2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/search?name=bue"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", empty()));
    }

    @Test
    void createLocalTest() throws Exception {
        Local local = new Local();
        local.setName("Stary Browar");
        localRepository.save(local);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/locals")
                .content(objectMapper.writeValueAsString(local))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(local.getName())));
    }

    @Test
    void updateLocalTest() throws Exception {
        Local local = new Local();
        local.setName("Browar");
        localRepository.save(local);

        Local modifiedLocal = new Local();
        modifiedLocal.setName("Stary Browar");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/locals/" + local.getId())
                .content(objectMapper.writeValueAsString(modifiedLocal))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Stary Browar"));
    }

    @Test
    void deleteLocalTest() throws Exception {
        Local local = new Local();
        local.setName("Kuźnia");
        localRepository.save(local);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locals/" + local.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}