package htw.webtech.myapp.persistence.repository;

import htw.webtech.myapp.persistence.entity.UserEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntryRepository extends JpaRepository<UserEntry, Long> {
    Optional<UserEntry> findByEmail(String email);
    boolean existsByEmail(String email);
}
