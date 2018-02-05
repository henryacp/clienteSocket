/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabajo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Asus
 */
public class ManejoVisual {
    private ClienteSocket cs;
    private String numero;
    private String mensaje;
    private String opcion;
     private static String NUMEROS = "0123456789"; 
	public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	
	private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' }; // cadena de carracteres para el cifrado de claves
	
    
    public ManejoVisual(String usuario,char[]pass) {
       cs = new ClienteSocket();
       opcion=2+"";
         inicio();
        cs.envioData(usuario);
        cs.envioData(String.valueOf(pass));
        cierre();
        numero=mensaje;
        mensaje="";
        
    }
    
       public ManejoVisual(String nombres,String email,String usuario,char[]pass) {
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
            
            //inicioBuffer();
            //inicioData();
            //enviamos codigo de verificacion 
            //comprueba();
            //Enviamos el nombre del fichero
           //dos.writeUTF(localFile.getName());
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
