/**
 * 
 */
package com.tl.baccarat;

import java.util.List;

/**
 * @author Tom.Tang
 *
 */
public interface BaccarateService {
	public void init();

	public void addChipsOnBanker(int chips);

	public void addChipsOnPlayer(int chips);

	public void addChipsOnTie(int chips);
	
	public int getBankerBet();
	
	public int getPlayerBet();

	public int getTieBet();
	
	public Wallet getWallet();

	public List<Card> getBankerCards();
	
	public List<Card> getPlayerCards();

	public int getBankerScore();

	public int getPlayerScore();
	
	public void clear();

	public String getResult();

	public boolean deal();
}
