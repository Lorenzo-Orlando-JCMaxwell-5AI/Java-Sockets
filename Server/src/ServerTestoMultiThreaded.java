/**
 *
 * @author LolZ, Samuele Pavone, Andrea Musso
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class ServerTestoMultiThreaded
{
    //Dichiarazione della lista dei nomi client e dei gruppi
    public static List<SocketWorker> listaClient = new ArrayList();
    public static List<ChatGroup> gruppi = new ArrayList();
    
    public static void main(String[] args) 
    {
        if (args.length != 1) 
        {
            System.out.println("Uso: java ServerTestoMultithreaded <Porta Server>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);
        
        try
        {
            ServerSocket server = new ServerSocket(portNumber);
            System.out.println("Server di Testo in esecuzione...  (CTRL-C quits)\n");

            while(true)
            {
                SocketWorker w;
                try 
                {
                    //server.accept returns a client connection
                    w = new SocketWorker(server.accept());
                    Thread t = new Thread(w);
                    listaClient.add(w);
                    System.out.println("Client aggiunto");
                    t.start();
                } 
                catch (IOException e) 
                {
                    System.out.println("Connessione NON riuscita con client: ");
                    System.exit(-1);
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error! Porta: " + portNumber + " non disponibile");
            System.exit(-1);
        }
    }
    
    public void reInvia()
    {
        
    }
}