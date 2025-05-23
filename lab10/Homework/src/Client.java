import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(in.readLine()); // Welcome message

        String userInput;
        while ((userInput = console.readLine()) != null) {
            out.println(userInput);
            System.out.println(in.readLine());
        }
    }
}
