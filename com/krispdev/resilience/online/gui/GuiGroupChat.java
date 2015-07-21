package com.krispdev.resilience.online.gui;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.online.irc.extern.BotManager;

public class GuiGroupChat extends JFrame implements ActionListener{
	
	private String channel;
	private String theirUsername;
	private BotManager bot;
	
	private boolean setNullOnClose;
	
	private JTextArea chatArea;
	private JTextField chat;
	private JScrollPane sbrText;
	private GuiGroupChat instance;
	
	public GuiGroupChat(String theirUsername, String initChannel, BotManager bot, boolean setNullOnClose){
		this.setNullOnClose = setNullOnClose;
		this.theirUsername = theirUsername;
		this.bot = bot;
		this.channel = initChannel;
		bot.getBot().joinChannel(channel);
		initComponents();
		this.instance = this;
	}
	
	public void initComponents(){
		Toolkit.getDefaultToolkit().beep();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setSize(372, 240);
		setLayout(new FlowLayout());
		setResizable(true);
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent windowEvent) {
		    	if(setNullOnClose){
		    		Resilience.getInstance().getValues().userChat = null;
		    	}else{
		    		bot.getBot().partChannel(channel);
		    	}
		    	bot.getBot().removeGui(instance);
		    	super.windowClosing(windowEvent);
		    }
		});
		setTitle(""+(setNullOnClose ? "Your" : theirUsername+"'s")+" chat room");
		
		chatArea = new JTextArea(10, 30);
		chat = new JTextField(30);
		
		chatArea.setLineWrap(true);
		chatArea.setEditable(false);
		sbrText = new JScrollPane(chatArea);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		chat.addActionListener(this);
		
		add(sbrText);
		add(chat);
		
		setVisible(true);
		this.setAlwaysOnTop(false);
		if(!setNullOnClose){
			bot.getBot().addGui(this);
		}
		chatArea.append("You are now chatting in "+(setNullOnClose ? "your" : theirUsername+"'s")+" chat room.");
		
		if(setNullOnClose){
			if(Resilience.getInstance().getInvoker().getIngameGUI() != null){
				Resilience.getInstance().getInvoker().getIngameGUI().notify("\247bResilience Online\247f: Your chat room has been opened!", 1000);
			}
		}
	}
	
	public void addLine(String line){
		chatArea.append("\n"+line);
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
	}
	
	public void setChannel(String channel){
		this.channel = channel;
	}
	
	public String getChannel(){
		return channel;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == chat){
			chatArea.append("\nMe: "+chat.getText());
			bot.getBot().sendMessage(channel, chat.getText());
			
			chat.setText("");
			chatArea.setCaretPosition(chatArea.getDocument().getLength());
		}
	}
	
}
