import java.io.*;
import java.net.*;

class TCPServer {
  private static final String TERMINATE_MESSAGE = "end";

  public static void main(String argv[]) throws Exception {
    ServerSocket welcomeSocket = new ServerSocket(6789);

    while (true) {
      Socket connectionSocket = welcomeSocket.accept();
      Runnable connectionHandler = new ConnectionHandler(connectionSocket);
      new Thread(connectionHandler).start();
    }
  }

  private static class ConnectionHandler implements Runnable {
    private final Socket clientSocket;

    public ConnectionHandler(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      BufferedReader inFromClient;
      DataOutputStream outToClient;

      try {
        inFromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        outToClient = new DataOutputStream(this.clientSocket.getOutputStream())
      } catch (Exception e) {
        System.out.println("Exception occurred in server: " + e.getMessage());
      }

      String clientExpression;
      String expressionResult;
      while (true) {
        clientExpression = inFromClient.readLine();

        if (clientExpression.equals(TERMINATE_MESSAGE)) {
          this.clientSocket.close();
          break;
        }

        expressionResult = clientExpression.toUpperCase() + '\n'; // TODO: change to actual result
        outToClient.writeBytes(expressionResult);
      }
    }

  }
}
