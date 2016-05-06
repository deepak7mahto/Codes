package encedit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;


class encedit {


    public static void doenc() {

        try {
            System.out.println("Enter the Absolute Path  of the File For Encryption");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();
            // System.out.println(s);
            //file to be encrypted
            FileInputStream inFile = new FileInputStream(s);
            // encrypted file
            System.out.println("Enter the name of output Encrypted File ");
            BufferedReader dr = new BufferedReader(new InputStreamReader(System.in));
            String x = dr.readLine();
            FileOutputStream outFile = new FileOutputStream(x);
            //password to encrypt the file
            System.out.println("Enter Password for Encryption ");//in dialog box
            BufferedReader z = new BufferedReader(new InputStreamReader(System.in));
            String password = z.readLine();
            if (password.equals(null) || password.equals("")) {
                System.out.println("Error: NO Password Inserted ");

            } else {
                //######################THE SALT GENERATION############################################

        /* password, iv and salt should be transferred to the other end in a secure manner
           salt is used for encoding
        */



                byte[] salt = new byte[8];// The byte data type is an 8-bit signed two's complement integer
                SecureRandom secureRandom = new SecureRandom();//This class provides a cryptographically strong random number generator
                secureRandom.nextBytes(salt);//produce a random salt using it .
                FileOutputStream saltOutFile = new FileOutputStream(x.concat("key"));//open the writing file descriptor
                saltOutFile.write(salt);//write the salt to the file
                saltOutFile.close();//close the file descriptor
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");//This class represents a factory for secret keys of algorithm PBKDF2WithHmacSHA1
                KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        /*  A user-chosen password that can be used with password-based encryption (PBE)
            The password can be viewed as some kind of raw key material, from which the encryption mechanism that
            uses it derives a cryptographic key.
            why toCharArray() is used this is because of the fact that  strings are immutable in nature so changes can not
            be made in it there is no way to overwrite its internal value when the password stored in it is no longer needed.
            Hence, this class requests the password as a char array, so it can be overwritten when done.
        */

                SecretKey secretKey = factory.generateSecret(keySpec);
                SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secret);
                AlgorithmParameters params = cipher.getParameters();
        /* iv adds randomness to the text and just makes the mechanism more secure used while initializing the cipher file to store the iv*/
                FileOutputStream ivOutFile = new FileOutputStream(x.concat("key"), true);
                byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
                ivOutFile.write(iv);
                ivOutFile.close();

                //file encryption
                byte[] input = new byte[64];
                int bytesRead;

                while ((bytesRead = inFile.read(input)) != -1) {
                    byte[] output = cipher.update(input, 0, bytesRead);
                    if (output != null)
                        outFile.write(output);
                }
                byte[] output = cipher.doFinal();
                if (output != null)
                    outFile.write(output);

                inFile.close();
                outFile.flush();
                outFile.close();

                System.out.println("File Encrypted.");

            }
        } catch (Exception e) {
            System.out.println("Error Occured Please Try Again" );
        }


    }


    public static void dodec() throws Exception {
        try {
            System.out.println("Enter the password");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String password = br.readLine();
            // reading the salt
            // user should have secure mechanism to transfer the
            // salt, iv and password to the recipient
            System.out.println("Enter the Absolute Path  of the Key");
            BufferedReader cr = new BufferedReader(new InputStreamReader(System.in));
            String key = cr.readLine();
            FileInputStream keyfile = new FileInputStream(key);
            byte[] saltiv = new byte[24];
            byte[] iv = new byte[16];
            byte[] salt = new byte[8];

            keyfile.read(saltiv);

            System.arraycopy(saltiv, 0, salt, 0, 8);
            System.arraycopy(saltiv, 8, iv, 0, 16);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            // file decryption
            System.out.println("Enter the Absolute Path  of the encrypted File  ");
            BufferedReader ek = new BufferedReader(new InputStreamReader(System.in));
            String d = ek.readLine();

try {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
    FileInputStream fis = new FileInputStream(d);
    FileOutputStream fos = new FileOutputStream(key + "decrypted.txt");
    byte[] in = new byte[64];
    int read;
    while ((read = fis.read(in)) != -1) {
        byte[] output = cipher.update(in, 0, read);
        if (output != null)
            fos.write(output);
    }

    byte[] output = cipher.doFinal();
    if (output != null)
        fos.write(output);
    fis.close();
    fos.flush();
    fos.close();
    System.out.println("File Decrypted.");
}
         catch (Exception e) {
            System.out.println("Wrong Password ");
        }



        } catch (Exception e2) {
            System.out.println("Error Occurred Please Try Again"+e2);
        }


    }


}

public class fileenc {
    public static void main(String[] args) throws Exception {
        System.out.println("Enter the Choice \n 1- Encryption  \n 2-Decryption ");
        BufferedReader ek = new BufferedReader(new InputStreamReader(System.in));
        String x = ek.readLine();
        encedit o = new encedit();
        if (x.equals("1")) {

            o.doenc();
        } else if (x.equals("2")) {
            o.dodec();
        } else {
            System.out.println("Enter The correct Choice ");


        }


    }
}




