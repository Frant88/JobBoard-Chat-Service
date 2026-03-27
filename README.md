# JobBoard Chat Service

Questo è il microservizio dedicato alla gestione della messaggistica in tempo reale per la piattaforma **JobBoard**. Il servizio è costruito con **Java** e **Spring Boot** e comunica con il frontend Laravel tramite WebSocket.

## Tecnologie Utilizzate

Il progetto sfrutta le seguenti tecnologie core:

* **Java 17+** & **Spring Boot 3.x**
* **Spring WebSocket**: Per la comunicazione bidirezionale in tempo reale.
* **Spring Security**: Per la protezione degli endpoint e la gestione dell'accesso.
* **Spring Data JPA**: Per l'interazione con il database tramite ORM.
* **MySQL**: Database relazionale per la persistenza di messaggi e ticket.
* **Lombok**: Per ridurre il boilerplate code (Getter, Setter, Constructor).
* **Validation**: Per garantire l'integrità dei dati in entrata.

## Funzionalità Principali

- **Real-time Messaging**: Invio e ricezione messaggi istantanei tramite protocollo STOMP.
- **Ticket-based Auth**: Integrazione con Laravel per convalidare le sessioni utente tramite ticket univoci salvati su DB.
- **Persistence**: Salvataggio dello storico dei messaggi per consultazioni future.
- **Security**: Configurazione di CORS e protezione degli endpoint tramite Spring Security.

## Requisiti

Assicurati di avere installato:
- JDK 17 o superiore
- Maven 3.6+
- MySQL Server attivo

## Configurazione (application.properties)

Prima di avviare il servizio, configura il file `src/main/resources/application.properties` con le tue credenziali:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nome_tuo_db
spring.datasource.username=tuo_utente
spring.datasource.password=tua_password
spring.jpa.hibernate.ddl-auto=update

# Configurazione WebSocket

## Collegamenti al Progetto

🔗 **Frontend & Auth**: Questo servizio è parte della piattaforma principale [JobBoard-Laravel](https://github.com/Frant88/JobBoard). Il frontend gestisce l'autenticazione e la generazione dei ticket necessari per connettersi a questo socket.

## Licenza

Questo progetto è distribuito sotto licenza MIT. Vedi il file [LICENSE](LICENSE) per i dettagli.

Copyright (c) 2026 Francesco Casaluce