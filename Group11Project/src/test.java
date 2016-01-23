//
//	SCC210 Example code
//
//		Andrew Scott, 2015
//
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class Test {
	private static int screenWidth  = 1280;
	private static int screenHeight = 720;

	//
	// The Java install comes with a set of fonts but these will
	// be on different filesystem paths depending on the version
	// of Java and whether the JDK or JRE version is being used.
	//
	private static String JavaVersion =
			Runtime.class.getPackage( ).getImplementationVersion( );
	private static String JdkFontPath =
			"C:\\Program Files\\Java\\jdk" + JavaVersion +
					"\\jre\\lib\\fonts\\";
	private static String JreFontPath =
			"C:\\Program Files\\Java\\jre" + JavaVersion +
					"\\lib\\fonts\\";

	private static int fontSize     = 48;
	private static String FontFile  = "LucidaSansRegular.ttf";
	private static String ImageFile = "team.jpg";

	private static String Title   = "Hello SCC210!";
	private static String Message = "Round and round...";

	private String FontPath;	// Where fonts were found

	private ArrayList<Actor> actors = new ArrayList<Actor>( );

	private abstract class Actor {
		Transformable obj;   // Base object

		int x  = 0;	// Current X-coordinate
		int y  = 0;	// Current Y-coordinate

		int r  = 0;	// Change in rotation per cycle
		int dx = 5;	// Change in X-coordinate per cycle
		int dy = 5;	// Change in Y-coordinate per cycle

		//
		// Is point x, y within area occupied by this object?
		//
		// This should really be done with bounding boxes not points
		// ...we should check whether any point in bounding box
		//    is covered by this object
		//
		boolean within (int x, int y) {
			// Should check object bounds here
			// -- we'd normally assume a simple rectangle
			//    ...and override as necessary
			return false;
		}

		//
		// Work out where object should be for next frame
		//
		void calcMove(int minx, int miny, int maxx, int maxy) {
			//
			// Add deltas to x and y position
			//
			x += dx;
			y += dy;

			//
			// Check we've not hit screen bounds
			// x and y would normally be the object's centre point
			// rather than its top corner so we can more easily
			// rotate objects and handle circular shapes
			//
			if (x <= minx || x >= maxx) { dx *= -1; x += dx; }
			if (y <= miny || y >= maxy) { dy *= -1; y += dy; }

			//
			// Check we've not collided with any other actor
			//
			for (Actor a : actors) {
				if (a.obj != obj && a.within(x, y)) {
					dx *= -1; x += dx;
					dy *= -1; y += dy;
				}
			}
		}

		//
		// Rotate and reposition the object
		//
		void performMove( ) {
			obj.rotate(r);
			obj.setPosition(x,y);
		}

		//
		// Render the object at its new position
		//
		void draw(RenderWindow w) {
			w.draw((Drawable)obj);
		}
	}

	private class Message extends Actor {
		public Message(int x, int y, int r, String message, Color c) {
			//
			// Load the font
			//
			Font sansRegular = new Font( );
			try {
				sansRegular.loadFromFile(
						Paths.get(FontPath+FontFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}

			Text text = new Text (message, sansRegular, fontSize);
			text.setColor(c);
			text.setStyle(Text.BOLD | Text.UNDERLINED);

			FloatRect textBounds = text.getLocalBounds( );
			// Find middle and set as origin/ reference point
			text.setOrigin(textBounds.width / 2,
					textBounds.height / 2);

			this.x = x;
			this.y = y;
			this.r = r;

			obj = text;
		}
	}

	private class Image extends Actor {
		public Image(int x, int y, int r, String textureFile) {
			//
			// Load image/ texture
			//
			Texture imgTexture = new Texture( );
			try {
				imgTexture.loadFromFile(Paths.get(textureFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}
			imgTexture.setSmooth(true);

			Sprite img = new Sprite(imgTexture);
			img.setOrigin(Vector2f.div(
					new Vector2f(imgTexture.getSize()), 2));

			this.x = x;
			this.y = y;
			this.r = r;

			obj = img;
		}
	}

	private class Bubble extends Actor {
		private int radius;

		public Bubble(int x, int y, int radius, Color c,
					  int transparency) {
			CircleShape circle = new CircleShape(radius);
			circle.setFillColor(new Color(c, transparency));
			circle.setOrigin(radius, radius);

			this.x = x;
			this.y = y;
			this.radius = radius;

			obj = circle;
		}

		//
		// Default method typically assumes a rectangle,
		// so do something a little different
		//
		@Override
		public boolean within (int px, int py) {
			//
			// In this example, For simplicty...
			// - We just treat circle as a box
			// - x, y is the top corner rather than the centre
			// - We use points x,y and px, py not bounding boxes
			//
			if (px > x && px < x + 2 * radius &&
					py > y && py < y + 2 * radius) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public void run ( ) {

		//
		// Check whether we're running from a JDK or JRE install
		// ...and set FontPath appropriately.
		//
		if ((new File(JreFontPath)).exists( )) FontPath = JreFontPath;
		else FontPath = JdkFontPath;

		//
		// Create a window
		//
		RenderWindow window = new RenderWindow( );
		window.create(new VideoMode(screenWidth, screenHeight),
				Title,
				WindowStyle.DEFAULT);

		window.setFramerateLimit(30); // Avoid excessive updates

		//
		// Create some actors
		//
		actors.add(new Image(screenWidth / 4, screenHeight / 4,
				10, ImageFile));
		actors.add(new Message(screenWidth / 2, screenHeight / 2,
				10, Message, Color.BLACK));
		actors.add(new Bubble(500, 500, 20, Color.MAGENTA, 128));
		actors.add(new Bubble(600, 600, 20, Color.YELLOW,  128));
		actors.add(new Bubble(500, 600, 20, Color.BLUE,    128));
		actors.add(new Bubble(600, 500, 20, Color.BLACK,   128));

		//
		// Main loop
		//
		while (window.isOpen( )) {
			// Clear the screen
			window.clear(Color.WHITE);

			// Move all the actors around
			for (Actor actor : actors) {
				actor.calcMove(0, 0, screenWidth, screenHeight);
				actor.performMove( );
				actor.draw(window);
			}

			// Update the display with any changes
			window.display( );

			// Handle any events
			for (Event event : window.pollEvents( )) {
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close( );
				}
			}
		}
	}

	public static void main (String args[ ]) {
		Test t = new Test( );
		t.run( );
	}
}
