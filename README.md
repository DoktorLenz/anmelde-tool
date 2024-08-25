# ScoutVenture

![Alt](https://repobeats.axiom.co/api/embed/3046a12e61b09e8f22a5835d1bb43fc6a383de9e.svg "Repobeats analytics image")

## Installationsanleitung

ScoutVenture benötigt eine PostgreSQL-Datenbank der Version 15. <br/>
Getestet wurde die Anwendung ausschließlich mit Version 15.5. <br/>
Zur Authentifizierung wird ein OAuth2-Provider benötigt der OIDC unterstützt (z.B. [Keycloakk](https://github.com/keycloak/keycloak), [Authentik](https://github.com/goauthentik/authentik), [Zitadel](https://github.com/zitadel/zitadel))
Wenn die Überwachungsendpunkte aktiv sind, wird empfohlen ein Reverse-Proxy vor ScoutVenture zu schalten, der verhindert, dass diese Endpunkte durch unberechtigte Benutzer erreicht werden können.


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

**Überwachung**
| Parameter                         | Beschreibung                     | Default |
| --------------------------------- | -------------------------------- | ------- |
| `sv.logging.directory`            | Ablageort der Log-Datei          | `logs`  |
| `sv.logging.file.size-limit`      | Max-Größe einer Log-Datei        | `5MB`   |
| `sv.monitoring.actuators.enabled` | Gibt Überwachungs-Endpunkte frei | `true`  |

### Informationen zur Überwachung

Über die Überwachungsendpunkt werden folgende Informationen bereitgestellt:
| Beschreibung                                | Pfad                         |
| ------------------------------------------- | ---------------------------- |
| Anwendung ist ansprechbar                   | `/actuator/health/liveness`  |
| Anwendung kann Anfragen korrekt verarbeiten | `/actuator/health/readiness` |
