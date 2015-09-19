/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author tegar
 */
public class User {
    private String name;
    private List<String> myChannels;

    public User(String name) {
        this.name = name;
        this.myChannels = new ArrayList<String>();
    }
    
    public User() {
        this.name = "";
        this.myChannels = new ArrayList<String>();        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMyChannels() {
        return myChannels;
    }

    public void setMyChannels(List<String> myChannels) {
        this.myChannels = myChannels;
    }

    public void addChannel(String channel) {
        this.myChannels.add(channel);
    }
    
    public void removeChannel (String channel)
    {
        this.myChannels.remove(this.myChannels.indexOf(channel));
    }
    public boolean isEmpty()
    {
        return this.name.isEmpty();
    }
}
