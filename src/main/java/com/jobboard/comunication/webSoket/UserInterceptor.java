package com.jobboard.comunication.webSoket;

import java.util.Collections;
import java.util.Map;


import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor

public class UserInterceptor implements ChannelInterceptor,HandlerInterceptor{
	private final RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	public String validateWithLaravel(String ticket) {
		System.out.println("STO VALIDANDO IL TICKET: " + ticket);
		try {
		String url= "http://127.0.0.1:8000/api/validate-ticket/" + ticket;
		Map<String,Object> response = restTemplate.getForObject(url, Map.class);
		if(response !=null && response.get("user_id") != null) {
			return response.get("user_id").toString();
		}
		}catch(Exception e) {
			System.out.println("Errore validazione : " + e);
		}
		return null;
		
	}
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel){
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		
		if(StompCommand.CONNECT.equals(accessor.getCommand())) {
			String ticket = accessor.getFirstNativeHeader("auth-ticket");
			if(ticket != null) {
				String userId = validateWithLaravel(ticket);
				if(userId != null) {
					System.out.println(">>> VALIDAZIONE OK! User ID: " + userId);
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
					accessor.setUser(auth);
				}
			} else {
				System.out.println(">>> VALIDAZIONE FALLITA: Laravel ha risposto picche o non è raggiungibile.");
				throw new RuntimeException("Ticket non valido o scaduto");
			}
		}
		return message;
	}
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Ignora le richieste OPTIONS (preflight CORS)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;

        String ticket = request.getHeader("auth-ticket");
        if (ticket != null) {
            String userId = validateWithLaravel(ticket);
            if (userId != null) {
                // Salviamo l'ID nella richiesta così il Controller può leggerlo
                request.setAttribute("userId", userId);
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
	
}
