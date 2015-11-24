/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;

import transaccion.Transaction;
import bancvirt.IBanco;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.Action;

/**
 *
 * @author david
 */
public class Coordinator extends UnicastRemoteObject implements ICoordinator {

    public final static String COORDINATOR_NAME = "Coordinator";
    public final static String COORDINATOR_IP = "127.0.0.1";
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
        System.out.println("Se manda a cerrar una transaccion");
        Transaction closed = transactions.get(tId);
        Set<String> resources = closed.getRecursosAfectados();
        Boolean listo = true;
        try {
            for (String resource : resources) {
                System.out.println("Recurso Afectado: " + resource);
                String[] recurso = resource.split("_");
                IBanco banco = (IBanco) registry.lookup(recurso[0]);
                listo = banco.canCommit(recurso[1], recurso[0], closed);
                if (!listo) {
                    abortTransaction(tId);
                    System.out.println("No puede hacer commit");
                    return false;
                }

            }

            // toca validar que una de las transacciones no haya empezado antess
            if (!hayConflicto(closed)) {
                for (String resource : resources) {
                    String[] recurso = resource.split("_");
                    IBanco banco = (IBanco) registry.lookup(recurso[0]);
                    banco.commit(recurso[1], recurso[0], closed);
                }
                transactions.remove(tId);
                return true;
            }else{
                abortTransaction(tId);
            }
            transactions.remove(tId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        transactions.remove(tId);
        return true;
    }

    public Boolean hayConflicto(Transaction transaccion) {
        Iterator it = transactions.entrySet().iterator();
        Boolean conflicto = false;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Transaction actual = (Transaction) pair.getValue();
            for (String recurso : transaccion.getRecursosAfectados()) {
                if (actual.getRecursosAfectados().contains(recurso) && !transaccion.tienePrioridad(actual)) {
                    System.out.println("Conflicto de " + transaccion.gettId() + " con " + actual.gettId());
                    return true;
                }
            }
        }
        return conflicto;
    }

    @Override
    public synchronized Boolean abortTransaction(Long tId) {
        System.out.println("Se pasa a abortar la transaccion");
        Transaction closed = transactions.get(tId);
        Set<String> resources = closed.getRecursosAfectados();

        for (String resource : resources) {
            try {
                String[] recurso = resource.split("_");
                IBanco banco = (IBanco) registry.lookup(recurso[0]);
                banco.rollback(recurso[1], recurso[0], closed);
                transactions.remove(tId);
            } catch (RemoteException ex) {
                Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;

    }

    @Override
    public Boolean addResource(Long tId, String resourceId, Boolean operacion, Long monto) throws RemoteException {
        Transaction transaccion = transactions.get(tId);
        Action accion = new Action(resourceId, operacion, monto);
        transaccion.getAcciones().add(accion);
        transaccion.getRecursosAfectados().add(resourceId);
        return true;
    }

    @Override
    public Long depositar(String idUsuario, String tipo, Long monto, Long tId) throws RemoteException {
        /* Buscar al servicio que haga eso y pedirles que se actualicen  */
        Transaction transaccion = transactions.get(tId);
        String resourceId = tipo + "_" + idUsuario;
        addResource(tId, resourceId, Action.SUMA, monto);
        String[] servicios = registry.list();
        for (String servicio : servicios) {
            String patron = "([0-9]*)" + tipo;
            if (servicio.matches(patron)) {
                try {
                    System.out.println("Servicio " + servicio + " cumple con el patron");
                    IBanco banco = (IBanco) registry.lookup(servicio);
                    return banco.depositar(idUsuario, tipo, monto, transaccion);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("servicio " + servicio + " no esta disponible");
                }

            }
        }
        return null;
    }

    @Override
    public Long retirar(String idUsuario, String tipo, Long monto, Long tId) throws RemoteException {
        Transaction transaccion = transactions.get(tId);
        String resourceId = tipo + "_" + idUsuario;
        addResource(tId, resourceId, Action.RESTA, monto);
        String[] servicios = registry.list();
        for (String servicio : servicios) {
            String patron = "([0-9]*)" + tipo;
            if (servicio.matches(patron)) {
                try {
                    IBanco banco = (IBanco) registry.lookup(servicio);
                    return banco.retirar(idUsuario, tipo, monto, transaccion);
                } catch (Exception ex) {
                    System.out.println("servicio " + servicio + " no esta disponible");
                }

            }
        }
        return null;
    }

}
