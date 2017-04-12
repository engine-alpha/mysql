DEADME	Version 1.0	(2015-02-17)

Ueberblick
----------
Bibliothek zum Zugriff auf MySQL-Datenbank-Server aus einer JAVA-Anwendung heraus. 
Die Ergebnis-Tabellen berstehen der Einfachheit halber aus String-Zellen. 
Die Zellinhalte koennen nach dem Auslesen aus der Tabelle zum entsprechen Typ gecastet werden. 


Klasse MySqlDatensatz
---------------------
Repraesentiert eine Zeile in einer Ergebnis-Tabelle. 
Container fuer Attributwerte vom Typ Sting. 
Hat nach der Erzeugung keine Attribute. 
Attribute koennen mit der Methode attributHinzufuegen(String) hinzugefuegt werden. 
Attribut-Werte koennen mit der Methode nenneAttributWert(int) abgefragt werden. 


Klasse MySqlTabelle
-------------------
Repraesentiert eine Ergebnis-Tabelle nach einem korrekten SELECT-Statement. 
Dient NUR zum Entnehmen der Abfrage-Ergebnisse in Form von Strings. 
Hat eine Methode zum Nennen der Anzahl seiner Spalten. 
Hat eine Methode zum Nennen der Anzahl seiner Datensaetze. 
Datensaetze koennen mit der Methode datensatzHinzufuegen(MySqlDatensatz) hinzugefuegt werden. 
Zell-Werte koennen mit der Methode nenneAttributWert(zeilenNr, spaltenNr) erfragt werden. 
Die Zellinhalte koennen nach dem Auslesen aus der Tabelle zum entsprechen Typ gecastet werden. 


Klasse MySqlZugriff
-------------------
Repraesentiert die Verbindung zum Datenbank-Server. 
- Laedt beim Erzeugen eines Objekts den MySQL-Treiber. 
- Kann eine Verbindung zu einem MySQL-Server herstellen. 
- Kann UPDATE-/INSERT-/DELETE-Satements abschicken mit der Methode sql_ohne_select(String). 
- Kann SELECT-Statements abschicken mit der Methode sql_select(String). 
- Bietet Zugriff auf: 
  * letztes SQL-Statement 
  * letzte SQL-Fehlermeldung 
  * ob die letzte Abfrage akzeptiert wurde 
  * die Ergebnis-Tabelle bei erfolgreicher Abfrage 
- Kann die Verbindung zum MySQL-Server wieder beenden  

