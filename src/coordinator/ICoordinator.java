/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author david
 */
public interface ICoordinator extends Remote{
    
    public Long openTransaction(String username, char[] password) throws RemoteException;
    public Boolean closeTransaction(Long tId) throws RemoteException;
    public Boolean abortTransaction(Long tid) throws RemoteException;
    public Boolean addResource(Long tId, String resourceId) throws RemoteException;
    
    
}
