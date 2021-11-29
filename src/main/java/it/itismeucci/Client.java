package it.itismeucci;

import java.io.*;
import java.net.*;

public class Client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket socket;

    BufferedReader tastiera;
    BufferedReader inDalServer;
    DataOutputStream outVersoServer;
    String stringaRicevuta;
    String stringaInviata;

    public Socket connetti(){
        System.out.println("CLIENT: in esecuzione...");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in)); //Prendo INPUT da tastiera
            socket = new Socket(nomeServer, portaServer);
            outVersoServer = new DataOutputStream(socket.getOutputStream());//Inizializzo il flusso di OUTPUT verso il Server
            inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
        }catch(UnknownHostException e){
            System.err.println("Host Sconosciuto");
        }
         catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return socket;
    }

    public void comunica(){
        try {
            stringaRicevuta = inDalServer.readLine(); //LEGGO la RISPOSTA del Server...
            System.out.println(stringaRicevuta);//...e la STAMPO a schermo
            stringaRicevuta = inDalServer.readLine();
            System.out.println(stringaRicevuta);

            stringaInviata = tastiera.readLine();
            outVersoServer.writeBytes(stringaInviata + '\n');//Leggo l'INPUT da tastiera e lo mando al Server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
