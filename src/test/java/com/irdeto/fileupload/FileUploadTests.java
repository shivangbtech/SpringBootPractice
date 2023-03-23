package com.irdeto.fileupload;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUploadTests {

    private static final String DOWNLOAD_PATH = "/Users/shivang.goel/Desktop/";
    private static final String DOWNLOAD_URL = "http://localhost:8080/productapi/download/";
    private static final String FILE_UPLOAD_URL = "http://localhost:8080/productapi/upload";
//    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testUpload() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new PathResource("/Users/shivang.goel/Desktop/Unlocking-image.png"));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Boolean> response = restTemplate.postForEntity(FILE_UPLOAD_URL, httpEntity, Boolean.class);

        System.out.println(response.getBody());
    }

    @Test
    public void testDownload() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String fileName = "Unlocking-image.png";

        ResponseEntity<byte[]> response = restTemplate.exchange(DOWNLOAD_URL+fileName, HttpMethod.GET,httpEntity,byte[].class);

        Files.write(Paths.get(DOWNLOAD_PATH+fileName),response.getBody());
    }

}
