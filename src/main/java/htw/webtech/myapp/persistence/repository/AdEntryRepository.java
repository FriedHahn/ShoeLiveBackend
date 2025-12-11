package htw.webtech.myapp.persistence.repository;

import htw.webtech.myapp.persistence.entity.AdEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdEntryRepository extends JpaRepository<AdEntry, Long> {
}
