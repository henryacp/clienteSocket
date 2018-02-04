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
        
        
    }
    
       public ManejoVisual(String nombres,String email,String usuario,char[]pass) {
        //this.cs = new ClienteSocket(usuario, pass);
        

       }    
       private String crypt(String input) {
        try
		{
			MessageDigest msgd = MessageDigest.getInstance("MD5");
			byte[] bytes = msgd.digest(input.getBytes());
			StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
			for (int i = 0; i < bytes.length; i++)
			{
				int bajo = bytes[i] & 0x0f;
				int alto = (bytes[i] & 0xf0) >> 4;
			strbCadenaMD5.append(CONSTS_HEX[alto]);
			strbCadenaMD5.append(CONSTS_HEX[bajo]);
			}
			return strbCadenaMD5.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
    }
       private void inicio(){
           cs.inicio();
        cs.inicioData();
        cs.envioData(opcion);
       }
    
       private void cierre(){
           cs.reciveData();
        numero=cs.getMensaje();
        cs.finData();
        cs.finCierre();
       }
    
}
