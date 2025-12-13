package htw.webtech.myapp.rest.controller;

import htw.webtech.myapp.business.service.AdService;
import htw.webtech.myapp.business.service.AuthService;
import htw.webtech.myapp.rest.model.PurchaseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://frnawebfront2.onrender.com"
})
public class PurchaseController {

    private final AdService adService;
    private final AuthService authService;

    public PurchaseController(AdService adService, AuthService authService) {
        this.adService = adService;
        this.authService = authService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                      @RequestBody PurchaseRequest request) {
        try {
            String email = authService.getEmailFromBearerHeader(authHeader);
            if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");

            adService.purchaseAds(request.getAdIds(), email);
            return ResponseEntity.ok("ok");
        } catch (IllegalArgumentException e) {
            if ("UNAUTHORIZED".equals(e.getMessage())) return ResponseEntity.status(401).body("Nicht eingeloggt");
            if ("NOT_FOUND".equals(e.getMessage())) return ResponseEntity.status(404).body("Anzeige nicht gefunden");
            if ("SOLD".equals(e.getMessage())) return ResponseEntity.status(409).body("Mindestens eine Anzeige ist bereits verkauft");
            if ("FORBIDDEN".equals(e.getMessage())) return ResponseEntity.status(403).body("Du kannst deine eigene Anzeige nicht kaufen");
            return ResponseEntity.badRequest().body("Checkout fehlgeschlagen");
        }
    }
}
