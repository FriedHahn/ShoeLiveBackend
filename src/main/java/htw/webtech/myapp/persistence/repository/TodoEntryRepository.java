package htw.webtech.myapp.persistence.repository;

import htw.webtech.myapp.persistence.entity.TodoEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoEntryRepository extends JpaRepository<TodoEntry, Long> {
}
