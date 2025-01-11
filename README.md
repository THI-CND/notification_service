# Notification Service

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=THI-CND_notification_service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=THI-CND_notification_service)

## Übersicht

Der Notification Service verwaltet Benachrichtigungen für Benutzer. 
____
## Schnittstellen

### REST API

#### GET /api/v1/notifications

Ruft alle Benachrichtigungen für einen bestimmten Benutzer ab.

**Request:**
- Methode: GET
- URL: `/api/v1/notifications`
- Parameter:
  - `username` (String): Der Benutzername des Benutzers, dessen Benachrichtigungen abgerufen werden sollen.

**Response:**
- Status: 200 OK
- Body: Eine Liste von Benachrichtigungen.

**Beispiel:**
GET "http://localhost:8080/api/v1/notifications?username=Bernd"

#### GET /api/v1/notifications/status/{status}

Ruft alle Benachrichtigungen mit einem bestimmten Status für einen bestimmten Benutzer ab.

**Request:**
- Methode: GET
- URL: `/api/v1/notifications/status/{status}`
- Parameter:
  - `username` (String): Der Benutzername des Benutzers, dessen Benachrichtigungen abgerufen werden sollen.
  - `status` (Notification.NotificationStatus): Der Status der Benachrichtigungen (READ, UNREAD, DELETED).

**Response:**
- Status: 200 OK
- Body: Eine Liste von Benachrichtigungen.

**Beispiel:**
GET "http://localhost:8080/api/v1/notifications/status/UNREAD?username=Bernd"

#### PUT /api/v2/notifications/{id}

Aktualisiert den Status einer Benachrichtigung.

**Request:**
- Methode: PUT
- URL: `/api/v2/notifications/{id}`
- Parameter:
  - `username` (String): Der Benutzername des Benutzers, der die Benachrichtigung aktualisiert.
  - `id` (Long): Die ID der Benachrichtigung.
  - **Body:**
    ```json
    {
      "status": "READ | UNREAD | DELETED"
    }
    ```

**Response:**
- Status: 200 OK
- Body: Die aktualisierte Benachrichtigung.

**Beispiel:**
PUT "http://localhost:8080/api/v2/notifications/1?username=Bernd"
```json
{
  "status": "READ"
}
```
____
### gRPC API

```java
syntax = "proto3";

package com.notification_service.stubs;

option java_multiple_files = true;
option java_package = "com.notification_service.stubs";
option java_outer_classname = "NotificationServiceProto";

service NotificationService {
rpc GetAllNotifications (GetAllNotificationsRequest) returns (GetAllNotificationsResponse);
rpc GetNotificationById (GetNotificationByIdRequest) returns (NotificationResponse);
rpc UpdateNotificationStatus (UpdateNotificationStatusRequest) returns (NotificationResponse);
rpc GetNotificationsByStatus (GetNotificationsByStatusRequest) returns (GetAllNotificationsResponse);
}

enum NotificationStatus {
READ = 0;
UNREAD = 1;
DELETED = 2;
}
message GetAllNotificationsRequest {
string username = 1;
}

message GetAllNotificationsResponse {
repeated NotificationResponse notifications = 1;
}

message GetNotificationsByStatusRequest {
string username = 1;
NotificationStatus status = 2;
}

message GetNotificationByIdRequest {
string username = 1;
int64 id = 2;
}

message UpdateNotificationStatusRequest {
string username = 1;
int64 id = 2;
NotificationStatus status = 3;
}

message NotificationResponse {
int64 id = 1;
string username = 2;
string title = 3;
string message = 4;
NotificationStatus status = 5;
}
```
____
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
____

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

