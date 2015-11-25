/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.Action;
import transaccion.Transaction;

/**
 *
 * @author esparratacus
 */
public class Ahorro extends Cuenta {

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Ahorro() {
        super();
        balance = new Long(0);
    }

    @Override
    public Boolean commit(Long tId) {
        FileOutputStream fout = null;
        System.out.println("se va a commitear a " + getResourceId());
        try {
            fout = new FileOutputStream(getResourceId());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            fout.close();
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public String getResourceId() {
        return Banco.BANCO_AHORRO + "_" + client.getId();
    }

    @Override
    public Boolean rollback(Long tId) {
        FileInputStream fos = null;
        try {
            fos = new FileInputStream(getResourceId());
            ObjectInputStream ois = new ObjectInputStream(fos);
            Ahorro nuevo = (Ahorro) ois.readObject();
            this.setBalance(nuevo.getBalance());
            this.setClient(nuevo.getClient());
            ois.close();
            fos.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ahorro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ahorro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ahorro.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Ahorro.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                return false;
            }

        }
    }

    @Override
    public Long abonar(Long cantidad, Transaction tId) {
        boolean ok = false;
        Ahorro clon = (Ahorro) clone();
        System.out.println("Se crea un clon con balance " + clon.getBalance());
        clon.returnToState(tId);
        try {
            ok = true;
            System.out.println("despues de la operacion el clon tiene " + clon.getBalance());
            return clon.getBalance();

        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
        } finally {
            return ok ? clon.getBalance() : null;
        }
    }

    @Override
    public Long retirar(Long cantidad, Transaction tId) {
        Ahorro clon = (Ahorro) clone();
        System.out.println("Se crea un clon con balance " + clon.getBalance());
        clon.returnToState(tId);
        boolean ok = false;
        try {
            ok = true;
            return clon.getBalance();
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
        } finally {
            return ok ? clon.getBalance() : null;
        }
    }

    @Override
    public void returnToState(Transaction t) {
        System.out.println("Recuperando transaccion");
        for (Action action : t.getAcciones()) {
            if (action.getRecursoAfectado().equals(getResourceId())) {
                if (action.isOperacion() == Action.SUMA) {
                    System.out.println("sumo " + action.getCantidad());
                    balance += action.getCantidad();
                } else {
                    System.out.println("resto " + action.getCantidad());
                    balance -= action.getCantidad();
                }
            }
        }
    }

}
