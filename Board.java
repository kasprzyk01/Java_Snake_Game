import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private snake waz = new snake();
	private java.awt.Image ball;
	private java.awt.Image food;
	private java.awt.Image brick1;
	private java.awt.Image brick2;
	private Timer timer;
	private Random generator;
	private enum Direction{
		NONE,
		RIGHT,
		LEFT,
		UP,
		DOWN;
	}
	Direction activeDirection = Direction.RIGHT;
	private enum GameState{
		ACTIVE,
		PAUSED,
		GAMEOVER;
	}
	private GameState state = GameState.ACTIVE;
	private ArrayList<Integer> Lista_x = new ArrayList<>();
	private ArrayList<Integer> Lista_y = new ArrayList<>();
	private final int speed = 80;
	private static final int dot_size = 20;
	private static final int dots_beginning =100 ;
	private static int dots = dots_beginning ;	
	private static int x_pos_food;
	private static int y_pos_food;


	public Board(){


		addKeyListener(new Keys());
		setBackground(Color.yellow);
		setFocusable(true);
		setPreferredSize(new Dimension(waz.getWidth(),waz.getHeight()));	
		initialPos();
		placeFood();
		loadImage();

	}

	private void initialPos(){
		if(state == GameState.ACTIVE){
			for(int i=0; i<dots+1; i++){

				Lista_x.add(100-(i*20));
				Lista_y.add(40);
			}
			timer = new Timer(speed, this);
			timer.start();
			placeFood();}

	}

	private void placeFood(){
		generator = new Random();
		int x_pos_food2 = generator.nextInt(waz.getWidth()-100);
		int y_pos_food2 = generator.nextInt(waz.getHeight()-100);
		while(x_pos_food2 % 20 !=0 ){
			x_pos_food2++;
		}
		while(y_pos_food2 % 20 !=0 ){
			y_pos_food2++;
		}
		if(x_pos_food2 < 20){
			x_pos_food2 = x_pos_food2+20;
		}
		if(x_pos_food2 > 480){
			x_pos_food2 = 440;
		}
		if(y_pos_food2 < 20){
			y_pos_food2 = 40; 		
		}
		if(y_pos_food2 > 380){
			y_pos_food2 = 300;
		}
		x_pos_food = x_pos_food2;
		y_pos_food = y_pos_food2;

	}

	private void  loadImage() {
		ImageIcon image = new ImageIcon("dot.png");
		ball = image.getImage();	

		ImageIcon image2 = new ImageIcon("dot.png");
		food = image2.getImage();

		ImageIcon image3 = new ImageIcon("block1.png");
		brick1 = image3.getImage();

		ImageIcon image4 = new ImageIcon("block2.png");
		brick2 = image4.getImage();

	}

	private void setPosition(){

		if(state == GameState.ACTIVE){
			for(int i=dots; i>0 ;i--){
				Lista_x.set(i, Lista_x.get(i-1));
				Lista_y.set(i, Lista_y.get(i-1));
			}
			setFirstPosition();
		}
		else if (state == GameState.GAMEOVER){

			timer.stop();
			setBackground(Color.red);
			int n = JOptionPane.showConfirmDialog(this, " Wynik: "+dots+"Piotreł to palantos,  Jeszcze raz?", "xd", JOptionPane.YES_NO_OPTION);
			Lista_x.clear();
			Lista_y.clear();
			if(n == 0){
				state = GameState.ACTIVE;
				dots = dots_beginning;
				initialPos();
				setBackground(Color.yellow);
				activeDirection = Direction.RIGHT;

			}
			if(n == 1){
				System.exit(0);
			}
		}}
	private void setFirstPosition(){
		switch(activeDirection){
		case RIGHT:
			Lista_x.set(0, Lista_x.get(0)+dot_size);	
			break;
		case LEFT:
			Lista_x.set(0, Lista_x.get(0)-dot_size);
			break;
		case DOWN:
			Lista_y.set(0, Lista_y.get(0)+dot_size);
			break;
		case UP:
			Lista_y.set(0, Lista_y.get(0)-dot_size);
			break;
		case NONE:
			break;
		}}

	private int checkDirection(){

		if(Lista_x.get(0) > Lista_x.get(1) && Lista_y.get(0) == Lista_y.get(1)){
			return 1;
		}
		if(Lista_x.get(0) < Lista_x.get(1) && Lista_y.get(0) == Lista_y.get(1)){
			return 2;
		}

		if(Lista_x.get(0) == Lista_x.get(1) && Lista_y.get(0) > Lista_y.get(1)){
			return 3;
		}

		if(Lista_x.get(0) == Lista_x.get(1) && Lista_y.get(0) < Lista_y.get(1)){
			return 4;
		}
		return 0;
	}
	private void checkCollision(){
		if(Math.abs(Lista_x.get(0) - x_pos_food) < 10  &&  Math.abs(Lista_y.get(0) - y_pos_food) < 10){
			placeFood();
			dots++;
			Lista_x.add(0);
			Lista_y.add(0);
		}
	}
	private void checkSelfCollision(){
		for(int i=4;i<dots;i++){
			if(Math.abs(Lista_x.get(0) - Lista_x.get(i))<10){
				if(Math.abs(Lista_y.get(0) - Lista_y.get(i))<10){
					state = GameState.GAMEOVER;}
			}}
	}
	private void checkBrickCollision(){

		if(Lista_x.get(0) < -20 || Lista_x.get(0) > 495 || Lista_y.get(0) < 0 || Lista_y.get(0)>390){
			state = GameState.GAMEOVER;
		}

	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);}

	public void doDrawing(Graphics g){
		for(int i=0;i<dots;i++){
			g.drawImage(ball, Lista_x.get(i),Lista_y.get(i), this);
		}

		g.drawImage(food, x_pos_food, y_pos_food, this);
		for(int i = 0; i < 13 ; i++){
			if(i<11){
				g.drawImage(brick2, 567, 1+i*44,this);
				g.drawImage(brick2, 0, 1+i*44,this);
			}
			g.drawImage(brick1, 0+i*44, 2, this);
			g.drawImage(brick1, 0+i*44, 443, this);
		}
		Toolkit.getDefaultToolkit().sync();

	}
	public void actionPerformed(ActionEvent e){
		setPosition();
		checkSelfCollision();
		checkCollision();
		checkBrickCollision();
		repaint();

	}
	private class Keys extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			int direction = checkDirection();
			if(key == KeyEvent.VK_RIGHT && direction != 2){
				activeDirection = Direction.RIGHT;
			}
			if(key == KeyEvent.VK_LEFT && direction != 1){
				activeDirection = Direction.LEFT;
			}
			if(key == KeyEvent.VK_UP && direction != 3){
				activeDirection = Direction.UP;
			}
			if(key == KeyEvent.VK_DOWN && direction != 4){
				activeDirection = Direction.DOWN;
			}


		}
	}
}
