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

/**
 *
 * @author david
 */
public class Banco extends UnicastRemoteObject implements IBanco {

    private HashMap<String, Client> clientes;
    private String tipo;
    public final static String BANCO_AHORRO = "bancoahorro";
    public final static String BANCO_CORRIENTE = "banco corriente";
    public final static String TARJETA_AHORROS = "tarjeta ahorros";
    public final static String TARJETA_CORRIENTE = "tarjeta corriente";
    
    public final static String ABONO = "abono";
    public final static String RETIRO = "retiro";

    /**
     * Representa el arreglo de todos los servicios que puede ofrecer el banco
     */
    public static enum SERVICIOS {

        BANCO_AHORRO,
        BANCO_CORRIENTE,
        TARJETA_AHORROS,
        TARJETA_CORRIENTE
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
                System.out.println("Cargados los elementos de ahorro");
                break;
            case BANCO_CORRIENTE:
                break;
            case TARJETA_AHORROS:
                break;
            case TARJETA_CORRIENTE:
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
            case TARJETA_AHORROS:
                return cliente.getCredito();
            case TARJETA_CORRIENTE:
                return cliente.getDebito();
            default:
                return null;
        }
    }

    @Override
    public Long depositar(String idUsuario, String tipoR, Long monto, Long tId) throws RemoteException {
        Client cliente = clientes.get(idUsuario);
        if(cliente == null){
            System.out.println("NO encuentra al mk");
        }
        switch (tipoR) {
            case BANCO_AHORRO:
                System.out.println("Van a depositar");
                try{
                    Thread.sleep(10000);
                }catch(Exception e){
                    
                }
                Recurso recurso = darRecurso(BANCO_AHORRO, cliente);
                return recurso.abonar(monto, tId);
            case BANCO_CORRIENTE:
                break;
            case TARJETA_AHORROS:
                break;
            case TARJETA_CORRIENTE:
                break;
        }

        return null;
    }

    @Override
    public Long retirar(String idUsuario, String tipoTransaccion, Long monto, Long tId) throws RemoteException {
        switch (tipo) {
            case BANCO_AHORRO:

                break;
            case BANCO_CORRIENTE:
                break;
            case TARJETA_AHORROS:
                break;
            case TARJETA_CORRIENTE:
                break;
        }

        return null;
    }

}
