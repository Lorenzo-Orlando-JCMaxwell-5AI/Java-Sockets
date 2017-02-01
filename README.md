
# Java-Sockets
Concetti e realizzazione di una semplice connessione Client/Server usando Sockets in Java
```
Obitettivo del progetto: inviare linee di testo da un programma (Client)... -->
                         --> ad un altro (Server) anche eventualmente remoto.
```
**Nota:** Visiona le [Slides della lezione](https://docs.google.com/presentation/d/19vhath-GbtUO7ofpukJE4Opi16aTfrnurJ2lUizB0iE/edit?usp=sharing)
## Istruzioni
Per replicare il progetto sul vostro computer personale. Gli allievi potranno quindi apportare modifiche ed eventualmente fonderle con il progetto principale, o in alcuni casi creare un progetto propio.

### Prerequisiti
Java SDK (Software Development Kit) - programma per la compilazione (javac) da codice in Java a ByteCode. L'installazione comprende anche Java JRE (Java Runtime Envirorment) che fornisce la Virtual Machine (VM) su cui far eseguire il ByteCode.
```
E' suggerito anche l'utilizzo di netBeans IDE (Integrated Development Envirorment)
```

### Installazione
Scarica i files del progetto cliccando "Clone/Download"
* in NetBeans crea due nuovi progetti "Java-Sockets-Client" e "Java-Sockets-Server"

* estrai i files e spostali nella sub-directory "src" dei rispettivi progetti (es. Documents\NetBeansProjects\Java-Sockets-Server\src)


## Uso
Dal terminale del Server:
```
java ServerTestoMultiThreaded <server port>
```
Da un terminale Client:
```
java clientTesto <host> <server port>
dove:
* host puo' essere espresso sia in forma numerica (es. 127.0.0.1) che in forma alfanumerica (es. www.nomeSito.com)
```
NOTA: posso collegarmi al server con quanti Clients desidero, sia su stesso computer che da terminali su computer diversi.

### Esempio usando stesso computer sia per eseguire Server che multipli Clients
Da finestra di comando esguire il Server:
```
cd Documents\NetBeansProjects\Java-Sockets-Server\src
java ServerTestoMultiThreaded 1234
```
Da nuova finestra di comando esguire il ```primo``` Client
```
cd Documents\NetBeansProjects\Java-Sockets-Client\src
java client-Testo localhost 1234
```
Da nuova finestra di comando esguire il ```secondo``` Client
```
cd Documents\NetBeansProjects\Java-Sockets-Client\src
java client-Testo localhost 1234
```
## Funzioni Del programma

Per salvare i nickname degli utenti connessi abbiamo utilizzato una lista creata nel ServerTestoMultithreaded.
Il programma all'avvio chiederà di inserire il nome e sarà possibile visualizzare la lista degli utenti connessi
al server utilizzando il comando !listautenti.

## Licenza
opensource nel modo piu' completo del termine :) senza alcuna restrizione!
