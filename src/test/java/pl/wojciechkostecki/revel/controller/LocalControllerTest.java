package pl.wojciechkostecki.revel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.repository.LocalRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class LocalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //    @Autowired
    @MockBean
    private LocalRepository mockRepository;

//    @Test
//    void getLocalTest() throws Exception {
//        //given
//        Local local = new Local();
//        local.setName("Bue");
//        localRepository.save(local);
//        //when
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/" + local.getId()))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().is(200))
//                .andReturn();
//        //then
//        Local local2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Local.class);
//        org.assertj.core.api.Assertions.assertThat(local2).isNotNull();
//        org.assertj.core.api.Assertions.assertThat(local2.getId()).isEqualTo(local.getId());
//        org.assertj.core.api.Assertions.assertThat(local2.getName()).isEqualTo("Bue");
//
//    }

    @Test
    void getLocalByIdTest() throws Exception {
        Local local = new Local();
        local.setId(1L);
        local.setName("Pijalnia");

        when(mockRepository.findById(local.getId())).thenReturn(Optional.of(local));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/locals/" + local.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(local.getName())));

        verify(mockRepository, times(1)).findById(1L);
    }

    @Test
    void getAllLocalsTest() throws Exception {
        Local local = new Local();
        local.setName("Bue");
        Local local2 = new Local();
        local2.setName("Bue Bue");
        List<Local> locals = Arrays.asList(local, local2);

        when(mockRepository.findAll()).thenReturn(locals);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/locals"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Bue")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Bue Bue")));

        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void getLocalsByNameTest() {

    }

    @Test
    void createLocalTest() {
    }

    @Test
    void updateLocalTest() {
    }

    @Test
    void deleteLocalTest() throws Exception {
        Local local = new Local();
        local.setId(1L);
        local.setName("Kuźnia");

        doNothing().when(mockRepository).deleteById(1l);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locals/" + local.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(mockRepository,times(1)).deleteById(1L);
    }
}