package htw.webtech.myapp.rest.controller;

import htw.webtech.myapp.business.service.AdService;
import htw.webtech.myapp.persistence.entity.AdEntry;
import htw.webtech.myapp.rest.model.AdRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://frnawebfront2.onrender.com"
})
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping
    public ResponseEntity<List<AdEntry>> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping
    public ResponseEntity<AdEntry> createAd(@RequestBody AdRequest request) {
        AdEntry created = adService.createAd(request);
        return ResponseEntity.ok(created);
    }
}
