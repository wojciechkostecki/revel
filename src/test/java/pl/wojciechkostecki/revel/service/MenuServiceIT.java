package pl.wojciechkostecki.revel.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wojciechkostecki.revel.mapper.MenuMapper;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MenuServiceIT {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuMapper menuMapper;

    @Test
    void findByIdTest() {
        Menu menu = new Menu();
        menu.setName("Pijalnia");
        menuRepository.save(menu);
        MenuDTO menuDTO = menuMapper.toDto(menu);

        Optional<MenuDTO> savedMenu = menuService.findById(menu.getId());

        assertThat(savedMenu.get()).isEqualTo(menuDTO);
    }
}