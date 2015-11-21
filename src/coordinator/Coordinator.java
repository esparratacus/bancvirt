/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 *
 * @author david
 */
public class Coordinator extends UnicastRemoteObject  implements ICoordinator {
    public final static String COORDINATOR_NAME="Coordinator";
    
    private Long totalTransactions; // temporal, solo para efectos de prueba
    private HashMap<Long,Transaction> transactions;
    
    
    public Coordinator()throws RemoteException{
        totalTransactions = new Long(0);      
    }
    @Override
    public synchronized Long openTransaction() {
        totalTransactions++;
        Transaction transaction = new Transaction(totalTransactions);
        transactions.put(totalTransactions, transaction);
        return totalTransactions;
    }

    @Override
    public synchronized Boolean closeTransaction(Long tId) {
        Transaction closed = transactions.get(tId);
        return false;
    }

    @Override
    public synchronized Boolean abortTransaction(Long tid) {
        Transaction closed = transactions.get(tid);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
