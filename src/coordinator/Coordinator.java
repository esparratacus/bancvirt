/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;

import bancvirt.IBanco;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Coordinator extends UnicastRemoteObject  implements ICoordinator {
    public final static String COORDINATOR_NAME="Coordinator";
    private Registry registry;
    private Long totalTransactions;
    private HashMap<Long,Transaction> transactions;
    
    
    public Coordinator()throws RemoteException{
        
    }

    public Coordinator(Registry registry)throws RemoteException{
       this.registry = registry;
       totalTransactions = new Long(0);
        transactions = new HashMap<>();
    }
    public Transaction createTransaction(){
        totalTransactions++;
        Transaction transaction = new Transaction(totalTransactions);
        transactions.put(totalTransactions, transaction);
        return transaction;
    }
    @Override
    public  Long openTransaction(String username, char[] password)throws RemoteException{
            Boolean valid = false;
            String[] banks = registry.list();
            for (String bank : banks) {
                System.out.println(bank);
                if(!bank.equals(COORDINATOR_NAME)){
                    try {
                        IBanco banco = (IBanco) registry.lookup(bank);
                        valid = banco.iniciarSesion(username, password);
                        if(!valid){
                            return null;
                        }
                    } catch (NotBoundException ex) {
                        Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (AccessException ex) {
                        Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            return createTransaction().gettId();
      
            
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
