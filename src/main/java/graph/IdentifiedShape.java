package graph;

/**识别的形状结果*/
public enum IdentifiedShape {

	/**线段*/
	Line("线段"),
	/**圆*/
	Round("圆"),
	/**角*/
	Angle("角"),
	/**三角形*/
	Triangle("三角形"), 
	/**矩形*/
	Rectangle("矩形"), 
	/**正方形*/
	Square("正方形"), 
	/**五边形*/
	Pentagon("五边形"), 
	/**不可识别*/
	Unidentify("不可识别");
	
	private String value;

    private IdentifiedShape(String value){
        this.value = value;
    }

    public static IdentifiedShape getIdentifiedShapeByValue(String value) {
        for (IdentifiedShape c : IdentifiedShape.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        return null;
    }
    
    public String toString() {
    	return this.value;
    }
}
