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
    public static List<User> broadcastChannels;
    public User currentUser;

    public ChatHandler() {
        defaultUsernames = new ArrayList<>(
                Arrays.asList("Kucing", "Sapi", "Rusa", "Kambing", "Platipus")
        );
        activeUsers = new ArrayList<>();
        activeChannels = new ArrayList<Channel>();
        currentUser = new User();
        broadcastChannels = new ArrayList<User>();
    }

    @Override
    public String createNickname(String name) {
        System.out.println("calling nick from " + name);
        String finalName = "";
        if (name.equals("")) { //kasus random username, diasumsikan masih ada nama yang tersedia
            int rndIdx = new Random().nextInt((defaultUsernames.size() - 0));
            String potentialName = defaultUsernames.get(rndIdx);
            while (isUsernameExists(potentialName)) {
                rndIdx = new Random().nextInt((defaultUsernames.size() - 0));
                potentialName = defaultUsernames.get(rndIdx);
            }
            defaultUsernames.remove(rndIdx);
            finalName = potentialName;
        } else {
            if (isUsernameExists(name)) {
                return "";
            } else {
                finalName = name;
            }
        }
        activeUsers.add(finalName);
        currentUser.setName(finalName);
        broadcastChannels.add(new User(finalName));
        return finalName;
    }

    @Override
    public boolean joinChannel(String name, String channel) {
        if (name.isEmpty() || channel.isEmpty()) {
            return false;
        }
        System.out.println("calling join from " + name);
        int i = 0;
        while (i < activeChannels.size()) {
            if (activeChannels.get(i).getName().compareToIgnoreCase(channel) == 0) {
                System.out.println(name + " successfully joined " + channel);
                activeChannels.get(i).addActiveUser(name);
                return true;
            } else {
                i++;
            }
        }
        Channel c = new Channel(channel);
        c.addActiveUser(name);
        activeChannels.add(c);
        currentUser.addChannel(channel);
        System.out.println(name + " successfully joined " + channel);
        return true;
    }

    @Override
    public boolean leaveChannel(String name, String channel) {
        if (name.isEmpty() || channel.isEmpty()) {
            return false;
        }
        System.out.println("calling leave from " + name);
        int i = 0;
        while (i < activeChannels.size()) {
            if (activeChannels.get(i).getName().compareToIgnoreCase(channel) == 0) {
                activeChannels.get(i).removeActiveUser(name);
                currentUser.removeChannel(channel);
                System.out.println(name + " successfully left " + channel);
                return true;
            } else {
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
    public boolean sendMessage(String name, String channel, String message) {
        String mesString = new String("[" + channel + "] " + "(" + name + ") " + message);
        System.out.println(name + " sending " + message + " to " + channel);
        int i = 0;
        boolean foundChannel = false;
        System.out.println("channel msg "+channel);
        if (!channel.equals("broadcast")) {
            while (i < activeChannels.size()) {
                if (activeChannels.get(i).getName().equals(channel)) {
                    foundChannel = true;
                    for (int j = 0; j < activeChannels.get(i).activeUser.size(); j++) {
                        if (!activeChannels.get(i).activeUser.get(j).getName().equals(name)) {
                            activeChannels.get(i).activeUser.get(j).addMessage(mesString);
                        }
                    }
                }
                i++;
            }
            return foundChannel;
        } else {
            for (int j = 0; j < broadcastChannels.size(); j++) {
                if (!broadcastChannels.get(j).getName().equals(name)) {
                    System.out.println("broadcast msg "+mesString);
                    broadcastChannels.get(j).addMessage(mesString);
                }
            }
            return true;
        }
    }

    @Override
    public String getMessage(String name) {
        StringBuilder msgBuilder = new StringBuilder();
        for (int i = 0; i < activeChannels.size(); i++) {
            for (int j = 0; j < activeChannels.get(i).activeUser.size(); j++) {
                if (activeChannels.get(i).activeUser.get(j).getName().equals(name)) {
                    if (!activeChannels.get(i).activeUser.get(j).getMessQueue().isEmpty()) {
                        msgBuilder.append(activeChannels.get(i).activeUser.get(j).getAllMessage());
                    }
                }
            }
        }
        for (int j = 0; j < broadcastChannels.size(); j++) {
            if (broadcastChannels.get(j).getName().equals(name)) {
//                    System.out.println("broad = "+broadcastChannels.get(j).getAllMessage());
                if (!broadcastChannels.get(j).getMessQueue().isEmpty()) {
//                    System.out.println("broad = "+broadcastChannels.get(j).getAllMessage());
                    msgBuilder.append(broadcastChannels.get(j).getAllMessage());
                }
            }
        }

        return msgBuilder.toString();
    }

    //Utility functions
    private boolean isUsernameExists(String name) {
        for (int i = 0; i < activeUsers.size(); i++) {
            if (name.equals(activeUsers.get(i))) {
                return true;
            }
        }
        return false;
    }

}
