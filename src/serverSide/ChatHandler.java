/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverSide;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class ChatHandler extends Thread{
        DataInputStream dis;
        PrintStream ps;
        static Vector<ChatHandler> clientsVector = new Vector<ChatHandler>();

//        public ChatHandler(Socket s) {
//           
//        }

    ChatHandler(Socket s) {
         try {
                dis = new DataInputStream(s.getInputStream());
                ps = new PrintStream(s.getOutputStream());
                clientsVector.add(this);
                start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

        public void run() {
            while (true) {
                try {
                    String msg = dis.readLine();
                    sendMessageToAll(msg);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        void sendMessageToAll(String msg) {
            for (ChatHandler ch : clientsVector) {
                ch.ps.println(msg + "" + getId());
            }
        }
}
