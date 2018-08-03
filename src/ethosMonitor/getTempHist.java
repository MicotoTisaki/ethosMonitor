package ethosMonitor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class getTempHist{
	static ArrayList<String[]> gpuTempHist = new ArrayList<String[]>();
	static ObjectMapper mapper = new ObjectMapper();
	static JsonNode mainAPI = null;
	static String jsonStr = null;
	
	public getTempHist GetHist(ArrayList<String> profile,String tergetRigID,int gpuMount) throws JsonParseException, JsonMappingException, IOException{
		getTempHist getHist  = new getTempHist();
		
		EditDate getD = new EditDate();
		EditDate getDate = getD.getDate(profile);
		String panel = profile.get(0).substring(7,13);
		try {
			Object o1 = null;
			//http://ethosdistro.com/graphs/?rig=496406&panel=hyperb&type=temp&json=yes
			o1 = new Getter5(new URL(profile.get(0) +"graphs/?rig="+ tergetRigID + "&panel=" + panel + "&type=temp&json=yes"));
			jsonStr = ((Getter5)o1).Show();
			mainAPI = mapper.readTree(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		for(int i = 0;i < gpuMount;i++) {
			String[] tempList;
			String iStr = String.valueOf(i);
			String a = mainAPI.get("GPU"+iStr).toString();
			tempList = mapper.readValue(a, String[].class);
			ArrayList<String[]> tempHistA = new ArrayList<String[]>();
			for(int j = 0; j < tempList.length;j++) {
				String[] b = tempList[j].split(" ");
				tempHistA.add(b);
			}
			gpuTempHist.add(tempHistA);
		}
		*/
		
		String[] tempList;
		String a = mainAPI.get("GPU"+Integer.toString(gpuMount)).toString();
		tempList = mapper.readValue(a, String[].class);
		for(int j = 0; j < tempList.length;j++) {
			String[] b = tempList[j].split(" ");
			gpuTempHist.add(b);
		}
		
		getHist.gpuTempHist = gpuTempHist;
		//System.out.println(gpuTempHist.get(0).get(0)[1]);
		
		return getHist;
	}
}