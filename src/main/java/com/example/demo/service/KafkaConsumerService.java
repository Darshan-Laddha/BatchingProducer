package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.PayloadRepository;
import com.example.demo.model.PayloadRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KafkaConsumerService {

    @Autowired
    private PayloadRepository payloadRepository;

    @Value("${app.kafka.batch-size}")
    private int targetBatchSize;

    private final List<String> buffer = new ArrayList<>();

    @KafkaListener(topics = "payload-topic", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public synchronized void consume(List<String> messages) {
        System.out.println("Received raw poll records: " + messages.size());

        for (String message : messages) {
            buffer.add(message);

            // Once the buffer reaches your exact batch size, process and save to DB
            if (buffer.size() >= targetBatchSize) {
                flushBuffer();
            }
        }
    }

    private void flushBuffer() {
        // Take a snapshot of the current batch and clear the buffer
        List<String> batchToProcess = new ArrayList<>(buffer);
        buffer.clear();

        // Map incoming strings to Entity objects
        List<PayloadRecord> records = batchToProcess.stream()
                .map(PayloadRecord::new)
                .collect(Collectors.toList());

        // Perform bulk insert to database
        payloadRepository.saveAll(records);
        System.out.println("Successfully flushed strict batch of " + records.size()  + " records to MySQL.");
    }
}
