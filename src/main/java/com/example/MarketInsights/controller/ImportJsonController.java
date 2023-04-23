package com.example.MarketInsights.controller;

import com.example.MarketInsights.service.JsonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RestController
@CrossOrigin(origins ="${my.property}")
@RequestMapping("/import-json")
public class ImportJsonController {
    @Autowired
    private JsonService jsonService;

    /**
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Read the contents of the file as a string
            String json = new String(file.getBytes(), StandardCharsets.UTF_8);
            // Parse the JSON string using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            String  insertionsCount=jsonService.addJSON_toDB(rootNode);
            return insertionsCount + " Insertions successful";

        } catch (IOException e) {
            e.printStackTrace();
            return "Error processing file";
        }
    }
}