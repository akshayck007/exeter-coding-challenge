package TextTranslator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
public class Main 
{

	public static void main(String[] args) 
	{
		double startTime=System.currentTimeMillis();
		long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		String find_words_path = "C:\\Users\\user\\Desktop\\15sec\\french_dictionary.csv";
		String text_path = "C:\\Users\\user\\Desktop\\15sec\\t8.shakespeare.txt";
		String out_path = "C:\\Users\\user\\Desktop\\15sec\\Assignment\\t8.shakespeare.translated.txt";
		String perf_path = "C:\\Users\\user\\Desktop\\15sec\\Assignment\\performance.txt";
		String freq_path = "C:\\Users\\user\\Desktop\\15sec\\Assignment\\frequency.csv";

		
		TreeMap<String,String> dictionary = new TreeMap<String,String>();
		LinkedHashMap<String,Integer> frequency = new LinkedHashMap<String,Integer>();
	
		
		try 
		{
			BufferedReader fw_br=new BufferedReader(new FileReader(find_words_path));
			BufferedReader text_br=new BufferedReader(new FileReader(text_path));
			BufferedReader freq_br=new BufferedReader(new FileReader(text_path));
			
			BufferedWriter text_bw=new BufferedWriter(new FileWriter(out_path));
			BufferedWriter perf_bw=new BufferedWriter(new FileWriter(perf_path));
			BufferedWriter freq_bw=new BufferedWriter(new FileWriter(freq_path));
			
			
			//creating dictionary
			String line = "";
			while( (line=fw_br.readLine()) !=null )
			{
				String[] words= line.split(",");
				dictionary.put(words[0],words[1]);
			}
					
			
			//init freq map
			
			for(Map.Entry<String,String> entry: dictionary.entrySet())
			{
				
				frequency.put(entry.getKey(), 0);

			}
//			
			
			//freq map
			
			String newLine="";
			while((newLine=freq_br.readLine())!=null)
			{
				String[] words = newLine.split("\\b");
				
				for(String s:words)
				{
					String word=s.toLowerCase();
					Integer val=frequency.get(word);
					if(dictionary.containsKey(word))
					{
						if(val==0)
						{
							frequency.put(word, 1);
						}
						else
						{
							frequency.put(word, val+1);
						}
					}
				}
			}
				

			for(Map.Entry<String,Integer> entry: frequency.entrySet())
			{
				
				freq_bw.write(entry.getKey()+","+entry.getValue());
				freq_bw.newLine();

			}
				
			
			
			
			

////			while( (line=text_br.readLine()) !=null )
////			{
////				newText+=line+"\n";
////				
////			}
			
			
			
//			
//			String newText="";
//			newText=Files.readString(Path.of(text_path));
//			for(Map.Entry<String,String> entry: dictionary.entrySet())
//			{
//				
//				newText=newText.replaceAll(("(?i)\\b"+entry.getKey())+"\\b",(entry.getValue()));
//
//			}
//			text_bw.write(newText);
			
			
			
			String transLine="";
			while((transLine=text_br.readLine())!=null)
			{
				String thisLine=transLine;
				String[] words = thisLine.split("\\b");
				for(String s: words)
				{
					if(dictionary.containsKey(s.toLowerCase()))
					{
						thisLine=thisLine.replaceAll(s,dictionary.get(s.toLowerCase()));
					}
				}
				text_bw.write(thisLine+"\n");
				
			}
			
			
			
			
			long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
			long actualMemUsed=afterUsedMem-beforeUsedMem;
			double stopTime=System.currentTimeMillis();
			double exeTime=(stopTime-startTime)/1000;
			String exeTimeW="Time to process: "+Math.round((exeTime/60)%60)+" Minutes "+Math.round(exeTime%60)+" Seconds \n";
			String exeMemW="Memory used: "+(actualMemUsed/((long)(1024*1024))+" MB");
			String perf = exeTimeW+""+exeMemW;
			System.out.println(perf);
			perf_bw.write(perf);
			
			fw_br.close();
			text_bw.close();
			text_br.close();
			perf_bw.close();
			freq_br.close();
			freq_bw.close();
		} 
		
		
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}

}