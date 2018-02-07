/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabajo;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class ClienteSocket {

    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private int in;
    private byte[] byteArray;
    private boolean acceso;
    private InetAddress servidor;
    private int puerto = 8888 ;
    private Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String usuario;
    private File localFile;
    private InputStream is;
    private String mensaje;
    private ObjectInputStream ois;
    private String [] listaArchivos;
   // private String filename;
   
        public void inicio() {
        try {
            servidor = InetAddress.getByName("172.17.209.28");
            client = new Socket(servidor, puerto);
            mensaje="";
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void inicioData() {
        //inicia el anvio de archivos
        try {
            
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());

        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
           public void inicioBuffer() {
        try {
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(client.getOutputStream());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String[] getListaArchivos() {
        return listaArchivos;
    }
        public void listaArchivos(){
           
        try {
            is= client.getInputStream();

            ois = new ObjectInputStream(is);
             listaArchivos = (String[]) ois.readObject();
			for (int i = 0; i < listaArchivos.length; i++) {
				System.out.println(listaArchivos[i]);
			}
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
			
        }
        public void envioData (String datos){
        try {
            dos.writeUTF(datos);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        public void reciveData (){
            
        try {
            mensaje =dis.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    public String getMensaje() {
        return mensaje;
    }    
    public void enviaFichero(String filename) {
        localFile = new File(filename);
        try {
            //inicioBuffer();
            //inicioData();
            //enviamos codigo de verificacion 
            //comprueba();
            //Enviamos el nombre del fichero
           dos.writeUTF(localFile.getName());
            byteArray = new byte[1024];
            while ((in = bis.read(byteArray)) != -1) {
             bos.write(byteArray, 0, in);
            }
            //mensaje=dis.readUTF();
            //finBuffer();
            //finData();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    public void descargar (String archivo){
        try {
            //Recibimos el nombre del fichero
            byteArray = new byte[8192];
           /// filename = dis.readUTF();
           String  filename = archivo;
             BufferedOutputStream bos1 = new BufferedOutputStream(new FileOutputStream(filename));
            while ((in = bis.read(byteArray)) != -1){
                    bos1.write(byteArray,0,in);
                    }
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
        public void finBuffer() {

        try {
            bis.close();
            bos.close();

        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void finData() {
        try {
            dis.close();
            dos.close();

        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
        
         public void finCierre(){
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
////////////      
    
        //Login de usuario 
   /*
    public ClienteSocket(String usuario, char[] pass) {
        try {
            inicio();

            this.usuario = usuario;
            
            dos.writeUTF("1:"+usuario+":"+String.valueOf(pass));
            dos.writeUTF(String.valueOf(pass));
            //retorna un numero de control de transacciones 
            numero = dis.readUTF();
            //evalua la transaccion
            if (numero != "false") {
                acceso = true;
            } else {
                acceso = false;
            }
            finData();
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        //respuesta de acceso 
    public boolean isAcceso() {
        return acceso;
    }
            //finaliza el anvio dedatos cierra la coneccion
    

    
    // inicio de sockets
    
    // encripta a md5 
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


    //para enviar archivos 
    public String[] archivos(){
        String []lista = null;
        inicioData();
        try {
            //4 para envio de parametro para listar archivos
            dos.writeUTF("5");
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        finData();
        return lista;
    }
    
    public boolean enviado(String filename){
    this.filename=filename;
    enviaFichero();
    return mensaje.equals("Archivo ingresado");
    }
    

    

    private void comprueba() {

        try {
            dos.writeUTF(crypt(numero));
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
*/
}
