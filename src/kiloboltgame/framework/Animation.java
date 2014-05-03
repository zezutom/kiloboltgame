package kiloboltgame.framework;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Animation {

	private List<AnimFrame> frames;
	
	private int currentFrame;
	
	private long animTime;
	
	private long totalDuration;
	
	public Animation() {
		frames = new ArrayList<>();
		totalDuration = 0;
		
		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}
	
	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;
			
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}
			
			while (animTime > getFrame(currentFrame).getEndTime()) {
				currentFrame++;
			}
		}
	}
	
	private AnimFrame getFrame(int i) {
		return (i > 0 && i < frames.size()) ? frames.get(i) : AnimFrame.emptyFrame();
	}
	
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).getImage();
		}
	}
}
