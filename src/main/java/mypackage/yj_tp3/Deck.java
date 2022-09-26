package mypackage.yj_tp3;

public class Deck {

    private final Card[] deck;
    private int currentCardIndex;

    public Deck (){
        deck = new Card[52];
        String [] allValues = {"2", "3", "4", "5", "6", "7", "8", "9", "IO", "J", "Q", "K", "A"};
        String [] allColors = {"♥", "♦", "♣", "♠"};
        int counter = 0;
        for(String value : allValues){
            for(String color : allColors){
                int points = switch(value){
                    case "IO","J", "Q", "K" -> 10;
                    case "A" -> 11;
                    default -> Integer.parseInt(value);
                };
                deck[counter] = new Card(value,color,points);
                counter++;
            }
        }
        shuffleDeck();
    }

    private void shuffleDeck() {
        for(int i = 51; i > 0; i--){
            int j = (int)(Math.random() * 52);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    public Card drawCard() {
        if (currentCardIndex == 51) {
            Card currCard = deck[currentCardIndex];
            shuffleDeck();
            return currCard;
        }
        else
            return deck[currentCardIndex++];
    }

}
