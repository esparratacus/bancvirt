/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import bancvirt.Banco;
import bancvirt.IBanco;
import coordinator.Coordinator;
import coordinator.ICoordinator;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class ClientMain {
    
    public static String menu(){
        System.out.println("*** escoge el recurso que quieres modificar ***");
        System.out.println("1. ahorros");
        System.out.println("2. corriente");
        System.out.println("3. visa");
        System.out.println("4. master card");
        System.out.println("5. abortar");
        System.out.println("6. Salir");
        String retorno;
        Scanner sc = new Scanner(System.in);
        retorno = sc.nextLine();
      
        return retorno;
    }
    public static String escoge(){
        System.out.println("*** Escoge la accion ***");
        System.out.println("1. abono");
        System.out.println("2. retiro");
        Scanner sc = new Scanner(System.in);
        String retorno = sc.nextLine();
        return retorno;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", Coordinator.COORDINATOR_IP);
            Registry myRegistry = LocateRegistry.getRegistry(Coordinator.COORDINATOR_IP, 1099);
            ICoordinator coordinador = (ICoordinator) myRegistry.lookup(Coordinator.COORDINATOR_NAME);
            Scanner sc = new Scanner(System.in);
            System.out.println("Ingrese su id de cliente");
            String cliente = sc.next();
            System.out.println("Ingrese contrasenia");
            char[] contra = sc.next().toCharArray();
            String idRecurso = "";
            String accion = "";
            long tId = coordinador.openTransaction(cliente, contra);
            String opcion = "";
            do{
                opcion = menu();
                System.out.println("TID: "+ tId);
                switch(opcion){
                    case "1":
                        idRecurso = Banco.BANCO_AHORRO;
                        break;
                    case "2":
                        idRecurso = Banco.BANCO_CORRIENTE;
                        break;
                    case "3":
                        idRecurso = Banco.VISA;
                        break;
                    case "4":
                        idRecurso = Banco.MASTER_CARD;
                        break;
                    case "5":
                        coordinador.abortTransaction(tId);
                        System.out.println("Indeciso");
                        System.exit(0);
                        break;
                    case "6":
                        coordinador.closeTransaction(tId);
                        System.out.println("Gracias por venir :) ");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opcion no disponible");
                        break;
                }
                String escoge = escoge();
                System.out.println("Ingresa el monto");
                String monto = "";
                monto = sc.next();
                System.out.println("Hago una peticion prar el recruso "+ idRecurso);
                
                switch(escoge){
                    case "1": 
                        Long saldoActual = coordinador.depositar(cliente, idRecurso, Long.parseLong(monto),tId);
                        System.out.println("Su nuevo saldo es de " + saldoActual);
                        break;
                    case "2":  
                        Long saldoActualRetiro = coordinador.retirar(cliente, idRecurso, Long.parseLong(monto),tId);
                        System.out.println("Su nuevo saldo es de " + saldoActualRetiro);
                        break;
                    default:
                        break;    
                }    
                
            }while(!opcion.equals("5"));
            
        } catch (RemoteException ex) {
            System.out.println("Error al conectarse al objeto remoto");
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
