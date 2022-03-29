/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extralab2._danaromero;

/**
 *
 * @author Dana Romero
 */
public class AccountAlreadyExists extends Exception{
    
    public AccountAlreadyExists(int num){
        super("Cuenta "+num+" ya está agregada en el sistema");
    }
}
