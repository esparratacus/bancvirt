/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

/**
 *
 * @author esparratacus
 */
public class Credito extends Tarjeta {

    @Override
    public boolean rollback() {
        return true;
    }

    @Override
    public boolean executeService() {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean commit() {
       return true; //To change body of generated methods, choose Tools | Templates.
    }
    
}
