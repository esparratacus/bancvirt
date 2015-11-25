/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import coordinator.Coordinator;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import prueba.recordFromHost;

/**
 *
 * @author david
 */
public class MainBanco {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
          
            String idBanco;
            Banco banco;
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("Ingrese el tipo de banco Tipo de banco");
                for (int i = 0; i < Banco.TIPOS_BANCO.length ; i++) {
                    System.out.println( (i+1) + ".  " + Banco.TIPOS_BANCO[i]); 
                }
                idBanco = sc.nextLine();
                banco = new Banco(Banco.TIPOS_BANCO[ Integer.parseInt(idBanco) - 1 ]);
                banco.setTipo(Banco.TIPOS_BANCO[ Integer.parseInt(idBanco) - 1 ]);
            }
            Registry registry = LocateRegistry.getRegistry(Coordinator.COORDINATOR_IP, 1099);
           // Registry registry = LocateRegistry.createRegistry(1099);
            recordFromHost  myRecord  = (recordFromHost) registry.lookup("recordFromHost");
            myRecord.recordObject(Banco.TIPOS_BANCO[ Integer.parseInt(idBanco) - 1 ], banco );
            //registry.rebind(Banco.TIPOS_BANCO[ Integer.parseInt(idBanco) - 1], banco);
            System.out.println("Banco " + Banco.TIPOS_BANCO[ Integer.parseInt(idBanco) - 1] + " corriendo");
        } catch (RemoteException ex) {
            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
