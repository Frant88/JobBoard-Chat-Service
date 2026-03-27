package com.jobboard.comunication.webSoket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	private final UserInterceptor userInterceptor;
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/queue");
		
		// I messaggi che arrivano dal JS iniziano con /app
		config.setApplicationDestinationPrefixes("/app");
		
		// Il prefisso magico per indirizzare i messaggi all'utente specifico
		config.setUserDestinationPrefix("/user");
	}
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-messaging").setAllowedOrigins("http://localhost:8000","http://127.0.0.1:8000").withSockJS();
	}
	
	@Override
	public void configureClientInboundChannel (ChannelRegistration registration) {
		registration.interceptors(userInterceptor);
	}
}
