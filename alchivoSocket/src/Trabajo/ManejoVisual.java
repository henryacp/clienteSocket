/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabajo;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class ManejoVisual {
    private ClienteSocket cs;
    private String numero;
    private String mensaje;
    private String opcion;
    private String[] lista;
    private String hora1;
    private String hora2;
    public String[] getLista() {
        return lista;
    }
	
    
    public ManejoVisual(String usuario,char[]pass) {
       cs = new ClienteSocket();
       opcion=2+"";
         inicio();
        cs.envioData(usuario);
        cs.envioData(String.valueOf(pass));
        cs.reciveData();
        numero=cs.getMensaje();
        lista=cs.getListaArchivos();//envio de parametro 
        cs.envioData(7+"");
        cs.reciveData();
        recalculoHora(cs.getMensaje());
        mensaje="";
          cierre();
        
    }
    private void recalculoHora(String hora){
        
    }
    private String obtenerHora(){
        DateFormat df = new SimpleDateFormat("hh:mm:ss:SSS");
            return df.format(new Date());
    }
       public ManejoVisual(String nombres,String email,String usuario,String fecha,char[]pass) {
        //this.cs = new ClienteSocket(usuario, pass);
         cs = new ClienteSocket();
       opcion=1+"";
         inicio();
         cs.envioData(nombres);
         cs.envioData(email);
        cs.envioData(usuario);
        cs.envioData(String.valueOf(pass));
        cierre();
        numero=mensaje;
        mensaje="";


       }    

       
       
       private void manejoHora(String hora){
           String comando = "cmd";
            //String hora = "15:53:00";
            String entrada = "time" + " " + hora;

            try {
                Process proceso = Runtime.getRuntime().exec(comando);
                BufferedOutputStream out = new BufferedOutputStream(proceso.getOutputStream());
                out.write(entrada.getBytes());
                out.write("\r\n".getBytes());
                out.flush();
                out.close();
                proceso.waitFor();
            } catch (IOException ex) {
                System.out.println("Error de I/O"+ex);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
                   }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
      
       
       public void envioArchivo (){
           cs=new ClienteSocket();
           opcion=5+"";
            inicio();    
            inicioBR();
           cs.envioData(numero);
           //en mensaje envio path
            cs.enviaFichero(mensaje);
            
       }
       private void inicio(){
           cs.inicio();
        cs.inicioData();
        cs.envioData(opcion);
       }
       private void inicioBR(){
           cs.inicioBuffer();    
       }
       private void cierre(){
           cs.reciveData();
        mensaje=cs.getMensaje();
        cs.finData();
        cs.finCierre();
       }
       
}
