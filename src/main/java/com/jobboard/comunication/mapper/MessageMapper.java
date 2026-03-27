package com.jobboard.comunication.mapper;

import org.springframework.stereotype.Component;

import com.jobboard.comunication.dto.MessageRequest;
import com.jobboard.comunication.dto.MessageResponse;
import com.jobboard.comunication.model.Message;

@Component
public class MessageMapper {
	public Message  toMessage(MessageRequest dto) {
		if(dto == null) return null;
		return Message.builder()
				.senderId(dto.getSenderId())
				.receiverId(dto.getReceiverId())
				.content(dto.getContent())
				.build();
	}
	
	public MessageResponse toResponse(Message message) {
		if (message == null) return null;
		return MessageResponse.builder()
				.id(message.getId())
				.senderId(message.getSenderId())
				.receiverId(message.getReceiverId())
				.content(message.getContent())
				.isRead(message.isRead())
				.createdAt(message.getCreatedAt())
				.build();
	}
}
