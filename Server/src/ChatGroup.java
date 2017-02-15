/**
 *
 * @author LolZ, Samuele Pavone, Andrea Musso
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatGroup 
{
    private String chatName;
    private List<String> groupMember = new ArrayList();
    
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
    public void addMember(String nomeClient)
    {
        groupMember.add(nomeClient);
    }
    
    //Rimuove un membro del gruppo che esce 
    public void removeMember(String nomeClient)
    {
        groupMember.remove(nomeClient);
    }
    
    //Restituisce i membri del gruppo collegati
    public List<String> getGroupMember()
    {
        return groupMember;
    }
    
    //Restituisce il nome del gruppo
    public String getName()
    {
        return chatName;
    }
    
    //Controlla se esiste il client
    public boolean exist(String name)
    {
        for(int i = 0; i < groupMember.size(); i++)
        {
            if(name.equals (groupMember.get(i)))
            {
                return true;
            }
        }
        return false;
    }
}
