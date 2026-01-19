package com.cirt.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CirclService {
    private final String BASE_URL = "https://vulnerability.circl.lu/api/";

    private final RestTemplate restTemplate = new RestTemplate();

    public String getCve(String id) {
        return restTemplate.getForObject(BASE_URL + "cve/" + id, String.class);
    }

    public String getCwe(String id) {
        return restTemplate.getForObject(BASE_URL + "cwe/" + id, String.class);
    }

    public String getCapec(String id) {
        return restTemplate.getForObject(BASE_URL + "capec/" + id, String.class);
    }

    public String searchByKeyword(String keyword) {
        return restTemplate.getForObject(BASE_URL + "search/" + keyword, String.class);
    }
}
