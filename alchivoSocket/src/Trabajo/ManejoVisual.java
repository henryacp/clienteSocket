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
	 //crear usuario 
       public ManejoVisual(String nombres,String email,String usuario,String fecha,char[]pass) {
        //this.cs = new ClienteSocket(usuario, pass);
         cs = new ClienteSocket();
       opcion=1+"";
         inicio();
         cs.envioData(nombres);
         cs.envioData(email);
        cs.envioData(usuario);
        cs.envioData(String.valueOf(pass));
          cs.reciveData();
        mensaje=cs.getMensaje();
        cierre();
        //mensaje="";


       }   
    //logueo 
    public ManejoVisual(String usuario,char[]pass) {
       cs = new ClienteSocket();
       opcion=2+"";
         inicio();
        cs.envioData(usuario);
        cs.envioData(String.valueOf(pass));
        cs.reciveData();
        numero=cs.getMensaje();
        lista=cs.getListaArchivos();//envio de parametro 
        hora1=obtenerHora();
        cs.envioData(7+"");
        cs.reciveData();
        hora2=obtenerHora();
          cierre();
       mensaje=cs.getMensaje();
    }
    public void envioArchivo(String archivo){
           opcion=5+"";
           inicio();
           inicioBR();
           cs.enviaFichero(archivo);
           cs.finBuffer();
           cierre();
       }
    
    public void recibeArchivo(String archivo){
        
        opcion=8+"";
        inicioBR();
        inicio();
        cs.descargar(archivo);
        cs.finBuffer();
        cierre();
    }
    public void eliminarArchivo(String archivo){
        opcion=9+"";
        inicio();
        inicioBR();
        cs.envioData(archivo);
        cierre();
    }
    
    private void recalculoHora(String hora){
        String [] h1=hora1.split(":");
        String [] h2=hora2.split(":");
        String [] h3=hora.split(":");
        String ht="";
       for (int i=0;i<h1.length-1;i++){
           if(i>0)ht=ht+":";
           ht=ht+(((Integer.parseInt(h2[i])-Integer.parseInt(h1[i]))/2)+Integer.parseInt(h3[i]));
       }
        manejoHora(ht);
        
        
    }
    private String obtenerHora(){
        DateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
            return df.format(new Date());
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
      
       
     
       
       
       
       
       
       private void inicio(){
           cs.inicio();
        cs.inicioData();
        cs.envioData(opcion);
       }
       private void inicioBR(){
           cs.inicioBuffer();    
       }
       private void cierre(){
         
        cs.finData();
        cs.finCierre();
       }
       
}
