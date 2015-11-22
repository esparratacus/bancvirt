/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import bloqueo.Bloqueo;
import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author esparratacus
 */
public abstract class Recurso implements IRollbackable,IService,ICommitable, Serializable {
    protected String resourceId;
    protected ReadWriteLock lock;
    protected Bloqueo bloqueo;

    public final Bloqueo getBloqueo() {
        return bloqueo;
    }

    public final void setBloqueo(Bloqueo bloqueo) {
        this.bloqueo = bloqueo;
    }
    
    public Recurso(){
        lock = new ReentrantReadWriteLock();
        bloqueo = new Bloqueo(this);
    }
    
    
    public abstract String getResourceId();
    
    
}
