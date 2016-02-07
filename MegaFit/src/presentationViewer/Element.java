package presentationViewer;

abstract class Element {

	protected int startTime, endTime, startClick, endClick;
	
	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getEndClick() {
		return startClick;
	}

	public int getStartClick() {
		return endClick;
	}
}
