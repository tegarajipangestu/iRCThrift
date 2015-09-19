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
import chat.ChatService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatHandler implements ChatService.Iface {
    public static List<String> defaultUsernames;
    public static List<String> activeUsers;

    public ChatHandler() {
        defaultUsernames = new ArrayList<>(
                Arrays.asList("Kucing", "Sapi", "Rusa")
        );
        activeUsers = new ArrayList<>();
    }
    
    @Override
    public boolean createNickname(String name) { 
        for(int i=0; i<activeUsers.size(); i++) {
            if(name.equals(activeUsers.get(i))) {
                return false;
            }
        }
        activeUsers.add(name);
        return true;
    }

    @Override
    public boolean joinChannel(String name, String channel) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean leaveChannel(String name, String channel) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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


}
