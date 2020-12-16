package pl.wojciechkostecki.revel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechkostecki.revel.model.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local,Long> {
}
