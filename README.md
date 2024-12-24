# Notification Service

## Übersicht

Der Notification Service verwaltet Benachrichtigungen für Benutzer. 

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

#### GET /notifications/status/{status}

Ruft alle Benachrichtigungen mit einem bestimmten Status für einen bestimmten Benutzer ab.

**Request:**
- Methode: GET
- URL: `/notifications/status/{status}`
- Parameter:
  - `username` (String): Der Benutzername des Benutzers, dessen Benachrichtigungen abgerufen werden sollen.
  - `status` (Notification.NotificationStatus): Der Status der Benachrichtigungen (READ, UNREAD, DELETED).

**Response:**
- Status: 200 OK
- Body: Eine Liste von Benachrichtigungen.

**Beispiel:**
GET "http://localhost:8080/notifications/status/UNREAD?username=Bernd"

#### PUT /notifications/{id}/status/{status}

Aktualisiert den Status einer Benachrichtigung.

**Request:**
- Methode: PUT
- URL: `/notifications/{id}/status/{status}`
- Parameter:
  - `username` (String): Der Benutzername des Benutzers, der die Benachrichtigung aktualisiert.
  - `id` (Long): Die ID der Benachrichtigung.
  - `status` (Notification.NotificationStatus): Der neue Status der Benachrichtigung (READ, UNREAD, DELETED).

**Response:**
- Status: 200 OK
- Body: Die aktualisierte Benachrichtigung.

**Beispiel:**
PUT "http://localhost:8080/notifications/1/status/READ?username=Bernd"

### RabbitMQ Consumer

#### Queues: `collection.updated`, `collection.deleted`, `collection.created`

Empfängt Nachrichten über aktualisierte, gelöschte und erstellte Sammlungen und speichert die Benachrichtigung zusammen mit dem Benutzer in der Datenbank.

**Listener:**
- Methode: `receiveMessage`
- Parameter:
  - `notificationMessage` (NotificationMessage): Die empfangene Benachrichtigung.

**Beispiel:**
```java
@RabbitListener(queues = {"collection.updated", "collection.deleted", "collection.created"})
public void receiveMessage(NotificationMessage notificationMessage) {
  // Verarbeitung der empfangenen Nachricht
}
```

### RabbitMQ Message Format

Eine RabbitMQ-Nachricht muss das folgende JSON-Format haben, damit sie korrekt verarbeitet wird:
"message" und "status" sind optional.
```json
{
  "user": "string",
  "title": "string",
  "message": "string",
  "status": "READ | UNREAD | DELETED"
}
```

### Datenbank

#### Tabellen

##### notifications

| Spalte  | Typ          | Beschreibung                  |
|---------|--------------|-------------------------------|
| id      | BIGSERIAL    | Auto-incrementing ID          |
| username| VARCHAR(255) | Foreign key zu users Tabelle  |
| title   | VARCHAR(255) | Titel der Benachrichtigung    |
| message | TEXT         | Inhalt der Benachrichtigung   |
| status  | VARCHAR(255) | Status der Benachrichtigung   |

