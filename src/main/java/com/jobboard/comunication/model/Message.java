package com.jobboard.comunication.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long senderId;
	@Column(nullable = false)
	private Long receiverId;
	
	@Column(columnDefinition= "TEXT", nullable = false)
	private String content;
	
	@Builder.Default
	@Column(nullable = false)
	private boolean isRead = false;
	
	@Builder.Default
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
}
