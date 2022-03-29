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
public enum TipoCuenta {
    NROMAL(0.02,500), PLANILLA(0,200), VIP(0.04,1000);
    public double tasaInteres, minSaldo;
    
    TipoCuenta (double tasa, double saldo){
        tasaInteres = tasa; 
        minSaldo = saldo;
    }
    
}
