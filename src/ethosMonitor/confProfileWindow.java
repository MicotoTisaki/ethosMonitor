package ethosMonitor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.*;

public class confProfileWindow extends JFrame implements ActionListener{
	JButton save = new JButton("Save");
	JButton cancel = new JButton("Cancel");
	JFrame frame = new JFrame();
	JTextField addressText = new JTextField();
	JTextField temp1Text = new JTextField();
	JTextField temp2Text = new JTextField();
	JTextField temp3Text = new JTextField();
	JTabbedPane tabbedPane = new JTabbedPane();
	JPanel ssh = new JPanel();
	ArrayList<String> profile = new ArrayList<String>();
	ArrayList<JPanel> sshPanel = new ArrayList<JPanel>();
	ArrayList<JTextField> sshIPText = new ArrayList<JTextField>();
	ArrayList<JTextField> sshPortText = new ArrayList<JTextField>();
	ArrayList<JTextField> sshNameText = new ArrayList<JTextField>();
	ArrayList<JTextField> sshPassText = new ArrayList<JTextField>();
	ArrayList<JButton> saveSSH = new ArrayList<JButton>();
	ArrayList<JButton> cancelSSH = new ArrayList<JButton>();
	ArrayList<String> rigNames = new ArrayList<String>();
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(cancel)) {
			frame.dispose();
		}
		if(e.getSource().equals(save)) {
			String newaddress = addressText.getText();
			String newtemp1 = temp1Text.getText();
			String newtemp2 = temp2Text.getText();
			String newtemp3 = temp3Text.getText();
			ArrayList<String> profiletemp = new ArrayList<String>();
			profiletemp.clear();
			profiletemp.add(newaddress);
			profiletemp.add(newtemp1);
			profiletemp.add(newtemp2);
			profiletemp.add(newtemp3);
			
			for(int i = 0;i < saveSSH.size();i++) {
				profiletemp.add(rigNames.get(i));
				profiletemp.add(sshIPText.get(i).getText());
				profiletemp.add(sshPortText.get(i).getText());
				profiletemp.add(sshNameText.get(i).getText());
				profiletemp.add(sshPassText.get(i).getText());
			}
			
			try {
				monitor.writeProfile(profiletemp);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			profile.clear();
			profile = profiletemp;
			frame.dispose();
		}
		for(int i = 0;i < rigNames.size();i++) {
			if(e.getSource().equals(saveSSH.get(i))) {
				
				String newaddress = addressText.getText();
				String newtemp1 = temp1Text.getText();
				String newtemp2 = temp2Text.getText();
				String newtemp3 = temp3Text.getText();
				ArrayList<String> profiletemp = new ArrayList<String>();
				profiletemp.clear();
				profiletemp.add(newaddress);
				profiletemp.add(newtemp1);
				profiletemp.add(newtemp2);
				profiletemp.add(newtemp3);
				
				for(int j = 0;j < saveSSH.size();j++) {
					profiletemp.add(rigNames.get(j));
					profiletemp.add(sshIPText.get(j).getText());
					profiletemp.add(sshPortText.get(j).getText());
					profiletemp.add(sshNameText.get(j).getText());
					profiletemp.add(sshPassText.get(j).getText());
				}
				
				try {
					monitor.writeProfile(profiletemp);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				profile.clear();
				profile = profiletemp;
				frame.dispose();
			}
			if(e.getSource().equals(cancelSSH.get(i))) {
				frame.dispose();
			}
		}
		
		
		
		
	}
	confProfileWindow(ArrayList<String> profiletmp,ArrayList<String> rigNames1) throws IOException{
		Container contentPane = frame.getContentPane();
		
		profiletmp = monitor.loadProfile();
		frame.remove(contentPane);
		profile.clear();
		sshPanel.clear();
		sshIPText.clear();
		sshPortText.clear();
		sshNameText.clear();
		sshPassText.clear();
		saveSSH.clear();
		cancelSSH.clear();
		repaint();
		
		
		for(int i = 0; i < profiletmp.size();i++) {
			profile.add(profiletmp.get(i));
		}
		
		
		EditDate getD = new EditDate();
		EditDate getDate = getD.getDate(profile);
		rigNames.clear();
		rigNames = getDate.rigNames;
		
		if(75+rigNames.size()*100 < 300) {
			frame.setBounds(100,80,300,250);
		}else {
			frame.setBounds(100,80,75+rigNames.size()*100,250);
		}
		
		if(profile.size()<4+rigNames.size()*4) {
			for(int i = 0;i < rigNames.size();i++) {
				profile.add(rigNames.get(i));
				profile.add("192.168.1.1");
				profile.add("22");
				profile.add("User");
				profile.add("Pass");
			}
		}
		
		
		GridLayout grid = new GridLayout(5,1);
		JPanel mainPanel = new JPanel();
		JPanel addressPanel = new JPanel();
		JPanel temp1Panel = new JPanel();
		JPanel temp2Panel = new JPanel();
		JPanel temp3Panel = new JPanel();
		JPanel ButtonPanel = new JPanel();
		
		addressText.setText(profile.get(0));
		temp1Text.setText(profile.get(1));
		temp2Text.setText(profile.get(2));
		temp3Text.setText(profile.get(3));
		
		JLabel addressLabel = new JLabel("Address");
		JLabel temp1Label = new JLabel("temp1");
		JLabel temp2Label = new JLabel("temp2");
		JLabel temp3Label = new JLabel("temp3");
		
		
		
		
		save.addActionListener(this);
		cancel.addActionListener(this);
		
		addressPanel.add(addressLabel);
		addressPanel.add(addressText);
		temp1Panel.add(temp1Label);
		temp1Panel.add(temp1Text);
		temp2Panel.add(temp2Label);
		temp2Panel.add(temp2Text);
		temp3Panel.add(temp3Label);
		temp3Panel.add(temp3Text);
		ButtonPanel.add(save);
		ButtonPanel.add(cancel);
		
		mainPanel.add(addressPanel);
		mainPanel.add(temp1Panel);
		mainPanel.add(temp2Panel);
		mainPanel.add(temp3Panel);
		mainPanel.add(ButtonPanel);
		
		
		mainPanel.setLayout(grid);
		
		tabbedPane.add("config",mainPanel);
		ArrayList<GridLayout> sshGrid = new ArrayList<GridLayout>();
		ArrayList<ArrayList<JPanel>> sshConfPanel = new ArrayList<ArrayList<JPanel>>();
		ArrayList<JLabel> sshIPLabel = new ArrayList<JLabel>();
		ArrayList<JLabel> sshPortLabel = new ArrayList<JLabel>();
		ArrayList<JLabel> sshNameLabel = new ArrayList<JLabel>();
		ArrayList<JLabel> sshPassLabel = new ArrayList<JLabel>();
		
		for(int i = 0;i < rigNames.size();i++) {
			sshPanel.add(new JPanel());
			tabbedPane.add(rigNames.get(i)+" ssh",sshPanel.get(i));
			sshGrid.add(new GridLayout(5,1));
			sshPanel.get(i).setLayout(sshGrid.get(i));
			sshConfPanel.add(new ArrayList<JPanel>());
			for(int j = 0;j < 5;j++) {
				sshConfPanel.get(i).add(new JPanel());
			}
			sshIPText.add(new JTextField(profile.get(4+i*5+1),15));
			sshPortText.add(new JTextField(profile.get(4+i*5+2),6));
			sshNameText.add(new JTextField(profile.get(4+i*5+3),10));
			sshPassText.add(new JTextField(profile.get(4+i*5+4),15));
			sshIPLabel.add(new JLabel("IP "));
			sshConfPanel.get(i).get(0).add(sshIPLabel.get(i));
			sshConfPanel.get(i).get(0).add(sshIPText.get(i));
			sshPortLabel.add(new JLabel("Port "));
			sshConfPanel.get(i).get(1).add(sshPortLabel.get(i));
			sshConfPanel.get(i).get(1).add(sshPortText.get(i));
			sshNameLabel.add(new JLabel("Name "));
			sshConfPanel.get(i).get(2).add(sshNameLabel.get(i));
			sshConfPanel.get(i).get(2).add(sshNameText.get(i));
			sshPassLabel.add(new JLabel("Pass "));
			sshConfPanel.get(i).get(3).add(sshPassLabel.get(i));
			sshConfPanel.get(i).get(3).add(sshPassText.get(i));
			saveSSH.add(new JButton("Save"));
			saveSSH.get(i).addActionListener(this);
			cancelSSH.add(new JButton("Cancel"));
			cancelSSH.get(i).addActionListener(this);
			sshConfPanel.get(i).get(4).add(saveSSH.get(i));
			sshConfPanel.get(i).get(4).add(cancelSSH.get(i));
			sshPanel.get(i).add(sshConfPanel.get(i).get(0));
			sshPanel.get(i).add(sshConfPanel.get(i).get(1));
			sshPanel.get(i).add(sshConfPanel.get(i).get(2));
			sshPanel.get(i).add(sshConfPanel.get(i).get(3));
			sshPanel.get(i).add(sshConfPanel.get(i).get(4));
			
		}
		
		
		contentPane.add(tabbedPane);
		frame.setVisible(true);
	}
}
