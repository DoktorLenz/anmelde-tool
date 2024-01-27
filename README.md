# ScoutVenture

## Installationsanleitung

### Voraussetzungen

ScoutVenture benötigt eine PostgreSQL-Datenbank der Version 15. <br/>
Getestet wurde die Anwendung ausschließlich mit Version 15.5. <br/>
Wenn die eingebaute IAM Lösung verwendet werden soll, wird eine zweite PostgreSQL-Datenbank dafür benötigt. <br/>
Beide Datenbanken können auf dem selben Server laufen.

### Konfiguration

Die Konfiguration von ScoutVenture kann über Startparameter oder Umgebungsvariablen erfolgen. <br/>
Folgend sind die gruppierten Parameter erläutert. <br/>

**Allgemeine Konfiguration**
| Parameter     | Beschreibung      | Default                |
| ------------- | ----------------- | ---------------------- |
| `sv.uri`      | URI zur Anwendung | -                      |
| `sv.nami.url` | Url zu DPSG NaMi  | `https://nami.dpsg.de` |

**Datenbank**
| Parameter        | Beschreibung                | Default         |
| ---------------- | --------------------------- | --------------- |
| `sv.db.url`      | Url der Datenbank           | `localhost`     |
| `sv.db.port`     | Port der Datenbank          | `5432`          |
| `sv.db.username` | Nutzername der Datenbank    | `scoutventure`  |
| `sv.db.password` | Passwort der Datenbank      | `scoutventure`  |
| `sv.db.db-name`  | Datenbankname der Datenbank | `scoutventure`  |
| `sv.db.tz`       | Zeitzone der Datenbank      | `Europe/Berlin` |


**Sicherheit**
| Parameter                        | Beschreibung                  | Default           |
| -------------------------------- | ----------------------------- | ----------------- |
| `sv.keycloak.enabled`            | Aktivieren von Keycloak (IAM) | `true`            |
| `sv.keycloak.adminUser.username` | Nutzername des Administrators | `admin`           |
| `sv.keycloak.adminUser.password` | Passwort des Administrators   | `admin`           |
| `sv.keycloak.db.url`             | Url der Keycloak-Datenbank    | `localhost`       |
| `sv.keycloak.db.port`            | Port der Datenbank            | `15432`           |
| `sv.keycloak.db.username`        | Nutzername der Datenbank      | `scoutventure`    |
| `sv.keycloak.db.password`        | Passwort der Datenbank        | `scoutventure`    |
| `sv.keycloak.db-name`            | Datenbankname der Datenbank   | `scoutventure_kc` |
| `sv.keycloak.tz`                 | Zeitzone der Datenbank        | `Europe/Berlin`   |



**Überwachung**
| Parameter                         | Beschreibung                     | Default |
| --------------------------------- | -------------------------------- | ------- |
| `sv.logging.directory`            | Ablageort der Log-Datei          | `logs`  |
| `sv.logging.file.size-limit`      | Max-Größe einer Log-Datei        | `5MB`   |
| `sv.monitoring.actuators.enabled` | Gibt Überwachungs-Endpunkte frei | `true`  |


## Informationen zur Überwachung

Über die Überwachungsendpunkt werden folgende Informationen bereitgestellt:
| Beschreibung                                | Pfad                         |
| ------------------------------------------- | ---------------------------- |
| Anwendung ist ansprechbar                   | `/actuator/health/liveness`  |
| Anwendung kann Anfragen korrekt verarbeiten | `/actuator/health/readiness` |
