
package com.natanconstrutora.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ErrorAutoCorrector {
    
    private static final Logger logger = LoggerFactory.getLogger(ErrorAutoCorrector.class);

    @EventListener
    public void handleApplicationFailed(ApplicationFailedEvent event) {
        Throwable exception = event.getException();
        logger.error("Application failed to start. Auto-correction triggered.", exception);
        
        // Log detalhado do erro para análise automática
        if (exception.getCause() != null) {
            logger.error("Root cause: {}", exception.getCause().getMessage());
        }
        
        // Aqui você pode adicionar lógica específica de autocorreção
        suggestAutoCorrection(exception);
    }
    
    private void suggestAutoCorrection(Throwable exception) {
        String message = exception.getMessage();
        
        if (message.contains("Bean")) {
            logger.warn("Bean configuration issue detected. Check @Component, @Service, @Repository annotations.");
        } else if (message.contains("SQL") || message.contains("database")) {
            logger.warn("Database issue detected. Check entity mappings and repository methods.");
        } else if (message.contains("Security")) {
            logger.warn("Security configuration issue detected. Check SecurityConfig and authentication setup.");
        }
    }
}
