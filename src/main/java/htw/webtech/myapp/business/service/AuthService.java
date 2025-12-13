package htw.webtech.myapp.business.service;

import htw.webtech.myapp.persistence.entity.UserEntry;
import htw.webtech.myapp.persistence.repository.UserEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserEntryRepository userRepo;
    private final SessionTokenStore tokenStore;

    public AuthService(UserEntryRepository userRepo, SessionTokenStore tokenStore) {
        this.userRepo = userRepo;
        this.tokenStore = tokenStore;
    }

    public String register(String email, String password) {
        String e = normalizeEmail(email);

        if (e.isEmpty() || password == null || password.length() < 4) return null;
        if (userRepo.existsByEmail(e)) return null;

        String pwHash = PasswordHasher.hash(password);
        userRepo.save(new UserEntry(e, pwHash));

        return tokenStore.issueToken(e);
    }

    public String authenticate(String email, String password) {
        String e = normalizeEmail(email);
        if (e.isEmpty() || password == null) return null;

        return userRepo.findByEmail(e)
                .filter(u -> PasswordHasher.verify(password, u.getPasswordHash()))
                .map(u -> tokenStore.issueToken(e))
                .orElse(null);
    }

    public String getEmailFromBearerHeader(String authorizationHeader) {
        String token = extractBearerToken(authorizationHeader);
        return tokenStore.getEmailByToken(token);
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null) return null;
        String h = authorizationHeader.trim();
        if (!h.toLowerCase().startsWith("bearer ")) return null;
        return h.substring(7).trim();
    }

    private String normalizeEmail(String email) {
        if (email == null) return "";
        return email.trim().toLowerCase();
    }
}
