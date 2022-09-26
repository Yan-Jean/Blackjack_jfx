package mypackage.yj_tp3;
import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private final String user;

    public Hand(String user) {
        this.user = user;
        this.hand = new ArrayList<Card>();
    }

    public ArrayList<Card> getCard() {
        return this.hand;
    }

    public int getPoints() {
        int total = 0;

        for (Card c : hand) {
            total += c.getPoints();
            if (total > 21) {
                total = 0;
                for (Card a : hand) {
                    if (a.isAce())
                        total += 1;
                    else
                        total += a.getPoints();
                }
            }
        }
        return total;
    }

    public String showCardValue(Card card) {
        return card.getValue();
    }

    public String showCardColor(Card card) {
        return card.getColor();
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public boolean isBlackjack() {
        return this.getPoints() == 21 && this.hand.size() == 2;
    }

    public boolean isBust() {
        return this.getPoints() > 21;
    }

    public String display() {
        StringBuilder showCard = new StringBuilder();
        for (int i = 0; i < hand.size(); i++) {
            showCard.append(" - - - - -\t");
            // Skip to next printable line.
            if (i == hand.size() - 1)
                showCard.append("\n");
        }
        for (int i = 0; i < hand.size(); i++) {
            showCard.append("     ").append(showCardValue(hand.get(i))).append("     \t");
            if (i == hand.size() - 1)
                showCard.append("\n");
        }
        for (int i = 0; i < hand.size(); i++) {
            showCard.append("     ").append(showCardColor(hand.get(i))).append("     \t");
            if (i == hand.size() - 1)
                showCard.append("\n");


        }
        showCard.append(" - - - - -\t".repeat(hand.size()));
        return showCard.toString();

    }

    public void throwCards() {
        this.hand = new ArrayList<Card>();
    }

    @Override
    public String toString() {
        return display();
    }
}
