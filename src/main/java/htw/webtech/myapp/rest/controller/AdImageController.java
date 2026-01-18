package htw.webtech.myapp.rest.controller;

import htw.webtech.myapp.business.service.AuthService;
import htw.webtech.myapp.business.service.ImageStorageService;
import htw.webtech.myapp.persistence.entity.AdEntry;
import htw.webtech.myapp.persistence.repository.AdEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ads")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://frnawebfront2.onrender.com"
})
public class AdImageController {

    private final AdEntryRepository adRepo;
    private final AuthService authService;
    private final ImageStorageService imageStorageService;

    public AdImageController(AdEntryRepository adRepo, AuthService authService, ImageStorageService imageStorageService) {
        this.adRepo = adRepo;
        this.authService = authService;
        this.imageStorageService = imageStorageService;
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String email = authService.getEmailFromBearerHeader(authHeader);
            if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");

            AdEntry ad = adRepo.findById(id).orElse(null);
            if (ad == null) return ResponseEntity.status(404).body("Anzeige nicht gefunden");

            if (!ad.getOwnerEmail().equalsIgnoreCase(email)) {
                return ResponseEntity.status(403).body("Nicht erlaubt");
            }

            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Keine Datei");
            }

            String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (ext == null || ext.isBlank()) ext = "jpg";
            ext = ext.toLowerCase();

            if (!(ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("webp"))) {
                return ResponseEntity.badRequest().body("Nur jpg, jpeg, png, webp erlaubt");
            }

            String secureUrl = imageStorageService.uploadImage(file.getBytes(), file.getOriginalFilename(), file.getContentType());
            ad.setImagePath(secureUrl);
            adRepo.save(ad);

            return ResponseEntity.ok(ad);
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload fehlgeschlagen");
        }
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<?> deleteImage(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        try {
            String email = authService.getEmailFromBearerHeader(authHeader);
            if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");

            AdEntry ad = adRepo.findById(id).orElse(null);
            if (ad == null) return ResponseEntity.status(404).body("Anzeige nicht gefunden");

            if (!ad.getOwnerEmail().equalsIgnoreCase(email)) {
                return ResponseEntity.status(403).body("Nicht erlaubt");
            }

            ad.setImagePath(null);
            adRepo.save(ad);

            return ResponseEntity.ok(ad);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Loeschen fehlgeschlagen");
        }
    }
}
