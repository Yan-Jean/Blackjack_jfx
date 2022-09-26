package mypackage.yj_tp3;

public class BlackjackGame {
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private double betAmount;
    private final double minBet;
    private final double maxBet;
    private double totalMoney;


    public BlackjackGame() {
        this.deck = new Deck();
        this.playerHand = new Hand("Player");
        this.dealerHand = new Hand("Dealer");
        this.minBet = 5.0;
        this.maxBet = 1000.0;
    }


    public void loadMoney() {totalMoney = 100;}

    public boolean isOutOfMoney() {
        return totalMoney < minBet;
    }


    public void resetMoney() {
        totalMoney = 100;
    }

    public double getMinBet() {
        return minBet;
    }

    public double getMaxBet() {
        return maxBet;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setBet(double betAmount) {
        this.betAmount = betAmount;
    }

    public double getBet() {
        return betAmount;
    }

    public void deal() {
        for (int i = 0; i < 2; i++) {
            playerHand.addCard(deck.drawCard());
            dealerHand.addCard(deck.drawCard());
        }
    }

    public void hit() {
        playerHand.addCard(deck.drawCard());
    }

    public void stand() {
        if (dealerHand.getPoints() < 17) {
            while (dealerHand.getPoints() < 17)
                dealerHand.addCard(deck.drawCard());
        }
    }

    public Card getDealerShowCard() {
        return dealerHand.getCard().get(1);
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public boolean isBlackjackOrBust() {
        return playerHand.isBlackjack() || playerHand.isBust()
                || dealerHand.isBlackjack() || dealerHand.isBust();
    }

    public boolean isPush() {
        return (playerHand.getPoints() <= 21 && playerHand.getPoints() == dealerHand.getPoints()) && !playerHand.isBlackjack();
    }

    public boolean playerWins() {
        return playerHand.isBlackjack()
                || playerHand.getPoints() > dealerHand.getPoints() && playerHand.getPoints() <= 21
                || dealerHand.isBust() && playerHand.getPoints() <= 21;
    }

    public void addBetToTotal() {
        totalMoney += betAmount;
    }

    public void addBlackjackToTotal() {
        totalMoney += betAmount + betAmount / 2;
    }

    public void subtractBetFromTotal() {
        totalMoney -= betAmount;
    }

    public double saveMoney() {
        return totalMoney;
    }
}
