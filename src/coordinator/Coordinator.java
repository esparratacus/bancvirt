/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author david
 */
public class Coordinator extends UnicastRemoteObject  implements ICoordinator {
    public final static String COORDINATOR_NAME="Coordinator";
    
    private Long totalTransactions; // temporal, solo para efectos de prueba
    
    
    public Coordinator()throws RemoteException{
        totalTransactions = new Long(0);      
    }
    @Override
    public synchronized Long openTransaction() {
        totalTransactions++;
        return totalTransactions;
    }

    @Override
    public synchronized Boolean closeTransaction(Long tId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized Boolean abortTransaction(Long tid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
