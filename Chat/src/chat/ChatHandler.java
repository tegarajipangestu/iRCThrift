/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author tegar
 */
import org.apache.thrift.TException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChatHandler implements ChatService.Iface {
    public static List<String> defaultUsernames;
    public static List<String> activeUsers;
    public static List<Channel> activeChannels;
    
    public ChatHandler() {
        defaultUsernames = new ArrayList<>(
                Arrays.asList("Kucing","Sapi","Rusa","Kambing","Platipus")
        );
        activeUsers = new ArrayList<>();
        activeChannels = new ArrayList<Channel>();
    }
    
    @Override
    public String createNickname(String name) {
        System.out.println("calling nick from "+name);
        String finalName = "";
        if(name.equals("")) { //kasus random username, diasumsikan masih ada nama yang tersedia
            int rndIdx = new Random().nextInt((defaultUsernames.size() - 0));
            String potentialName = defaultUsernames.get(rndIdx); 
            while(isUsernameExists(potentialName)) {
                rndIdx = new Random().nextInt((defaultUsernames.size() - 0));
                potentialName = defaultUsernames.get(rndIdx); 
            }
            defaultUsernames.remove(rndIdx);
            finalName = potentialName;
        }
        else{
            if(isUsernameExists(name)) {
                return "";
            }
            else {
                finalName = name;
            }
        }
        activeUsers.add(finalName);
        return finalName;
    }

    @Override
    public boolean joinChannel(String name, String channel) {
        if (name.isEmpty() || channel.isEmpty()) return false;
        System.out.println("calling join from "+name);
        int i = 0;
        while (i<activeChannels.size())
        {
            if (activeChannels.get(i).getName().compareToIgnoreCase(channel)==0)
            {
                System.out.println(name+" successfully joined "+channel);
                activeChannels.get(i).addActiveUser(name);
                return true;
            }
            else
            {
                i++;
            }
        }
        Channel c = new Channel(channel);
        c.addActiveUser(name);
        activeChannels.add(c);
        System.out.println(name+" successfully joined "+channel);
        return true;
    }

    @Override
    public boolean leaveChannel(String name, String channel) {
        if (name.isEmpty() || channel.isEmpty()) return false;
        System.out.println("calling leave from "+name);
        int i = 0;
        while (i<activeChannels.size())
        {
            System.out.println(activeChannels.get(i).getName());
            System.out.println(channel);
            if (activeChannels.get(i).getName().compareToIgnoreCase(channel)==0)
            {
                activeChannels.get(i).removeActiveUser(name);
                System.out.println(name+" successfully left "+channel);
                return true;
            }
            else
            {
                i++;
            }
        }
        System.out.println("Channel not found");
        return false;

    }

    @Override
    public boolean exitProgram(String name) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendMessage(String name, String channel) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMessage(String name) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Utility functions
    private boolean isUsernameExists(String name) {
        for(int i=0; i<activeUsers.size(); i++) {
            if(name.equals(activeUsers.get(i))) {
                return true;
            }
        }
        return false;
    }

}
