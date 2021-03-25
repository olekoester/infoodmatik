# Infoodmatik ein Projekt im Rahmen des Moduls SWEIII

## Requirements
- Apache maven 3.6.3
- Wildfly 21.0.0.Final Anwendungsserver
- JBoss Tools
- JDK 15.0.2


## Installation
- Alle angegebenen Softwarepakete aus den Requirements muessen installiert werden
- In der IDE ein Deployment via "Run Configurations" erstellen Goal: "package wildfly:deploy"

## Betriebsanleitung
- oeffnen Sie die index.xhtml

Admin :
- Wenn man sich als Admin Registrieren moechte, muss man bei der Registrierung das Adminpasswort "admin" mitgeben
- Als Admin hat man die moeglichkeit alle Rezepte zu Bearbeiten und zu loeschen

Angemeldeter Benutzer:
- Um ein Rezept zu erstellen, muss man zuerst als Benutzer eingeloggt sein
- Man kann jedes Rezept nur einmal Bewerten
- Eigene Rezepte Bearbeiten und Loeschen

Nicht angemeldeter Benutzer:
- Rezepte ansehen, filtern und suchen



