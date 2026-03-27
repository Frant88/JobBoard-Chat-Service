package com.jobboard.comunication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {
	@NotNull(message = "Il mittente è obbligatorio")
	private Long senderId;
	@NotNull(message = "Il destinatario è obbligatorio")
	private Long receiverId;
	@NotBlank(message= "Il messaggio non può essere vuoto")
	@Size(max = 5000, message = "Il messaggio è troppo lungo")
	private String content;
}
