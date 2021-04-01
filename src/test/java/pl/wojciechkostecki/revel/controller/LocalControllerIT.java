package pl.wojciechkostecki.revel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LocalControllerIT {

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
        assertThat(local).isNotNull();
        assertThat(local.getId()).isEqualTo(newLocal.getId());
        assertThat(local.getName()).isEqualTo("Pijalnia");
        assertThat(local.getOpeningTime()).isEqualTo(LocalTime.parse("16:00:00"));
        assertThat(local.getClosingTime()).isEqualTo(LocalTime.parse("23:30:00"));
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
        assertThat(locals).isNotNull();
        assertThat(locals).hasSize(2);
        assertThat(locals[0].getName()).isEqualTo(local.getName());
        assertThat(locals[1].getName()).isEqualTo(local2.getName());
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
        assertThat(locals).isNotNull();
        assertThat(locals).hasSize(2);
        assertThat(locals[0].getName()).containsIgnoringCase("bue");
        assertThat(locals[1].getName()).containsIgnoringCase("bue");
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
        assertThat(locals).isEmpty();
    }

    @Test
    void createLocalTest() throws Exception {
        //given
        Local local = new Local();
        local.setName("Stary Browar");
        local.setOpeningTime(LocalTime.of(16, 0));
        local.setClosingTime(LocalTime.of(23, 30));

        int dbSize = localRepository.findAll().size();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/locals")
                .content(objectMapper.writeValueAsString(local))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        //then

        int dbSizeAfter = localRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize+1);

        Local savedLocal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local.class);
        savedLocal = localRepository.getOne(savedLocal.getId());

        assertThat(savedLocal).isNotNull();
        assertThat(savedLocal.getName()).isEqualTo(local.getName());
        assertThat(savedLocal.getOpeningTime()).isEqualTo(LocalTime.parse("16:00:00"));
        assertThat(savedLocal.getClosingTime()).isEqualTo(LocalTime.parse("23:30:00"));
        assertThat(savedLocal.getMenu()).isNull();
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
        assertThat(originalLocal.getId()).isEqualTo(local.getId());
        assertThat(originalLocal.getName()).isEqualTo(local.getName());

        originalLocal.setName("Pijalnia");

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/locals/" + local.getId())
                .content(objectMapper.writeValueAsString(originalLocal))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        Local changedLocal = localRepository.getOne(originalLocal.getId());

        assertThat(changedLocal.getId()).isEqualTo(originalLocal.getId());
        assertThat(changedLocal.getName()).isEqualTo("Pijalnia");
    }

    @Test
    void deleteLocalTest() throws Exception {
        Local local = new Local();
        local.setName("Kuźnia");
        localRepository.save(local);

        int dbSize = localRepository.findAll().size();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locals/" + local.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        int dbSizeAfter = localRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize-1);
    }
}