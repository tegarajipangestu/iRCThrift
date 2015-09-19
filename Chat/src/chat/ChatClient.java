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
        while(!command.equals("/EXIT")) {
            if(command.length()>=5 && command.substring(0,5).equals("/NICK")) {
                if(command.length()==5) { //default username
                    u.setName(client.createNickname(""));
                }
                else if(command.charAt(5) == ' ' && command.length()>=7) {
                    String name = client.createNickname(command.substring(6,command.length()));
                    if(!name.equals("")) {
                        u.setName(name);
                    }
                    else {
                        System.out.println("Name was taken. Choose another name");
                    }
                    
                }
            }
            command = sc.nextLine();
        }
    }
}
