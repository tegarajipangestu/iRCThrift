/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.List;

/**
 *
 * @author tegar
 */
public class Channels {
    public static List<String> activeChannels;

    public void addActiveChannel(String channel)
    {
        activeChannels.add(channel);
    }
}
