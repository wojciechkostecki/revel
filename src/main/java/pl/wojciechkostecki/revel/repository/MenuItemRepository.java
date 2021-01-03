package pl.wojciechkostecki.revel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.MenuItem;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

    List<MenuItem> findByNameContainingIgnoreCase(String name);
}
