/**
 * 
 */
package com.tl.baccarat;

import java.util.ArrayList;

/**
 * @author Tom.Tang
 *
 */
public class Deck {

	private ArrayList<Card> deck;   // An array of Cards, representing the Deck.   
	static int howManyDecks; // The Deck consist of n amount
	static int shuffleTimes; // The Deck is shuffled n times
	
	Deck(int decks, int burn, int times) 
   	{
		deck = new ArrayList<Card>(); // Instantiate the ArrayList.
	   	howManyDecks = decks; // How many 52-card decks the deck should contain.
	   	shuffleTimes = times; // How many times to shuffle the deck
	   
	   	fillDeck(); // Fill it with Cards.
	   	shuffleDeck(); //shuffle the deck 
	   	burn(burn); // remove given amount of Cards from the top of the deck
	}
	    
	private void burn(int b)
	{		
		for ( int counter = 0; counter < b ; counter++ )
			deck.remove(0);
	}
	
	Card drawCard()
	{
		Card card = deck.get(0); // Draw a Card from the top of the deck.
		deck.remove(0); // Remove the Card from the Deck.
		return card; // Return the Card.
	}
	
	private Card popCard(short s)
	{
		Card card = deck.get(s); // Fetch a Card.
		deck.remove(s); // Remove the Card from the Deck.
		return card; // Return the Card.
	}
	
	void fillDeck()
	{
		// Fill the deck with 52 Cards, 4 different suits & 13 different cards in each suit.  
		
		for (int decks = 0 ; decks < howManyDecks ; decks++ )
			for ( int suit = 0 ; suit < 4 ; suit++ )
				for ( int value = 1 ; value < 14 ; value++ ) 
					deck.add ( new Card (value , suit , true) );
	}
	
	void shuffleDeck()
	{
		for ( byte t = 0; t < shuffleTimes; t++ )
		{
			for ( short i = (short)(deck.size()-1); i > 0; i-- )	
		
			{ 	
				short rand = (short)(Math.random()*i); // Fetch a random Card in the range 0-i	     
	    	
				// Take care of case where indexes rand and i represent the same card.
				while (rand==i){ rand = (short)(Math.random()*i); } 
	    	
				Card iCard = popCard(i); // Pop the OLD Card.
				Card randCard = popCard(rand); //Pop the NEW random card.
	    	
				//Put back them in swapped places.
				deck.add(i-1,randCard);  
				deck.add(rand,iCard);
			}
		}
	}
	
	//Accessors
	short cardsLeft()
	{ return (short)deck.size(); }

}
