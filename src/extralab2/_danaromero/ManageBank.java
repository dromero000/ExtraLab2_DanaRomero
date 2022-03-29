/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extralab2._danaromero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dana Romero
 */
public class ManageBank {
    
    RandomAccessFile rcuentas;
    
    //Constructor
    public ManageBank() throws FileNotFoundException{
        File folder = new File("banco");
        if(!folder.exists())
            folder.mkdir();
        
        rcuentas = new RandomAccessFile("banco/cuentas.bnk","rw");
    }
    
    public long buscar (int code) throws IOException{
        rcuentas.seek(0);
        while(rcuentas.getFilePointer()<rcuentas.length()){
            if(rcuentas.readInt()== code){
                return rcuentas.getFilePointer();
            }else{
                rcuentas.readUTF();
                rcuentas.skipBytes(16);
                //Skip tipo
                rcuentas.readUTF();
            }
        }
        return 1;
    }
    
    
    public void addCuenta(int code, String nombre, TipoCuenta tipo) throws AccountAlreadyExists{
        try {
            if(buscar(code)==1){
                rcuentas.seek(rcuentas.length());
                rcuentas.writeInt(code);
                rcuentas.writeUTF(nombre);
                rcuentas.writeLong(Calendar.getInstance().getTimeInMillis());
                rcuentas.writeDouble(tipo.minSaldo);
                rcuentas.writeUTF(tipo.name());
                System.out.println("La cuenta fue creada exitosamente");
            }else{
                throw new AccountAlreadyExists(code);
            }
        } catch (IOException ex) {
        }
    }
    
    public boolean deposito(int code, double monto) throws IOException{
        long pos =buscar(code);
        if(pos!=1){
            rcuentas.readUTF();
            rcuentas.skipBytes(8);
            double saldoActual = rcuentas.readDouble();
            rcuentas.seek(pos);
            rcuentas.readUTF();
            rcuentas.writeLong(Calendar.getInstance().getTimeInMillis());
            rcuentas.writeDouble(saldoActual+monto);
            return true;
        }else{
            System.out.println("La cuenta "+ code+" no existe");
        }
        return false;
    }
    
    public boolean retiro(int code, double monto) throws IOException{
        long pos =buscar(code);
        if(pos!=1){
            rcuentas.readUTF();
            rcuentas.skipBytes(8);
            double saldoActual = rcuentas.readDouble();
            if(saldoActual> monto){
                rcuentas.seek(pos);
                rcuentas.readUTF();
                rcuentas.writeLong(Calendar.getInstance().getTimeInMillis());
                rcuentas.writeDouble(saldoActual-monto);
                return true;
            }
            
        }else{
            System.out.println("La cuenta "+ code+" no existe");
        }
        return false;
    }
    
    public void registrarIntereses() throws IOException{
        rcuentas.seek(0);
        while(rcuentas.getFilePointer()<rcuentas.length()){
            rcuentas.skipBytes(4);
            rcuentas.readUTF();
            rcuentas.skipBytes(8);
            long posSaldo = rcuentas.getFilePointer();
            double saldo = rcuentas.readDouble();
            TipoCuenta tipo = TipoCuenta.valueOf(rcuentas.readUTF());
            double interes = tipo.tasaInteres*saldo;
            rcuentas.seek(posSaldo);
            rcuentas.writeDouble(saldo+interes);
            rcuentas.readUTF(); 
        }        
    }
    
    public void Import(String filename) throws IOException{
        FileWriter archivo = new FileWriter(filename);
        rcuentas.seek(0);
        while(rcuentas.getFilePointer()<rcuentas.length()){
            int codigo = rcuentas.readInt();
            String nombre = rcuentas.readUTF();
            rcuentas.skipBytes(8);
            double saldo = rcuentas.readDouble();
            String tipo =rcuentas.readUTF();
            archivo.write(codigo+" - "+nombre+" - Lps. "+saldo+" - Tipo "+ tipo+"\n");
            archivo.flush();
        }          
        
    }
    
    
    

    
   
    
    
    
}
