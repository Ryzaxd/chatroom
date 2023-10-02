# ArtoChat Chatroom
## 3 Semester Chatroom projekt udarbejdet af Casper Hechmann(JuanWeasley), Nicklas Galver(Ryzaxd) og Justin Chardonnay(Justinxc1337).

**ArtoChat Chatroom funktioner** <br />
ArtoChat Chatroom er opbygget ved hjælp af programmeringssproget Java for "Server", "Clienthandler", "ClientGUI" og "Client" og opmærkningssproget JavaFX for GUI. <br />
Chatrummet funktioner giver brugere mulighed for at vælge deres eget unikke navn, og tilslutte sig til et chatrum sammen med andre brugere, hvor de kan indsende tekstbeskeder til hinanden i reeltid, derudover kan man til højre se hvilke brugere der er online på chatrummet. <br />
<br />
## Server <br />
1. Server lytter på port 8080 for indgående Clients forbindelser. <br />
2. Når en Client forbinder sig oprettes der et "ClientHandler" object som håndterer kommunikationen med den pågældende Client. <br />
3. Serveren opretholder en arrayliste af tilsluttede Clients, og kontrollerer at deres username er unikt. <br />
4. Derfra broadcaster den arraylisten til de andre tilsluttede Clients i chatrummet hver gang der sker ændringer. <br />
5. Den broadcaster derudover også alle til- og fraslutninger af Clients samt alle beskeder der ville blive skrevet i chatrummet med en timestamp i Serverkonsolen. <br />
<br />
## Client <br />
1. Clients GUI implementeres ved hjælp af JavaFX. Det starter med en startskærm til indtastning af username. <br />
2. Når Clients indtaster sit username og klikker på "Enter", etablerer den en forbindelse til Serveren. <br />
3. Clients kommunikerer med Serveren gennem en Socket og separaterer input- og outputstreams - meddelelser sendes til serveren og modtages fra
      serveren. <br />
4. Clients kan sende chatbeskeder til Serveren, og Serveren udsender disse beskeder til alle tilsluttede Clients. <br />
5. CLients GUI viser chatbeskederne i et TextArea, og det giver Clients mulighed for at sende beskeder ved at skrive i et TextField <br />
<br />
## ClientHandler <br />
1. Hver tilsluttet Clients administreres af en instance af ClientHandler-klassen. <br />
2. ClientHandler-klassen håndterer kommunikationen med Clients, herunder parsing af beskeder, broadcasting og håndterer af usernames. <br />
<br />
<br />
## Protokolspecifikation <br />
<br />
Client-to-Server beskeder: <br />
<br />
Tilslutter sig Chat: <br />
<br />
Format: /join username <br />
Eksempel: /join Hans <br />
Beskrivelse: Sendt af en Client når de gerne vil tilslutte sig chatrummet med et specifikt username. <br />
<br />
Sender en chatbesked: <br />
<br />
Format: message <br />
Eksempel: Hello, everyone! <br />
Beskrivelse: Sendt af en Client for at broadcaste en chatbesked til alle tilsluttede Clients. <br />
<br />
Server-to-Client Beskeder: <br />
<br />
Anerkendelse af succesfuld tilslutning: <br />
<br />
Format: SUCCESS <br />
Eksempel: SUCCESS <br />
Beskrivelse: Sendt af Serveren for at anerkende en vellykket Client tilslutning. <br />
<br /> 
Anerkendelse af mislykket tilslutning: <br />
<br />
Format: ERROR message <br />
Eksempel: ERROR: Username is not unique. Please choose a different username. <br />
Beskrivelse: Sendt af Serveren for at informere Client at deres tilslutning var mislykket. <br />
<br />
Broadcast Chatbeskeder: <br />
<br />
Format: timestamp username: message <br />
Eksempel: [12:30:45] Hans: Hello, everyone! <br />
Beskrivelse: Sendt af Serveren for at broadcaste chatbeskeder til tilsluttede Clients med et timestamp og afsenders username.<br />
<br />
Userlist Update: <br />
<br />
Format: USERLIST username1,username2,... <br />
Eksempel: USERLIST Hans,Erik <br />
Beskrivelse: Sendt af Serveren til at opdatere listen over tilsluttede Clients for alle Clients. <br />
<br />
<br />
![TCP Model af vores Chatprojekt](https://github.com/Ryzaxd/chatroom/assets/121102386/f4c1ac32-ad9e-43a3-ac7f-4561fa681d8b)
<br />
 ![start](https://github.com/Ryzaxd/chatroom/assets/110767229/b361aa92-be3c-4fdf-ac0a-76afcee0e01e)
<br />
![chatroom](https://github.com/Ryzaxd/chatroom/assets/110767229/7a79cc70-ce78-4e0d-86d2-97bd6235527a)
<br />
![logo](src/main/resources/com/example/chatroom/Pictures/artochat.png)
<br />
© 2023 Casper Emil Hechmann, Nicklas Flor Galver & Justin Xander Chardonnay. All Rights Reserved.
