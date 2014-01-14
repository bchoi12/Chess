import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JLabel;
import javax.swing.JTextPane;


public class Chess extends JFrame{
	private JTextField textFieldMsg;
	private JButton btnHost, btnConnect;
	private boolean host;
	private ServerSocket ss;
	private Socket socket;
	private InputStream is;
	private final static int END_CHAR = 254;
	private final static int MSG_CHAR = 253;
	private final static int MOVE_CHAR = 252;
	private boolean connecting;
	private boolean connected;
	private String username;

    public Chess() {

        final Board board = new Board();
        getContentPane().add(board);
        board.setLayout(null);
        
        username = JOptionPane.showInputDialog("Enter username:");
        
       
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(476, 13, 226, 456);
        board.add(tabbedPane);
        
        JPanel panelConnection = new JPanel();
        panelConnection.setLayout(null);
        tabbedPane.addTab("Connection", null, panelConnection, null);
        
        JScrollPane scrollPaneChat = new JScrollPane();
        scrollPaneChat.setBounds(12, 51, 197, 327);
        panelConnection.add(scrollPaneChat);
        
        final JTextArea textAreaChat = new JTextArea();
        textAreaChat.setEditable(false);
        textAreaChat.setText("Chess!\nClick host or connect to start");
        scrollPaneChat.setViewportView(textAreaChat);
        DefaultCaret caretDisplay = (DefaultCaret) textAreaChat.getCaret();
        caretDisplay.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        JPanel panelLog = new JPanel();
        panelLog.setLayout(null);
        tabbedPane.addTab("Game Log", null, panelLog, null);
        
        JScrollPane scrollPaneLog = new JScrollPane();
        scrollPaneLog.setBounds(0, 0, 221, 426);
        panelLog.add(scrollPaneLog);
        
        final JTextArea textAreaGame = new JTextArea();
        textAreaGame.setEditable(false);
        scrollPaneLog.setViewportView(textAreaGame);
        caretDisplay = (DefaultCaret) textAreaChat.getCaret();
        caretDisplay.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        textFieldMsg = new JTextField();
        textFieldMsg.setBounds(12, 391, 197, 22);
        panelConnection.add(textFieldMsg);
        textFieldMsg.setColumns(10);
        
        textFieldMsg.addKeyListener(new KeyListener() {
        	
        	@Override
        	public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
	                if (!connected){
	                	textFieldMsg.setText("");
	                    textAreaChat.setText(textAreaChat.getText() + "\nNot connected, message not sent");
	                    return;
	                }
	                if (textFieldMsg.getText().length() > 0){
	                	textAreaChat.setText(textAreaChat.getText() + "\n"  +username + ": " + textFieldMsg.getText());
	                } else {
	                	return;
	                }
	                try {
	                	socket.getOutputStream().write(MSG_CHAR);
	                	for (int i=0;i<username.length();i++){
	                		socket.getOutputStream().write(username.charAt(i));
	                	}
	                	socket.getOutputStream().write(':');
	                	socket.getOutputStream().write(' ');
	                    for(int i=0;i<textFieldMsg.getText().length();i++){
	                        socket.getOutputStream().write(textFieldMsg.getText().charAt(i));
	                    }
	                    socket.getOutputStream().write(END_CHAR);
	                    socket.getOutputStream().flush();
	                    //socket.getOutputStream().close();
	                } catch (IOException e1) {
	                    textAreaChat.setText(textAreaChat.getText() + "\nFailed to send message");
	                }
	                textFieldMsg.setText("");
	            }
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
        });
        
        final Object lock = new Object();
        
		final Thread hostThread = new Thread(){
			public void run(){
				while(true){
					synchronized(lock){
						while(!host){
							try{
								System.out.println("waiting");
								lock.wait();
							} catch (InterruptedException e) {}
						}
					}
					try {
						ss = new ServerSocket(8505);
						socket = ss.accept();
						is = socket.getInputStream();
						board.setSide(0);
						connected = true;
						textAreaChat.setText(textAreaChat.getText() + "\nConnected!");
						btnHost.setText("Host");
	                    while(connected){
	                        int next = END_CHAR;
	                        String out = "";
	                        try {
	                        	next = is.read();
	                        	if (next == MSG_CHAR){
		                            while((next = is.read()) != END_CHAR){
		                                out += (char) next;
		                            }
		                            if (out.length() > 0){
		                                textAreaChat.setText(textAreaChat.getText() + "\n" + out);
		                                out = "";
		                            }
	                        	} else if (next == MOVE_CHAR){
	                        		while((next = is.read()) != END_CHAR){
	                        			out += (char) next + " ";
	                        		}
	                        		String[] temp = out.split(" ");
	            					textAreaGame.setText(textAreaGame.getText() + new Position(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])).getPosition() + " moves to " + new Position(Integer.parseInt(temp[4]), Integer.parseInt(temp[4])).getPosition() + "\n");
	                        		board.movePieces(out);
	                        		out = "";
	                        	}
	                        } catch (IOException e){
	                        	connected = false;
	                        	host = false;
	                            textAreaChat.setText(textAreaChat.getText() + "\nConnection lost!");
	                            ss.close();
	                        }
	                    }
					} catch (IOException e) {
						textAreaChat.setText(textAreaChat.getText() + "\nFailed to host");
						btnHost.setText("Host");
						host = false;
					}
				}
			}
		};
		
		final Thread connectThread = new Thread(){
			public void run(){
				while(true){
					synchronized(lock){
						while(!connecting){
							try{
								lock.wait();
							} catch (InterruptedException e) {
								System.out.println("starting");
							}
						}
					}
					try {
						socket = new Socket(JOptionPane.showInputDialog("Enter IP:"), 8505);
						is = socket.getInputStream();
						board.setSide(1);
						connected = true;
						btnConnect.setText("Connect");
						textAreaChat.setText(textAreaChat.getText() + "\nConnected!");
	                    while(connected){
	                        int next = END_CHAR;
	                        String out = "";
	                        try {
	                        	next = is.read();
	                        	if (next == MSG_CHAR){
		                            while((next = is.read()) != END_CHAR){
		                                out += (char) next;
		                            }
		                            if (out.length() > 0){
		                                textAreaChat.setText(textAreaChat.getText() + "\n" + out);
		                                out = "";
		                            }
	                        	} else if (next == MOVE_CHAR){
	                        		while((next = is.read()) != END_CHAR){
	                        			out += (char) next + " ";
	                        		}
	                        		board.movePieces(out);
	                        		String[] temp = out.split(" ");
	            					textAreaGame.setText(textAreaGame.getText() + new Position(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])).getPosition() + " moves to " + new Position(Integer.parseInt(temp[4]), Integer.parseInt(temp[5])).getPosition() + "\n");
	                        		out = "";
	                        	}
	                        } catch (IOException e){
	                        	connected = false;
	                        	connecting = false;
	                            textAreaChat.setText(textAreaChat.getText() + "\nConnection lost!");
	                            socket.close();
	                        }
	                    }
					} catch (IOException e) {
						btnConnect.setText("Connect");
						textAreaChat.setText(textAreaChat.getText() + "\nFailed to start connection");
						connecting = false;
					}
				}
			}
		};
		
		connected = false;
		connecting = false;
		host = false;
		hostThread.start();
		connectThread.start();
        
        btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if (connected)	return;
        		if (connecting){
        			connecting = false;
        			btnConnect.setText("Connect");
        			try {
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			connected = false;
        		} else {
        			synchronized(lock){
        				connecting = true;
        				lock.notifyAll();
        			}
        			btnConnect.setText("Cancel");
        		}
        	}
        });
        btnConnect.setBounds(120, 13, 89, 25);
        panelConnection.add(btnConnect);
        
        btnHost = new JButton("Host");
        btnHost.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if (connected) return;
        		if (host){
        			host = false;
        			btnHost.setText("Host");
        			try {
						ss.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			connected = false;
        		} else {
        			synchronized(lock){
        				host = true;
        				System.out.println("NOTIFYING");
        				lock.notifyAll();
        			}
        			btnHost.setText("Cancel");
        		}
        	}
        });
        btnHost.setBounds(12, 13, 89, 25);
        panelConnection.add(btnHost);
        
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(null);
        tabbedPane.addTab("About", null, aboutPanel, null);
        
        JTextPane textPaneAbout = new JTextPane();
        textPaneAbout.setText("Unfinished Chess Program\nWritten during Spring Break 2013\n\nMissing features:\n -Check and checkmate\n -Castling\n -En Passant \n -Interface options \n -Sprites and sprite skins\n\n\nTo be rewritten soon in the future...");
        textPaneAbout.setEditable(false);
        textPaneAbout.setBounds(0, 0, 221, 426);
        aboutPanel.add(textPaneAbout);
        
        board.getPCS().addPropertyChangeListener("moved", new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				System.out.println("property changed");
				try {
					socket.getOutputStream().write(MOVE_CHAR);
					String[] temp = board.sendString.split(" ");
					for(int i=0;i<temp.length;i++){
						socket.getOutputStream().write(temp[i].charAt(0));
					}
					socket.getOutputStream().write(END_CHAR);
					
					textAreaGame.setText(textAreaGame.getText() + new Position(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])).getPosition() + " moves to " + new Position(Integer.parseInt(temp[4]), Integer.parseInt(temp[5])).getPosition() + "\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					textAreaChat.setText(textAreaChat.getText() + "\nFailed to send move");
				}
			}
        });
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 515);
        setLocationRelativeTo(null);
        setTitle("Chess");

        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Chess();
    }
}
