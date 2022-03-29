/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extralab2._danaromero;

import java.io.FileNotFoundException;
import java.util.Scanner;
import extralab2._danaromero.TipoCuenta;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dana Romero
 */
public class Main {
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        ManageBank mb = new ManageBank();
        int opcion=0;
        System.out.println("BANCO");
        do{
            try {
                System.out.println("Menú\n1 - Agregar Cuenta\n2- Depositar\n3 - Retirar"
                        + "\n4 - Registrar Intereses\n5 - Importar\n6 - Salir");
                System.out.print("Seleccione una opción: ");
                opcion = leer.nextInt();
                
                switch(opcion){
                    case 1:
                        System.out.println("---Agregar Cuenta---");
                        System.out.print("Ingrese código: ");
                        int code = leer.nextInt();
                        System.out.print("Ingrese nombre: ");
                        String nombre = leer.next();
                        System.out.println("Tipo\n0 - NORMAL\n1 - PLANILLA\n2 - VIP");
                        System.out.print("Ingrese tipo: ");
                        TipoCuenta tipo = TipoCuenta.values()[leer.nextInt()];
                        try{
                            mb.addCuenta(code, nombre, tipo);
                        }catch(AccountAlreadyExists ex){
                            System.out.println(ex.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("---Depositar---");
                        System.out.print("Ingrese código: ");
                        code = leer.nextInt();
                        System.out.print("Ingrese monto: ");
                        Double monto = leer.nextDouble();
                        if(mb.deposito(code, monto)){
                            System.out.println("Se depositó Lps."+monto+" en la cuenta "+code);
                        }else{
                            System.out.println("No se pudo efectuar el depósito");
                        }
                        break;
                    case 3:
                        System.out.println("---Retirar---");
                        System.out.print("Ingrese código: ");
                        code = leer.nextInt();
                        System.out.print("Ingrese monto: ");
                        monto = leer.nextDouble();
                        if(mb.retiro(code, monto)){
                            System.out.println("Se retiró Lps."+monto+" de la cuenta "+code);
                        }else{
                            System.out.println("No se pudo efectuar el retiro");
                        }
                        break;
                    case 4:
                        System.out.println("---Registrar Intereses---");
                        mb.registrarIntereses();
                        System.out.println("Los intereses fueron registradoes exitosamente");
                        break;
                    case 5:
                        System.out.println("---Importar---");
                        System.out.print("Ingrese dirección: ");
                        String filename = leer.next();
                        mb.Import(filename);
                        break;
                    case 6:
                        System.out.println("¡GRACIAS!");
                        break;
                }
            } catch (Exception ex) {
            }
        }while(opcion!=6);
    }
}
