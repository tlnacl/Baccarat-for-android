/**
 * 
 */
package com.tl.baccarat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tom.Tang
 * 
 */
public class BaccarateServiceImp implements BaccarateService {
	private int bankerBet;
	private int playerBet;
	private int tieBet;

	private Deck shoe;
	private List<Card> bankerCards;
	private List<Card> playerCards;

	private Wallet wallet;// cents

	private int bankerScore;
	private int playerScore;
	private int bankerValue;
	private int playerValue;

	private int result;

	public BaccarateServiceImp() {
		wallet = new Wallet(10000);
	}

	public void init() {
		bankerBet = 0;
		playerBet = 0;
		tieBet = 0;

		bankerScore = 0;
		playerScore = 0;
		bankerValue = 0;
		playerValue = 0;
		bankerCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();

		// New shoe every round can change to shuffle deck when card number low
		shoe = new Deck(1, 1, 3);
	}

	public void addChipsOnBanker(int chips) {
		bankerBet += chips;
		wallet.withdraw(chips);
	}

	public void addChipsOnPlayer(int chips) {
		playerBet += chips;
		wallet.withdraw(chips);
	}

	public void addChipsOnTie(int chips) {
		tieBet += chips;
		wallet.withdraw(chips);
	}

	public int getBankerBet() {
		return bankerBet;
	}

	public int getPlayerBet() {
		return playerBet;
	}

	public int getTieBet() {
		return tieBet;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public List<Card> getBankerCards() {
		return bankerCards;
	}

	public List<Card> getPlayerCards() {
		return playerCards;
	}

	public int getBankerScore() {
		return bankerScore;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public int getResult() {
		return result;
	}

	/**
	 * 
	 */
	public boolean deal() {
		// TODO Auto-generated method stub
		if (bankerBet + playerBet + tieBet > 0) {
			playBaccarate();
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void clear() {
		bankerBet = 0;
		playerBet = 0;
		tieBet = 0;

	}

	public void playBaccarate() {

		bankerDraw();
		playerDraw();
		bankerDraw();
		playerDraw();

		int bankerScore = bankerValue % 10;
		int playerScore = playerValue % 10;

		
		Card playerThird = null;
		if (playerScore > 7 || bankerScore > 7) {
			gameend();
			return;
		}

		if (playerScore < 6) {
			playerThird = playerDraw();

			if (bankerScore < 3) {
				bankerDraw();
			} else if (bankerScore == 3) {
				if (playerThird.getScore() != 8) {
					bankerDraw();
				}
			} else if (bankerScore == 4) {
				if (playerThird.getScore() != 0 || playerThird.getScore() != 1
						|| playerThird.getScore() != 8
						|| playerThird.getScore() != 9) {
					bankerDraw();
				}
			} else if (bankerScore == 5) {
				if (playerThird.getScore() != 0 || playerThird.getScore() != 1
						|| playerThird.getScore() != 2
						|| playerThird.getScore() != 3
						|| playerThird.getScore() != 8
						|| playerThird.getScore() != 9) {
					bankerDraw();
				}
			} else if (bankerScore == 6) {
				if (playerThird.getScore() == 6 || playerThird.getScore() == 7) {
					bankerDraw();
				}
			}
		} else {
			if (bankerScore < 4) {
				bankerDraw();
			}
		}
		gameend();

	}

	Card playerDraw() {
		Card card = shoe.drawCard();
		playerCards.add(card);
		playerValue += card.getScore();
		System.out.println("card" + card.getValue() + "playerValue"
				+ playerValue);
		return card;
	}

	void bankerDraw() {
		Card card = shoe.drawCard();
		bankerCards.add(card);
		bankerValue += card.getScore();
//		System.out.println("card" + card.getValue() + "bankerValue"
//				+ bankerValue);
	}

	private void gameend() {
		bankerScore = bankerValue % 10;
		playerScore = playerValue % 10;

		if (bankerScore > playerScore) {
			result = 0;
			wallet.depositCents(bankerBet * 195);
		} else if (bankerScore == playerScore) {
			result = 2;
			wallet.depositCents(tieBet * 900);
		} else {
			result = 1;
			wallet.depositCents(playerBet * 200);
		}
//		System.out.println("bankerScore" + bankerScore + "playerScore"+playerScore + "result" + result);
	}
}
