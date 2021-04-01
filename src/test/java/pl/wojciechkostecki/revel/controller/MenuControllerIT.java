package pl.wojciechkostecki.revel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
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
import pl.wojciechkostecki.revel.mapper.MenuMapper;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;
import pl.wojciechkostecki.revel.repository.LocalRepository;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void createMenuTest() throws Exception {
        //given
        Local local = new Local();
        local.setName("Kuźnia");
        localRepository.save(local);

        MenuDTO menu = new MenuDTO();
        menu.setName("Stary Browar");
        menu.setLocalId(local.getId());

        int dbSize = menuRepository.findAll().size();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/menus")
                .content(objectMapper.writeValueAsString(menu))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        //then
        int dbSizeAfter = menuRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize+1);

        Menu savedMenu = menuRepository.getOne
                (objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Menu.class).getId());

        assertThat(savedMenu).isNotNull();
        assertThat(savedMenu.getName()).isEqualTo(menu.getName());
        assertThat(savedMenu.getLocal()).isEqualTo(local);
    }


}