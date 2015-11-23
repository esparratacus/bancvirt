/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import bloqueo.Bloqueo;
import coordinator.Coordinator;
import coordinator.ICoordinator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author esparratacus
 */
public class Corriente extends Cuenta {

 

    @Override
    public String getResourceId() {
         return Banco.BANCO_CORRIENTE+ "_" + client.getId();
    }

    @Override
    public Boolean rollback(Long tId) {
        FileInputStream fos = null;
        try {
            fos = new FileInputStream(getResourceId());
            ObjectInputStream ois = new ObjectInputStream(fos);
            Corriente nuevo = (Corriente) ois.readObject();
            this.setBalance(nuevo.getBalance());
            this.setClient(nuevo.getClient());
            ois.close();
            fos.close();
            bloqueo.libera(tId);
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
    public Long abonar(Long cantidad, Long tId) {
        bloqueo.adquiere(tId, Bloqueo.ESCRITURA);
        boolean ok = false;
        try {
            balance += cantidad;
            ok = true;
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            ICoordinator coordinador = (ICoordinator) registry.lookup(Coordinator.COORDINATOR_NAME);
            coordinador.addResource(tId, getResourceId());
        }catch(Exception e){
            e.printStackTrace();
            ok = false;
        }finally {
            return ok? balance : null;
        }
    }

    @Override
    public Long retirar(Long cantidad, Long tId) {
       bloqueo.adquiere(tId, Bloqueo.ESCRITURA);
        boolean ok = false;
        try {
            if(balance - cantidad >= 0){
               balance -= cantidad;
               ok = true;
                Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                ICoordinator coordinador = (ICoordinator) registry.lookup(Coordinator.COORDINATOR_NAME);
                coordinador.addResource(tId, getResourceId());
            }
 
        }catch(Exception e){
            e.printStackTrace();
            ok = false;
        }finally {
            System.out.println("El balance de la cuenta es de " + balance);
            return ok? balance : null;
        }
    }
    
}
