package pl.wojciechkostecki.revel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import pl.wojciechkostecki.revel.mapper.MenuItemMapper;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.MenuItem;
import pl.wojciechkostecki.revel.model.dto.MenuItemDTO;
import pl.wojciechkostecki.revel.repository.MenuItemRepository;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import javax.transaction.Transactional;
import java.util.List;

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

    @Autowired
    private MenuItemMapper itemMapper;

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
    void getAllMenuItemsTest() throws Exception{
        //given
        Menu menu = new Menu();
        menu.setName("Menu");
        menuRepository.save(menu);

        MenuItem menuItem = new MenuItem();
        menuItem.setName("Krupnik");
        menuItem.setMenu(menu);
        itemRepository.save(menuItem);

        MenuItem menuItem2 = new MenuItem();
        menuItem2.setName("Paluszki");
        menuItem2.setMenu(menu);
        itemRepository.save(menuItem2);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/menu-items"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //then
        List<MenuItemDTO> itemsDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<MenuItemDTO>>() {});
        List<MenuItem> items = itemMapper.toEntity(itemsDTO);

        assertThat(items).isNotNull();
        assertThat(items).hasSize(2);
        assertThat(items).contains(menuItem);
        assertThat(items).contains(menuItem2);
    }

    @Test
    void getMenuItemsByNameTest() {
    }

    @Test
    void updateMenuItemTest() {
    }

    @Test
    void deleteMenuItemTest() throws Exception {
        Menu menu = new Menu();
        menu.setName("Menu");
        menuRepository.save(menu);

        MenuItem item = new MenuItem();
        item.setName("Woda");
        item.setMenu(menu);
        itemRepository.save(item);

        int dbSize = itemRepository.findAll().size();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/menu-items/" + item.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        int dbSizeAfter = itemRepository.findAll().size();

        assertThat(dbSizeAfter).isEqualTo(dbSize - 1);
    }
}