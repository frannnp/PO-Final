package backend.format;


public class ColorData {
    private final int red, green, blue;
    private final double alpha;

    public ColorData(int red, int green, int blue, double alpha) { //todo validar rango de color
        this.red   = red;
        this.green = green;
        this.blue  = blue;
        this.alpha = alpha;
    }

    public int getRed()   { return red;   }
    public int getGreen() { return green; }
    public int getBlue()  { return blue;  }
    public double getAlpha(){ return alpha;}

    @Override
    public String toString() {
        return String.format("rgba(%d,%d,%d,%.2f)", red, green, blue, alpha);
    }
}