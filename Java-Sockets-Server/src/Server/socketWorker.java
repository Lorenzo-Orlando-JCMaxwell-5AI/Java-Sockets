package Server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class SocketWorker implements Runnable {
    private Socket client;
    
    //Constructor: inizializza le variabili
    SocketWorker(Socket client) 
    {
        this.client = client;
        System.out.println("Connesso con: " + client);
    }
    
    // Questa e' la funzione che viene lanciata quando il nuovo "Thread" viene generato
    public void run()
    {
        BufferedReader in = null;
        PrintWriter out = null;
        
        try
        {
          // connessione con il socket per ricevere (in) e mandare(out) il testo
          in = new BufferedReader(new InputStreamReader(client.getInputStream()));
          out = new PrintWriter(client.getOutputStream(), true);
        }
        catch (IOException e) 
        {
            System.out.println("Errore: in|out fallito");
            System.exit(-1);
        }
        
        String nomeHost = "";
        String line = "";
        int clientPort = client.
        getPort(); //il "nome" del mittente (client)
        
        try
        {
            //se il nome del client nella variabile non c'è lo inserisce
            if(nomeHost.equals(""))
            {
                out.println("Inserisci il nickname");
                nomeHost = in.readLine();
                ServerTestoMultiThreaded.utenti.add(nomeHost);
            }
        }
        catch (IOException e) 
        {
            System.out.println("Errore: in|out fallito");
            System.exit(-1);
        }
        
        while(line != null)
        {
            try
            {
                //inserisce il testo, se il testo è un comando !listautenti il comando verrà eseguito
                out.println("Scrivi il comando: !listautenti per sapere chi e connesso, !exit per uscire");
                line = in.readLine();
                if(line != null)
                {
                    if(line.equals("!listautenti"))
                    {
                        for(int j=0; j<ServerTestoMultiThreaded.utenti.size(); j++)
                        {
                            out.println(ServerTestoMultiThreaded.utenti.get(j));
                        }
                    }
                    
                    if(line.equals("!exit"))
                    {
                        client.close();
                        ServerTestoMultiThreaded.utenti.remove(nomeHost);
                        System.out.println("Il client: " + client + " è uscito");
                    }
                }
                else
                {
                    ServerTestoMultiThreaded.utenti.remove(nomeHost);
                    System.out.println("Il client: " + client + " è uscito");
                }
                
                //Manda lo stesso messaggio appena ricevuto con in aggiunta il "nome" del client
                out.println("Server-->" + nomeHost + ">> " + line);

                //scrivi messaggio ricevuto su terminale
                System.out.println(nomeHost + ">> " + line);
            }
            catch (IOException e) 
            {
                System.out.println("lettura da socket fallito");
                System.exit(-1);
            }
        }
        
        try 
        {
            client.close();
            System.out.println("connessione con client: " + client + " terminata!");
        }
        catch (IOException e) 
        {
            System.out.println("Errore connessione con client: " + client);
        }
    }
}