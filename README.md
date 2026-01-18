# ShoeLive Backend
Friedrich Hahn 594124  
Nam Le 597511

Dieses Repository enthält das Backend der Anwendung *ShoeLive*.  
Es stellt eine REST-API für das Frontend bereit und verwaltet Nutzer, Anzeigen, Käufe, Benachrichtigungen und Profile.

Das Backend basiert auf Spring Boot.

## Aufgaben des Backends

- Registrierung und Login von Nutzern
- Ausgabe und Validierung von Session-Tokens
- Verwaltung von Anzeigen
- Verwaltung von Käufen und Status „verkauft“
- Erstellung von Benachrichtigungen für Verkäufer
- Berechnung von Profilwerten (Käufe, Verkäufe, Summen)
- Bildupload über Cloudinary (Speicherung der URL in der Datenbank)

## Tests

Das Projekt enthält Integrationstests, die alle Kernfunktionen abdecken:

```bash
./gradlew test
