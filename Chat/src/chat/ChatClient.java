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
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import java.util.Random;
import java.util.Scanner;

public class ChatClient {


    public static void main(String[] args) {
        
        try {
            TTransport transport;

            transport = new TSocket("localhost", 9090);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            ChatService.Client client = new ChatService.Client(protocol);
            perform(client);
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void perform(ChatService.Client client)
            throws TException {
        User u = new User();
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        while (!command.equals("/EXIT")) {
            if (command.length() >= 5 && command.substring(0, 5).equals("/NICK")) {
                if (command.length() == 5) { //default username
                    u.setName(client.createNickname(""));
                    System.out.println("Successfully created nickname "+u.getName());
                } else if (command.charAt(5) == ' ' && command.length() >= 7) {
                    String name = client.createNickname(command.substring(6, command.length()));
                    if (!name.equals("")) {
                        u.setName(name);
                        System.out.println("Successfully created nickname "+u.getName());
                    } else {
                        System.out.println("Name was taken. Choose another name");
                    }

                }
            } else if (command.length() >= 5 && command.substring(0, 5).equals("/JOIN") && !u.isEmpty()) {
                System.out.println("Join");
                if (command.length() == 5) { //default username
                    if (client.joinChannel(u.getName(), "channelname")) {
                        u.addChannel("channelname");
                        System.out.println("Successfully joined channelname");
                    }
                } else if (command.charAt(5) == ' ' && command.length() >= 7) {
                    if (client.joinChannel(u.getName(), command.substring(6, command.length()))) {
                        u.addChannel(command.substring(6, command.length()));
                        System.out.println("Successfully joined " + command.substring(6, command.length()));
                    } else {
                        System.out.println("Join channel failed");
                    }

                }
            }
            else if (command.length() >= 6 && command.substring(0, 6).equals("/LEAVE") && !u.isEmpty()) {
                System.out.println("Leave");
                if (command.charAt(6) == ' ' && command.length() >= 8) {
                    if (client.leaveChannel(u.getName(), command.substring(7, command.length()))) {
                        u.removeChannel(command.substring(7, command.length()));
                        System.out.println("Successfully left " + command.substring(7, command.length()));
                    } else {
                        System.out.println("Leave channel failed");
                    }

                }
            }
            command = sc.nextLine();
        }
    }
}
