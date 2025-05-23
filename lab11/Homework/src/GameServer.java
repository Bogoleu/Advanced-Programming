import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class GameServer {
    private static final int PORT = 12345;
    private static ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Game server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new GameHandler(clientSocket, games)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
