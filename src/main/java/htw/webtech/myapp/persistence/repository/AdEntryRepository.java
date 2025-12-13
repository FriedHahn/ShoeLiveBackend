package htw.webtech.myapp.persistence.repository;

import htw.webtech.myapp.persistence.entity.AdEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdEntryRepository extends JpaRepository<AdEntry, Long> {

    List<AdEntry> findAllBySoldFalse();

    List<AdEntry> findAllByOwnerEmail(String ownerEmail);

    List<AdEntry> findAllByOwnerEmailAndSoldTrue(String ownerEmail);

    List<AdEntry> findAllByBuyerEmail(String buyerEmail);
}
