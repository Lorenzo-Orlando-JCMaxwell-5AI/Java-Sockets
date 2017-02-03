/**
 *
 * @author LolZ, Samuele Pavone, Andrea Musso
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatGroup 
{
    private static String chatName;
    private static List<String> groupMember = new ArrayList();
    
    //Costruttore del gruppo
    public ChatGroup(String chatName, List<String> groupMember)
    {
        this.chatName = chatName;
        this.groupMember = groupMember;
    }
    
    //Altro costruttore
    public ChatGroup(String chatName)
    {
        this.chatName = chatName;
    }
    
    //Aggiunge un membro al gruppo
    public static void addMember(String nomeClient)
    {
        groupMember.add(nomeClient);
    }
    
    //Rimuove un membro del gruppo che esce 
    public void removeMember(String nomeClient)
    {
        groupMember.remove(nomeClient);
    }
    
    //Restituisce i membri del gruppo collegati
    public static List<String> getGroupMember()
    {
        return groupMember;
    }
    
    //Restituisce il nome del gruppo
    public static String getName()
    {
        return chatName;
    }
}
