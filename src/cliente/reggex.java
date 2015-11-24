/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author david
 */
public class reggex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nuevo = "([0-9]*)"+"bancoahorro";
        System.out.println("1bancoahorro".matches(nuevo));
    }
    
}
