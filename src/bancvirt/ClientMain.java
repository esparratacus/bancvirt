/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

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
            Registry myRegistry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            ICoordinator coordinador = (ICoordinator) myRegistry.lookup(Coordinator.COORDINATOR_NAME);
            String cliente = "1020";
            char[] contra = "david".toCharArray();
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
                }
                String escoge = escoge();
                System.out.println("Ingresa el monto");
                String monto = "";
                Scanner sc = new Scanner(System.in);
                monto = sc.next();
                switch(escoge){
                    case "1":
                        IBanco banco = (IBanco) myRegistry.lookup(idRecurso);
                        Long saldoActual = banco.depositar(cliente, Banco.BANCO_AHORRO, Long.parseLong(monto),tId);
                        System.out.println("Su nuevo saldo es de " + saldoActual);
                        break;
                    case "2":
                        //retiro
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
