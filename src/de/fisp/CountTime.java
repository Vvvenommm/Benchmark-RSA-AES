package de.fisp;

public class CountTime 
{

	public static double startTime, diff, summeAES, summeRSA, runsAES, runsRSA, averageAES, averageRSA,
						summeCPUTimeRSA, summeCPUTimeAES;
	
	public static double[] zeitenArrayAES = new double[25], zeitenArrayRSA = new double[25], zeitenArrayAESCPU = new double[25], zeitenArrayRSACPU = new double[25];
	
	
	public static int zaehlerAES = 0, zaehlerRSA = 0, zaehlerAESCPU = 0, zaehlerRSACPU = 0;
	

	public int runsRSACPU, runsAESCPU;

	
	public void startTime() 
	{
		startTime = System.nanoTime();
	}
	
	public void consumedTime(String verfahren) 
	{
		diff = ((System.nanoTime() - startTime) / 1000000000);
		
		if (verfahren == "AES") 
		{
			runsAES++;
			summeAES += diff;
			
			zeitenArrayAES[zaehlerAES] = diff;
			zaehlerAES++;
		} else if(verfahren == "RSA") 
		{
			runsRSA++;
			summeRSA += diff;
			
			zeitenArrayRSA[zaehlerRSA] = diff;
			zaehlerRSA++;
		}

	}
	
	public void consumedCPUTime(String verfahren, double cpuTime) 
	{
		
		if(verfahren == "RSA") 
		{
			runsRSACPU++;
			summeCPUTimeRSA += cpuTime;
			
			zeitenArrayRSACPU[zaehlerRSACPU] = cpuTime;
			zaehlerRSACPU++;
		}
		else if (verfahren == "AES") 
		{
			runsAESCPU++;
			summeCPUTimeAES += cpuTime;
			
			zeitenArrayAESCPU[zaehlerAESCPU] = cpuTime;
			zaehlerAESCPU++;
		}
	}

	public static double averageTimeAES() 
	{
		averageAES = (summeAES / runsAES);
		return averageAES;
	}
	
	public static double averageTimeRSA() 
	{
		averageRSA = (summeRSA / runsRSA);
		return averageRSA;
	}
	
	
	public static double minAES() 
	{
		double minAES = zeitenArrayAES[0];
		for (int zaehler = 1; zaehler < zeitenArrayAES.length; zaehler++) 
		{
			if(zeitenArrayAES[zaehler]<minAES) 
			{
				minAES=zeitenArrayAES[zaehler];
			}
		}
		return minAES;
	}
	
	public static double minRSA() 
	{
		double minRSA = zeitenArrayRSA[0];
		for (int zaehler = 1; zaehler < zeitenArrayRSA.length; zaehler++) 
		{
			if(zeitenArrayRSA[zaehler]<minRSA) 
			{
				minRSA=zeitenArrayRSA[zaehler];
			}
		}
		return minRSA;
	}
	
	public static double maxAES() 
	{
		double maxAES = 0;
		for (int zaehler = 1; zaehler < zeitenArrayAES.length; zaehler++) 
		{
			if(zeitenArrayAES[zaehler]>maxAES) 
			{
				maxAES=zeitenArrayAES[zaehler];
			}
		}
		return maxAES;
	}
	
	public static double maxRSA() 
	{
		double maxRSA = 0;
		for (int zaehler = 1; zaehler < zeitenArrayRSA.length; zaehler++) 
		{
			if(zeitenArrayRSA[zaehler]>maxRSA) 
			{
				maxRSA=zeitenArrayRSA[zaehler];
			}
		}
		return maxRSA;
	}
	

	public static double minCPUTimeAES() 
	{
		double minCPUTimeAES = zeitenArrayAESCPU[0];
		
		
		for (int zaehler = 1; zaehler < zeitenArrayAESCPU.length; zaehler++) 
		{
			if(zeitenArrayAESCPU[zaehler]<minCPUTimeAES) 
			{
				minCPUTimeAES=zeitenArrayAESCPU[zaehler];
			}
		}
		return minCPUTimeAES;
	}
	
	public static double minCPUTimeRSA() 
	{
		double minCPUTimeRSA = zeitenArrayRSACPU[0];
		for (int zaehler = 1; zaehler < zeitenArrayRSACPU.length; zaehler++) 
		{
			if(zeitenArrayRSACPU[zaehler]<minCPUTimeRSA) 
			{
				minCPUTimeRSA=zeitenArrayRSACPU[zaehler];
			}
		}
		return minCPUTimeRSA;
	}
	
	public static double maxCPUTimeRSA() 
	{
		double maxCPUTimeRSA = 0;
		for (int zaehler = 1; zaehler < zeitenArrayRSACPU.length; zaehler++) 
		{
			if(zeitenArrayRSACPU[zaehler]>maxCPUTimeRSA) 
			{
				maxCPUTimeRSA=zeitenArrayRSACPU[zaehler];
			}
		}
		return maxCPUTimeRSA;
	}
	
	public static double maxCPUTimeAES() 
	{
		double maxCPUTimeAES = 0;
		for (int zaehler = 1; zaehler < zeitenArrayAESCPU.length; zaehler++) 
		{
			if(zeitenArrayAESCPU[zaehler]>maxCPUTimeAES) 
			{
				maxCPUTimeAES=zeitenArrayAESCPU[zaehler];
			}
		}
		return maxCPUTimeAES;
	}
	
}
