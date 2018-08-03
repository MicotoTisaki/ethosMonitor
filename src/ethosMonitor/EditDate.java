package ethosMonitor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class EditDate {
	private ArrayList<String> profile = new ArrayList<String>();
	static ObjectMapper mapper = new ObjectMapper();
	static JsonNode mainAPI = null;
	static String jsonStr = null;
	static ArrayList<String> rigNames = new ArrayList<String>();
	static ArrayList<String[]> gpuTemp = new ArrayList<String[]>();
	static ArrayList<ArrayList<String>> gpuName = new ArrayList<ArrayList<String>>();
	static ArrayList<String[]> hashrate = new ArrayList<String[]>();
	
	
	public EditDate getDate (ArrayList<String> profile) {
		EditDate getD = new EditDate();
		rigNames.clear();
		gpuTemp.clear();
		gpuName.clear();
		hashrate.clear();
		
		
		try {
			Object o1 = null;
			o1 = new Getter5(new URL(profile.get(0) + "?json=yes"));
			jsonStr = ((Getter5)o1).Show();
			mainAPI = mapper.readTree(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String a = jsonStr;
		while(a.indexOf("condition")!=-1) {
			int b = a.indexOf("condition")-10;
			rigNames.add(a.substring(b, b+6));
			a = a.substring(b+11,a.length());
		}
		
		
		
		for(int i = 0;i < rigNames.size(); i++) {
			a = mainAPI.get("rigs").get(rigNames.get(i).toString()).get("temp").toString();
			a = a.substring(1,a.length()-1);
			System.out.println(rigNames.get(i));
			System.out.println(a);
			gpuTemp.add(a.split(" "));
		}
		
		for(int i = 0;i < rigNames.size(); i++) {
			String c = mainAPI.get("rigs").get(rigNames.get(i).toString()).get("miner_hashes").toString();
			c = c.substring(1,c.length()-1);
			System.out.println(rigNames.get(i));
			System.out.println(c);
			hashrate.add(a.split(" "));
		}
		
		
		
		
		for(int i = 0;i < rigNames.size(); i++) {
			String b = mainAPI.get("rigs").get(rigNames.get(i).toString()).get("meminfo").toString();
			b = b.substring(1,b.length()-1);
			int f = -1;
			String d;
			while(b.indexOf("GPU",1)!=-1) {
				f++;
				int c = b.indexOf("GPU",1);
				d = b.substring(0,c);
				d = d.substring(0,d.length()-2);
				b = b.substring(c,b.length());
				gpuName.add(new ArrayList<String>());
				gpuName.get(i).add(d);
			}
			gpuName.get(i).add(b);
		}
		getD.gpuTemp = gpuTemp;
		getD.rigNames = rigNames;
		getD.gpuName = gpuName;
		getD.hashrate = hashrate;
		
		return getD;
		
	}
}