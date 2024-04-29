import java.io.*;
import java.util.Scanner;

import client.ChatClient;
import common.ChatIF;
import ocsf.server.ConnectionToClient;

public class ServerConsole implements ChatIF{
	Scanner fromConsole;
	EchoServer server;
    static int port;
    String loginID="";

   

   
    final public static int DEFAULT_PORT = 5555;
	
	public ServerConsole(int port) {
		   server= new EchoServer(port);
		  

		  // Create scanner object to read from console
		  fromConsole = new Scanner(System.in); 
		}

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
	        		server.close();
	        		server.serverClosed();
	        		System.exit(0);
	        		executed=true;
	        		break;
	        		
	        	case "#stop":
	        		server.stopListening();
	        		server.serverStopped();
	        		executed=true;
	        		break;
	        	
	        	case "#close":
	        		//server.stopListening();
	        		//server.serverStopped();
	        		server.close();        		        		
	       	     	server.serverClosed(); 
	        		
	        	
	        		
	        		
	        		
	       		executed=true;
	        		break;
	       
	        	case "#start":	
		        		executed=true;
		        		try {
	        		server.listen();
		        		}
		        		catch(Exception e) {
		        			
		        		}
	        		server.serverStarted();
	        		
	        		
	        		break;
	        	
	        	case "#getport":
	        		executed=true;
	        		System.out.println(server.getPort());
	        		break;
	        	      
	        }
	        	
  //set port
if(!executed){
        		if(!server.isListening()) {

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
                      server.setPort(myPort);
                                     
                      
                    } 
                       
                   
        		}
        		

	        }  
	        }
	        
	        if(!executed) {
	        	
	        server.handleMessageFromServerUI(message);
	       display(message);  
	      
	        
	
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

	public static void main(String[] args) throws IOException {
		
		 
  try {
      port=Integer.valueOf(args[0]);
  		
  }
  catch(Throwable e) {

  	 port = DEFAULT_PORT;

  }
  	   
  	 ServerConsole srv= new ServerConsole(port);
     srv.accept();

  	  
    }
           
     @Override
 	public void display(String message) {
 	    System.out.println("SERVER MSG> " + message);
}
}
