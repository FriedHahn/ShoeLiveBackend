package htw.webtech.myapp.rest.controller;

import htw.webtech.myapp.business.service.AuthService;
import htw.webtech.myapp.persistence.entity.NotificationEntry;
import htw.webtech.myapp.persistence.repository.NotificationEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://frnawebfront2.onrender.com"
})
public class NotificationController {

    private final AuthService authService;
    private final NotificationEntryRepository notificationRepo;

    public NotificationController(AuthService authService, NotificationEntryRepository notificationRepo) {
        this.authService = authService;
        this.notificationRepo = notificationRepo;
    }

    @GetMapping
    public ResponseEntity<?> getUnread(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        String email = authService.getEmailFromBearerHeader(authHeader);
        if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");

        List<NotificationEntry> list = notificationRepo.findAllByUserEmailAndReadFalseOrderByCreatedAtDesc(email.trim().toLowerCase());
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<?> markRead(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                      @PathVariable Long id) {
        String email = authService.getEmailFromBearerHeader(authHeader);
        if (email == null) return ResponseEntity.status(401).body("Nicht eingeloggt");

        return notificationRepo.findById(id)
                .map(n -> {
                    if (!n.getUserEmail().equalsIgnoreCase(email.trim())) {
                        return ResponseEntity.status(403).body("Nicht erlaubt");
                    }
                    n.setRead(true);
                    notificationRepo.save(n);
                    return ResponseEntity.ok("ok");
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Nicht gefunden"));
    }
}
