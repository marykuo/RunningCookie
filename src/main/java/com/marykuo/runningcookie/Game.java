package com.marykuo.runningcookie;

import java.io.FileNotFoundException;

public class Game extends Thread {

	public static void main(String[] args) throws FileNotFoundException {
			
		Thread thread = new Thread(new Runnable() {
			public void run() { 
				try {
					new WalkMian();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	    thread.start();  
	}
}