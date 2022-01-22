/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverSide;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class server {

    ServerSocket myServerSocket;
    Socket s;

    public server() {
        try {
            myServerSocket = new ServerSocket(5005);
            while (true) {
                s = myServerSocket.accept();
                new ChatHandler(s);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new server();
    }

    
}
