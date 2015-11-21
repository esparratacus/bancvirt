/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import coordinator.Coordinator;
import coordinator.ICoordinator;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Registry myRegistry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            ICoordinator coordinador = (ICoordinator) myRegistry.lookup(Coordinator.COORDINATOR_NAME);      
            Long tId = coordinador.openTransaction();
            System.out.println("TID: "+ tId);
        } catch (RemoteException ex) {
            System.out.println("Error al conectarse al objeto remoto");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
