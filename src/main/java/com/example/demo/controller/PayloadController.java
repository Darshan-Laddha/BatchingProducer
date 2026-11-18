package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.KafkaProducerService;

@RestController
@RequestMapping("/api/payload")
public class PayloadController {

    @Autowired
    private KafkaProducerService producerService;

    @PostMapping("/send")
    public ResponseEntity<String> receivePayload(@RequestBody String payload) {
        producerService.sendMessage(payload);
        return ResponseEntity.ok("Payload published to Kafka successfully!");
    }
}
