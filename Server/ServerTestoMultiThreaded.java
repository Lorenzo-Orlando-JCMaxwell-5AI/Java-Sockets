import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerTestoMultiThreaded 
{
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
                    List<String> utenti= new ArrayList();
                    w = new SocketWorker(server.accept());
                    Thread t = new Thread(w);
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
}