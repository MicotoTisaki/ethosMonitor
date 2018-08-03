package ethosMonitor;

import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class monitor{
	public static String profileAd = "profile.txt";
	public static ArrayList<String> profile = new ArrayList<String>();
	static ObjectMapper mapper = new ObjectMapper();
	static JsonNode mainAPI = null;
	static String jsonStr = null;
	static ArrayList<String> rigNames = new ArrayList<String>();
	static ArrayList<ArrayList<String>> gpuName = new ArrayList<ArrayList<String>>();
	static ArrayList<String[]> gpuTemp = new ArrayList<String[]>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		try {
			profile = loadProfile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EditDate getD = new EditDate();
		EditDate getDate = getD.getDate(profile);
		
		rigNames = getDate.rigNames;
		gpuName = getDate.gpuName;
		gpuTemp = getDate.gpuTemp;
		
		if(profile.size()<=4) {
			for(int i = 0;i < rigNames.size();i++) {
				profile.add(rigNames.get(i));
				profile.add("192.168.1.1");
				profile.add("22");
				profile.add("User");
				profile.add("Pass");
			}
		}
		
		
		try {
			writeProfile(profile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		setJFrame frame = new setJFrame(gpuTemp,rigNames,profile,gpuName);
	}
	
	
	
	
	static ArrayList<String> loadProfile() throws IOException{
			profile.clear();
			rigNames.clear();
			gpuName.clear();
			gpuTemp.clear();
			try {
				if(new File(profileAd).exists()==true) {
					BufferedReader br = new BufferedReader(new FileReader(profileAd));
					String line;
					while((line = br.readLine()) != null) {
						profile.add(line);
					}
				}else {
					File newProfile = new File("profile.txt");
					newProfile.createNewFile();
					PrintWriter bw = new PrintWriter(new FileWriter(profileAd));
					bw.println("http://000000.ethosdistro.com/");
					bw.println("50");
					bw.println("65");
					bw.println("70");
					bw.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return profile;
	}
	
	public static void writeProfile(ArrayList<String> profilebw) throws IOException{
		File file1 = new File("profile.txt");
		String absFileStr = file1.getAbsolutePath();
		File file = new File(absFileStr);
		file.delete();
		file.createNewFile();
		PrintWriter bw = new PrintWriter(new FileWriter("profile.txt"));
		for(int i = 0;i < profilebw.size(); i++) {
			bw.println(profilebw.get(i));
		}
		bw.close();
		
	}
	
	public static void makeProfile() {
		
	}
}

class Getter5{
	private String charset = "Shift_JIS";
	private JTextArea htmlArea;
	
	public Getter5(URL url){
		htmlArea = new JTextArea();
        // Webページを読み込む
        try {
            // 接続
            URLConnection uc = url.openConnection();
            // HTMLを読み込む
            BufferedInputStream bis = new BufferedInputStream(uc.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(bis, charset));
            htmlArea.setText("");//初期化
            String line;
            while ((line = br.readLine()) != null) {
                htmlArea.append(line + "\n");
            }
        } catch (MalformedURLException ex) {
            htmlArea.setText("URLが不正です。");
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            htmlArea.setText("サイトが見つかりません。");
        } catch (IOException ex) {
            ex.printStackTrace();
        }    
    }
	public String Show(){
		//System.out.println(htmlArea.getText());
		return htmlArea.getText();
	}
}



