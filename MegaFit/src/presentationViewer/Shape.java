package presentationViewer;

import java.awt.Color;

abstract class Shape extends VisualElement {
	protected float width, height, gx1, gx2, gy1, gy2;
	protected Color gcolour1, gcolour2, lineColour;

	public Shape(int startTime, int endTime, int startClick,
			int endClick, float x_pos, float y_pos, float width,
			float height, float gx1, float gx2, float gy1, float gy2,
			Color gcolour1, Color gcolour2, Color lineColour, String targetLoc) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.startClick = startClick;
		this.endClick = endClick;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.width = width;
		this.height = height;
		this.gx1 = gx1;
		this.gx2 = gx2;
		this.gy1 = gy1;
		this.gy2 = gy2;
		this.gcolour1 = gcolour1;
		this.gcolour2 = gcolour2;
		this.lineColour = lineColour;
		this.targetLoc = targetLoc;
	}
	
}
