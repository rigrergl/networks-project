import java.io.*;
import java.net.*;
import java.util.Date; 
import java.text.SimpleDateFormat;

class TCPServer {

  private static final String TERMINATE_MESSAGE = "end"; //specifyig message for ending the connection
  static String timeStamp; 

  public static void main(String argv[]) throws Exception {
    ServerSocket welcomeSocket = new ServerSocket(6789); //creating welcome socket

    while (true) {
        Socket connectionSocket = welcomeSocket.accept(); //listens and accepting connection to socket 

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy.HH:mm:ss"); 
        timeStamp = df.format(new Date());  //saving time of connection request
  
        //creating one thread when a client is creating connection
        Runnable connectionHandler = new ConnectionHandler(connectionSocket);
        new Thread(connectionHandler).start();

    }
  }
  private static class ConnectionHandler implements Runnable {
    //creating client socket
    private final Socket clientSocket;
    public ConnectionHandler(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }
    
    public void run() {
        long startTime = System.nanoTime(); //logging start time
        String clientName = "";

        try {
          BufferedReader inFromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream())); //creating way to receive from client
          DataOutputStream outToClient = new DataOutputStream(this.clientSocket.getOutputStream()); //creating way to send to client
          
          clientName = inFromClient.readLine(); //getting client name from client

          System.out.println("The connection request for " + clientName + " was made at: " + timeStamp); //logging the time stamp of the connection request
          System.out.println("The connection for " + clientName + " has been acknowledged"); //sending acknowledgment message to the client

          String clientRequest;
          String expressionResult;

          while (true) {
            clientRequest = inFromClient.readLine(); //getting the client request information from client
    
            //if the message client sent is the termination message, end the connection
            if (clientRequest.equals(TERMINATE_MESSAGE)) {
              this.clientSocket.close();
              break;
            }
            //else, perform the calculation on the expression that the client requested

            System.out.println(clientName + " has requested this expression to be calculated: " + clientRequest); //log the expression which client requested
            expressionResult = calculator(clientRequest) + '\n';  //get the result of the calculation
            outToClient.writeBytes(expressionResult); //send the result to the client
          }
  
          clientSocket.close(); //closing client socket once operations are completed
        } catch (Exception e) {
          System.out.println("Exception occurred in server: " + e.getMessage());
          e.printStackTrace();
        }
  
        //once the connection has been closed, log that client has closed connection
        System.out.println(clientName + " closed connection" );
        
        //compute the elapsed time of the connection and log that information
        long endTime = System.nanoTime();
        long total = (endTime - startTime)/1000000000; 
        System.out.println("Connection for " + clientName + " ran for " + total + " seconds");
      }
    }
    public static String calculator(String expression) {

      /* switch statement for calculator operations. 
         upon completion return the result
         in case of a invalid command, return an error message
      */
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
