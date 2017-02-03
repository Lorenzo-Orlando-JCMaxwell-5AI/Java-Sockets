/**
 *
 * @author LolZ, Samuele Pavone, Andrea Musso
 */
import java.net.*;
import java.io.*;

class SocketWorker implements Runnable 
{
    private Socket client;
    public String nomeClient;
    public boolean exist = false, back = false, trov = false;
    
    BufferedReader in = null;
        PrintWriter out = null;

    
    //Costruttore: inizializza le variabili
    SocketWorker(Socket client) 
    {
        this.client = client;
        System.out.println("Connesso con: " + client);
    }

    
    // Questa è la funzione che viene lanciata quando il nuovo "Thread" viene generato
    public void run()
    {
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
                out.println("Benvenuto!, scrivi un messaggio o usa i comandi:");
                out.println("-!clientlist per sconoscere quanti client sono connessi");
                out.println("-!group per conoscere i comandi delle chat");
                out.println("-!exit per uscire");
                line = in.readLine();
                
                //Comandi a disposizione del client 
                if(line.equals ("!clientlist"))
                {
                    for(int i = 0; i < ServerTestoMultiThreaded.listaClient.size(); i++)
                    {
                        out.println(ServerTestoMultiThreaded.listaClient.get(i));
                    }
                }
                
                //Comando per unirsi alla chat di gruppo
                if(line.equals("!group"))
                {
                    do{
                        out.println("Scrivi:");
                        out.println("-!newGroup per creare una chat");
                        out.println("-!joinGroup per entrare in una chat");
                        out.println("-!listGroup per sapre le chat che sono attive");
                        out.println("-!back per tornare indietro");
                        System.out.println("");
                        line = in.readLine();
                        //Comandi per le chat
                        if(line.equals ("!newGroup"))
                        {   
                            out.println("Inserisci il nome del gruppo che vuoi creare");
                            line = in.readLine();
                            //Controllo se esiste gia il gruppo
                            for(int i = 0; i < ServerTestoMultiThreaded.gruppi.size(); i++)
                            {
                                if(line.equals (ServerTestoMultiThreaded.gruppi.get(i).getName()))
                                {
                                    out.println("Questo gruppo esiste già");
                                    System.out.println("");
                                }
                                else
                                {
                                    trov = true;
                                }
                            }
                            if(trov)
                            {
                                ChatGroup c = new ChatGroup(line);
                                ServerTestoMultiThreaded.gruppi.add(c);
                                out.println("Gruppo creato con successo!");
                                System.out.println("");
                            }
                        }
                        if(line.equals ("!joinGroup"))
                        {
                            if(ServerTestoMultiThreaded.gruppi.isEmpty())
                            {
                                System.out.println("Non è stato creato nessun gruppo");
                                System.out.println("");
                            }
                            else
                            {
                                out.println("Inserisci il nome del gruppo in cui vuoi entrare");
                                line = in.readLine();
                                for(int i = 0; i < ServerTestoMultiThreaded.gruppi.size(); i++)
                                {
                                    if(line.equals (ServerTestoMultiThreaded.gruppi.get(i).getName()))
                                    {
                                        ChatGroup.addMember(nomeClient);
                                    }
                                    else
                                    {
                                        System.out.println("Il gruppo non esiste");
                                        System.out.println("");
                                    }
                                }
                            }
                        }
                        if(line.equals ("!listGroup"))
                        {   
                            if(ServerTestoMultiThreaded.gruppi.isEmpty())
                            {
                                System.out.println("Non è stato creato nessun gruppo");
                                System.out.println("");
                            }
                            else
                            {
                                for(int i = 0 ; i < ServerTestoMultiThreaded.gruppi.size(); i++)
                                {
                                   out.println("Gruppi aperti:" + ServerTestoMultiThreaded.gruppi.get(i).getName()); 
                                   System.out.println("");
                                }
                            }
                        }
                        if(line.equals ("!back"))
                        {
                            back = true;
                        }
                    }while(!back);
                }
                
                //Comando per uscire dal server
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