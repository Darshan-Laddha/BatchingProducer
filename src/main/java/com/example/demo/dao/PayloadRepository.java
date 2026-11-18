package com.example.demo.dao;

import com.example.demo.model.PayloadRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayloadRepository extends JpaRepository<PayloadRecord, Long> {
}
