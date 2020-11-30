package message.app;


import javax.swing.*;

import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server implements ActionListener{

	JPanel p1;
	JTextField t1;
	JButton b1;
	static JPanel a1;
	static JFrame f1 = new JFrame();

	static Box vertical = Box.createVerticalBox();
	int posX=0,posY=0;
	static ServerSocket skt;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;

	Boolean typing;
	
	
	
	

	Server(String user){
		f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(Color.red);
		p1.setBounds(0, 0, 450, 67);
		f1.add(p1);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("message/app/icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5, 17, 30, 30);
		p1.add(l1);
		
		f1.addMouseListener(new MouseAdapter()
		{
		   public void mousePressed(MouseEvent e)
		   {
		      posX=e.getX();
		      posY=e.getY();
		   }
		});
		
		f1.addMouseMotionListener(new MouseAdapter()
		{
		     public void mouseDragged(MouseEvent evt)
		     {
				//sets frame position when mouse dragged			
				f1.setLocation (evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
							
		     }
		});

		l1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ae){
				System.exit(0);
			}
		});

		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("message/app/icons/1.jpg"));
		Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40, 5, 60, 60);
		p1.add(l2);

		


		JLabel l3 = new JLabel(user);
		l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110, 15, 100, 18);
		p1.add(l3);   


		JLabel l4 = new JLabel("Active Now");
		l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110, 35, 100, 20);
		p1.add(l4);  

		Timer t = new Timer(1, new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(!typing){
					l4.setText("Active Now");
				}
			}
		});

		t.setInitialDelay(1000);


		a1 = new JPanel();

		a1.setBackground(Color.BLACK);
		a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		//f1.add(a1);

		JScrollPane sp = new JScrollPane(a1);
		sp.setBounds(0, 65, 440, 585);
		sp.setBorder(BorderFactory.createEmptyBorder());


		ScrollBarUI ui = new BasicScrollBarUI() {
			@SuppressWarnings("unused")
			protected JButton createDecrementButtonButton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBackground(new Color(7,94,84));
				button.setForeground(Color.WHITE);
				this.thumbColor=new Color(7,94,84);
				return button;

			}
			@SuppressWarnings("unused")
			protected JButton createIncrementbutton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBackground(new Color(7,94,84));
				button.setForeground(Color.WHITE);
				this.thumbColor=new Color(7,94,84);
				return button;

			}
		};


		sp.getVerticalScrollBar().setUI(ui);
		f1.add(sp);


		t1 = new JTextField();
		t1.setBounds(5, 655, 310, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f1.add(t1);

		t1.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				l4.setText("typing...");

				t.stop();

				typing = true;
			}

			public void keyReleased(KeyEvent ke){
				typing = false;

				if(!t.isRunning()){
					t.start();
				}
			}
		});

		b1 = new JButton("Send");
		b1.setBounds(320, 655, 115, 40);
		b1.setBackground(new Color(7, 94, 84));
		b1.setForeground(Color.WHITE);
		b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
		b1.addActionListener(this);
		f1.add(b1);


		f1.getContentPane().setBackground(Color.BLACK);
		f1.setLayout(null);
		f1.setSize(440,700); 
		f1.setLocation(300, 100); 
		f1.setUndecorated(true);
		f1.setVisible(true);
		f1.setResizable(false);
		
		
		

	}

	protected void setLocation(int i, int j) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent ae){
		try{
			String out = t1.getText();

			JPanel p2 = formatLabel(out);
			p2.setBackground(Color.BLACK);

			a1.setLayout(new BorderLayout());

			JPanel right = new JPanel(new BorderLayout());
			right.setBackground(Color.black);
			right.add(p2, BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));

			a1.add(vertical, BorderLayout.PAGE_START);
			dout.writeUTF(out);
			t1.setText("");
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static JPanel formatLabel(String out){
		JPanel p3 = new JPanel();
		p3.setBackground(Color.BLACK);
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

		JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
		l1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		l1.setBackground(new Color(37, 211, 102));
		l1.setOpaque(true);
		l1.setBorder(new EmptyBorder(14,14,14,50));

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		JLabel l2 = new JLabel();
		l2.setText(sdf.format(cal.getTime()));
		l2.setForeground (Color.WHITE);
		p3.add(l1);
		p3.add(l2);
		return p3;
	}






	public static void main(String[] args){
		
		JFrame f=new JFrame("Provide your Name"); 
		
		JButton b=new JButton("Submit");    
		b.setBounds(100,100,140, 40);   
		b.setSize(90,40);
		
		JLabel label = new JLabel("Text Color: Red");
		label.setForeground (Color.red);
		label.setText("Enter Name :");
		 label.setFont(new Font("Verdana", Font.BOLD, 12));
		label.setBounds(10, 10, 90, 100);
		
		JLabel label1 = new JLabel();
		label1.setBounds(10, 110, 200, 100);
		
		JTextField textfield= new JTextField();
		 textfield.setFont(new Font("Verdana", Font.PLAIN, 12));
		textfield.setBounds(110, 50, 130, 30);
		
		f.add(label1);
		f.add(textfield);
		f.add(label);
		f.add(b);    
		f.setSize(300,200); 
		f.getContentPane().setBackground(Color.BLACK);
		f.setLayout(null);    
		f.setVisible(true);    
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

		
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String user = textfield.getText();
				new Server(user);
				Server.f1.setVisible(true);

			}          
		});
   




	
	String msginput = "";
	try{
		skt = new ServerSocket(6001);
		while(true){
			s = skt.accept();
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());

			while(true){
				msginput = din.readUTF();
				JPanel p2 = formatLabel(msginput);
				p2.setBackground(Color.BLACK);
				JPanel left = new JPanel(new BorderLayout());
				left.setBackground(Color.BLACK);
				left.add(p2, BorderLayout.LINE_START);
				vertical.add(left);
				f1.validate();
			}

		}

	}catch(Exception e){}
}    
}
