<p align="center">
 <img src="CurrencyConverter10.png" alt="Banner" width="100%" height="400px" style="object-fit: cover;"></p> 
💱 Currency Converter (Java)
 
🇩🇪 Deutsch 

Eine hocheffizienter Weahrungsrechner mit grafischer Benutzeroberfläche (Swing), der moderne 
Software-Architektur und Echtzeit-API-Anbindung demonstriert. 
Der App wurde auf anspruchsvolle Niveau eines Informatikstudiums entwickelt, um Konzepte wie Daten-Caching und 
Netzwerkschnittstellen zu visualisieren.

✨Highlight

* Robustheit: Die App ist absturzsicher! Bei falschen Eingaben (Buchstaben statt Zahlen) stürzt das Programm
  nicht ab, sondern gibt klare Anweisungen.
* Live-Daten: Nutzung einer API für aktuelle Wechselkurse.
* Offline-Modus: Funktioniert dank GSON-Caching auch ohne Internet.
* Responsive UI: Dank Multithreading (SwingWorker) bleibt die Benutzeroberfläche auch während laufender API-Abfrage
  jederzeit reaktionsschnell und friert nicht ein.

🛠 Technische Details

• Sprache: Java

• Framework: Swing (GUI)

• Asynchrones Laden: Entlasstung der Event Dispatch Threade (EDT) durch asynchrone API-Abfrage. 

• Layout: GridBagLayout für eine präzise Anordnung der Komponenten.

• Build-Tool: Maven (pom.xml)

• Workflows Actions: (maven.yml) - (release.yml)


🚀 **Setup**

Das Projekt nutzt Maven. Einfach in IntelliJ öffnen – alle Abhängigkeiten (GSON) laden automatisch!

------------------------------------------------------------------------------------------------------------------------------------
💱 Currency Converter (Java) 
🇺🇸 English

A highly efficient currency calculator with a graphical user interface (Swing) that demonstrates modern 
software architecture and real-time API connectivity.
The application was developed to the rigorous standards of a computer science currculum in order to 
visualize concepts such as data caching and network interfaces.

✨Highlight

* Robustness: The app is crash-proof! If incorrect entries are made (letters instead of numbers), 
  the program doesn't crash, but instead gives clear instructions.
* Live-data: Uses an API for current exchange rates.
* Offline mode: Works even without an internet connection thank to GSON caching.
* Responsive UI: Background data fetching via SwingWorker ensures the interface never freezes during API calls.


• 🛠 Technical Details

• Language: Java

• Framework: Swing (GUI)

• Asynchronous Loading: Offloading network tasks from the Event Dispatch Thread (EDT) for a smooth user experience.

• Layout: GridBagLayout for precise component positioning.

• Build-Tool: Maven (pom.xml)

• Workflows Actions: (maven.yml) - (release.yml)


🚀 **Setup**

The project uses Maven. Simply open it in IntelliJ - all dependencies (JSON) will load automatically.

### 📸 Screenshots

* 🔜[UI CurrencyConverter](#ui-screenshot1)

* 🔜 [ Currency List](#list-screenshot2)

* 🔜[Online-Mode](#online-screenshot3)

* 🔜[Offline-Mode](#offline-screenshot4)
  
* 🔜 [ Error Handling](#error-screenshot5)


<p align="center" id = "ui-screenshot1" >
UI CurrencyConverter<br>
<img width="730" height="703" alt="screenshot1" src="https://github.com/user-attachments/assets/dc6ad0c5-2c51-4c19-bba7-81a6004ffb1e" />

<p align="center" id= "list-screenshot2">
Currency List<br>
<img width="722" height="735" alt="screenshot2" src="https://github.com/user-attachments/assets/8713a6d1-4639-450c-ba86-7452a77907b5" />

<p align="center" id= "online-screenshot3">
Online-Mode<br>
<img width="715" height="712" alt="screenshot3" src="https://github.com/user-attachments/assets/f8618eb7-184e-414b-bca8-c89689add6ad" />

<p align="center" id= "offline-screenshot4">
Offline-Mode<br>
<img width="711" height="720" alt="screenshot4" src="https://github.com/user-attachments/assets/df608f00-3cec-4fe8-b730-f01965f47744" />

<p align="center" id= "error-screenshot5">
Error Handling<br>
<img width="711" height="704" alt="screenshot5" src="https://github.com/user-attachments/assets/14657166-9b19-4c22-9d65-b91f0cde2a93" />


🚀 **Setup**

Das Projekt nutzt Maven. Einfach in IntelliJ öffnen – alle Abhängigkeiten (GSON) laden automatisch!
