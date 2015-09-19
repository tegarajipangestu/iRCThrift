/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tegar
 */
public class Channel {
    public List<String> activeUser;
    
    private String name;
    
    public void addActiveUser(String user)
    {
        activeUser.add(user);
    }

    public Channel(String _name)
    {
        activeUser = new ArrayList<String>();
        name = new String(_name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
