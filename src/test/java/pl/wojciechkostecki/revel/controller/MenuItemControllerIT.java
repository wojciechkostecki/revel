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
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.MenuItem;
import pl.wojciechkostecki.revel.model.dto.MenuItemDTO;
import pl.wojciechkostecki.revel.repository.MenuItemRepository;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuItemControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemRepository itemRepository;

    @Test
    void createMenuItemTest() throws Exception{
        //given
        Menu menu = new Menu();
        menu.setName("Menu");
        menuRepository.save(menu);

        MenuItemDTO menuItem = new MenuItemDTO();
        menuItem.setMenuId(menu.getId());
        menuItem.setName("Herbata");

        int dbSize = itemRepository.findAll().size();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/menu-items")
                .content(objectMapper.writeValueAsString(menuItem))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //then
        int dbSizeAfter = itemRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize + 1);

        MenuItem savedItem = itemRepository.getOne
                (objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MenuItem.class).getId());

        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getName()).isEqualTo(menuItem.getName());
    }

    @Test
    void getAllMenuItemsTest() {
    }

    @Test
    void getMenuItemsByNameTest() {
    }

    @Test
    void updateMenuItemTest() {
    }

    @Test
    void deleteMenuItemTest() {
    }
}