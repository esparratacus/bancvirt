/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author david
 */
public interface recordFromHost extends Remote {
    int recordObject(String name, Remote obj) throws RemoteException;
}
