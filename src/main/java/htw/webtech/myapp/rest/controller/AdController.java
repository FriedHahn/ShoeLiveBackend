package htw.webtech.myapp.rest.controller;

import htw.webtech.myapp.business.service.AdService;
import htw.webtech.myapp.business.service.AuthService;
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
    private final AuthService authService;

    public AdController(AdService adService, AuthService authService) {
        this.adService = adService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<AdEntry>> getAllAds() {
        return ResponseEntity.ok(adService.getAllAvailableAds());
    }

    @PostMapping
    public ResponseEntity<?> createAd(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                      @RequestBody AdRequest request) {
        try {
            String email = authService.getEmailFromBearerHeader(authHeader);
            if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");
            return ResponseEntity.ok(adService.createAd(request, email));
        } catch (IllegalArgumentException e) {
            if ("UNAUTHORIZED".equals(e.getMessage())) return ResponseEntity.status(401).body("Nicht eingeloggt");
            return ResponseEntity.badRequest().body("Ungültige Eingaben");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAd(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                      @PathVariable Long id,
                                      @RequestBody AdRequest request) {
        try {
            String email = authService.getEmailFromBearerHeader(authHeader);
            if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");
            return ResponseEntity.ok(adService.updateAd(id, request, email));
        } catch (IllegalArgumentException e) {
            if ("NOT_FOUND".equals(e.getMessage())) return ResponseEntity.status(404).body("Anzeige nicht gefunden");
            if ("FORBIDDEN".equals(e.getMessage())) return ResponseEntity.status(403).body("Nicht erlaubt");
            if ("SOLD".equals(e.getMessage())) return ResponseEntity.status(409).body("Anzeige ist bereits verkauft");
            if ("UNAUTHORIZED".equals(e.getMessage())) return ResponseEntity.status(401).body("Nicht eingeloggt");
            return ResponseEntity.badRequest().body("Ungültige Eingaben");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                      @PathVariable Long id) {
        try {
            String email = authService.getEmailFromBearerHeader(authHeader);
            if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");
            adService.deleteAd(id, email);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            if ("NOT_FOUND".equals(e.getMessage())) return ResponseEntity.status(404).body("Anzeige nicht gefunden");
            if ("FORBIDDEN".equals(e.getMessage())) return ResponseEntity.status(403).body("Nicht erlaubt");
            if ("SOLD".equals(e.getMessage())) return ResponseEntity.status(409).body("Anzeige ist bereits verkauft");
            if ("UNAUTHORIZED".equals(e.getMessage())) return ResponseEntity.status(401).body("Nicht eingeloggt");
            return ResponseEntity.badRequest().body("Fehler");
        }
    }
}
