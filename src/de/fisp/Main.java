package de.fisp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.crypto.NoSuchPaddingException;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Main 
{
	private static CPUInformation cpu = new CPUInformation();
	private static CountTime ct = new CountTime();
	private static RSAEncryption rsa;
	private static AESEncryption aes;
	private static Writer write;
	private static FileOutputStream f;
	
	//Wiederholungsmenge der Ver- bzw. Entschlüsselung
	public static int anzahlDurchgänge = 25;
	
	//Menge der Daten die erzeugt werden sollen
	private static int DatenRSA = 125;
	private static int DatenAES = 2500;
	
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InterruptedException, RowsExceededException, WriteException, IOException 
	{
		f = new FileOutputStream("AusgabeAnwendung1.txt");
		System.setOut(new PrintStream(f));
		write = new Writer();
		aes = new AESEncryption();
		rsa = new RSAEncryption();
		
		for (int zaehler = 0; zaehler < 3; zaehler++) 
		{
			//damit die Threads parallel laufen können, wird der ExecuuterService verwendet
			ExecutorService es = Executors.newFixedThreadPool(2);
			
			rsaencrypt t1 = new rsaencrypt();
			aesencrypt t2 = new aesencrypt();
			es.execute(t1);
			es.execute(t2);
			es.shutdown();
			while(!es.isTerminated()) 
			{
			}
			
			DatenRSA = DatenRSA * 2;
			RSAEncryption.rsaSumme = 0;
			
			DatenAES = DatenAES * 2;
			AESEncryption.aesSumme = 0;
			
			//die Zähler werden auf Null gesetzt, damit der nächste Durchlauf genau berechnet werden kann
			CountTime.zaehlerAES = 0;
			CountTime.zaehlerRSA = 0;
			CountTime.zaehlerAESCPU = 0;
			CountTime.zaehlerRSACPU = 0;
			
			CPUInformation.zaehlerAESCPU = 0;
			CPUInformation.zaehlerRSACPU = 0;
			
			//Pause für 60 Sekunden, um eine vergleichbare Basis herzustellen
			//ansonsten fließt der Load Average Wert vom abgelaufenen Prozess mit ein
			Thread.sleep(60000);
		}
		
		//die ermittelten Werte sollen in die Excel Datei geschrieben werden
		write.writer();

	}
	
	static class rsaencrypt extends Thread  
	{
		public void run() 
		{
			for (int i = 0; i < anzahlDurchgänge; i++) 
			{
				try {
					rsa.encrypt(DatenRSA);
				} catch (Exception e) {
					System.err.println("Fehler beim Aufruf der encrypt - Methode in der " + rsa.getClass() + " " + e);
					e.printStackTrace();
				}
			}

		}

	}
	
	static class aesencrypt extends Thread 
	{
		public void run() 
		{
			for (int i = 0; i < anzahlDurchgänge; i++) 
			{
				try 
				{
					aes.encrypt(DatenAES);
				} catch (Exception e) {
					System.err.println("Fehler beim Aufruf der encrypt - Methode in der " + aes.getClass() + " " + e);
					e.printStackTrace();
				}
			}

		}
	}

}
