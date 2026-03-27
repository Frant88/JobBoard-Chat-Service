package com.jobboard.comunication.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobboard.comunication.dto.MessageRequest;
import com.jobboard.comunication.dto.MessageResponse;
import com.jobboard.comunication.service.MessageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@org.springframework.web.bind.annotation.CrossOrigin(
	    origins = {"http://localhost:8000", "http://127.0.0.1:8000"},
	    allowedHeaders = "*",
	    allowCredentials = "true"
	    )
public class MessageController {
	private final MessageService service;
	
	@GetMapping ("/history/{recipientId}")
	public ResponseEntity<List<MessageResponse>> getChat(HttpServletRequest request, @PathVariable("recipientId") Long u2){
		List<MessageResponse> chat = service.getChat(Long.valueOf((String) request.getAttribute("userId")), u2);
		return ResponseEntity.ok(chat);
	}
	
	@PostMapping
	public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest message){
		MessageResponse response = service.save(message);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PatchMapping("/read/{id}")
	public ResponseEntity<Void> markAsRead(HttpServletRequest request,@PathVariable("id") Long u2){
		service.markChatAsRead(Long.valueOf((String)request.getAttribute("userId")), u2);
		return ResponseEntity.noContent().build();
	}
}
