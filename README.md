# Notification Service

## Übersicht

Der Notification Service verwaltet Benachrichtigungen für Benutzer verwaltet. 

## Schnittstellen

### REST API

#### GET /notifications

Ruft alle Benachrichtigungen für einen bestimmten Benutzer ab.

**Request:**
- Methode: GET
- URL: `/notifications`
- Parameter:
  - `username` (String): Der Benutzername des Benutzers, dessen Benachrichtigungen abgerufen werden sollen.

**Response:**
- Status: 200 OK
- Body: Eine Liste von Benachrichtigungen.

**Beispiel:**
GET "http://localhost:8080/notifications?username=Bernd"

### RabbitMQ Consumer

#### Queue: `collection.updated`

Empfängt aktuell nur Nachrichten über "updated Collections" und speichert die Benachrichtigung zusammen mit dem User in der Datenbank.

**Listener:**
- Methode: `receiveMessage`
- Parameter:
  - `notification` (Notification): Die empfangene Benachrichtigung.

**Beispiel:**
```java
@RabbitListener(queues = "collection.updated")
public void receiveMessage(Notification notification) {
    // Verarbeitung der empfangenen Nachricht
}
```

### Datenbank

#### Tabellen

##### users

| Spalte   | Typ         | Beschreibung                  |
|----------|-------------|-------------------------------|
| id       | SERIAL      | Auto-incrementing ID          |
| username | VARCHAR(255)| Benutzername des Benutzers    |

##### notifications

| Spalte   | Typ         | Beschreibung                  |
|----------|-------------|-------------------------------|
| id       | SERIAL      | Auto-incrementing ID          |
| user_id  | BIGINT      | Foreign key zu users Tabelle  |
| title    | VARCHAR(255)| Titel der Benachrichtigung    |
| message  | TEXT        | Inhalt der Benachrichtigung   |