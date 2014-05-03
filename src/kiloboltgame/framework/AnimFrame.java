package kiloboltgame.framework;

import java.awt.Image;

public class AnimFrame {

	private Image image; 
	private long endTime;
	
	public AnimFrame(Image image, long endTime) {
		super();
		this.image = image;
		this.endTime = endTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
		
	public Image getImage() {
		return image;
	}

	public static AnimFrame emptyFrame() {
		return new AnimFrame(null, -1);
	}
}
