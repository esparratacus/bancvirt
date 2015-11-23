/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import bloqueo.Bloqueo;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author esparratacus
 */
public abstract class Recurso implements IRollbackable,IService,ICommitable, Serializable, IConsumable {
    protected String resourceId;
    protected Bloqueo bloqueo;
    protected Client client;
    protected Long balance;

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
    
    
    public Boolean commit(Long tId) {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(getResourceId());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            fout.close();
            bloqueo.libera(tId);
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
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    
    public final Bloqueo getBloqueo() {
        return bloqueo;
    }

    public final void setBloqueo(Bloqueo bloqueo) {
        this.bloqueo = bloqueo;
    }
     
    
    public Recurso(){
        bloqueo = new Bloqueo(this);
        balance = new Long(0);
    }
    
    @Override
    public final Boolean canConsume(Long tId){
       return bloqueo.getIdTransaccionActual().equals(tId);
    }
    
    public abstract String getResourceId();
    
    
}
