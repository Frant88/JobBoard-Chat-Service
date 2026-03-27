package com.jobboard.comunication.webSoket;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Applichiamo la validazione del ticket a tutte le API della chat
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/api/chat/**");
    }
}
