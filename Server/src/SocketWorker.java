/**
 *
 * @author LolZ
 */
import java.net.*;
import java.io.*;

class SocketWorker implements Runnable 
{
    private Socket client;
    public String nomeClient;
    public boolean exist = false;

    
    //Costruttore: inizializza le variabili
    SocketWorker(Socket client) 
    {
        this.client = client;
        System.out.println("Connesso con: " + client);
    }

    
    // Questa è la funzione che viene lanciata quando il nuovo "Thread" viene generato
    public void run()
    {
        BufferedReader in = null;
        PrintWriter out = null;
        
        try
        {
            //Connessione con il socket per ricevere (in) e mandare(out) il testo
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } 
        catch (IOException e) 
        {
            System.out.println("Errore: in|out fallito");
            System.exit(-1);
        }

        
        String line = "";
        //Inserimento del nome
        try
        {
            if(nomeClient == null)
            {
                out.println("Inserisci il nome del client");
                nomeClient = in.readLine();
                ServerTestoMultiThreaded.listaClient.add(nomeClient);
            } 
        }
        catch (IOException e) 
        {
            System.out.println("Lettura da socket fallito");
            System.exit(-1);
        }
        
        
        int clientPort = client.getPort(); //Il "nome" del mittente (client)
        //Inserimento del messaggio che viene inviato al server
        while(line != null)
        {
            try
            {   
                out.println("Scrivi un messaggio o usa i comandi !clientlist per sapere quanti client sono connessi, !exit per uscire");
                line = in.readLine();
                //Comandi a disposizione del client 
                if(line.equals ("!clientlist"))
                {
                    for(int i = 0; i < ServerTestoMultiThreaded.listaClient.size(); i++)
                    {
                        out.println(ServerTestoMultiThreaded.listaClient.get(i));
                    }
                }
                if(line.equals("!exit"))
                {
                    ServerTestoMultiThreaded.listaClient.remove(nomeClient);
                    try
                    {   
                        out.println("Grazie per aver usato il nostro programma XD");
                        client.close();
                        line = null;
                        out.println("Client --> " + nomeClient + " uscito");    
                    }
                    catch (IOException e) 
                    { 
                        System.out.println("Errore connessione con client: " + nomeClient);
                    }
                }
                //Manda lo stesso messaggio appena ricevuto con in aggiunta il "nome" del client
                out.println("Server --> " + nomeClient + " >> " + line);
                //scrivi messaggio ricevuto su terminale
                System.out.println(nomeClient + " >> " + line);
            } 
            catch (IOException e) 
            {
                System.out.println("Lettura da socket fallito");
                System.exit(-1);
            }
        }
        try 
        {
            client.close();
            System.out.println("Connessione con client: " + nomeClient + " terminata!");
        }
        catch (IOException e) 
        {
            System.out.println("Errore connessione con il client: " + nomeClient);
        }
    }
}