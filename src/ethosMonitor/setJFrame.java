package ethosMonitor;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jcraft.jsch.JSchException;

public class setJFrame extends JFrame implements ActionListener,MouseListener,KeyListener{
	//ArrayList<JPanel> MainPanel = new ArrayList<JPanel>();
	ArrayList<JLabel> rigNameLabel = new ArrayList<JLabel>();
	//ArrayList<JPanel> tempPanel = new ArrayList<JPanel>();
	ArrayList<JPanel> rigtempArray = new ArrayList<JPanel>();
	JPanel contPanel = new JPanel();
	
	JFrame frame = new JFrame("monitor");
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile = new JMenu("File");
	JMenu menuConf = new JMenu("conf");
	JMenuItem menuOpen = new JMenuItem("open");
	JMenuItem menuClose = new JMenuItem("close");
	JMenuItem confProfile = new JMenuItem("profile");
	JButton repaint = new JButton("update the panel");
	ArrayList<String> profile = new ArrayList<String>();
	ArrayList<String[]> gpuTempList = new ArrayList<String[]>();
	ArrayList<ArrayList<JPanel>> rigtempDArray = new ArrayList<ArrayList<JPanel>>();
	ArrayList<ArrayList<String>> rigtempDArrayStr = new ArrayList<ArrayList<String>>();
	ArrayList<String> rigNames = new ArrayList<String>();
	int[] nowPointGpu = new int[2];
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(confProfile)) {
			try {
				confProfileWindow frame = new confProfileWindow(profile,rigNames);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource().equals(repaint)) {
			try {
				profile = monitor.loadProfile();
				EditDate getD = new EditDate();
				EditDate getDate = getD.getDate(profile);
				
				rigNames = getDate.rigNames;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int j = 0;j < gpuTempList.size();j++) {
				for(int i = 0;i < gpuTempList.get(j).length;i++) {
					rigtempDArray.get(j).get(i).removeAll();
					rigtempDArray.get(j).get(i).revalidate();
					rigtempDArray.get(j).get(i).setBackground(returnColor(Integer.parseInt(gpuTempList.get(j)[i]),profile));
					rigtempDArray.get(j).get(i).repaint();
				}
				//MainPanel.get(j).repaint();
				rigNameLabel.get(j).repaint();
				//tempPanel.get(j).repaint();
				rigtempArray.get(j).repaint();
				contPanel.repaint();
				repaint();
			}
			
			setItems(profile);
			try {
				monitor.writeProfile(profile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void keyPressed(KeyEvent e) {
		int mod = e.getModifiersEx();
		if(e.getKeyChar() == 'r') {
			if((mod == 256)) {
				try {
					profile = monitor.loadProfile();
					
					EditDate getD = new EditDate();
					EditDate getDate = getD.getDate(profile);
					rigNames.clear();
					rigNames = getDate.rigNames;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int j = 0;j < gpuTempList.size();j++) {
					for(int i = 0;i < gpuTempList.get(j).length;i++) {
						rigtempDArray.get(j).get(i).removeAll();
						rigtempDArray.get(j).get(i).revalidate();
						rigtempDArray.get(j).get(i).setBackground(returnColor(Integer.parseInt(gpuTempList.get(j)[i]),profile));
						rigtempDArray.get(j).get(i).repaint();
					}
					//MainPanel.get(j).repaint();
					rigNameLabel.get(j).repaint();
					//tempPanel.get(j).repaint();
					rigtempArray.get(j).repaint();
					contPanel.repaint();
					repaint();
				}
				setItems(profile);
				
				try {
					monitor.writeProfile(profile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	setJFrame(ArrayList<String[]> gpuTemp,ArrayList<String> rigNames1,ArrayList<String> profileTemp,ArrayList<ArrayList<String>> gpuName) {
		profile.clear();
		for(int i = 0; i < profileTemp.size(); i++) {
			profile.add(profileTemp.get(i));
		}

		setItems(profile);
		
		

		rigNameLabel.clear();
		rigtempArray.clear();
		gpuTempList.clear();
		rigNames.clear();
		rigtempDArray.clear();
		rigtempDArrayStr.clear();
		
		EditDate getD = new EditDate();
		EditDate getDate = getD.getDate(profile);
		
		rigNames = getDate.rigNames;
		gpuName = getDate.gpuName;
		gpuTemp = getDate.gpuTemp;
		
		for(int i = 0;i < gpuTemp.size(); i++) {
			gpuTempList.add(gpuTemp.get(i));
		}
		
		
		
		
		
		frame.setSize(1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel name = new JPanel();
		JLabel rigName = new JLabel();
		JPanel temp = new JPanel();
		JPanel rigtemp = new JPanel();
		
		
		
		
		rigtemp.setLayout(new BoxLayout(rigtemp, BoxLayout.X_AXIS));
		contPanel.setLayout(new BoxLayout(contPanel, BoxLayout.Y_AXIS));
		GridLayout gridLayout = new GridLayout(rigNames.size()*2+1,1);
		gridLayout.setHgap(10);
		gridLayout.setVgap(5);
		contPanel.setLayout(gridLayout);
		repaint.addActionListener(this);
		repaint.addKeyListener(this);
		contPanel.add(repaint);
		
		for(int j = 0;j < gpuTemp.size();j++) {
			rigtempDArray.add(new ArrayList<JPanel>());
			for(int i = 0;i < gpuTemp.get(j).length;i++) {
				
				rigtempDArray.get(j).add(new JPanel());
				
				rigtempDArray.get(j).get(i).setBackground(returnColor(Integer.parseInt(gpuTemp.get(j)[i]),profile));
				
				
				rigtempDArray.get(j).get(i).setSize(30, 80);
				BevelBorder border = new BevelBorder(BevelBorder.RAISED);
				rigtempDArray.get(j).get(i).setBorder(border);
				//rigtempDArray.add(tempPanel);
				//rigtemp.add(tempPanel.get(i));
				
				
				
			}
			rigNameLabel.add(new JLabel());
			rigNameLabel.get(j).setText(rigNames.get(j).toString());
			
			
			name.add(rigNameLabel.get(j));
			for(int i = 0;i < gpuTemp.get(j).length;i++) {
				rigtempArray.add(new JPanel());
				BevelBorder border = new BevelBorder(BevelBorder.RAISED);
				rigtempDArray.get(j).get(i).addMouseListener(this);
				rigtempArray.get(j).setBorder(border);
				rigtempArray.get(j).add(rigtempDArray.get(j).get(i));
			}
			
			
		}
		for(int i = 0; i < rigNames.size();i++) {
			rigNameLabel.get(i).addMouseListener(this);
			contPanel.add(rigNameLabel.get(i));
			contPanel.add(rigtempArray.get(i));
			rigNameLabel.get(i).setToolTipText(profile.get(4+i*5+1));
		}
		
		menuFile.addActionListener(this);
		menuConf.addActionListener(this);
		menuOpen.addActionListener(this);
		menuClose.addActionListener(this);
		confProfile.addActionListener(this);
		
		//menuBar.add(menuFile);
		//menuFile.add(menuOpen);
		//menuFile.add(menuClose);
		menuBar.add(menuConf);
		menuConf.add(confProfile);
		
		frame.setJMenuBar(menuBar);
		
		JScrollPane sbar = new JScrollPane(contPanel);
		sbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sbar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		
		Container contentPane = frame.getContentPane();
		contentPane.add(sbar,BorderLayout.CENTER);
		contentPane.setLayout(new FlowLayout());
		
		
		
		
		for(int i = 0;i < rigtempDArray.size();i++) {
			ArrayList<String> tempStr = new ArrayList<String>();
			for(int j = 0;j < rigtempDArray.get(i).size();j++) {
				tempStr.add(rigtempDArray.get(i).get(j).toString());
				rigtempDArray.get(i).get(j).setToolTipText("temp:"+gpuTempList.get(i)[j]+"     gpuName:"+gpuName.get(i).get(j).toString());
			}
			rigtempDArrayStr.add(tempStr);
		}
		
		int max = 0;
		for(int i = 0;i < rigtempDArray.size();i++) {
			if(max <= rigtempDArray.get(i).size()) {
				max = rigtempDArray.get(i).size();
			}
		}
		
		int width = max * 30;
		if(width < 300) {
			frame.setSize(300,500);
		}else {
			frame.setSize(width, 500);
		}
		frame.setVisible(true);
		
	}
	
	
	Color returnColor(int temp,ArrayList<String> profile){
		
		if(temp <=  Integer.parseInt(profile.get(1))) {
			return java.awt.Color.BLUE;
		}else if(temp > Integer.parseInt(profile.get(1)) && Integer.parseInt(profile.get(2)) >= temp) {
			return java.awt.Color.GREEN;
		}else if(temp > Integer.parseInt(profile.get(2)) && Integer.parseInt(profile.get(3)) >= temp) {
			return java.awt.Color.ORANGE;
		}else if(temp > Integer.parseInt(profile.get(3))) {
			return java.awt.Color.RED;
		}
		return null;
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.toString().indexOf("text")!=-1) {
			int a = arg0.toString().indexOf("text")+5;
			String id = arg0.toString().substring(a,a+6);
			int point = profile.indexOf(id);
			try {
				sshCliant frame = new sshCliant(profile.get(point),profile.get(point+1),profile.get(point+2),profile.get(point+3),profile.get(point+4));
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(arg0.toString().indexOf("14x14")!=-1) {
			//System.out.println(arg0);
			//System.out.println(rigtempDArray.get(1).get(2).toString());
			int a[] = new int[2];
			for(int i = 0;i < rigtempDArray.size();i++) {
				for(int j = 0;j < rigtempDArray.get(i).size();j++) {
					if(arg0.toString().contains(rigtempDArray.get(i).get(j).toString())) {
						a[0]=i;
						a[1]=j;
					}
				}
			}
			try {
				gpuDetail detailframe = new gpuDetail(frame.getX()+frame.getWidth(),frame.getY(),gpuTempList,a,profile);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(gpuTempList.get(nowPointGpu[0])[nowPointGpu[1]]);
		}
		
		
		int max = 0;
		for(int i = 0;i < rigtempDArray.size();i++) {
			if(max <= rigtempDArray.get(i).size()) {
				max = rigtempDArray.get(i).size();
			}
		}
		
		int width = max * 30;
		if(width < 300) {
			frame.setSize(300,500);
		}else {
			frame.setSize(width, 500);
		}
		frame.setVisible(true);
		
	}
	
	
	void setItems(ArrayList<String> profile){
		
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int onPoint = arg0.toString().indexOf("on ");
		String a = arg0.toString().substring(onPoint + 3);
		if(arg0.toString().indexOf(",14x14")!=-1) {
			for(int i = 0;i < rigtempDArray.size();i++) {
				for(int j = 0;j < rigtempDArray.get(i).size();j++) {
					if(rigtempDArray.get(i).get(j).toString().equals(a.toString())) {
						nowPointGpu[0]=i;
						nowPointGpu[1]=j;
						break;
					}
				}
			}
		}
		//System.out.println(arg0);
		//System.out.println(gpuTempList.get(nowPointGpu[0])[nowPointGpu[1]]);
		
		
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		/*
		monitor.getData();
		frame.getContentPane().repaint();
		*/
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
