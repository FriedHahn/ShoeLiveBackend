package htw.webtech.myapp.rest.controller;

import htw.webtech.myapp.business.service.AuthService;
import htw.webtech.myapp.persistence.entity.AdEntry;
import htw.webtech.myapp.persistence.repository.AdEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://frnawebfront2.onrender.com"
})
public class ProfileController {

    private final AuthService authService;
    private final AdEntryRepository adRepo;

    public ProfileController(AuthService authService, AdEntryRepository adRepo) {
        this.authService = authService;
        this.adRepo = adRepo;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        String email = authService.getEmailFromBearerHeader(authHeader);
        if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");

        email = email.trim().toLowerCase();

        List<AdEntry> allMine = adRepo.findAllByOwnerEmail(email);
        List<AdEntry> soldByMe = adRepo.findAllByOwnerEmailAndSoldTrue(email);
        List<AdEntry> boughtByMe = adRepo.findAllByBuyerEmail(email);

        BigDecimal revenue = BigDecimal.ZERO;
        for (AdEntry a : soldByMe) revenue = revenue.add(parsePrice(a.getPrice()));

        BigDecimal spent = BigDecimal.ZERO;
        for (AdEntry a : boughtByMe) spent = spent.add(parsePrice(a.getPrice()));

        Map<String, Object> result = new HashMap<>();
        result.put("email", email);

        result.put("totalAds", allMine.size());

        result.put("soldCount", soldByMe.size());
        result.put("revenueTotal", revenue);

        result.put("boughtCount", boughtByMe.size());
        result.put("spentTotal", spent);

        result.put("soldAds", soldByMe);
        result.put("boughtAds", boughtByMe);

        return ResponseEntity.ok(result);
    }

    private BigDecimal parsePrice(String price) {
        if (price == null) return BigDecimal.ZERO;
        String p = price.trim().replace(",", ".");
        if (p.isEmpty()) return BigDecimal.ZERO;
        try {
            return new BigDecimal(p);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
