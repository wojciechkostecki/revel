package pl.wojciechkostecki.revel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechkostecki.revel.model.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
}
