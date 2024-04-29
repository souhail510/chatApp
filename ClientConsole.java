// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.Scanner;

import client.*;
import common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version September 2020
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  EchoServer server;
  	
  
  
  
  /**
   * Scanner to read from the console
   */
  Scanner fromConsole; 
 
  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String loginID,String host, int port) 
  {
	
	  
    try 
    {

      client= new ChatClient(loginID,host, port, this);
      
      
    } 
    catch(IOException exception) 
    {
    	
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
    
    // Create scanner object to read from console
    fromConsole = new Scanner(System.in); 
	  }


  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
	 boolean executed=false;
    try
    {

    String message;

      while (true) 
      {   	  
        message = fromConsole.nextLine();
        if(message.charAt(0)=='#') {
        	switch(message) {
        	
        	case "#quit":
        		client.closeConnection();
        		client.quit();
        		executed=true;
        		break;
        		
   		      		
        	case "#logoff":
        
        		client.closeConnection();
        		
        	
        		executed=true;
        		break;
        		
        		
        	case "#login":
        		if(client.isConnected()) {
            		System.out.println("le client est deja connecte");
            		executed=true;
            		break;
            		
            		
            	}
        		else { try {
		            	 client.openConnection();		            	
		            	 executed=true;
		            	 break;
		            	 }
		            	 catch(Exception e1 ) {
		            		 
		            	 }
		            	 
		             
        		}
        	
        	case "#gethost":
        		System.out.println(client.getHost());
        		executed=true;
        		break;
        		
        		
        		   		
        	case "#getport" :
        	System.out.println(client.getPort());
        	executed=true;
        	break;
        	
        	
        	
        	}    	
        	
        	
       //set port
        	if(!executed) {
        	   String msg="";
               String myPort0="";
               for(int i=0;i<9;i++) {
               	msg=msg+message.charAt(i);
               }
 
                  if(msg.equals("#setport<")) {
                	  executed=true;
                           	
              	for(int j=9;j<message.length();j++) {
           		               		
               		if(message.charAt(j)=='>') {
               			break;
               		}
               		myPort0=myPort0+message.charAt(j);
               	
               	}
              	 int myPort=Integer.parseInt(myPort0);
                 client.setPort(myPort);
                 System.out.println("port set to: "+myPort);

               }                
        	}
              
        
   
     //set host
        	  if(!executed) {    
        String msg2="";
        String myHost="";
        for(int i=0;i<9;i++) {
        	msg2=msg2+message.charAt(i);
        
        }

           if(msg2.equals("#sethost<")) {
        	   executed=true;
                    	
       	for(int p=9;p<message.length();p++) {
    		               		
        		if(message.charAt(p)=='>') {
        			break;
        		}
        		myHost=myHost+message.charAt(p);
        
        	}
       	
       	if(!client.isConnected()) {
        client.setHost(myHost);
        System.out.println("host set to: "+myHost);
       
       	}
       	else {
       		System.out.println("error:le client est connecte");
       		
       		
       	}
        }     
     
        	  }
      
 }
         
 if(!executed) {
        client.handleMessageFromClientUI(message);
 }
 executed=false;

      }
    } 
    catch (Exception ex) 
    {
    	System.out.println(ex);
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }


  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
	  String loginID =null;
	  String host = "";
	    int port = 0;
	    try {
	    loginID=args[0];
	    }
	    catch(Exception e) {
	    	throw new RuntimeException("ERROR - No login ID specified.  Connection aborted.");
	    	
	    }
	    
	    try
	    {
	      host = args[1];

	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      host = "localhost";
	           
	    }
	    
try {
    port=Integer.valueOf(args[2]);
		
}
catch(Throwable e) {

	 port = DEFAULT_PORT;

}
	    ClientConsole chat= new ClientConsole(loginID,host, port);
	   
	   chat.accept();  //Wait for console data
	 
  }



}
//End of ConsoleChat class
