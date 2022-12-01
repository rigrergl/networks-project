
import java.io.*;
import java.net.*;
import java.util.function.IntPredicate;

class TCPClient {
  private static final String TERMINATE_MESSAGE = "end";


  public static void main(String[] args) throws Exception {

    if(args.length != 1){
        System.out.println("Error. Please include client name when creating request");
    }



    Socket clientSocket = new Socket("127.0.0.1", 6789);
    
    
    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));



    String clientName = args[0];
    outToServer.writeBytes(clientName + '\n');


    System.out.print("\nENTER THE MATH EXPRESSION >>> ");

    String expression = inFromUser.readLine();
    String result;

    while (!expression.equals(TERMINATE_MESSAGE)) {
      outToServer.writeBytes(expression + '\n');
      result = inFromServer.readLine();
      System.out.println("CALCULATED RESULT FROM SERVER: " + result + "\n");

      System.out.print("ENTER ANOTHER MATH EXPRESSION or end to close connection >>> ");
      expression = inFromUser.readLine();
    }

    outToServer.writeBytes(TERMINATE_MESSAGE);
    System.out.println("Connection Close Thank you for using our calculator");
    clientSocket.close();
  }

}

