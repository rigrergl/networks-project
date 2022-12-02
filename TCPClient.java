
import java.io.*;
import java.net.*;
import java.util.function.IntPredicate;

class TCPClient {
  private static final String TERMINATE_MESSAGE = "end"; //specifying message to end the connection


  public static void main(String[] args) throws Exception {

    if(args.length != 1){ // if the client name isn't included when creating request, send error
        System.out.println("Error. Please include client name when creating request");
    }

    Socket clientSocket = new Socket("127.0.0.1", 6789); //creating client scoket
    
    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); //creating way to send to server
    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //creating way to receive from server
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); //creating way to receive from user

    String clientName = args[0]; //storing client name from command line
    System.out.println("Shell for: " + clientName);
    outToServer.writeBytes(clientName + '\n'); //sending name to server

    //getting expression from user
    System.out.print("\nENTER THE MATH EXPRESSION >>> "); 
    String expression = inFromUser.readLine(); 
    String result;

    /*until the user decides to terminate the connection: repeat this: 
        sending to server the expression taken from user
        getting the result back form the server
        printing result
        requesting user to give another expression
    */
    while (!expression.equals(TERMINATE_MESSAGE)) {
      outToServer.writeBytes(expression + '\n');
      result = inFromServer.readLine();
      System.out.println("CALCULATED RESULT FROM SERVER: " + result + "\n");

      System.out.print("ENTER ANOTHER MATH EXPRESSION or end to close connection >>> ");
      expression = inFromUser.readLine();
    }

    //once the user gives the termination message, send that to server
    outToServer.writeBytes(TERMINATE_MESSAGE); 

    //close the socket
    System.out.println("Connection Close Thank you for using our calculator");
    clientSocket.close();
  }

}

