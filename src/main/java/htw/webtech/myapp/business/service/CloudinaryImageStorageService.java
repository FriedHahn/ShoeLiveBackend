package htw.webtech.myapp.business.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CloudinaryImageStorageService implements ImageStorageService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String uploadImage(byte[] fileBytes, String filename, String contentType) throws Exception {
        String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
        String uploadPreset = System.getenv("CLOUDINARY_UPLOAD_PRESET");

        if (cloudName == null || cloudName.isBlank() || uploadPreset == null || uploadPreset.isBlank()) {
            throw new IllegalStateException("Cloudinary ENV fehlt (CLOUDINARY_CLOUD_NAME/CLOUDINARY_UPLOAD_PRESET)");
        }

        String boundary = "----Boundary" + UUID.randomUUID();
        String url = "https://api.cloudinary.com/v1_1/" + cloudName + "/image/upload";

        List<byte[]> parts = new ArrayList<>();

        parts.add(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        parts.add(("Content-Disposition: form-data; name=\"upload_preset\"\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        parts.add((uploadPreset + "\r\n").getBytes(StandardCharsets.UTF_8));

        String safeName = (filename == null || filename.isBlank()) ? "upload.jpg" : filename;
        String ct = (contentType == null || contentType.isBlank()) ? "application/octet-stream" : contentType;

        parts.add(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
        parts.add(("Content-Disposition: form-data; name=\"file\"; filename=\"" + safeName + "\"\r\n").getBytes(StandardCharsets.UTF_8));
        parts.add(("Content-Type: " + ct + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        parts.add(fileBytes);
        parts.add("\r\n".getBytes(StandardCharsets.UTF_8));

        parts.add(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArrays(parts))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() / 100 != 2) {
            throw new IllegalStateException("Cloudinary Upload fehlgeschlagen (Status " + res.statusCode() + ")");
        }

        JsonNode json = objectMapper.readTree(res.body());
        String secureUrl = json.path("secure_url").asText();

        if (secureUrl == null || secureUrl.isBlank()) {
            throw new IllegalStateException("Cloudinary Response ohne secure_url");
        }

        return secureUrl;
    }
}
