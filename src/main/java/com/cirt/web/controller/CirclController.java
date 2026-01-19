package com.cirt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cirt.web.service.CirclService;

@RestController
@RequestMapping("/api/circl")
public class CirclController {
    @Autowired
    private CirclService circlService;

    @GetMapping("/cve")
    public ResponseEntity<String> getCVEs(@RequestParam String query) {
        String url = "https://vulnerability.circl.lu/api/cve/" + query;

        RestTemplate rest = new RestTemplate();
        String response = rest.getForObject(url, String.class);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cve/{id}")
    public ResponseEntity<String> getCVE(@PathVariable String id) {
        return ResponseEntity.ok(circlService.getCve(id));
    }

    @GetMapping("/cwe/{id}")
    public ResponseEntity<String> getCWE(@PathVariable String id) {
        return ResponseEntity.ok(circlService.getCwe(id));
    }

    @GetMapping("/capec/{id}")
    public ResponseEntity<String> getCapec(@PathVariable String id) {
        return ResponseEntity.ok(circlService.getCapec(id));
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String keyword) {
        return ResponseEntity.ok(circlService.searchByKeyword(keyword));
    }
}
