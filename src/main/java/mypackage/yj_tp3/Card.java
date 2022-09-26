package mypackage.yj_tp3;
public class Card {
    private String color;
    private String value;
    private int points;

    public Card(String color, String value, int points) {
        this.color = color;
        this.value = value;
        this.points = points;
    }
   public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }


    public int getPoints() {
        return points;
    }

    public boolean isAce() {
        return color.equalsIgnoreCase("A");
    }

    public String display(){
        return "- - - - -\n" + "    " + value + "\n" + "    " + color + "\n- - - - -";
    }

}
