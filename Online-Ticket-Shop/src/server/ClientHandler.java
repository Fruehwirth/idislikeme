package server;

import client.model.Event;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    ResultSet rs;
    ArrayList<Event> eventArrayList = new ArrayList<>();

    public ClientHandler(Socket clientSocket, ResultSet rs) {
        this.clientSocket = clientSocket;
        this.rs = rs;
    }

    @Override
    public void run() {
        try(
                //ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream()))
        {
            while(rs.next()){
                Event e = new Event(rs.getString(2), rs.getString(4), rs.getString(6));
                eventArrayList.add(e);
            }

            oos.writeObject(eventArrayList);

            while (true) {
                System.out.println("no operatfdion" + clientSocket.getPort());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("user disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

