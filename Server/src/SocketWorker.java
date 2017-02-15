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
    public boolean exist = false, back = false, trov = true, indietro = false;
    
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
                out.println("-!clientList per conoscere quanti client sono connessi");
                out.println("-!group per conoscere i comandi delle chat");
                out.println("-!exit per uscire");
                line = in.readLine();
                //Comandi a disposizione del client 
                if(line.equals ("!clientList"))
                {
                    for(int i = 0; i < ServerTestoMultiThreaded.listaClient.size(); i++)
                    {
                        out.println(ServerTestoMultiThreaded.listaClient.get(i).nomeClient);
                    }
                }
                
                //Comando per unirsi alla chat di gruppo
                if(line.equals("!group"))
                {
                    do
                    {
                        out.println("Scrivi i comandi:");
                        out.println("-!newGroup per creare una chat");
                        out.println("-!joinGroup per entrare in una chat");
                        out.println("-!listGroup per sapre le chat che sono attive");
                        out.println("-!removeGroup per eliminare un gruppo");
                        out.println("-!back per tornare indietro");
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
                                    trov = false;
                                    out.println("Questo gruppo esiste già");
                                }
                            }
                            if(trov)
                            {
                                ChatGroup c = new ChatGroup(line);
                                ServerTestoMultiThreaded.gruppi.add(c);
                                out.println("Gruppo creato con successo!");
                            }
                            trov = true;
                        }
                        if(line.equals ("!joinGroup"))
                        {
                            //Controlla se la lista di gruppi è vuota
                            if(!ServerTestoMultiThreaded.gruppi.isEmpty())
                            {
                                out.println("Inserisci il nome del gruppo in cui vuoi entrare");
                                line = in.readLine();
                                for(int i = 0; i < ServerTestoMultiThreaded.gruppi.size(); i++)
                                {
                                    if(line.equals (ServerTestoMultiThreaded.gruppi.get(i).getName()))
                                    {
                                        ServerTestoMultiThreaded.gruppi.get(i).addMember(nomeClient);
                                        //Comandi da usare dentro il gruppo
                                        out.println("Sei entrato nel gruppo");
                                        out.println("Scrivi i comandi:");
                                        out.println("-!memberList per conoscere i membri che sono online nel gruppo");
                                        out.println("-!back per tornare al menù dei comandi per i gruppi");
                                        do
                                        {
                                            line = in.readLine();
                                            if(line.equals ("!memberList"))
                                            {
                                                out.println("Membri del gruppo: " + ServerTestoMultiThreaded.gruppi.get(i).getGroupMember());
                                            }
                                            if(line.equals ("!back"))
                                            {
                                                indietro = true;
                                            }
                                            for (int f = 0; f < ServerTestoMultiThreaded.gruppi.size(); f++)
                                            {
                                                for(int j = 0; j < ServerTestoMultiThreaded.listaClient.size(); j++)
                                                {
                                                    if(ServerTestoMultiThreaded.gruppi.get(f).exist(ServerTestoMultiThreaded.listaClient.get(j).nomeClient))
                                                    {
                                                        ServerTestoMultiThreaded.listaClient.get(j).out.println(nomeClient + "-->" + line);
                                                    }
                                                }
                                            }
                                        }while(!indietro);
                                    }
                                    else
                                    {
                                        out.println("Il gruppo non esiste");
                                    }
                                }   
                            }
                            else
                            {
                                out.println("Non è stato creato nessun gruppo");
                            }
                        }
                        if(line.equals ("!listGroup"))
                        {   
                            //Controlla se la lista di gruppi è vuota
                            if(ServerTestoMultiThreaded.gruppi.isEmpty())
                            {
                                out.println("Non è stato creato nessun gruppo");
                            }
                            else
                            {
                                for(int i = 0 ; i < ServerTestoMultiThreaded.gruppi.size(); i++)
                                {
                                   out.println("Gruppi aperti:" + ServerTestoMultiThreaded.gruppi.get(i).getName());
                                }
                            }
                        }
                        if(line.equals ("!removeGroup"))
                        {
                            //Controlla se la lista di gruppi è vuota
                            if(ServerTestoMultiThreaded.gruppi.isEmpty())
                            {
                                out.println("Non è stato creato nessun gruppo");
                            }
                            else
                            {
                                out.println("Inserisci il nome del gruppo che vuoi eliminare");
                                line = in.readLine();
                                for(int i = 0 ; i < ServerTestoMultiThreaded.gruppi.size(); i++)
                                {
                                    if(line.equals (ServerTestoMultiThreaded.gruppi.get(i).getName()))
                                    {
                                        ServerTestoMultiThreaded.gruppi.remove(i);
                                    }
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
                    try
                    {   
                        out.println("Grazie per aver usato il nostro programma XD");
                        ServerTestoMultiThreaded.listaClient.remove(nomeClient);
                        client.close();
                        line = null;
                        System.out.println("Client --> " + nomeClient + " uscito");    
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