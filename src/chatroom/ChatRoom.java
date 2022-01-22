/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatroom;

import com.sun.istack.internal.logging.Logger;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class ChatRoom extends Application {

    TextArea txt = new TextArea();
    Button sendBtn = new Button("Send");
    Button saveBtn = new Button("Save");
    Button openBtn = new Button("Open");
    Label lbl = new Label("Enter Your Message");
    TextField msg = new TextField();
    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;
    FlowPane flowPane = new FlowPane();
    BorderPane pane = new BorderPane();

    @Override
    public void init() {
        sendBtn.setDefaultButton(true);
        sendBtn.setMinWidth(50);
        sendBtn.setMaxWidth(200);
        sendBtn.setPrefWidth(120);
        saveBtn.setMinWidth(50);
        saveBtn.setMaxWidth(200);
        saveBtn.setPrefWidth(120);
        openBtn.setMinWidth(50);
        openBtn.setMaxWidth(200);
        openBtn.setPrefWidth(120);
        lbl.setPrefWidth(210);
        msg.setPromptText("Enter Your Message");
        txt.setEditable(false);
        try {
            mySocket = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String ReplyMsg;
                    try {
                        ReplyMsg = dis.readLine();
                        txt.appendText("\n" + ReplyMsg);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }).start();
        
        //////////////////////send
        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ps.println(msg.getText());
                msg.setText("");
            }
        });
        //////////////////////open
        openBtn.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
          try{
//          File opened_file = new File("C:\\Users\\Admin\\Desktop\\hello.txt");
          FileChooser fc = new FileChooser();
          FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("txt files", "*.txt");
          File opened_file = fc.showOpenDialog(null);
          Scanner scan = new Scanner(opened_file);
          while (scan.hasNext()) {
               txt.appendText(scan.nextLine() + '\n');
            }
            scan.close();
          }catch(Exception e){
           System.out.println(e.getMessage());}
        }});
        //////////////////////save
        saveBtn.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
          FileChooser fc = new FileChooser();
          FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("txt files", "*.txt");
          File saved_file = fc.showSaveDialog(null);
          try{
          FileWriter fw = new FileWriter(saved_file);
          fw.write(txt.getText());
          fw.close();
          } catch(Exception e){
              System.out.println(e.getMessage());}
        }});
        
        ObservableList list = flowPane.getChildren();
        list.addAll(lbl, msg, sendBtn, saveBtn,openBtn);
        pane.setCenter(txt);
        pane.setBottom(flowPane);
    }

    @Override

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(pane, 360, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ChatRoom");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
