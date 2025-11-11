package htw.webtech.myapp.business.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {
    private static final Map<String, String> USERS = Map.of("test@mail.de", "1234");

    public String authenticate(String email, String password) {
        String expected = USERS.get(email);
        if (expected != null && expected.equals(password)) {
            return "abc123"; // Platzhalter Token
        }
        return null;
    }
}
