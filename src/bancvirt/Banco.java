/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import transaccion.Transaction;

/**
 *
 * @author david
 */
public class Banco extends UnicastRemoteObject implements IBanco {

    private HashMap<String, Client> clientes;
    private String tipo;
    public final static String BANCO_AHORRO = "bancoahorro";
    public final static String BANCO_CORRIENTE = "banco corriente";
    public final static String VISA = "Visa";
    public final static String MASTER_CARD = "Master Card";
    public final static String ABONO = "abono";
    public final static String RETIRO = "retiro";
    public final static String[] TIPOS_BANCO = {BANCO_AHORRO, BANCO_CORRIENTE, VISA, MASTER_CARD};

    @Override
    public Boolean canCommit(String idUsuario, String tipo, Transaction tID) throws RemoteException {
        Client cliente = clientes.get(idUsuario);
        Recurso r = darRecurso(tipo, cliente);
        return r.canConsume(tID.gettId());
    }

    @Override
    public Boolean commit(String idUsuario, String tipo, Transaction tID) throws RemoteException {
        Client cliente = clientes.get(idUsuario);
        Recurso r = darRecurso(tipo, cliente);
        r.returnToState(tID);
        return r.commit(tID.gettId());
    }

    @Override
    public Boolean rollback(String idUsuario, String tipo, Transaction tID) throws RemoteException {
        System.out.println("id usuario " + idUsuario);
        Client cliente = clientes.get(idUsuario);
        Recurso r = darRecurso(tipo, cliente);
        return r.rollback(tID.gettId());
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public HashMap<String, Client> getClientes() {
        return clientes;
    }

    public Banco(String pTipo) throws RemoteException {
        clientes = new HashMap<>();
        tipo = pTipo;
        /* Usuarios */
        Client one = new Client("David", "david".toCharArray(), "1020");
        Client two = new Client("Carlos", "silva".toCharArray(), "1111");
        Client three = new Client("Daniel", "Serrano".toCharArray(), "0909");
        Client four = new Client("Gabriel", "Fuentes".toCharArray(), "0123");
        clientes.put(one.getId(), one);
        clientes.put(two.getId(), two);
        clientes.put(three.getId(), three);
        clientes.put(four.getId(), four);
        /* Cuentas */
        switch (tipo) {
            case BANCO_AHORRO:
                Ahorro oneAhorro = new Ahorro();
                oneAhorro.setBalance(new Long(100));
                one.setAhorro(oneAhorro);
                oneAhorro.setClient(one);
                oneAhorro.rollback(new Long(0));
                System.out.println("Cargados los elementos de ahorro");
                break;
            case BANCO_CORRIENTE:
                Corriente corriente = new Corriente();
                corriente.setBalance(new Long(100));
                one.setCorriente(corriente);
                corriente.setClient(one);
                corriente.rollback(new Long(0));
                System.out.println("Se han cargado los elementos de corriente");
                break;
            case VISA:
                Visa visa = new Visa();
                visa.setBalance(new Long(100));
                one.setVisa(visa);
                visa.setClient(one);
                visa.rollback(new Long(0));
                break;
            case MASTER_CARD:
                MasterCard master = new MasterCard();
                master.setBalance(new Long(100));
                one.setMasterCard(master);
                master.setClient(one);
                master.rollback(new Long(0));       
                break;
        }

    }

    public void setClientes(HashMap<String, Client> clientes) {
        this.clientes = clientes;
    }

    @Override
    public Boolean iniciarSesion(String idUsuario, char[] contrasenia) throws RemoteException {
        Client client = clientes.get(idUsuario);
        if (client != null) {
            return Arrays.equals(contrasenia, client.getPasword());
        }
        return false;
    }

    public static Recurso darRecurso(String tipoR, Client cliente) {
        switch (tipoR) {
            case BANCO_AHORRO:
                return cliente.getAhorro();
            case BANCO_CORRIENTE:
                return cliente.getCorriente();
            case VISA:
                return cliente.getVisa();
            case MASTER_CARD:
                return cliente.getMasterCard();
            default:
                return null;
        }
    }

    @Override
    public Long depositar(String idUsuario, String tipoR, Long monto, Transaction tId) throws RemoteException {
        Client cliente = clientes.get(idUsuario);
        System.out.println("Van a depositar");
        Recurso recurso = darRecurso(tipoR, cliente);
        if(recurso == null){
            System.out.println("Recurso no encontrado");
        }else{
            System.out.println("Encuentra el recurso");
        }
        return recurso.abonar(monto, tId);

    }

    @Override
    public Long retirar(String idUsuario, String tipoTransaccion, Long monto, Transaction tId) throws RemoteException {
        Client cliente = clientes.get(idUsuario);
        System.out.println("Van a depositar");
        Recurso recurso = darRecurso(tipoTransaccion, cliente);
        return recurso.retirar(monto, tId);
    }

}
