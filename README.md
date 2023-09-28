# ArtoChat Chatroom
## 3 Semester Chatroom projekt udarbejdet af Casper Hechmann(JuanWeasley), Nicklas Galver(Ryzaxd) og Justin Chardonnay(Justinxc1337).

**ArtoChat Chatroom funktioner** <br />
ArtoChat Chatroom er opbygget ved hjælp af programmeringssproget Java for "Server", "Clienthandler", "ClientGUI" og "Client" og opmærkningssproget JavaFX for GUI. <br />
Chatrummet funktioner giver brugere mulighed for at vælge deres eget unikke navn, og tilslutte sig til et chatrum sammen med andre brugere, hvor de kan indsende tekstbeskeder til hinanden i reeltid, derudover kan man til højre se hvilke brugere der er online på chatrummet. <br />
<br />
*Server* <br />
1. Server lytter på port 8080 for indgående Clients forbindelser. <br />
2. Når en Client forbinder sig oprettes der et "ClientHandler" object som håndterer kommunikationen med den pågældende Client. <br />
3. Serveren opretholder en arrayliste af tilsluttede Clients, og kontrollerer at deres username er unikt. <br />
4. Derfra broadcaster den arraylisten til de andre tilsluttede Clients i chatrummet hver gang der sker ændringer. <br />
5. Den broadcaster derudover også alle til- og fraslutninger af Clients samt alle beskeder der ville blive skrevet i chatrummet med en timestamp. <br />
<br />
*Client* <br />



 ![start](https://github.com/Ryzaxd/chatroom/assets/110767229/b361aa92-be3c-4fdf-ac0a-76afcee0e01e)
<br />
![chatroom](https://github.com/Ryzaxd/chatroom/assets/110767229/7a79cc70-ce78-4e0d-86d2-97bd6235527a)
<br />
![logo](src/main/resources/com/example/chatroom/Pictures/artochat.png)
<br />
© 2023 Casper Emil Hechmann, Nicklas Flor Galver & Justin Xander Chardonnay. All Rights Reserved.
