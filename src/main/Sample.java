package main;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Sample extends Applet implements Runnable, KeyListener, Tetorisu{
	Setting set = new Setting();
	int hor = 0;

	public void init(){
		addKeyListener(this);
		Thread th = new Thread(this);
		th.start();
	}
	public void run(){
		try{
			set.make_block();
			set.init_var();
			for (set.fall=0; set.fall<18; set.fall++) {
				repaint();
				Thread.sleep(10000);
			}
		}
		catch(InterruptedException e){}

	}

	public void paint(Graphics g){
		if(set.gameover_flag != 0) {
			System.out.print("gameover");
		}else{
			//set.make_block();
			set.gameover();
			set.make_field();
			set.init_field();
			set.freeze_block();
			draw(g);
			

			requestFocusInWindow();
			
			set.clear_field();
			
		}
		
	}

	public void keyPressed(KeyEvent e){
		set.make_collision_field();
		int x,y;
		int fall_flag = 0;
		int rl;
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			rl = 1;
			set.side_check(rl);
		    repaint();
		}else if (key == KeyEvent.VK_LEFT) {
			rl = -1;
			set.side_check(rl);
		    repaint();
		}else if (key == KeyEvent.VK_DOWN) {
			for(y=0;y<BLOCK_HEIGHT;y++){
				for(x=0;x<BLOCK_WIDTH;x++){
			     if(block[y][x] != 0){
			      if(collision_field[set.fall + (y + 1)][set.side + x] != 0){
			       fall_flag++;
			      }
			     }
			    }
			}
		   if(fall_flag == 0){
		    set.fall++;
		   }
		   repaint();
		}else if(key == KeyEvent.VK_UP) {
			rl = 1;
			set.turn(rl);
			repaint();
		}else if(key == KeyEvent.VK_Z) {
			rl = -1;
			set.turn(rl);
			repaint();
		}
	}
	public void keyReleased(KeyEvent e){
		
   	}
   	public void keyTyped(KeyEvent e){

	}
   	
   	public static void draw(Graphics g){
   		String str = "";
		for (int y=0; y<FIELD_HEIGHT; y++) {
			for (int x=0; x<FIELD_WIDTH; x++) {
				if(field[y][x]==0){
					str += "　";
				}else if (field[y][x]==1) {
					str += "□";
				}else if (field[y][x]==9) {
					str += "■";
				}else{
					str += "×";
				}
			}
		}
		String str1 = str.substring(0,16);
		String str2 = str.substring(16,32);
		String str3 = str.substring(32,48);
		String str4 = str.substring(48,64);
		String str5 = str.substring(64,80);
		String str6 = str.substring(80,96);
		String str7 = str.substring(96,112);
		String str8 = str.substring(112,128);
		String str9 = str.substring(128,144);
		String str10 = str.substring(144,160);
		String str11 = str.substring(160,176);
		String str12 = str.substring(176,192);
		String str13 = str.substring(192,208);
		String str14 = str.substring(208,224);
		String str15 = str.substring(224,240);
		String str16 = str.substring(240,256);
		String str17 = str.substring(256,272);
		String str18 = str.substring(272,288);
		String str19 = str.substring(288,304);
		String str20 = str.substring(304,320);
		String str21 = str.substring(320,336);
		String str22 = str.substring(336,352);
		String str23 = str.substring(352,368);

		g.drawString(str1,10,10);
		g.drawString(str2,10,20);
		g.drawString(str3,10,30);
		g.drawString(str4,10,40);
		g.drawString(str5,10,50);
		g.drawString(str6,10,60);
		g.drawString(str7,10,70);
		g.drawString(str8,10,80);
		g.drawString(str9,10,90);
		g.drawString(str10,10,100);
		g.drawString(str11,10,110);
		g.drawString(str12,10,120);
		g.drawString(str13,10,130);
		g.drawString(str14,10,140);
		g.drawString(str15,10,150);
		g.drawString(str16,10,160);
		g.drawString(str17,10,170);
		g.drawString(str18,10,180);
		g.drawString(str19,10,190);
		g.drawString(str20,10,200);
		g.drawString(str21,10,210);
		g.drawString(str22,10,220);
		g.drawString(str23,10,230);
   	}

}