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
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;
import pl.wojciechkostecki.revel.repository.LocalRepository;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
        local.setName("Stary Browar");
        localRepository.save(local);

        MenuDTO menu = new MenuDTO();
        menu.setName("Menu Stary Browar");
        menu.setLocalId(local.getId());

        int dbSize = menuRepository.findAll().size();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/menus")
                .content(objectMapper.writeValueAsString(menu))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //then
        int dbSizeAfter = menuRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize + 1);

        Menu savedMenu = menuRepository.getOne
                (objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Menu.class).getId());

        assertThat(savedMenu).isNotNull();
        assertThat(savedMenu.getName()).isEqualTo(menu.getName());
        assertThat(savedMenu.getLocal()).isEqualTo(local);
    }

    @Test
    void createMenuWhenMenuIsAlreadyAssignedToLocalTest() throws Exception {
        //given
        Local local = new Local();
        local.setName("Stary Browar");
        localRepository.save(local);

        MenuDTO menu = new MenuDTO();
        menu.setName("Menu Stary Browar");
        menu.setLocalId(local.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/menus")
                .content(objectMapper.writeValueAsString(menu))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        MenuDTO menu2 = new MenuDTO();
        menu.setName("Menu");
        menu.setLocalId(local.getId());

        int dbSize = menuRepository.findAll().size();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/menus")
                .content(objectMapper.writeValueAsString(menu2))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //then
        int dbSizeAfter = menuRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize);
    }

    @Test
    void createMenuWhenTheLocalCannotBeFoundTest() throws Exception {
        //given
        MenuDTO menu = new MenuDTO();
        menu.setName("Menu Stary Browar");
        menu.setLocalId(3L);

        int dbSize = menuRepository.findAll().size();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/menus")
                .content(objectMapper.writeValueAsString(menu))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());


        //then
        int dbSizeAfter = menuRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize);
    }

    @Test
    void getAllMenus() throws Exception {
        //given
        Menu menu = new Menu();
        menu.setName("Menu Bue Bue");
        menuRepository.save(menu);

        Menu menu2 = new Menu();
        menu2.setName("Menu Stary Browar");
        menuRepository.save(menu2);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/menus"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //then
        Menu[] menus = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Menu[].class);

        assertThat(menus).isNotNull();
        assertThat(menus).hasSize(2);

        assertThat(menus[0].getName()).isEqualTo(menu.getName());
        assertThat(menus[1].getName()).isEqualTo(menu2.getName());
    }

    @Test
    void deleteMenuTest() throws Exception {
        Menu menu = new Menu();
        menu.setName("Menu Bue Bue");
        menuRepository.save(menu);

        int dbSize = menuRepository.findAll().size();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/menus/" + menu.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        int dbSizeAfter = menuRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize - 1);
    }

    @Test
    void updateMenuTest() throws Exception {
        //given
        Local local = new Local();
        local.setName("Stary Browar");
        localRepository.save(local);

        MenuDTO menu = new MenuDTO();
        menu.setName("Menu Stary Browar");
        menu.setLocalId(local.getId());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/menus")
                .content(objectMapper.writeValueAsString(menu))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MenuDTO originalMenu = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MenuDTO.class);
        originalMenu.setName("Menu");
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/menus/" + originalMenu.getId())
                .content(objectMapper.writeValueAsString(originalMenu))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        //then
        Menu changedMenu = menuRepository.getOne(originalMenu.getId());

        assertThat(changedMenu.getId()).isEqualTo(originalMenu.getId());
        assertThat(changedMenu.getName()).isEqualTo("Menu");
    }
}