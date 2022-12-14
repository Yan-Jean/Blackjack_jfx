package mypackage.yj_tp3;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.nio.charset.MalformedInputException;
import java.security.DrbgParameters;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Locale;

public class BlackjackController {

    // Instantiation of the blackjack game
    BlackjackGame game = new BlackjackGame();

    // JFX attributes
    @FXML
    private Button btnExit,btnHit,btnPlay,btnStand;
    @FXML
    private TextField tfBet, tfDealerPoints, tfMoney, tfPlayerPoints, tfResult;
    @FXML
    private TextArea tfDealerCards, tfPlayerCards;


    // JFX events
    @FXML
    void btnExitClicked(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void btnHitClicked(ActionEvent event) {
        game.hit();
        tfPlayerCards.setText(game.getPlayerHand().toString());
        tfPlayerPoints.setText("" + game.getPlayerHand().getPoints());
        if (game.getPlayerHand().isBust()){
            showWinner();
        }
    }

    @FXML
    void btnPlayClicked(ActionEvent event) {
        lunchPlayBtn();
    }
    @FXML
    void btnStandClicked(ActionEvent event) {
        game.stand();
        tfDealerCards.setText(game.getDealerHand().toString());
        showWinner();
    }

    // Set the table for the first game.
    public void initialize(){
        btnPlay.setDisable(true);
        btnHit.setDisable(true);
        btnStand.setDisable(true);
        game.loadMoney();
        tfMoney.setText("" + formatNumber(game.getTotalMoney()));
    }

    // Get the game started
    private void startNewGame(){
        game.getPlayerHand().throwCards();
        game.getDealerHand().throwCards();
        game.deal();
        tfPlayerPoints.setText("" + game.getPlayerHand().getPoints());
        if (game.getPlayerHand().isBlackjack()) {
            showWinner();
        }
        else {
            tfPlayerCards.setText(game.getPlayerHand().toString());
            tfDealerCards.setText("" + game.getDealerShowCard().display());
        }
    }

    // Manage the bet system
    public void placeBet() {
        // Convert the bet to double and check for exception
        String bet = tfBet.getText();
        double parseBet = 0;
        clearTextbox();
        try {
          parseBet = Double.parseDouble(bet);
          if (parseBet <  game.getMinBet() || parseBet > game.getMaxBet()){
              throw new ArithmeticException();
          }
          if (parseBet > game.getTotalMoney()){
              throw new Exception();
          }
          if (Double.isNaN(Double.parseDouble(bet))){
                throw new NumberFormatException();
          }
        }
        catch(NumberFormatException e) {
            tfPlayerCards.setText("Invalid bet! Bet must be in digits only");
        }
        catch (ArithmeticException e) {
            tfPlayerCards.setText("Invalid bet! Minimum bet is 5$ and maximum is 1000$");
        }
        catch (Exception e){
            tfPlayerCards.setText("Not enough money!");
        }

        // Clear the text from the text box
        if (bet.trim().isEmpty() || (parseBet < game.getMaxBet() && parseBet > game.getMinBet() && parseBet < game.getTotalMoney())) {
            clearTextbox();
        }
        // Manage the Play button availability accordingly if the bet is valid or not.
        boolean isDisable = bet.isEmpty() || bet.trim().isEmpty() || parseBet < game.getMinBet() || parseBet > game.getMaxBet() || parseBet > game.getTotalMoney() ;
        btnPlay.setDisable(isDisable);

        //Set the bet
        game.setBet(parseBet);

        // When Play button is clicked we transform bet text into formated bet Local.CANADA
        double finalParseBet = parseBet;
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tfBet.setText("" + formatNumber(finalParseBet));
                if (game.getPlayerHand().isBlackjack()){
                    tfBet.setText("");
                }
                lunchPlayBtn();
            }
        });
    }

    // Available the hit and stand button when needed
    public void releaseHitStand(){
            btnHit.setDisable(false);
            btnStand.setDisable(false);
            btnExit.setDisable(true);
    }


    private void lunchPlayBtn(){
        tfBet.setFocusTraversable(false);
        startNewGame();

        if (!game.getPlayerHand().isBlackjack()){
            releaseHitStand();
        }
        btnPlay.setDisable(true);
        if (!game.getPlayerHand().isBlackjack()) {
            tfBet.setDisable(true); //Does not grey out because of style properties at line 16 in fxml file.
        }
    }


    // Show formated to local.CANADA money
    private void showMoney(){
        tfMoney.setText("" + formatNumber(game.getTotalMoney()));
    }

    // Show both player and dealer hands
    private void showHands(){
        tfPlayerCards.setText(game.getPlayerHand().toString());
        tfDealerCards.setText(game.getDealerHand().toString());
        tfPlayerPoints.setText("" + game.getPlayerHand().getPoints());
        tfDealerPoints.setText("" + game.getDealerHand().getPoints());
    }

    private void showWinner(){
        showHands();
        if (game.isPush()){
            tfResult.setText("Push!");
        }else if (game.getPlayerHand().isBlackjack()){
            tfResult.setText("BLACKJACK! You win!");
            game.addBlackjackToTotal();
        }else if (game.playerWins()){
            tfResult.setText("You win!");
            game.addBetToTotal();
        }else {
            tfResult.setText("Sorry you lose...");
            game.subtractBetFromTotal();
        }
        showMoney();
        replay();

        // Give automatically money back to the player when short
        if (game.isOutOfMoney()){
            game.loadMoney();
            tfMoney.setText("" + formatNumber(game.getTotalMoney()));
        }
    }

    // Get the table ready for another round
    private void replay() {
        tfBet.setDisable(false);
        btnExit.setDisable(false);
        btnPlay.setDisable(true);
        btnHit.setDisable(true);
        btnStand.setDisable(true);
        tfBet.clear();
        tfBet.requestFocus();

    }

    // Clean the text box
    private void clearTextbox() {
        tfPlayerCards.setText("");
        tfDealerCards.setText("");
        tfPlayerPoints.setText("");
        tfDealerPoints.setText("");
        tfResult.setText("");
    }

    // Homebrew formatNumber method
    public static String formatNumber (double number) {
        NumberFormat unformatedNumber = NumberFormat.getCurrencyInstance(Locale.CANADA);
        return unformatedNumber.format(number);
    }

}

