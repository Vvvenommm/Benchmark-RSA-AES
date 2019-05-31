package de.fisp;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import javax.rmi.ssl.SslRMIClientSocketFactory;

// Java Build path -> Libraries -> Access Rules -> Access (com/sun/management/OperatingSystemMXBean)
import com.sun.management.OperatingSystemMXBean;
import jxl.*;

import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
//import de.calc.prime.*;

//cat /proc/cpuinfo

public class CPUInformation {
	
	public static CountTime ct = new CountTime();
	
	public static double dblProcessCpuTimeNsRSA, dblProcessCpuTimeSRSA, dblProcessCpuTimeNsAES, dblProcessCpuTimeSAES,
						 dblProcessCpuTimeSRSACPU, dblProcessCpuTimeNsRSACPU, dblProcessCpuTimeSAESCPU, dblProcessCpuTimeNsAESCPU;
	
	private static double zwischenFeldRSA = 0, zwischenFeldAES = 0, zwischenFeldCPURSA = 0, zwischenFeldCPUAES = 0;
						  
	public static int zaehlerRSA = 0, zaehlerAES = 0, zaehlerAESCPU = 0, zaehlerRSACPU = 0;
	public static double load, processCPUTime;
	
	private static double[] zeitRSA = new double [10], zeitAES = new double [10], zeitRSACPU = new double[25], zeitAESCPU = new double[25];
	
	public static OperatingSystemMXBean mbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	
	
	public static void printMemoryInfo()
	{
		Runtime rt = Runtime.getRuntime();
		DecimalFormat df = new DecimalFormat( "###,##0" );
		
		// Gesamter physikalischer Arbeitsspeicher
		long lngTotMemSizeByte = mbean.getTotalPhysicalMemorySize();
		// Freier physikalischer Arbeitsspeicher
		long lngFreMemSizeByte = mbean.getFreePhysicalMemorySize();
		// VerfÃ¼gbarer Arbeitsspeicher (-Xmx für maximalen Heap Size)
		long lngMaxMemSizeByte = rt.maxMemory();
		// Reservierter Arbeitsspeicher (-Xms für initialen Arbeitsspeicher)
		long lngResMemSizeByte = rt.totalMemory();
		// Aktuell genutzter Arbeitsspeicher
		long lngUseMemSizeByte = rt.totalMemory() - rt.freeMemory();		

		System.out.println("================ Arbeitsspeicher ===========================");
		System.out.println("   ================= System =============================");
		System.out.println("Gesamter Arbeitsspeicher:     " + df.format(lngTotMemSizeByte) + " Byte");
		System.out.println("Freier Arbeitsspeicher:       " + df.format(lngFreMemSizeByte) + " Byte");

		System.out.println("   ================= VM =================================");		
		System.out.println("VerfÃ¼gbarer Arbeitsspeicher:  " + df.format(lngMaxMemSizeByte) + " Byte");
		System.out.println("Reservierter Arbeitsspeicher: " + df.format(lngResMemSizeByte) + " Byte");
		System.out.println("Verbrauchter Arbeitsspeicher: " + df.format(lngUseMemSizeByte) + " Byte");
	}

	public static void printCpuInfo(String verfahren, int Datenmenge) throws RowsExceededException, WriteException, FileNotFoundException
	{
		switch(verfahren) 
		{
			case "RSA":
				
				if(zaehlerRSA == 0) 
				{
					zeitRSA[zaehlerRSA] = mbean.getProcessCpuTime();					
					zwischenFeldRSA = zeitRSA[zaehlerRSA];
					dblProcessCpuTimeNsRSA = zwischenFeldRSA;
					zaehlerRSA++;
				} 
				else if (zaehlerRSA != 0) 
				{
					zeitRSA[zaehlerRSA] = mbean.getProcessCpuTime();
					for (int i = 0; i < zaehlerRSA; i++) 
					{				
						zeitRSA[zaehlerRSA] -= zeitRSA[i];	
						dblProcessCpuTimeNsRSA = zeitRSA[zaehlerRSA];
						zwischenFeldRSA = dblProcessCpuTimeNsRSA;
					}
					zaehlerRSA++;
					
				}
				
				dblProcessCpuTimeSRSA = dblProcessCpuTimeNsRSA / 1000000000d;
				
				
			case "AES":
				
				if(zaehlerAES == 0) 
				{
					zeitAES[zaehlerAES] = mbean.getProcessCpuTime();
					zwischenFeldAES = zeitAES[zaehlerAES];
					dblProcessCpuTimeNsAES = zwischenFeldAES;
					zaehlerAES++;
				} 
				else if (zaehlerAES != 0) 
				{
					zeitAES[zaehlerAES] = mbean.getProcessCpuTime();	
					for (int i = 0; i < zaehlerAES; i++) 
					{				
						zeitAES[zaehlerAES] -= zeitAES[i];	
						dblProcessCpuTimeNsAES = zeitAES[zaehlerAES];
						zwischenFeldAES = dblProcessCpuTimeNsAES;
					}
					zaehlerAES++;
					
				}
				
				dblProcessCpuTimeSAES = dblProcessCpuTimeNsAES / 1000000000d;
				
		}
				
		
		if(verfahren == "RSA") 
		{
			System.out.println();
			System.out.println("===============================================================");
			System.out.println("============ RSA - Encryption - CPU - Info ====================");
			System.out.println("================== " + Datenmenge + " Daten ===================");
		}
		else if(verfahren == "AES") 
		{
			System.out.println();
			System.out.println("================================================================");
			System.out.println("============ AES - Encryption - CPU - Info =====================");
			System.out.println("================== " + Datenmenge + " Daten ===================");
		}
//		System.out.println("================ Prozessor =================================");
//		System.out.println("   ================= System =============================");
//		System.out.println("Name:                         " + mbean.getName());
//		System.out.println("Architektur:                  " + mbean.getArch()); 
//		System.out.println("OS:                           " + mbean.getVersion());
//		System.out.println("Anzahl CPUs:                  " + mbean.getAvailableProcessors());
		
		
		//je nach Datenmenge, werden die ermittelten Werte zugeordnet
		if(verfahren == "RSA" && Datenmenge == 125) 
		{
			double load125 = mbean.getSystemLoadAverage();
			Number number = new Number(3, 13, load125);
			Writer.sheetRSA.addCell(number);
			System.out.println();
			printMemoryInfo();
			System.out.println();
			System.out.println("   ================= VM =================================");
			System.out.println("Prozess CPU Load:             " + String.format("%1.12f", mbean.getProcessCpuLoad()));
			System.out.println("System  CPU Load:             " + String.format("%1.12f", mbean.getSystemCpuLoad()));
			Number number4 = new Number(3, 15, dblProcessCpuTimeSRSA);
			Writer.sheetRSA.addCell(number4);
			
			double processCpuTime = dblProcessCpuTimeSRSA = (dblProcessCpuTimeSRSA / Main.anzahlDurchgänge);
			Number number5 = new Number(3, 17, processCpuTime);
			Writer.sheetRSA.addCell(number5);
			
			Number number6 = new Number(3, 19, ct.averageTimeRSA());
			Writer.sheetRSA.addCell(number6);
			
			Number zeitMin = new Number(6, 14, ct.minRSA());
			Writer.sheetRSA.addCell(zeitMin);
			
			Number zeitMax = new Number(6, 15, ct.maxRSA());
			Writer.sheetRSA.addCell(zeitMax);
			
			Number zeitCPUMin = new Number(6, 12, ct.minCPUTimeRSA());
			Writer.sheetRSA.addCell(zeitCPUMin);
			
			Number zeitCPUMax = new Number(6, 13, ct.maxCPUTimeRSA());
			Writer.sheetRSA.addCell(zeitCPUMax);
		} 
		else if(verfahren == "RSA" && Datenmenge == 250) 
		{
			double load250 = mbean.getSystemLoadAverage();
			Number number = new Number(3, 29, load250);
			Writer.sheetRSA.addCell(number);
			System.out.println();
			printMemoryInfo();
			System.out.println();
			System.out.println("   ================= VM =================================");
			System.out.println("Prozess CPU Load:             " + String.format("%1.12f", mbean.getProcessCpuLoad()));
			System.out.println("System  CPU Load:             " + String.format("%1.12f", mbean.getSystemCpuLoad()));
			Number number4 = new Number(3, 31, dblProcessCpuTimeSRSA);
			Writer.sheetRSA.addCell(number4);
			
			double processCpuTime = dblProcessCpuTimeSRSA = (dblProcessCpuTimeSRSA / Main.anzahlDurchgänge);
			Number number5 = new Number(3, 33, processCpuTime);
			Writer.sheetRSA.addCell(number5);
			
			Number number6 = new Number(3, 35, ct.averageTimeRSA());
			Writer.sheetRSA.addCell(number6);
			
			Number zeitMin = new Number(6, 30, ct.minRSA());
			Writer.sheetRSA.addCell(zeitMin);
			
			Number zeitMax = new Number(6, 31, ct.maxRSA());
			Writer.sheetRSA.addCell(zeitMax);
			
			Number zeitCPUMin = new Number(6, 28, ct.minCPUTimeRSA());
			Writer.sheetRSA.addCell(zeitCPUMin);
			
			Number zeitCPUMax = new Number(6, 29, ct.maxCPUTimeRSA());
			Writer.sheetRSA.addCell(zeitCPUMax);
		}
		else if(verfahren == "RSA" && Datenmenge == 500) 
		{
			double load500 = mbean.getSystemLoadAverage();
			Number number = new Number(3, 45, load500);
			Writer.sheetRSA.addCell(number);
			System.out.println();
			printMemoryInfo();
			System.out.println();
			System.out.println("   ================= VM =================================");
			System.out.println("Prozess CPU Load:             " + String.format("%1.12f", mbean.getProcessCpuLoad()));
			System.out.println("System  CPU Load:             " + String.format("%1.12f", mbean.getSystemCpuLoad()));
			Number number4 = new Number(3, 47, dblProcessCpuTimeSRSA);
			Writer.sheetRSA.addCell(number4);
			
			double processCpuTime = dblProcessCpuTimeSRSA = (dblProcessCpuTimeSRSA / Main.anzahlDurchgänge);
			Number number5 = new Number(3, 49, processCpuTime);
			Writer.sheetRSA.addCell(number5);
			
			Number number6 = new Number(3, 51, ct.averageTimeRSA());
			Writer.sheetRSA.addCell(number6);
			
			Number zeitMin = new Number(6, 46, ct.minRSA());
			Writer.sheetRSA.addCell(zeitMin);
			
			Number zeitMax = new Number(6, 47, ct.maxRSA());
			Writer.sheetRSA.addCell(zeitMax);
			
			Number zeitCPUMin = new Number(6, 44, ct.minCPUTimeRSA());
			Writer.sheetRSA.addCell(zeitCPUMin);
			
			Number zeitCPUMax = new Number(6, 45, ct.maxCPUTimeRSA());
			Writer.sheetRSA.addCell(zeitCPUMax);
		}
		if(verfahren == "AES" && Datenmenge == 2500) 
		{
			double load2500 = mbean.getSystemLoadAverage();
			Number number = new Number(3, 13, load2500);
			Writer.sheetAES.addCell(number);
			System.out.println();
			printMemoryInfo();
			System.out.println();
			System.out.println("   ================= VM =================================");
			System.out.println("Prozess CPU Load:             " + String.format("%1.12f", mbean.getProcessCpuLoad()));
			System.out.println("System  CPU Load:             " + String.format("%1.12f", mbean.getSystemCpuLoad()));
			Number number4 = new Number(3, 15, dblProcessCpuTimeSAES);
			Writer.sheetAES.addCell(number4);
			
			double processCpuTime = dblProcessCpuTimeSAES = (dblProcessCpuTimeSAES / Main.anzahlDurchgänge);
			Number number5 = new Number(3, 17, processCpuTime);
			Writer.sheetAES.addCell(number5);
			
			Number number6 = new Number(3, 19, ct.averageTimeAES());
			Writer.sheetAES.addCell(number6);
			
			Number zeitMin = new Number(6, 14, ct.minAES());
			Writer.sheetAES.addCell(zeitMin);
			
			Number zeitMax = new Number(6, 15, ct.maxAES());
			Writer.sheetAES.addCell(zeitMax);
			
			Number zeitCPUMin = new Number(6, 12, ct.minCPUTimeAES());
			Writer.sheetAES.addCell(zeitCPUMin);
			
			Number zeitCPUMax = new Number(6, 13, ct.maxCPUTimeAES());
			Writer.sheetAES.addCell(zeitCPUMax);
		} 
		else if(verfahren == "AES" && Datenmenge == 5000) 
		{
			double load5000 = mbean.getSystemLoadAverage();
			Number number = new Number(3, 29, load5000);
			Writer.sheetAES.addCell(number);
			System.out.println();
			printMemoryInfo();
			System.out.println();
			System.out.println("   ================= VM =================================");
			System.out.println("Prozess CPU Load:             " + String.format("%1.12f", mbean.getProcessCpuLoad()));
			System.out.println("System  CPU Load:             " + String.format("%1.12f", mbean.getSystemCpuLoad()));
			Number number4 = new Number(3, 31, dblProcessCpuTimeSAES);
			Writer.sheetAES.addCell(number4);
			
			double processCpuTime = dblProcessCpuTimeSAES = (dblProcessCpuTimeSAES / Main.anzahlDurchgänge);
			Number number5 = new Number(3, 33, processCpuTime);
			Writer.sheetAES.addCell(number5);
			
			Number number6 = new Number(3, 35, ct.averageTimeAES());
			Writer.sheetAES.addCell(number6);
			
			Number zeitMin = new Number(6, 30, ct.minAES());
			Writer.sheetAES.addCell(zeitMin);
			
			Number zeitMax = new Number(6, 31, ct.maxAES());
			Writer.sheetAES.addCell(zeitMax);
			
			Number zeitCPUMin = new Number(6, 28, ct.minCPUTimeAES());
			Writer.sheetAES.addCell(zeitCPUMin);
			
			Number zeitCPUMax = new Number(6, 29, ct.maxCPUTimeAES());
			Writer.sheetAES.addCell(zeitCPUMax);

		}
		else if(verfahren == "AES" && Datenmenge == 10000) 
		{
			double load10000 = mbean.getSystemLoadAverage();
			Number number = new Number(3, 45, load10000);
			Writer.sheetAES.addCell(number);
			System.out.println();
			printMemoryInfo();
			System.out.println();
			System.out.println("   ================= VM =================================");
			System.out.println("Prozess CPU Load:             " + String.format("%1.12f", mbean.getProcessCpuLoad()));
			System.out.println("System  CPU Load:             " + String.format("%1.12f", mbean.getSystemCpuLoad()));
			Number number4 = new Number(3, 47, dblProcessCpuTimeSAES);
			Writer.sheetAES.addCell(number4);
			
			double processCpuTime = dblProcessCpuTimeSAES = (dblProcessCpuTimeSAES / Main.anzahlDurchgänge);
			Number number5 = new Number(3, 49, processCpuTime);
			Writer.sheetAES.addCell(number5);
			
			Number number6 = new Number(3, 51, ct.averageTimeAES());
			Writer.sheetAES.addCell(number6);
			
			Number zeitMin = new Number(6, 46, ct.minAES());
			Writer.sheetAES.addCell(zeitMin);
			
			Number zeitMax = new Number(6, 47, ct.maxAES());
			Writer.sheetAES.addCell(zeitMax);
			
			Number zeitCPUMin = new Number(6, 44, ct.minCPUTimeAES());
			Writer.sheetAES.addCell(zeitCPUMin);
			
			Number zeitCPUMax = new Number(6, 45, ct.maxCPUTimeAES());
			Writer.sheetAES.addCell(zeitCPUMax);
		}

	}
	
	public static void getLoadaverage(String verfahren) 
	{
		load = mbean.getSystemLoadAverage();
	}
	
	public static double getProcessCPUTimeRSA() 
	{
						
				if(zaehlerRSACPU == 0) 
				{
					zeitRSACPU[zaehlerRSACPU] = mbean.getProcessCpuTime();					
					zwischenFeldCPURSA = zeitRSACPU[zaehlerRSACPU];
					dblProcessCpuTimeNsRSACPU = zwischenFeldCPURSA;
					zaehlerRSACPU++;
				} 
				else if (zaehlerRSACPU != 0) 
				{
					zeitRSACPU[zaehlerRSACPU] = mbean.getProcessCpuTime();
					for (int i = 0; i < zaehlerRSACPU; i++) 
					{				
						zeitRSACPU[zaehlerRSACPU] -= zeitRSACPU[i];	
						dblProcessCpuTimeNsRSACPU = zeitRSACPU[zaehlerRSACPU];
						zwischenFeldCPURSA = dblProcessCpuTimeNsRSACPU;
					}
					zaehlerRSACPU++;
			
				}
			return dblProcessCpuTimeSRSACPU = dblProcessCpuTimeNsRSACPU / 1000000000d;
		}
		
	public static double getProcessCPUTimeAES() 
		{
				
				if(zaehlerAESCPU == 0) 
				{
					zeitAESCPU[zaehlerAESCPU] = mbean.getProcessCpuTime();					
					zwischenFeldCPUAES = zeitAESCPU[zaehlerAESCPU];
					dblProcessCpuTimeNsAESCPU = zwischenFeldCPUAES;
					zaehlerAESCPU++;
				} 
				else if (zaehlerAESCPU != 0) 
				{
					zeitAESCPU[zaehlerAESCPU] = mbean.getProcessCpuTime();
					for (int i = 0; i < zaehlerAESCPU; i++) 
					{				
						zeitAESCPU[zaehlerAESCPU] -= zeitAESCPU[i];	
						dblProcessCpuTimeNsAESCPU = zeitAESCPU[zaehlerAESCPU];
						zwischenFeldCPUAES = dblProcessCpuTimeNsAESCPU;
					}
					zaehlerAESCPU++;
			
				}
			return dblProcessCpuTimeSAESCPU = dblProcessCpuTimeNsAESCPU / 1000000000d;
		}
		
	

}
