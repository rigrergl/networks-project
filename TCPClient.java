import java.io.*;
import java.net.*;
import java.util.function.IntPredicate;

class TCPClient {
  private static final String TERMINATE_MESSAGE = "end";

  public static void main(String[] args) throws Exception {
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    Socket clientSocket = new Socket("127.0.0.1", 6789);
    
    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    String expression = inFromUser.readLine();
    String result;
    while (!expression.equals(TERMINATE_MESSAGE)) {
      outToServer.writeBytes(expression + '\n');
      result = inFromServer.readLine();
      System.out.println("FROM SERVER: " + result);

      expression = inFromUser.readLine();
    }

    outToServer.writeBytes(TERMINATE_MESSAGE);
    clientSocket.close();
  }

}
