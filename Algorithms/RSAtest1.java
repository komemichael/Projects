/*******************************************************************************
  *
  * RSA.java
  *
  * This program implements the RSA algorithm to encrypt and then decrypt a
  * simple string represented in text format.
  *
  * RSA is a public key encryption system which makes use of two keys, a public
  * one and a private one. Each key is made up of two parts an exponent value and
  * a modulus value.
  *
  * The modulus value is determined by two (pseudo) random numbers of a given
  * (large) size, and is the same for both keys. The public exponent may then be
  * selected, and a private exponent calculated.
  *
  * The BigIntegers class provides native support for implementing encryption
  * algorithms in Java.
  *
  *******************************************************************************/

/**
* LAB5 ECE 3790
*
* ECE 3790 SECTION A01
* INSTRUCTOR Bob McLeod
* ASSIGNMENT Lab 5, question 1
* @author Michael Oghenekome
* @version 2017-April 7th
*
* PURPOSE: The purpose of this java program is to 
*            encrypt and decrypt a file using RSA Encryption.
* 
*  References:
*             Lab 4, ECE3790. Written by Kome Michael - 
*             Lab 5 , ECE3790. D2L algorithm template for RSA (Modified)
* */
import java.math.BigInteger;
import java.io.*;
import java.util.*;


class RSAtest1  {
  
  static public void main(String args[]){
    
    Scanner kbd = new Scanner(System.in);
    System.out.println("Enter the name of the file you want to Encrypt. Ex:fileToEncrypt.txt");
    String filename = "fileToEncrypt.txt"; //kbd.nextLine();
    System.out.println();
    
    int[] keySizes = new int[]{64, 128, 256,512,1024,2048};
    
    for (int i = 0 ; i < keySizes.length; i++)
    {
      rsaTest(keySizes[i], i+1, filename);    
    }
    System.out.println("\nEnd of Program\n RSA Program implemented by Kome Michael");
  }

  /**
   * rsaTest - performs encryption and decryption of a file
   * @param fileToEncrypt - The name of the file that is to be encrypted.
   * @param encryptedFile - The name of the file that the encrypted data is stored in.
   * @param lstartTimeX - the Date value of the initial current time.
   * @param lendTimeX - the Date value of the final current time.
   * @param filename - The name of the file that is to be encrypted.
   * @param e - the encryption key to be used to encrypt the data or number.
   * @param n - modulus value for which the message is calculated mod for. This is a public value.
   * @param outputX - the time difference between the start and end time.
   */
  public static void rsaTest(int keySize, int numFile, String filename)
  {
    int KEY_SIZE = keySize;
    
    // Create a BigInteger constant for the integer ’1’.
    BigInteger one = new BigInteger("1");
    
    // Determine a modulus of sufficient size (based on the size of P and Q).
    BigInteger p = new BigInteger(KEY_SIZE,20, new Random());
    BigInteger q = new BigInteger(KEY_SIZE,20, new Random());
    BigInteger n = p.multiply(q);
    
    // Calculate PHI(n) for use in determining the public and private exponents.
    BigInteger phi = p.subtract(one).multiply(q.subtract(one));
    
    // Select a public exponent (repeat until GCD condition is met).
    long lStartTime1 = new Date().getTime(); 
    BigInteger e = new BigInteger(32,4, new Random());
    long lEndTime1 = new Date().getTime(); 
    long loutput1 = lEndTime1 - lStartTime1;
    
    long lStartTime2 = new Date().getTime(); 
    BigInteger gcd = phi.gcd(e);
    while (!gcd.equals(one)) {
      e = new BigInteger(32,4, new Random());
      gcd = phi.gcd(e);
    }
    BigInteger d = e.modInverse(phi);
    long lEndTime2 = new Date().getTime(); 
    long loutput2 = lEndTime2 - lStartTime2;
    
    String decryptedFile = "decryptedFile" + keySize +"bit.txt";
    String encryptedFile = "encryptedFile" + keySize +"bit.txt";
    
    String fileToEncrypt = filename; 
    long lStartTime3 = new Date().getTime(); 
    encrypt(fileToEncrypt,encryptedFile, e, n);
    long lEndTime3 = new Date().getTime(); 
    long loutput3 = lEndTime3 - lStartTime3;
    
    
    long lStartTime4 = new Date().getTime(); 
    decrypt(encryptedFile,decryptedFile, d, n);
    long lEndTime4 = new Date().getTime(); 
    long loutput4 = lEndTime4 - lStartTime4;
    
    System.out.println("Calculation Time for KEYSIZE " + keySize + "bit");
    System.out.println("Time to calculate encryption key: " + loutput1 + " miliseconds" + " || Time to calculate decryption key: " + loutput2 + " miliseconds");
    System.out.println("Time to calculate encryption: " + loutput3 + " miliseconds || " + " Time to calculate decryption: " + loutput4 + " miliseconds");
    System.out.println();
  }
  
  
  
  /**
   * encrypt - gets the name of a file and encrypts the data in the file and stores
   *           it in another file.
   * @param fileToEncrypt - The name of the file that is to be encrypted.
   * @param encryptedFile - The name of the file that the encrypted data is stored in.
   * @param line - a single line in the file to encrypt. Encryption is done in parts.
   * @param lines - the words in lines is encrypted. This is to make sure the keysize is not exceeded.
   * @param byteArray - The conversion of the string into bytes.
   * @param messageAsBigInt - The big Integer representation of the string when converted to bytes.
   * @param cypherAsBigInt - The encrypted text or version message of the string.
   * @param fileToEncrypt - The name of the file that is to be encrypted.
   * @param e - the encryption key to be used to encrypt the data or number.
   * @param n - modulus value for which the message is calculated mod for. This is a public value.
   * @param printWriter - the name of the method to write to a text file.
   * @param file - the name of the object file which is to be encrypted.
   * @param sc - the scanner object value of the file being read into java program.
   */
  public static void encrypt(String fileToEncrypt, String encryptedFile, BigInteger e, BigInteger n)
  {
    try
    {
      File file = new File(fileToEncrypt);
      Scanner sc = new Scanner(file);
      
      FileWriter  writer      = new FileWriter(encryptedFile);
      PrintWriter printWriter = new PrintWriter(writer);
      
      while (sc.hasNextLine()) 
      {
        String line = sc.nextLine();
        String[] lines = line.split(" ");
        
        for(int i = 0; i < lines.length; i++)
        {
          byte[] byteArray = lines[i].getBytes();
          BigInteger messageAsBigInt = new BigInteger(byteArray);
          
          BigInteger cypherAsBigInt = messageAsBigInt.modPow(e,n);
          printWriter.print(cypherAsBigInt + "-");
        }
        printWriter.print("\n");
      }
      sc.close();
      printWriter.close();
    }
    catch (IOException error)
    {
      System.out.println("RSA is super cool");
    }
    catch(ArrayIndexOutOfBoundsException error){
      String messageString = "RSA is cool";
    }
  }
  
    /**
   * decrypt - gets the name of a file and decrypts the data in the file and stores
   *           it in another file.
   * @param decryptedFile - The name of the file where the decrypted data is stored in.
   * @param encryptedFile - The name of the file that the encrypted data is stored in.
   * @param line - a single line in the file to encrypt. Encryption is done in parts.
   * @param lines - the words in lines is encrypted. This is to make sure the keysize is not exceeded.
   * @param byteArrayBack - The conversion of the string into bytes.
   * @param messageAsBigInt - The big Integer representation of the string when converted to bytes.
   * @param messageStringBack - The decrypted message recorvered.
   * @param decypherAsBigInt - The decrypted string of an encrypted message.
   * @param d - the decryption key to be used to decrypt the big integer number.
   * @param n - modulus value for which the message is calculated mod for. This is a public value.
   * @param printWriter - the name of the method to write to a text file.
   * @param file - the name of the object file which is to be encrypted.
   * @param sc - the scanner object value of the file being read into java program.
   */
  public static void decrypt(String encryptedFile, String decryptedFile, BigInteger d, BigInteger n)
  {
    try
    {
      File file = new File(encryptedFile);
      Scanner sc = new Scanner(file);
      
      FileWriter  writer      = new FileWriter(decryptedFile);
      PrintWriter printWriter = new PrintWriter(writer);
      
      while (sc.hasNextLine()) 
      {
        String line = sc.nextLine();
        String[] lines = line.split("-");
        
        for (int i = 0; i < lines.length; i++)
        {
          BigInteger messageAsBigInt = new BigInteger(lines[i]);
          
          BigInteger  decypherAsBigInt = messageAsBigInt.modPow(d,n);
          byte[] byteArrayBack = decypherAsBigInt.toByteArray(); 
          String messageStringBack = new String(byteArrayBack);
          
          printWriter.print(messageStringBack + " ");
        }
        printWriter.print("\n");
      }
      sc.close();
      printWriter.close();
    }
    catch (IOException e)
    {
      System.out.println("RSA is super cool");
    }
    catch(ArrayIndexOutOfBoundsException error){
      String messageString = "RSA is cool";
    }
  }
} // RSA