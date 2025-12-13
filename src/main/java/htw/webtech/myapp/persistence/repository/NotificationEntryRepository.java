package htw.webtech.myapp.persistence.repository;

import htw.webtech.myapp.persistence.entity.NotificationEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationEntryRepository extends JpaRepository<NotificationEntry, Long> {
    List<NotificationEntry> findAllByUserEmailAndReadFalseOrderByCreatedAtDesc(String userEmail);
}
