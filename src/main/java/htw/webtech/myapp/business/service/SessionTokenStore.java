package htw.webtech.myapp.business.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionTokenStore {

    private final Map<String, String> tokenToEmail = new ConcurrentHashMap<>();

    public String issueToken(String email) {
        String token = UUID.randomUUID().toString();
        tokenToEmail.put(token, email);
        return token;
    }

    public String getEmailByToken(String token) {
        if (token == null || token.isBlank()) return null;
        return tokenToEmail.get(token);
    }

    public void revoke(String token) {
        if (token == null || token.isBlank()) return;
        tokenToEmail.remove(token);
    }
}
