package it.itismeucci;

public class MainS {
    public static void main( String[] args )
    {
       Server server = new Server();
       server.attendi();
       while(!server.client.isClosed()){
           server.comunica();
       }
       
    }

}
