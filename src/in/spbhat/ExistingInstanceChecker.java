package in.spbhat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ExistingInstanceChecker {
    // Start a server at port and accept connections
    // If the port is in use
    //     - start a client and connect to server
    //     - close the program
    // If client connects to server close the program
    private final static int PORT = 3354;

    public static void checkAndExit() {
        new Thread(() -> {
            try {
                System.out.println("Attempting to open port: " + PORT);
                ServerSocket socket = new ServerSocket(PORT);
                System.out.println("Opened port: " + PORT);
                socket.accept(); // First instance
                System.out.println("Another instance opened.");
                socket.close();
                System.exit(0);
            } catch (IOException e) { // PORT already open, second instance
                System.out.println("Another instance already running.");
                try {
                    Socket socket = new Socket(InetAddress.getLocalHost(), PORT);
                    socket.close();
                    System.exit(0);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
    }
}
