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
        //given
        Local local = new Local();
        local.setName("Kuźnia");
        localRepository.save(local);
        Local local2 = new Local();
        local2.setName("Pijalnia");
        localRepository.save(local2);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/search?name=bue"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        Local[] locals = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local[].class);
        Assertions.assertThat(locals).isEmpty();
    }

    @Test
    void createLocalTest() throws Exception {
        //given
        Local local = new Local();
        local.setName("Stary Browar");
        local.setOpeningTime(LocalTime.of(16, 0));
        local.setClosingTime(LocalTime.of(23, 30));
        localRepository.save(local);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/locals")
                .content(objectMapper.writeValueAsString(local))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        //then
        Local savedLocal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local.class);
        Assertions.assertThat(savedLocal).isNotNull();
        Assertions.assertThat(savedLocal.getId()).isEqualTo(local.getId());
        Assertions.assertThat(savedLocal.getName()).isEqualTo(local.getName());
        Assertions.assertThat(savedLocal.getOpeningTime()).isEqualTo(LocalTime.parse("16:00:00"));
        Assertions.assertThat(savedLocal.getClosingTime()).isEqualTo(LocalTime.parse("23:30:00"));
        Assertions.assertThat(savedLocal.getMenu()).isNull();
    }

    @Test
    void updateLocalTest() throws Exception {
        //given
        Local local = new Local();
        local.setName("Stary Browar");
        localRepository.save(local);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/" + local.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Local originalLocal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local.class);
        org.assertj.core.api.Assertions.assertThat(originalLocal.getId()).isEqualTo(local.getId());
        org.assertj.core.api.Assertions.assertThat(originalLocal.getName()).isEqualTo(local.getName());

        originalLocal.setName("Pijalnia");

        //when
        MvcResult mvcResultAfterChange = mockMvc.perform(MockMvcRequestBuilders.put("/api/locals/" + local.getId())
                .content(objectMapper.writeValueAsString(originalLocal))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //then
        Local changedLocal = objectMapper.readValue(mvcResultAfterChange.getResponse().getContentAsString(), Local.class);
        Assertions.assertThat(changedLocal.getId()).isEqualTo(originalLocal.getId());
        Assertions.assertThat(changedLocal.getName()).isEqualTo("Pijalnia");
    }

    @Test
    void deleteLocalTest() throws Exception {
        Local local = new Local();
        local.setName("Kuźnia");
        localRepository.save(local);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/" + local.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(local.getName())));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locals/" + local.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}