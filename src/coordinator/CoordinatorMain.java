/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;

import bancvirt.Banco;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import prueba.recordFromHost;
import prueba.recordFromHostImpl;

/**
 *
 * @author david
 */
public class CoordinatorMain {
    
    
    
    
    private void start(){
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(Coordinator.COORDINATOR_NAME, new Coordinator(registry));
            System.out.println("Service running");
            String name = "recordFromHost";
            recordFromHost myObj = new recordFromHostImpl ();
            registry.bind(name, myObj);
        } catch (RemoteException ex) {
            Logger.getLogger(CoordinatorMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(CoordinatorMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public CoordinatorMain(){
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CoordinatorMain main = new CoordinatorMain();
        main.start();
    }
    
}
