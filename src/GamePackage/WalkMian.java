package GamePackage;

import java.awt.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class WalkMian extends JFrame implements KeyListener {
	Image img,img2,img3,img4;
	Image jump,jump2,jump3,jump4;
	Image slide,slide2;
	Image[] images = {img,img2,img3,img4};
    Image[] slides = {slide,slide2,slide,slide2};
    Image[] jumps = {jump,jump2,jump3,jump4};
	Image back;
	Image floor;
    Image[] floors = {floor,floor,floor,floor,floor,floor,floor,floor,floor,floor,floor};
	Image obstacle1;
	Image obstacle2;
	Image obstacle3;
	Image fork;
	Image cake;
	Image heart;
	Image heart_eat;
	Image heart_long;
	Font score_font;
	int heart_value=300;
	int score=0;
	int rank = 1;
	Timer t1;
	long time;
	int walk_length=0;
	int walk_floor_length=0;
	public static final int floor_length=204;
	int[] floor_manager;
	int[] cake_manager;
	public boolean key_ori = true;
	public boolean key_slide = false;
	public boolean key_jump = false;
	public boolean key_jump2 = false;
	
	public boolean running = true;
	
	public int key_jump2_y=0;
	int h=100,w=100;
	int x = 120, y = 500, dir = 0, num = 0;
	int i = 0;
	
	// Constructor
	public WalkMian() throws FileNotFoundException {
		super("Running cookie");
		//³¯ªÛ¿o¡B¦¶¯Âè°
		img = getToolkit().getImage("Resources/run-0.png");
		img2 = getToolkit().getImage("Resources/run-1.png");
		img3 = getToolkit().getImage("Resources/run-2.png");
		img4 = getToolkit().getImage("Resources/run-3.png");
		images[0]=img;
		images[1]=img2;
		images[2]=img3;
		images[3]=img4;
		
		slide = getToolkit().getImage("Resources/slide-2.png");
		slide2 = getToolkit().getImage("Resources/slide-3.png");
		slides[0]=slide;
		slides[1]=slide2;
		slides[2]=slide;
		slides[3]=slide2;
		
		jump = getToolkit().getImage("Resources/jump-0.png");
		jump2 = getToolkit().getImage("Resources/jump-1.png");
		jump3 = getToolkit().getImage("Resources/jump-2.png");
		jump4 = getToolkit().getImage("Resources/jump-3.png");
		jumps[0]=jump;
		jumps[1]=jump2;
		jumps[2]=jump3;
		jumps[3]=jump4;
		
		//³¢¬ü­§
		obstacle1 = getToolkit().getImage("Resources/obstacle1.png");
		obstacle2 = getToolkit().getImage("Resources/obstacle2.png");
		obstacle3 = getToolkit().getImage("Resources/obstacle3.jpg");
		fork = getToolkit().getImage("Resources/fork.png");
		
		heart = getToolkit().getImage("Resources/heart.png");
		heart_eat = getToolkit().getImage("Resources/heart_eat.png");
		heart_long = getToolkit().getImage("Resources/heart_long.png");
		back = getToolkit().getImage("Resources/background.png");
		floor = getToolkit().getImage("Resources/floor.png");
		for(int k=0;k<11;k++) {
			floors[k]=floor;
		}
		score_font = new Font("Times New Roman", Font.PLAIN, 28);
		cake = getToolkit().getImage("Resources/cake.png");
		floor_manager = new int[floor_length];
		cake_manager = new int[floor_length];
		t1 = new Timer();
		t1.schedule(new timerTask(),25,25);

		//³¢¬ü­§
		Sound.play(0);//background music
		try {
			File file = new File("Resources/floor.txt");
			Scanner scanner = new Scanner(file);
			String[] tokens;
	        for(int k=0;k<floor_length;k++) {
	        	tokens = scanner.next().split(" ");
	        	floor_manager[k]=Integer.parseInt(tokens[0]);
	        }
	        scanner.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			File file = new File("Resources/cake.txt");
			Scanner scanner = new Scanner(file);
			String[] tokens;
	        for(int k=0;k<floor_length;k++) {
	        	tokens = scanner.next().split(" ");
	        	cake_manager[k]=Integer.parseInt(tokens[0]);
	        }
	        scanner.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		//³¯ªÛ¿o¡B¦¶¯Âè°
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		Thread t=new Thread() {
			public void run() {
				try {
					while(running) {
						arun();
						
						Thread.sleep(100);
       
					}
       
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		t.start();
		
	    setVisible(true);
	}
	//³¯ªÛ¿o¡B³¢¬ü­§
    public void jumping(){
    	if(key_jump2){
    		if(key_jump2_y==0){
    			key_jump2_y=250;
    		}
    		if(y<key_jump2_y){
    			key_jump=false;
    			key_jump2=false;
    			key_jump2_y=0;
    		}
    	}else if(key_jump2==false){
    		if(y<350){
    			key_jump=false;
    		}
    	}
    	if(key_jump){
    		y-=20;
    	}else if(y<480){
    		y+=20;
    	}else if(y<500){
    		y=500;
    	}
    }
    
    //¦¶¯Âè°
    public void falling(){
    	if((key_jump == false && y >= 500) && walk_length < -60 && floor_manager[2+walk_floor_length]==0){
			Sound.play(3);
    		y+=90;
			gameover();
		}
    }
    //³¢¬ü­§¡B³¯ªÛ¿o¡B¦¶¯Âè°
	public void arun() {
		System.out.println(1);
		i++;
		i=i%4;
		walk_length-=10;
		if(walk_length<-100) {
			walk_length=0;
			walk_floor_length+=1;
		}
		if(walk_length < -50) {
			int use=floor_manager[2+walk_floor_length];
			if(y > 450 && use==2){
				Sound.play(6);
				heart_value-=10;
				if(heart_value<=0) {
					heart_value=0;
				}
			}else if(y > 450 && use==3){
				Sound.play(6);
				heart_value-=10;
				if(heart_value<=0) {
					heart_value=0;
				}
			}else if(y > 350 && use==4){
				Sound.play(6);
				heart_value-=15;
				if(heart_value<=0) {
					heart_value=0;
				}
			}else if(key_slide == false && use==5){
				Sound.play(6);
				heart_value-=20;
				if(heart_value<=0) {
					heart_value=0;
				}
			}
		}
		
		this.repaint();
	}
	//³¢¬ü­§¡B³¯ªÛ¿o¡B¦¶¯Âè°
	class timerTask extends TimerTask{
		@Override
		public void run() {
			jumping();
			arun();
			if(heart_value<=0) {
				gameover();
			}
			falling();
			repaint();
		}
	}
	
	//³¢¬ü­§¡B³¯ªÛ¿o¡B¦¶¯Âè°
	public void paint(Graphics g) {
		super.paint(g);
		//³¢¬ü­§
		g.drawImage(back, 0, 0, this);
		g.drawImage(heart_long, 50, 67, heart_value, 21, null);
		g.drawImage(heart, 25, 50, 50, 50, this);
		g.setFont(score_font);
		g.setColor(Color.WHITE);
		g.drawString("Score:"+Integer.toString(score), 800, 75);
		//¦¶¯Âè°
		if(key_ori==false && key_slide==true){
			g.drawImage(slides[i], x, y+43, h, w-43, null);
		}else if(key_ori==true && key_slide==false){
			g.drawImage(images[i], x, y, h, w, null);
		}
		//³¯ªÛ¿o
		if(key_ori==false && key_jump==true){
			g.drawImage(jumps[i], x, y, h, w, null);
		}else if(key_ori==true && key_jump==false){
			g.drawImage(images[i], x, y, h, w, null);
		}
		
		System.out.println("i"+i);
		System.out.println("x"+x+"y"+y);
		paint_floor(g);
	}
	//³¢¬ü­§¡B³¯ªÛ¿o¡B¦¶¯Âè°
	public void paint_floor(Graphics g){
		for(int k=0;k<11;k++) {
			int use=floor_manager[k+walk_floor_length];
			int use2=h*k+walk_length;
			int use3=cake_manager[k+walk_floor_length];
			if(use!=0){
				g.drawImage(floors[k], use2, 600, w, h, null);
			}
			if(use==2) {
				g.drawImage(obstacle1, 25+use2, 550, 50, 50, null);
			}else if(use==3) {
				g.drawImage(obstacle2, 25+use2, 550, 50, 50, null);
			}else if(use==4) {
				g.drawImage(obstacle3, 25+use2, 450, 50, 150, null);
			}else if(use==5) {
				g.drawImage(fork, use2, 0, h, 540, null);
			}
			
			if(use3==1) {
				g.drawImage(cake, 25+use2, 550, 30, 30, null);
			}else if(use3==2) {
				g.drawImage(cake, 25+use2, 450, 30, 30, null);
			}else if(use3==3) {
				g.drawImage(cake, 25+use2, 450, 30, 30, null);
			}else if(use3==4) {
				g.drawImage(cake, 25+use2, 350, 30, 30, null);
			}else if(use3==5) {
				g.drawImage(cake, 25+use2, 550, 30, 30, null);
			}else if(use3==6) {
				g.drawImage(heart_eat, 25+use2, 550, 50, 50, null);
			}
		}
		int use=cake_manager[2+walk_floor_length];
		if(y > 450 && walk_length < -50 && use==6) {
			Sound.play(5);
			heart_value+=100;
			if(heart_value>300) {
				heart_value=300;
			}
			cake_manager[2+walk_floor_length]=0;
		}
		
		if(walk_length < -50) {
			if(y > 450 && y < 600 && use==1){
				Sound.play(4);
				score+=5;
				cake_manager[2+walk_floor_length]=0;
			}
			if(y > 350 && y < 500){
				if(use==2) {
					Sound.play(4);
					score+=5;
					cake_manager[2+walk_floor_length]=0;
				}else if(use==3) {
					Sound.play(4);
					score+=5;
					cake_manager[2+walk_floor_length]=0;
				}
			}
			if(y > 250 && y < 450 && use==4){
				Sound.play(4);
				score+=5;
				cake_manager[2+walk_floor_length]=0;
			}
			if(y > 480 && y < 650 && use==5){
				Sound.play(4);
				score+=5;
				cake_manager[2+walk_floor_length]=0;
			}
		}
    }

	//³¢¬ü­§¡B³¯ªÛ¿o¡B¦¶¯Âè°
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) { 
		case KeyEvent.VK_SPACE :
			if(key_jump==true){
				Sound.play(2);
				key_jump2=true;
			}
			if(key_jump==false){
				Sound.play(1);
				key_jump=true;
				key_ori=false;
			}
			break;
		case KeyEvent.VK_DOWN:
			key_slide = true;
			key_ori=false;
			break;
		}
		this.repaint();
	}
	
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) { 
	    	case KeyEvent.VK_SPACE :
            case KeyEvent.VK_DOWN:
	    		key_slide = false;
	    		key_ori=true;
	    		break;
		}
	}
	public void keyTyped(KeyEvent e) {
		
	}
	//³¢¬ü­§¡B³¯ªÛ¿o¡B¦¶¯Âè°
	public static void insertTable( int rank,String name,int score) {	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/g13?"
					+ "user=root&password=Olden8874&serverTimezone=UTC&useSSL=false");

			Statement stmt = conn.createStatement();
		
			PreparedStatement ps = conn.prepareStatement("INSERT INTO score(Rank,Name,Score) VALUES(?,?,?);"); 
			ps.setLong(1, rank);
			ps.setString(2, name); 
			ps.setLong(3, score);
			ps.executeUpdate();
			ResultSet rs1 = stmt.executeQuery("select *" + " from score"+ " where Rank");
		
			while (rs1.next()) {
				System.out.println(
						rs1.getInt(1) + "\t" + rs1.getString(2) + "\t" + rs1.getInt(3) );
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			System.out.println("InsertDB Exception :" + ex.toString()); 
		}
	}
	//¦¶¯Âè°
	public void input() {
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		String name =JOptionPane.showInputDialog( "Enter name" );
		insertTable( rank,name,score);
	}
	public void gameover(){
		running = false;
		input();
		System.exit(ABORT);
		
	}
	
}