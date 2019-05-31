package de.fisp;

import jxl.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.*;

import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

public class Writer 
{
	
	//Excel-Datei Verarbeitung
	
	private static String filename;
	
	public static WritableWorkbook workbook;
	public static WritableSheet sheetRSA, sheetAES;
	
	public static Label rsa125, rsa250, rsa500,
						 aes2500, aes5000, aes10000,
						 ohneParameterRSA, ohneParameterAES,
						 zeitenMinMaxRSA, zeitenMinMaxAES,
						 xms128RSA, xms128AES,
						 xms256RSA, xms256AES,
						 xms512RSA, xms512AES,
						 xms1024RSA, xms1024AES,
						 loadAverageBeginnRSA, loadAverageBeginnAES, 
						 processCPULoadRSA, processCPULoadAES,
						 systemCPULoadRSA, systemCPULoadAES,
						 loadAverageRSA, loadAverageAES,
						 cpuZeitRSA, cpuZeitAES,
						 averageCPUZeitRSA, averageCPUZeitAES,
						 averageRuntimeRSA, averageRuntimeAES;
	
	public static Label cpuZeitMinRSA, cpuZeitMinAES, cpuZeitMaxRSA, cpuZeitMaxAES,
						runtimeMinRSA, runtimeMinAES, runtimeMaxRSA, runtimeMaxAES;
	
	
	public static WritableCell loadAverageBeginnRSA1, loadAverageBeginnAES1, 
	 							processCPULoadRSA1, processCPULoadAES1,
	 							systemCPULoadRSA1, systemCPULoadAES1,
	 							loadAverageRSA1, loadAverageAES1,
	 							cpuZeitRSA1, cpuZeitAES1,
	 							averageCPUZeitRSA1, averageCPUZeitAES1,
	 							averageRuntimeRSA1, averageRuntimeAES1,
	 							loadAverageBeginnRSA2, loadAverageBeginnAES2, 
	 							processCPULoadRSA2, processCPULoadAES2,
	 							systemCPULoadRSA2, systemCPULoadAES2,
	 							loadAverageRSA2, loadAverageAES2,
	 							cpuZeitRSA2, cpuZeitAES2,
	 							averageCPUZeitRSA2, averageCPUZeitAES2,
	 							averageRuntimeRSA2, averageRuntimeAES2;
	
	public static WritableCell  cpuZeitMinRSA1, cpuZeitMinAES1, cpuZeitMaxRSA1, cpuZeitMaxAES1,
								runtimeMinRSA1, runtimeMinAES1, runtimeMaxRSA1, runtimeMaxAES1,
								cpuZeitMinRSA2, cpuZeitMinAES2, cpuZeitMaxRSA2, cpuZeitMaxAES2,
								runtimeMinRSA2, runtimeMinAES2, runtimeMaxRSA2, runtimeMaxAES2;
	
	
	public static Label testcell;
	
	public Writer() throws IOException, RowsExceededException, WriteException 
	{
		try 
		{
			filename = "AuswertungAnwendung1.xls";
			
			workbook = Workbook.createWorkbook(new File(filename));
			sheetRSA = workbook.createSheet("RSA", 0);
			sheetAES = workbook.createSheet("AES", 1);
			
			//RSA
			
			//Überschriften für die Labels x-Achse
			rsa125 = new Label(1, 5, "125 Daten");
			rsa250 = new Label(1, 21, "250 Daten");
			rsa500 = new Label(1, 37, "500 Daten");

			
			ohneParameterRSA = new Label(3, 5, "Ohne Parameter");
			zeitenMinMaxRSA = new Label(5, 5, "Zeiten Min/Max");
			cpuZeitMinRSA = new Label(5, 12, "Minimum CPU-Zeit");
			cpuZeitMaxRSA = new Label(5, 13, "Maximum CPU-Zeit");
			runtimeMinRSA = new Label(5, 14, "Minimum Laufzeit");
			runtimeMaxRSA = new Label(5, 15, "Maximum Laufzeit");
		
			
			//Überschriften für die Labels y-Achse - RSA
			loadAverageBeginnRSA = new Label(0, 7, "Load-Average Beginn:");
			processCPULoadRSA = new Label(0, 9, "Process-CPU Load:");
			systemCPULoadRSA = new Label(0, 11, "System-CPU Load:");
			loadAverageRSA = new Label(0, 13, "Load-Average:");
			cpuZeitRSA = new Label(0, 15, "CPU-Zeit:");
			averageCPUZeitRSA = new Label(0, 17, "Durchschnitt CPU-Zeit:");
			averageRuntimeRSA = new Label(0, 19, "Durchscnitt Laufzeit:");
			
			//AES
			aes2500 = new Label(1, 5, "2500 Daten");
			aes5000 = new Label(1, 21, "5000 Daten");
			aes10000 = new Label(1, 37, "10000 Daten");
			
			//Überschriften für die Labels x-Achse
			ohneParameterAES = new Label(3, 5, "Ohne Parameter");
			zeitenMinMaxAES = new Label(5, 5, "Zeiten Min/Max");
			cpuZeitMinAES = new Label(5, 12, "Minimum CPU-Zeit");
			cpuZeitMaxAES = new Label(5, 13, "Maximum CPU-Zeit");
			runtimeMinAES = new Label(5, 14, "Minimum Laufzeit");
			runtimeMaxAES = new Label(5, 15, "Maximum Laufzeit");
			
			//Ãœberschriften fÃ¼r die Labels y-Achse - AES
			loadAverageBeginnAES = new Label(0, 7, "Load-Average Beginn:");
			processCPULoadAES = new Label(0, 9, "Process-CPU Load:");
			systemCPULoadAES = new Label(0, 11, "System-CPU Load:");
			loadAverageAES = new Label(0, 13, "Load-Average:");
			cpuZeitAES = new Label(0, 15, "CPU-Zeit:");
			averageCPUZeitAES = new Label(0, 17, "Durchschnitt CPU-Zeit:");
			averageRuntimeAES = new Label(0, 19, "Durchscnitt Laufzeit:");
			
			
			sheetRSA.addCell(rsa125);
			sheetRSA.addCell(rsa250);
			sheetRSA.addCell(rsa500);
			
			sheetAES.addCell(aes2500);
			sheetAES.addCell(aes5000);
			sheetAES.addCell(aes10000);
			
			sheetRSA.addCell(ohneParameterRSA);
			sheetRSA.addCell(zeitenMinMaxRSA);
			sheetRSA.addCell(cpuZeitMinRSA);
			sheetRSA.addCell(cpuZeitMaxRSA);
			sheetRSA.addCell(runtimeMinRSA);
			sheetRSA.addCell(runtimeMaxRSA);
			
			sheetRSA.addCell(loadAverageBeginnRSA);
			sheetRSA.addCell(processCPULoadRSA);
			sheetRSA.addCell(systemCPULoadRSA);
			sheetRSA.addCell(loadAverageRSA);
			sheetRSA.addCell(cpuZeitRSA);
			sheetRSA.addCell(averageCPUZeitRSA);
			sheetRSA.addCell(averageRuntimeRSA);
			
			sheetAES.addCell(ohneParameterAES);
			sheetAES.addCell(zeitenMinMaxAES);
			sheetAES.addCell(cpuZeitMinAES);
			sheetAES.addCell(cpuZeitMaxAES);
			sheetAES.addCell(runtimeMinAES);
			sheetAES.addCell(runtimeMaxAES);
			
			sheetAES.addCell(loadAverageBeginnAES);
			sheetAES.addCell(processCPULoadAES);
			sheetAES.addCell(systemCPULoadAES);
			sheetAES.addCell(loadAverageAES);
			sheetAES.addCell(cpuZeitAES);
			sheetAES.addCell(averageCPUZeitAES);
			sheetAES.addCell(averageRuntimeAES);
			
			cpuZeitMaxRSA1 = cpuZeitMaxRSA.copyTo(5, 29);
			cpuZeitMaxRSA2 = cpuZeitMaxRSA.copyTo(5, 45);
			cpuZeitMaxAES1 = cpuZeitMaxAES.copyTo(5, 29);
			cpuZeitMaxAES2 = cpuZeitMaxAES.copyTo(5, 45);
			
			cpuZeitMinRSA1 = cpuZeitMinRSA.copyTo(5, 28);
			cpuZeitMinRSA2 = cpuZeitMinRSA.copyTo(5, 44);
			cpuZeitMinAES1 = cpuZeitMinAES.copyTo(5, 28);
			cpuZeitMinAES2 = cpuZeitMinAES.copyTo(5, 44);
			
			runtimeMaxRSA1 = runtimeMaxRSA.copyTo(5, 31);
			runtimeMaxRSA2 = runtimeMaxRSA.copyTo(5, 47);
			runtimeMaxAES1 = runtimeMaxAES.copyTo(5, 31);
			runtimeMaxAES2 = runtimeMaxAES.copyTo(5, 47);
			
			runtimeMinRSA1 = runtimeMinRSA.copyTo(5, 30);
			runtimeMinRSA2 = runtimeMinRSA.copyTo(5, 46);
			runtimeMinAES1 = runtimeMinAES.copyTo(5, 30);
			runtimeMinAES2 = runtimeMinAES.copyTo(5, 46);
			
			loadAverageBeginnRSA1 = loadAverageBeginnRSA.copyTo(0, 23);
			loadAverageBeginnRSA2 = loadAverageBeginnRSA.copyTo(0, 39);
			loadAverageBeginnAES1 = loadAverageBeginnAES.copyTo(0, 23);
			loadAverageBeginnAES2 = loadAverageBeginnAES.copyTo(0, 39);
			
			processCPULoadRSA1 = processCPULoadRSA.copyTo(0, 25);
			processCPULoadRSA2 = processCPULoadRSA.copyTo(0, 41);
			processCPULoadAES1 = processCPULoadAES.copyTo(0, 25);
			processCPULoadAES2 = processCPULoadAES.copyTo(0, 41);
			
			systemCPULoadRSA1 = systemCPULoadRSA.copyTo(0, 27);
			systemCPULoadRSA2 = systemCPULoadRSA.copyTo(0, 43);
			systemCPULoadAES1 = systemCPULoadAES.copyTo(0, 27);
			systemCPULoadAES2 = systemCPULoadAES.copyTo(0, 43);
			
			loadAverageRSA1 = loadAverageRSA.copyTo(0, 29);
			loadAverageRSA2 = loadAverageRSA.copyTo(0, 45);
			loadAverageAES1 = loadAverageAES.copyTo(0, 29);
			loadAverageAES2 = loadAverageAES.copyTo(0, 45);
			
			cpuZeitRSA1 = cpuZeitRSA.copyTo(0, 31);
			cpuZeitRSA2 = cpuZeitRSA.copyTo(0, 47);
			cpuZeitAES1 = cpuZeitAES.copyTo(0, 31);
			cpuZeitAES2 = cpuZeitAES.copyTo(0, 47);
			
			averageCPUZeitRSA1 = averageCPUZeitRSA.copyTo(0, 33);
			averageCPUZeitRSA2 = averageCPUZeitRSA.copyTo(0, 49);
			averageCPUZeitAES1 = averageCPUZeitAES.copyTo(0, 33);
			averageCPUZeitAES2 = averageCPUZeitAES.copyTo(0, 49);
			
			averageRuntimeRSA1 = averageRuntimeRSA.copyTo(0, 35);
			averageRuntimeRSA2 = averageRuntimeRSA.copyTo(0, 51);
			averageRuntimeAES1 = averageRuntimeRSA.copyTo(0, 35);
			averageRuntimeAES2 = averageRuntimeRSA.copyTo(0, 51);
			
			
			sheetRSA.addCell(loadAverageBeginnRSA1);
			sheetRSA.addCell(loadAverageBeginnRSA2);
			sheetRSA.addCell(processCPULoadRSA1);
			sheetRSA.addCell(processCPULoadRSA2);
			sheetRSA.addCell(systemCPULoadRSA1);
			sheetRSA.addCell(systemCPULoadRSA2);
			sheetRSA.addCell(loadAverageRSA1);
			sheetRSA.addCell(loadAverageRSA2);
			sheetRSA.addCell(cpuZeitRSA1);
			sheetRSA.addCell(cpuZeitRSA2);
			sheetRSA.addCell(averageCPUZeitRSA1);
			sheetRSA.addCell(averageCPUZeitRSA2);
			sheetRSA.addCell(averageRuntimeRSA1);
			sheetRSA.addCell(averageRuntimeRSA2);
			
			sheetAES.addCell(loadAverageBeginnAES1);
			sheetAES.addCell(loadAverageBeginnAES2);
			sheetAES.addCell(processCPULoadAES1);
			sheetAES.addCell(processCPULoadAES2);
			sheetAES.addCell(systemCPULoadAES1);
			sheetAES.addCell(systemCPULoadAES2);
			sheetAES.addCell(loadAverageAES1);
			sheetAES.addCell(loadAverageAES2);
			sheetAES.addCell(cpuZeitAES1);
			sheetAES.addCell(cpuZeitAES2);
			sheetAES.addCell(averageCPUZeitAES1);
			sheetAES.addCell(averageCPUZeitAES2);
			sheetAES.addCell(averageRuntimeAES1);
			sheetAES.addCell(averageRuntimeAES2);
			
			sheetRSA.addCell(cpuZeitMaxRSA1);
			sheetRSA.addCell(cpuZeitMaxRSA2);
			sheetAES.addCell(cpuZeitMaxAES1);
			sheetAES.addCell(cpuZeitMaxAES2);
			
			sheetRSA.addCell(cpuZeitMinRSA1);
			sheetRSA.addCell(cpuZeitMinRSA2);
			sheetAES.addCell(cpuZeitMinAES1);
			sheetAES.addCell(cpuZeitMinAES2);
			
			sheetRSA.addCell(runtimeMaxRSA1);
			sheetRSA.addCell(runtimeMaxRSA2);
			sheetAES.addCell(runtimeMaxAES1);
			sheetAES.addCell(runtimeMaxAES2);
			
			sheetRSA.addCell(runtimeMinRSA1);
			sheetRSA.addCell(runtimeMinRSA2);
			sheetAES.addCell(runtimeMinAES1);
			sheetAES.addCell(runtimeMinAES2);
			
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public static void writer() throws RowsExceededException, WriteException, IOException 
	{
		workbook.write();
		workbook.close();
	}
}
