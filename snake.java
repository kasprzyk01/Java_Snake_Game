
public class snake {

	public static int width = 600;
	public static int height = 500;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Okno board = new Okno(width, height);
		board.setVisible(true);
		

	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}

}
