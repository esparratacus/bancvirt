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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Coordinator extends UnicastRemoteObject implements ICoordinator {

    public final static String COORDINATOR_NAME = "Coordinator";
    private Registry registry;
    private Long totalTransactions;
    private HashMap<Long, Transaction> transactions;

    public Coordinator() throws RemoteException {

    }

    public Coordinator(Registry registry) throws RemoteException {
        this.registry = registry;
        totalTransactions = new Long(0);
        transactions = new HashMap<>();
    }

    public Transaction createTransaction() {
        totalTransactions++;
        Transaction transaction = new Transaction(totalTransactions);
        transactions.put(totalTransactions, transaction);
        return transaction;
    }

    @Override
    public Long openTransaction(String username, char[] password) throws RemoteException {
        Boolean valid = false;
        String[] banks = registry.list();
        for (String bank : banks) {
            System.out.println(bank);
            if (!bank.equals(COORDINATOR_NAME)) {
                try {
                    IBanco banco = (IBanco) registry.lookup(bank);
                    valid = banco.iniciarSesion(username, password);
                    if (!valid) {
                        return null;
                    }
                } catch (NotBoundException ex) {
                    Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AccessException ex) {
                    Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Transaction transaction = createTransaction();
        transaction.setUsername(username);
        return transaction.gettId();

    }

    @Override
    public synchronized Boolean closeTransaction(Long tId) throws RemoteException {
        Transaction closed = transactions.get(tId);
        Set<String> resources = closed.getRecursosAfectados();
        Boolean listo = true;
        try {
            for (String resource : resources) {
                String[] recurso = resource.split("_");
                IBanco banco = (IBanco) registry.lookup(recurso[0]);
                listo = banco.canCommit(recurso[1],recurso[0], tId);
                if (!listo) {
                    abortTransaction(tId);
                    System.out.println("No puede hacer commit");
                    return false;
                }

            }
            for (String resource : resources) {
                String[] recurso = resource.split("_");
                IBanco banco = (IBanco) registry.lookup(recurso[0]);
                listo = banco.commit(recurso[1],recurso[0], tId);
            }
        } catch (Exception ex) {
            return false;
        }

        transactions.remove(tId);
        return true;
    }

    @Override
    public synchronized Boolean abortTransaction(Long tId) {

        Transaction closed = transactions.get(tId);
        Set<String> resources = closed.getRecursosAfectados();

            for (String resource : resources) {
            try {
                String[] recurso = resource.split("_");
                IBanco banco = (IBanco) registry.lookup(recurso[0]);
                banco.rollback(recurso[1],recurso[0], tId);
            } catch (RemoteException ex) {
                Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
            }

            }
            return true;
       
    }

    @Override
    public Boolean addResource(Long tId, String resourceId) throws RemoteException {
        Transaction transaccion = transactions.get(tId);
        transaccion.getRecursosAfectados().add(resourceId);
        return true;
    }

}
