package module11.server;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
public class Server extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
// Text area for displaying contents
        TextArea ta = new TextArea();
// Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        new Thread( () -> {
            try {
// Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() ->
                        ta.appendText("Server started at " + new Date() + '\n'));
// Listen for a connection request
                Socket socket = serverSocket.accept();
// Create data input and output streams
                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());
                boolean isPrime = true;

                while (true) {
// Receive number from the client
                    int number = inputFromClient.readInt();
// See if number received from client is prime
                    if(number <= 1) {
                        isPrime = false;
                    }

                    for (int i = 2; i < number / 2; i++) {
                        if (number % i == 0) {
                            isPrime = false;
                            break;
                        }
                    }
// Send area back to the client
                    Platform.runLater(() -> {
                        ta.appendText("Number received from client: "
                                + number + '\n');
                        ta.appendText("Calculating if " + number + " is prime" + '\n');
                    });
                    outputToClient.writeBoolean(isPrime);
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}