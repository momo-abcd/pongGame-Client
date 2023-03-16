package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.stage.Stage;

public class QueueThread extends Thread {
    // contructor
    Socket socket;
    BufferedReader br;
    PrintWriter pw;
    Scanner sc = new Scanner(System.in);
    private Stage primaryStage;

    // constructor
    public QueueThread(Stage stage){
        primaryStage = stage;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 3333));
            System.out.println(socket.getInetAddress());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
            
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String data ="";
        try {
            while((data = br.readLine()) != null){
                System.out.println(data);
                // System.out.println(data.substring(0,8));
                if(data.equals("ready?")){
                    pw.println("ready");
                    pw.flush();
                }
                if(data.split(":")[0].equals("gameStart")){
                    String playerCode =  data.split(":")[1];
                    Platform.runLater(()->{
                    primaryStage.setTitle("온라인 게임신");
                    primaryStage.setScene(new MultiGameScene(primaryStage, socket, playerCode));
                    }); 
                    break;
                }
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void cancle() {
        try {
            pw.println("close");
            pw.flush();
            pw.close();
            br.close();
            socket.close();
            System.out.println(socket);
            System.out.println(socket.isClosed());
            Thread.sleep(100);
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}