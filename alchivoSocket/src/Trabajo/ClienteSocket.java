/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabajo;

import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

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
    private int puerto = 500;
    private Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String numero;
    private String usuario;
    private File localFile;
    private String mensaje;
    private String filename;

    public String getMensaje() {
        return mensaje;
    }

    public ClienteSocket(String usuario, char[] pass) {
        try {
            inicio();

            this.usuario = usuario;
            
            dos.writeUTF("1:"+usuario+":"+String.valueOf(pass));
            dos.writeUTF(String.valueOf(pass));
            //retorna un numero de control de transacciones 
            numero = dis.readUTF();
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

    public boolean isAcceso() {
        return acceso;
    }

    private void finData() {
        try {
            dis.close();
            dos.close();

        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void inicioData() {

        try {
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());

        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void inicio() {
        try {
            servidor = InetAddress.getByName("localhost");
            client = new Socket(servidor, puerto);

            inicioData();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String crypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void inicioBuffer() {
        try {
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(client.getOutputStream());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private boolean enviado(String filename){
    this.filename=filename;
    enviaFichero();
    return mensaje.equals("Archivo recivido con exito");
    }
    private void finBuffer() {

        try {
            bis.close();
            bos.close();

        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviaFichero() {
        localFile = new File(filename);
        try {
            inicioBuffer();
            inicioData();
            //enviamos codigo de verificacion 
            comprueba();
            //Enviamos el nombre del fichero
            dos.writeUTF(localFile.getName());
            byteArray = new byte[8192];
            while ((in = bis.read(byteArray)) != -1) {
             bos.write(byteArray, 0, in);
            }
            mensaje=dis.readUTF();
            finBuffer();
            finData();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void comprueba() {

        try {
            dos.writeUTF(crypt(numero));
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}