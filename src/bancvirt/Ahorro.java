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

/**
 *
 * @author esparratacus
 */
public class Ahorro extends Cuenta {
    
    
    public final static String ACCOUNT_RESOURCE_NAME="ahorro_";
    private Long balance;
    private Client client;

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public Ahorro(){
        super();
        balance = new Long(0);
    }
    @Override
    public Boolean commit() {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(getResourceId());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            fout.close();
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                return false;
            }
        }
    }
    @Override
    public String getResourceId() {
        return ACCOUNT_RESOURCE_NAME+client.getId();
    }

    @Override
    public Boolean rollback() {
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
            }finally{
                return false;
            }
         
        }
    }

    @Override
    public Long abonar(Long cantidad) {
        lock.writeLock().lock();
        boolean ok = false;
        try {
            balance += cantidad;
            ok = true;
        }catch(Exception e){
            ok = false;
        }finally {
            lock.writeLock().unlock();
            return ok? balance : null;
        }
    }

    @Override
    public Long retirar(Long cantidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
