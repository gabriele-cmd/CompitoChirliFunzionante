package it.itismeucci;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    ArrayList<String> note = new ArrayList<>();
    ServerSocket server;
    Socket client;
    String stringaInDalClient;

    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public Socket attendi(){//Connessione con UN SOLO Client
        System.out.println("SERVER: in esecuzione...");
        try {
            server = new ServerSocket(6789); //BIND della porta 6789
            client = server.accept(); //rimane IN ATTESA di un CLIENT
            server.close(); //CHIUDO il server per INIBIRE altri client

            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream())); //Inizializzo il flusso proveniente DAL Client
            outVersoClient = new DataOutputStream(client.getOutputStream()); //Inizializzo il flusso VERSO il Client
            outVersoClient.writeBytes("Connessione Effettuata, Benvenuto!\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }

    return client;
    }

    public void comunica(){
        try {
            outVersoClient.writeBytes("Inserisci la nota da memorizzare o digita LISTA per visualizzare le note salvate. Digita ESCI per uscire e RIMUOVI + nota per rimuovere una nota salvata.\n");
            stringaInDalClient = inDalClient.readLine(); //LEGGO la stringa inviata dal Client

            ArrayList<String> copiaNote = new ArrayList<>();
            for(String x: note){
                copiaNote.add(x);
            }

            String[] controllo = stringaInDalClient.split(" ", 2);
            switch(controllo[0]){
                case "LISTA":
                    outVersoClient.writeBytes("Note salvate: ");
                    for(String x: copiaNote){
                        outVersoClient.writeBytes(x + ", ");
                    }
                    outVersoClient.writeBytes(" " + '\n');
                break;
                case "ESCI":
                    outVersoClient.writeBytes("Disconesso!");
                    outVersoClient.close();
                    inDalClient.close();
                    client.close();
                    server.close(); //Chiusura Server perché non serve la connessione di nessun altro Client
                break;
                case "RIMUOVI":
                    for(String x: copiaNote){
                        if(x.equals(controllo[1])){
                            note.remove(x);
                        }
                    }
                break;
                default:
                    note.add(stringaInDalClient);
                    outVersoClient.writeBytes("Nota Salvata!\n");
                break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione");
            System.exit(1);
        }
    }
}