package de.fisp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import jxl.write.Number;

public class AESEncryption 
{

	private static CountTime ct = new CountTime();

	private static KeyGenerator kg;
	private static SecretKey skey;
	
	private static String decryptedText, umwString;
	
	private static byte[] encryptedArray, arrayToEncrypt;

	private static Cipher ecipher = null, dcipher = null;
	
	public static int aesSumme;
	

	public AESEncryption() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException 
	{
		//in dem gewünschten Algorithmus wird ein Key generiert
		kg = KeyGenerator.getInstance("AES");

		//KeyGenerator wird mit einer keysize von 128 bits initialisiert 
		kg.init(128);

		//SecretKey wird generiert
		skey = kg.generateKey();
		
		//für die Verschlüsselung werden die Objekte initialisiert
		try 
		{
			//neue Instanz mit dem erwünschten Algorithmus wird erzeugt
			ecipher = Cipher.getInstance("AES");
			ecipher.init(Cipher.ENCRYPT_MODE, skey);
		} 
		catch (Exception e) 
		{
			System.out.println("Fehler bei der Objektinitialisierung ENCRYPT_MODE: " + e);
			e.printStackTrace();
		}
		
		//für die Entschlüsselung werden die Objekte initialisiert
		try 
		{
			//neue Instanz mit dem erwünschten Algorithmus wird erzeugt
			dcipher = Cipher.getInstance("AES");
			dcipher.init(Cipher.DECRYPT_MODE, skey);
		} 
		catch (Exception e) 
		{
			System.out.println("Fehler bei der Objektinitialisierung DECRYPT_MODE: " + e);
			e.printStackTrace();
		}
	}
	
	public void encrypt(int Datenmenge) throws Exception {

		ct.startTime();
		
		aesSumme++;
		
		//zu Beginn wird der Load-Average für die entsprechende Datenmenge abgefragt
		if(aesSumme == 1 && Datenmenge == 2500) 
		{
			CPUInformation.getLoadaverage("AES");
			Number number = new Number(3, 7, CPUInformation.load);
			Writer.sheetAES.addCell(number);
		}
		else if(aesSumme == 1 && Datenmenge == 5000) 
		{
			CPUInformation.getLoadaverage("AES");
			Number number = new Number(3, 23, CPUInformation.load);
			Writer.sheetAES .addCell(number);
		}
		else if(aesSumme == 1 && Datenmenge == 10000) 
		{
			CPUInformation.getLoadaverage("AES");
			Number number = new Number(3, 39, CPUInformation.load);
			Writer.sheetAES.addCell(number);
		}

		//in einer Schleife wird bis die entsprechende Datenmenge erreicht wurde Bytes generiert, verschlüsselt und entschlüsselt
		for (int zaehler = 0; zaehler <= Datenmenge; zaehler++) 
		{
			try {
				//byte array von 3000
				byte[] b = new byte[3000];
				//es werden in der festgelegten Byte-Arraylänge zufällige Bytes generiert
				new Random().nextBytes(b);
				b.toString();
				umwString = new String(b);
				//Zeichensatz wird festgelegt
				arrayToEncrypt = umwString.getBytes("UTF-8");
				//mit ecipher.doFinal wird der Datensatz verschlüsselt
				encryptedArray = ecipher.doFinal(arrayToEncrypt);
				//mit dcipher.doFinal wird der Datensatz entschlüsselt
				decryptedText = new String(dcipher.doFinal(encryptedArray));
			} catch (Exception e) 
			{
				System.err.println("Fehler bei AES! " + e);
				e.printStackTrace();
			}
			
		}
		
		/*
		 * die Methode einer anderen Klasse wird aufgerufen
		 * dafür wird der entsprechende Algorithmus und die Datenmenge übergeben, 
		 * damit die Zeit zugeordnet werden kann
		 */
		ct.consumedTime("AES");
		
		/*
		 * für die Berechnung der verbrauchten CPU-Zeit wird eine 
		 * weitere Methode aufgerufen, welches die CPU-Zeit ermittelt
		 * der Algorithmus wird wegen der Zuordnung auch übergeben
		 */
		ct.consumedCPUTime("AES", CPUInformation.getProcessCPUTimeAES());
		
		/*
		 * nach dem der gesamte Vorgang fertig ist werden die für den
		 * Prozess verbrauchten Kapazitäten ausgegeben
		 */
		if(Main.anzahlDurchgänge == aesSumme) 
		{
			CPUInformation.printCpuInfo("AES", Datenmenge);
			
			//die Zeiten werden auf Null gesetzt, um den nächsten Durchlauf genau berechnen zu können
			CPUInformation.dblProcessCpuTimeNsAES = 0;
			CPUInformation.dblProcessCpuTimeSAES = 0;
			CPUInformation.dblProcessCpuTimeNsAESCPU = 0;
			CPUInformation.dblProcessCpuTimeSAESCPU = 0;

			ct.runsAES = 0;
			ct.summeAES = 0;
			
			ct.runsAESCPU = 0;
			ct.summeCPUTimeRSA = 0;
		}
		
	}
	
	
}
