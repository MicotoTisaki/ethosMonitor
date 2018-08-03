package ethosMonitor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class sshCliant extends JFrame implements ActionListener, KeyListener{
	JFrame frame = new JFrame();
	JTextField sshCommand = new JTextField("",20);
	JButton disconnect = new JButton("disconnect");
	JPanel panel = new JPanel();
	JPanel sendPanel  = new JPanel();
	JTextArea sshresponse = new JTextArea();
	Session session = null;
	ChannelExec channel = null;
	BufferedInputStream bin = null;
	String command = "";
	String response = "";
	boolean timer = true;
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			command = sshCommand.getText();
			try {
				sendCommand(command);
				sshCommand.setText("");
			} catch (IOException | JSchException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(disconnect)) {
			channel.disconnect();
			frame.dispose();
		}
	}
	sshCliant(String rigName,String IP,String PortStr,String Name,String Pass) throws JSchException, IOException{
		frame.setTitle("ssh  "+rigName);
		frame.setBounds(100,100,400,400);
		sshresponse.setText(response);
		sshresponse.setOpaque(false);
		sshresponse.setEditable(false);
		sshresponse.setFocusable(false);
		
		sshCommand.addKeyListener(this);
		disconnect.addActionListener(this);
		sendPanel.add(sshCommand);
		sendPanel.add(disconnect);
		panel.add(sshresponse);
		JPanel mainPanel = new JPanel();
		

		JScrollPane sbar = new JScrollPane(sshresponse);
		
		sbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sbar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add("South",sendPanel);
		mainPanel.add("Center",sbar);
		
		frame.getContentPane().add(mainPanel);
		
		
		
		int Port = Integer.parseInt(PortStr);
		
		
		
		
		
		JSch jsch = new JSch();
		session = jsch.getSession(Name,IP,Port);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(Pass);
		try {
		    session.connect(5000);
			sendCommand(command);
		} catch (JSchException e) {
			JOptionPane.showMessageDialog(null, "The session is Timeout. Please check the ssh profile at conf/profile");
		    return;
		}
		
		frame.setVisible(true);
	}
	void sendCommand(String command) throws IOException, JSchException{
		channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(command);
		channel.connect();
		bin = new BufferedInputStream(channel.getInputStream());
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int length;
		while (true) {
			length = bin.read(buf);
			if (length == -1) {
				break;
			}
			bout.write(buf, 0, length);
		}
		//標準出力
		//System.out.format("実行結果=%1$s", new String(bout.toByteArray(), StandardCharsets.UTF_8));
		response = response + new String(bout.toByteArray(), StandardCharsets.UTF_8) ;
		sshresponse.setText(response);
		sshresponse.repaint();
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
