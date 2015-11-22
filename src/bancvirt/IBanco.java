/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author david
 */
public interface IBanco extends Remote{
    public Boolean iniciarSesion(String idUsuario, char[] contrasenia) throws RemoteException;
    public Long depositar(String idUsuario, String tipo, Long monto, Long tId) throws RemoteException;
    public Long retirar(String idUsuario, String tipo, Long monto, Long tId) throws RemoteException;
    public Boolean canCommit(String idUsuario, String tipo, Long tID) throws RemoteException;
    public Boolean commit(String idUsuario, String tipo, Long tID) throws RemoteException;
    public Boolean rollback(String idUsuario, String tipo, Long tID) throws RemoteException;
}
