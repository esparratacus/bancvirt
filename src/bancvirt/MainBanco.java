/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import coordinator.Coordinator;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                System.out.println("Tipo de banco");
                //idBanco = sc.nextLine();
                banco = new Banco(Banco.BANCO_AHORRO);
                banco.setTipo(Banco.BANCO_AHORRO);
            }
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            registry.rebind(Banco.BANCO_AHORRO, banco);
            System.out.println("Banco " + Banco.BANCO_AHORRO + " corriendo");
        } catch (RemoteException ex) {
            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
