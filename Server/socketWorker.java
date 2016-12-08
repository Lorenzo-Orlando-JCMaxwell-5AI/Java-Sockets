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
        String line = "";
        String nomeHost = "";
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
        
        int clientPort = client.getPort(); //il "nome" del mittente (client)
        while(line != null){
          try
          {
            //se il nome nella variabile non c'è lo inserisce
            if(nomeHost == "")
            {
                out.println("Scrivi il comando !listautenti per sapere chi è connesso");
                out.println("Inserisci il nickname");
                nomeHost = in.readLine();
                ServerTestoMultiThreaded.utenti.add(nomeHost);
            }
            //inserisce il testo se il testo e il comando il comando verrà eseguito
            line = in.readLine();
            if(line.equals("!listautenti"));
            {
                for(int j=0; j<ServerTestoMultiThreaded.utenti.size(); j++)
                {
                    out.println(ServerTestoMultiThreaded.utenti.get(j));
                }
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