import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Okno extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private double screenWidth = screenSize.getWidth();
	private double screenHeight = screenSize.getHeight();
	
	private static int width;
	private static int height;
	public Okno(int board_width,int board_height){
		width = board_width;
		height = board_height;
		Board board = new Board();
		add(board);
		setLocation((int)screenWidth/2-width/2,(int)screenHeight/2-height/2);
		setResizable(false);
		setTitle("SNAKE");
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
}
