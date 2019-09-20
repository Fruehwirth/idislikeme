package server;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Properties;

public class Server{
    static int port = 6666;
    static Properties prop;
    static Reader reader;
    static Connection connection;
    static Statement stmt;
    static ResultSet rs;

    /*
      Properties prop = new Properties();
      Reader reader = new FileReader("database_config.properties");
      prop.load(reader);
      Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
    */

    /*Statement stmt = connection.createStatement();
      PreparedStatement pushChangeLog = connection.prepareStatement("INSERT INTO CHANGELOGTEST (TIME_OF_CHANGE, CHANGE) VALUES (? ,?)");
      connection.setAutoCommit(false);*/

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server started...");

            //JDBC Part
            prop = new Properties();
            reader = new FileReader("database_config.properties");
            prop.load(reader);
            connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));

            stmt = connection.createStatement();


            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("User connected");
                rs = stmt.executeQuery("SELECT * FROM events");
                System.out.println("Server: socket closed?:" + socket.isClosed());
                ClientHandler ch = new ClientHandler(socket, rs);
                new Thread(ch).start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
