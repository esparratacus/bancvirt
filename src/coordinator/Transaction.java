/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;
/**
 *
 * @author david
 */
public class Transaction {
    private Long tId;


    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Transaction(Long tId) {
        this.tId = tId;
    }
    
    
    public Transaction(){
        
    }
}
