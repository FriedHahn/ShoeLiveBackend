package htw.webtech.myapp.business.service;

import htw.webtech.myapp.persistence.entity.AdEntry;
import htw.webtech.myapp.persistence.repository.AdEntryRepository;
import htw.webtech.myapp.rest.model.AdRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {

    private final AdEntryRepository adEntryRepository;

    public AdService(AdEntryRepository adEntryRepository) {
        this.adEntryRepository = adEntryRepository;
    }

    public List<AdEntry> getAllAds() {
        return adEntryRepository.findAll();
    }

    public AdEntry createAd(AdRequest request) {
        AdEntry ad = new AdEntry(
                request.getBrand(),
                request.getSize(),
                request.getPrice()
        );
        return adEntryRepository.save(ad);
    }
}
