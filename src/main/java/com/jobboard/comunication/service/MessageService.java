package com.jobboard.comunication.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobboard.comunication.dto.MessageRequest;
import com.jobboard.comunication.dto.MessageResponse;
import com.jobboard.comunication.mapper.MessageMapper;
import com.jobboard.comunication.repository.MessageRepository;
import com.jobboard.comunication.model.Message;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly= true)
public class MessageService {
	private final MessageMapper mapper;
	private final MessageRepository repository;
	
	public List<MessageResponse> getChat(Long u1, Long u2){
		List<Message> messaggi = repository.findChatHistory(u1,u2);
		List<MessageResponse> risposta = messaggi.stream().map(mapper :: toResponse ).toList();
		return risposta;
	}
	
	@Transactional
	public MessageResponse save(MessageRequest mRequest) {
		Message m = mapper.toMessage(mRequest);
		Message save = repository.save(m);
		return mapper.toResponse(save);
	}
	
	@Transactional
	public void markChatAsRead(Long senderId, Long receiverId) {
		repository.markChatAsRead(senderId, receiverId);
	}
}
