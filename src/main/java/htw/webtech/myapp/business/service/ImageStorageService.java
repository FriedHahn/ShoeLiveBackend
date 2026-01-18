package htw.webtech.myapp.business.service;

public interface ImageStorageService {
    String uploadImage(byte[] fileBytes, String filename, String contentType) throws Exception;
}
