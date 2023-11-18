package com.marykuo.runningcookie;

import java.awt.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static com.marykuo.runningcookie.Sound.*;

public class WalkMian extends JFrame implements KeyListener {
	private final Image backGround = getToolkit().getImage("./image/background.png");
	private final Image  cake = getToolkit().getImage("./image/cake.png");
	private final Image heart = getToolkit().getImage("./image/heart.png");
	private final Image heart_eat = getToolkit().getImage("./image/heart_eat.png");
	private final Image heart_long = getToolkit().getImage("./image/heart_long.png");

	Image[] player_run_images = new Image[4];
    Image[] player_slide_images = new Image[4];
    Image[] player_jump_images = new Image[4];
    Image[] floors = new Image[11];
	Image[] obstacles = new Image[4];

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
		player_run_images[0]=getToolkit().getImage("./image/run-0.png");
		player_run_images[1]=getToolkit().getImage("./image/run-1.png");
		player_run_images[2]=getToolkit().getImage("./image/run-2.png");
		player_run_images[3]=getToolkit().getImage("./image/run-3.png");

		player_slide_images[0] = getToolkit().getImage("./image/slide-2.png");
		player_slide_images[1] = getToolkit().getImage("./image/slide-3.png");
		player_slide_images[2] = player_slide_images[0];
		player_slide_images[3] = player_slide_images[1];

		player_jump_images[0] = getToolkit().getImage("./image/jump-0.png");
		player_jump_images[1] = getToolkit().getImage("./image/jump-1.png");
		player_jump_images[2] = getToolkit().getImage("./image/jump-2.png");
		player_jump_images[3] = getToolkit().getImage("./image/jump-3.png");

		//������
		obstacles[0] = getToolkit().getImage("./image/obstacle1.png");
		obstacles[1] = getToolkit().getImage("./image/obstacle2.png");
		obstacles[2] = getToolkit().getImage("./image/obstacle3.jpg");
		obstacles[3] = getToolkit().getImage("./image/fork.png");

		Image floor = getToolkit().getImage("./image/floor.png");
		for (int k = 0; k < 11; k++) {
			floors[k] = floor;
		}
		score_font = new Font("Times New Roman", Font.PLAIN, 28);
		floor_manager = new int[floor_length];
		cake_manager = new int[floor_length];
		t1 = new Timer();
		t1.schedule(new timerTask(),25,25);

		//������
		Sound.play(BACKGROUND);//background music
		try {
			InputStream file = getClass().getClassLoader().getResourceAsStream("./floor.txt");
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
			InputStream file = getClass().getClassLoader().getResourceAsStream("./cake.txt");
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

		//���ۿo�B�����
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
	//���ۿo�B������
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

    //�����
    public void falling(){
    	if((key_jump == false && y >= 500) && walk_length < -60 && floor_manager[2+walk_floor_length]==0){
			Sound.play(PLAYER_FALLING);
    		y+=90;
			gameover();
		}
    }
    //�������B���ۿo�B�����
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
				Sound.play(PLAYER_HIT);
				heart_value-=10;
				if(heart_value<=0) {
					heart_value=0;
				}
			}else if(y > 450 && use==3){
				Sound.play(PLAYER_HIT);
				heart_value-=10;
				if(heart_value<=0) {
					heart_value=0;
				}
			}else if(y > 350 && use==4){
				Sound.play(PLAYER_HIT);
				heart_value-=15;
				if(heart_value<=0) {
					heart_value=0;
				}
			}else if(key_slide == false && use==5){
				Sound.play(PLAYER_HIT);
				heart_value-=20;
				if(heart_value<=0) {
					heart_value=0;
				}
			}
		}

		this.repaint();
	}
	//�������B���ۿo�B�����
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

	//�������B���ۿo�B�����
	public void paint(Graphics g) {
		super.paint(g);
		//������
		g.drawImage(backGround, 0, 0, this);
		g.drawImage(heart_long, 50, 67, heart_value, 21, null);
		g.drawImage(heart, 25, 50, 50, 50, this);
		g.setFont(score_font);
		g.setColor(Color.WHITE);
		g.drawString("Score:"+Integer.toString(score), 800, 75);
		//�����
		if(key_ori==false && key_slide==true){
			g.drawImage(player_slide_images[i], x, y+43, h, w-43, null);
		}else if(key_ori==true && key_slide==false){
			g.drawImage(player_run_images[i], x, y, h, w, null);
		}
		//���ۿo
		if(key_ori==false && key_jump==true){
			g.drawImage(player_jump_images[i], x, y, h, w, null);
		}else if(key_ori==true && key_jump==false){
			g.drawImage(player_run_images[i], x, y, h, w, null);
		}

		System.out.println("i"+i);
		System.out.println("x"+x+"y"+y);
		paint_floor(g);
	}
	//�������B���ۿo�B�����
	public void paint_floor(Graphics g){
		for(int k=0;k<11;k++) {
			int use=floor_manager[k+walk_floor_length];
			int use2=h*k+walk_length;
			int use3=cake_manager[k+walk_floor_length];
			if(use!=0){
				g.drawImage(floors[k], use2, 600, w, h, null);
			}
			if(use==2) {
				g.drawImage(obstacles[0], 25+use2, 550, 50, 50, null);
			}else if(use==3) {
				g.drawImage(obstacles[1], 25+use2, 550, 50, 50, null);
			}else if(use==4) {
				g.drawImage(obstacles[2], 25+use2, 450, 50, 150, null);
			}else if(use==5) {
				g.drawImage(obstacles[3], use2, 0, h, 540, null);
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
			Sound.play(EAT_HEART_POTION);
			heart_value+=100;
			if(heart_value>300) {
				heart_value=300;
			}
			cake_manager[2+walk_floor_length]=0;
		}

		if(walk_length < -50) {
			if(y > 450 && y < 600 && use==1){
				Sound.play(EAT_CAKE);
				score+=5;
				cake_manager[2+walk_floor_length]=0;
			}
			if(y > 350 && y < 500){
				if(use==2) {
					Sound.play(EAT_CAKE);
					score+=5;
					cake_manager[2+walk_floor_length]=0;
				}else if(use==3) {
					Sound.play(EAT_CAKE);
					score+=5;
					cake_manager[2+walk_floor_length]=0;
				}
			}
			if(y > 250 && y < 450 && use==4){
				Sound.play(EAT_CAKE);
				score+=5;
				cake_manager[2+walk_floor_length]=0;
			}
			if(y > 480 && y < 650 && use==5){
				Sound.play(EAT_CAKE);
				score+=5;
				cake_manager[2+walk_floor_length]=0;
			}
		}
    }

	//�������B���ۿo�B�����
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE :
			if(key_jump){
				Sound.play(PLAYER_JUMP2);
				key_jump2=true;
			} else {
				Sound.play(PLAYER_JUMP);
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
	//�������B���ۿo�B�����
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
	//�����
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