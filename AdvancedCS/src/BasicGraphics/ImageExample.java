package graphics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

// we will extend JPanel (a built-in Java class). A panel will 
// have the graphics capabilities we want.
public class ImageExample {
	
	// constants that are predefined and won't change 
	// don't use 'magic numbers' in your code!!!

	
	// the width/height of our window - note I set this 
	// final bc I didn't allow the window size to change
	private final int width = 800, height = 600;
	
	// our images, will be assigned later.
	private Image img;
 
	// this is where we do the graphics initializations
	public ImageExample() {
		
		// the frame holds the panel. A frame is simply a container,
		// it does nothing but hold panels and other graphics tools
		JFrame frame = new JFrame();
		
		// set the window size - notice, no magic numbers!
		frame.setSize(width, height);
		
		// this ends the program when the close button is pressed
		// probably always a good idea to use this
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// add our customized panel to the container
		frame.add(new JPanel() {
			// defines how to paint our panel - this is called 
			// note that I never call this directly.
			// If I want to update my original graphics display, I call repaint()
			public void paint(Graphics g) {
			
				// painted at location 0, 0 and scaled to size width x height
				g.drawImage(img, 0, 0, width, height, this);	
			}
		});
		
		// this line centers the window upon startup
		frame.setLocationRelativeTo(null);
		
		// decide whether the user can resize the window - 
		// sometimes this is good, sometimes bad.
		// if you choose to set this true, make sure to be 
		// careful with your height/width variables!!
		frame.setResizable(false);
		
		// we need to tell the computer to make your frame and 
		// its contents visible (I don't know why this is automatically
		// set to false...)
		frame.setVisible(true);
		
		// decide whether you will need focus in your program.
		// focus is the ability for the program to pay attention 
		// to just one component - for example, if you have multiple 
		// text input boxes, we need to know which box to focus on
		// at all times
		frame.setFocusable(true);
		
		// load our image and save it for future use
		img = Toolkit.getDefaultToolkit().getImage("World Map.jpg");
		
		
		// get our functionality going (if we have any)
		run();
	}

	// This is what we want the code to do as the panel is open.
	public void run() {

		// note - I don't have anything besides graphics setup in
		// this code, so my program won't actually 'do' anything.
		// If I wanted to 'do something', this is where I would do that
	}
	
	// very simple main method - create our graphics object
	public static void main(String[] args) {
		new ImageExample(); 
	}
}


