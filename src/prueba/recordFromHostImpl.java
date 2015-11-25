/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author david
 */
public class recordFromHostImpl extends UnicastRemoteObject implements recordFromHost {

    public recordFromHostImpl () throws RemoteException {
        super();
    }

    public int recordObject(String name, Remote obj) {
       int ret=0;
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(name, obj);
        } catch (Exception e) {
            System.err.println("recordObject exception:");
            e.printStackTrace();
            ret = -1;
        }
    return ret;
    }

}
