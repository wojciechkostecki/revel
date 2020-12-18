package pl.wojciechkostecki.revel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechkostecki.revel.model.Local;

import java.util.List;

@Repository
public interface LocalRepository extends JpaRepository<Local,Long> {

    List<Local> findByName(String name);
}
