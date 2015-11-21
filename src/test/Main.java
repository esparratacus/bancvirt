/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import bancvirt.Ahorro;
import bancvirt.Client;

/**
 *
 * @author david
 */
public class Main {

    
    public static void main(String[] args) {
        Client client = new Client("David", "david".toCharArray(), "120");
        Ahorro ahorro = new Ahorro();
        ahorro.setClient(client);
        Boolean commit = ahorro.commit();
        ahorro.getClient().setName("Carlos");
        //ahorro.rollback();
        System.out.println(ahorro.getClient().getName());
        System.out.println(commit);
    }
    
}
