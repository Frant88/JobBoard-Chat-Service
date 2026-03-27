package com.jobboard.comunication.webSoket;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.jobboard.comunication.model.Message;
import com.jobboard.comunication.repository.MessageRepository;
import com.jobboard.comunication.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class webSocketController {
	private final SimpMessagingTemplate messagingTemplate;
	private final MessageRepository repository;
	private final MessageService service;
	
	@MessageMapping("/chat.send")
	public void handlePrivateMessage(@Payload Map<String,Object> map, Principal principal) {
		Message msg = Message.builder()
				.senderId(Long.parseLong(principal.getName()))
				.receiverId(Long.parseLong(map.get("recipientId").toString()))
				.content(map.get("content").toString())
				.isRead(false)
				.createdAt(LocalDateTime.now())
				.build();
		
		Message savedMessage = repository.save(msg);
		
		messagingTemplate.convertAndSendToUser(
				savedMessage.getReceiverId().toString(),
				"/queue/messages",
				savedMessage);
	}
	@MessageMapping("/chat.read")
	public void processReadRecepit(@Payload Map<String,Object> payload, Principal principal) {
		Long senderId = Long.parseLong((String) payload.get("senderId"));
		Long receiverId = Long.parseLong((String)principal.getName());
		
		service.markChatAsRead(senderId, receiverId);
		
		messagingTemplate.convertAndSendToUser(senderId.toString(), "/queue/messages", Map.of("readBy", receiverId.toString()));
	}
}
