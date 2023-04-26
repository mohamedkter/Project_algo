
public class Rectangle {
	
    private String x, y, width, height;

    public Rectangle( String string, String string2, String string3, String string4) {
       
        this.x = string;
        this.y = string2;
        this.width = string3;
        this.height = string4;
    }

    // Setters
   
    
    public String toString() {
        return "Rectangle(" + " x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ")";
    }
}
