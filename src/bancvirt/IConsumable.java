/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import transaccion.Transaction;

/**
 *
 * @author sala_bd
 */
public interface IConsumable {
    public Boolean canConsume(Long tId);
    public void returnToState(Transaction t);
}
