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
    public void run() {
        try {
          BufferedReader inFromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
          DataOutputStream outToClient = new DataOutputStream(this.clientSocket.getOutputStream());
  
          String clientRequest;
          String expressionResult;
          while (true) {
            clientRequest = inFromClient.readLine();
    
            if (clientRequest.equals(TERMINATE_MESSAGE)) {
              this.clientSocket.close();
              break;
            }
    
            expressionResult = calculator(clientRequest) + '\n'; 
            outToClient.writeBytes(expressionResult);
          }
  
          clientSocket.close();
        } catch (Exception e) {
          System.out.println("Exception occurred in server: " + e.getMessage());
          e.printStackTrace();
        }
  
        System.out.println("Connection closed");
      }
    }
    public static String calculator(String expression) {
      int answer;
      String[] items = expression.split(" ");

      switch (items[1].charAt(0))
      {
          case '+':
              answer = Integer.parseInt(items[0]) + Integer.parseInt(items[2]);
              return Integer.toString(answer);
          case '-':
              answer = Integer.parseInt(items[0]) - Integer.parseInt(items[2]);
              return Integer.toString(answer);
          case '*':
              answer = Integer.parseInt(items[0]) * Integer.parseInt(items[2]);
              return Integer.toString(answer);
          case '/':
              answer = Integer.parseInt(items[0]) / Integer.parseInt(items[2]);
              return Integer.toString(answer);
          default:
              return "INVALID COMMAND";
      }
    }
}

           