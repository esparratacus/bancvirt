/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

/**
 *
 * @author esparratacus
 */
public interface IService {
    public Long abonar(Long cantidad);
    public Long retirar(Long cantidad);
}
