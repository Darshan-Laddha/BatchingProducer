package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payload_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayloadRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payload_seq")
	@SequenceGenerator(name = "payload_seq", sequenceName = "payload_sequence", allocationSize = 50)
	private Long id;

    @Column(columnDefinition = "TEXT")
    private String payloadData;
    
    public PayloadRecord(String payloadData) {
        this.payloadData = payloadData;
    }
}
