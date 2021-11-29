package it.itismeucci;

public class MainC 
{
    public static void main( String[] args )
    {
        Client utente = new Client();
        utente.connetti();
        while(!utente.socket.isClosed()){
            utente.comunica();
        }
        
    }
}
