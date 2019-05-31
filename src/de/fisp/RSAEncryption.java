package de.fisp;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.Cipher;

import jxl.*;

import jxl.write.*;
import jxl.write.Number;

public class RSAEncryption 
{
private static CountTime ct = new CountTime();
	
	private static KeyPairGenerator rsaKPG;
	
	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	
	private static String decryptedText;
	private static String umwString;
	
	private static Cipher ecipher = null;
	private static Cipher dcipher = null;
	
	private static byte[] encryptedArray;
	private static byte[] arrayToEncrypt;
	
	public static int rsaSumme;

	public RSAEncryption () throws NoSuchAlgorithmException 
	{
		/*
		 * KeyPairGenerator wird in dem entsprechenden
		 * Algorithmus erzeugt
		 */
		rsaKPG = KeyPairGenerator.getInstance("RSA");
		
		//KeyPairGenerator wird mit einer keysize von 4096 bits initialisiert
		rsaKPG.initialize(4096);

		//KeyPair wird generiert um im folgenden public und private Keys erzeugen zu k�nnen
		KeyPair kp = rsaKPG.generateKeyPair();

		// Erzeugung vom PublicKey
		publicKey = kp.getPublic();

		// Erzeugung vom PrivateKey
		privateKey = kp.getPrivate();

		// Cipherinstanz zum ausgew�hlten Verschl�sselungsalgorithmus
		// Verschl�sselung
		try {
			ecipher = Cipher.getInstance("RSA");
			ecipher.init(Cipher.ENCRYPT_MODE, publicKey);
		} catch (Exception e1) {
			System.out.println("Fehler bei der Objektinitialisierung ENCRYPT_MODE: " + e1);
			e1.getMessage();
		}
		
		// Entschl�sselung
		try {
			dcipher = Cipher.getInstance("RSA");
			dcipher.init(Cipher.DECRYPT_MODE, privateKey);
		} catch (Exception e1) {
			System.out.println("Fehler bei der Objektinitialisierung DECRYPT_MODE: " + e1);
			e1.getMessage();
		}
		
	}	

	public void encrypt(int Datenmenge) throws Exception {
		
		//es wird begonnen die Zeit zu messen
		ct.startTime();
		
		rsaSumme++;
		
		//zu Beginn wird der Load-Average f�r die entsprechende Datenmenge abgefragt
		if(rsaSumme == 1 && Datenmenge == 125) 
		{
			CPUInformation.getLoadaverage("RSA");
			Number number = new Number(3, 7, CPUInformation.load);
			Writer.sheetRSA.addCell(number);
		}
		else if(rsaSumme == 1 && Datenmenge == 250) 
		{
			CPUInformation.getLoadaverage("RSA");
			Number number = new Number(3, 23, CPUInformation.load);
			Writer.sheetRSA.addCell(number);
		}
		else if(rsaSumme == 1 && Datenmenge == 500) 
		{
			CPUInformation.getLoadaverage("RSA");
			Number number = new Number(3, 39, CPUInformation.load);
			Writer.sheetRSA.addCell(number);
		}

		//in einer Schleife wird bis die entsprechende Datenmenge erreicht wurde Bytes generiert, verschl�sselt und entschl�sselt
		for (int zaehler = 0; zaehler <= Datenmenge; zaehler++) {
			
			try 
			{	//byte array von 250
				byte[] t = new byte[200];
				//es werden in der festgelegten byte Arrayl�nge zuf�llige bytes generiert
				new Random().nextBytes(t);
				t.toString();
				umwString = new String(t);
				//Zeichensatz wird festgelegt
				arrayToEncrypt = umwString.getBytes("UTF-8");
				//mit ecipher.doFinal wird der Datensatz verschl�sselt
				encryptedArray = ecipher.doFinal(arrayToEncrypt);
				//mit dcipher.doFinal wird der Datensatz entschl�sselt
				decryptedText = new String(dcipher.doFinal(encryptedArray));
			} 
			catch (Exception e) 
			{
				System.err.println("Fehler bei RSA! " + e);
				e.printStackTrace();
			}
			
		}
		
		/*
		 * die Methode einer anderen Klasse wird aufgerufen
		 * daf�r wird der entsprechende Algorithmus und die Datenmenge �bergeben, 
		 * damit die Zeit zugeordnet werden kann
		 */
		ct.consumedTime("RSA");
		
		/*
		 * f�r die Berechnung der verbrauchten CPU-Zeit wird eine 
		 * weitere Methode aufgerufen, welches die CPU-Zeit ermittelt
		 * der Algorithmus wird wegene der Zuordnung auch �bergeben
		 */
		ct.consumedCPUTime("RSA", CPUInformation.getProcessCPUTimeRSA());
		
		/*
		 * nach dem der gesamte Vorgang fertig ist werden die f�r den
		 * Prozess verbrauchten Kapazit�ten ausgegeben
		 */
		if(Main.anzahlDurchg�nge == rsaSumme) 
		{
			CPUInformation.printCpuInfo("RSA", Datenmenge);
			
			//die Zeiten werden auf Null gesetzt, um den n�chsten Durchlauf genau berechnen zu k�nnen
			CPUInformation.dblProcessCpuTimeNsRSA = 0;
			CPUInformation.dblProcessCpuTimeSRSA = 0;
			CPUInformation.dblProcessCpuTimeNsRSACPU = 0;
			CPUInformation.dblProcessCpuTimeSRSACPU = 0;
			
			ct.runsRSA = 0;
			ct.summeRSA = 0;
			
		}
		
		
		
	}

}
