package domain;

public class CellStyleObject {
	boolean top;
	boolean bottom;
	boolean left;
	boolean right;
	boolean ALIGN_LEFT;
	boolean ALIGN_RIGHT;
	boolean numberType;
	boolean quantityType;
	
	public CellStyleObject(boolean top, boolean bottom, boolean left, boolean right, boolean ALIGN_LEFT, boolean ALIGN_RIGHT, boolean numberType, boolean quantityType) {
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.ALIGN_LEFT = ALIGN_LEFT;
		this.ALIGN_RIGHT = ALIGN_RIGHT;
		this.numberType = numberType;
		this.quantityType = quantityType;
	}

	public String getResult() {
		return String.valueOf(top) + bottom + left + right + ALIGN_LEFT + ALIGN_RIGHT + numberType + quantityType;
	}
}
