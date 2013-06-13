/**
 * 
 */
package com.tl.baccarat;

/**
 * @author Tom.Tang
 *
 */
public class Card {
	
	/*
	[+]Ids for the 4 suits.
	
	final static byte SPADES = 0,
      						 HEARTS = 1,
      						 DIAMONDS = 2,
      						 CLUBS = 3;
      						 
	[+]Ids for face cards. Cards 2 through 10 have their numerical values as their ids.  
	
	final static byte ACE = 1,         
      					     KING = 11,       
      					     QUEEN = 12,     
      					     JACK = 13;
	*/
      
	private final int suit;	//The suit of this card, one of the constants: SPADES, HEARTS, DIAMONDS, CLUBS. Immutable.     
	private final int value; 	//The value of this card, from 1 to 13. Immutable.
	private Integer cardID;
       
	Card(int value, int suit, boolean flip)
	{
		// Construct a card with the specified value and suit. Value must be between 1 and 13. Suit must be between 0 and 3.
		this.value = value;
		this.suit = suit;
		setImage();
	}
	
	void flip()
	{ 
		setImage();
	}
	
	private void setImage()
	{
		cardID=null;
		
			switch(suit)
			{
				case 0:
				{
					switch(value)
					{
						case 1:  { cardID=R.drawable.s1; break;  }
						case 2:  { cardID=R.drawable.s2; break;  }
						case 3:  { cardID=R.drawable.s3; break;  }
						case 4:  { cardID=R.drawable.s4; break;  }
						case 5:  { cardID=R.drawable.s5; break;  }
						case 6:  { cardID=R.drawable.s6; break;  }
						case 7:  { cardID=R.drawable.s7; break;  }
						case 8:  { cardID=R.drawable.s8; break;  }
						case 9:  { cardID=R.drawable.s9; break;  }
						case 10: { cardID=R.drawable.s10; break; }
						case 11: { cardID=R.drawable.s11; break; }
						case 12: { cardID=R.drawable.s12; break; }
						case 13: { cardID=R.drawable.s13; break; }
					}
					break;
				}
				case 1:
				{
					switch(value)
					{	
						case 1:  { cardID=R.drawable.h1; break;  }
						case 2:  { cardID=R.drawable.h2; break;  }
						case 3:  { cardID=R.drawable.h3; break;  }
						case 4:  { cardID=R.drawable.h4; break;  }
						case 5:  { cardID=R.drawable.h5; break;  }
						case 6:  { cardID=R.drawable.h6; break;  }
						case 7:  { cardID=R.drawable.h7; break;  }
						case 8:  { cardID=R.drawable.h8; break;  }
						case 9:  { cardID=R.drawable.h9; break;  }
						case 10: { cardID=R.drawable.h10; break; }
						case 11: { cardID=R.drawable.h11; break; }
						case 12: { cardID=R.drawable.h12; break; }
						case 13: { cardID=R.drawable.h13; break; }
					}
					break;
				}
				case 2:
				{	
					switch(value)
					{	
						case 1:  { cardID=R.drawable.d1; break;  }
						case 2:  { cardID=R.drawable.d2; break;  }
						case 3:  { cardID=R.drawable.d3; break;  }
						case 4:  { cardID=R.drawable.d4; break;  }
						case 5:  { cardID=R.drawable.d5; break;  }
						case 6:  { cardID=R.drawable.d6; break;  }
						case 7:  { cardID=R.drawable.d7;  break; }
						case 8:  { cardID=R.drawable.d8;  break; }
						case 9:  { cardID=R.drawable.d9;  break; }
						case 10: { cardID=R.drawable.d10; break; }
						case 11: { cardID=R.drawable.d11; break; }
						case 12: { cardID=R.drawable.d12; break; }
						case 13: { cardID=R.drawable.d13; break; }
					}
					break;
				}
				case 3:
				{
					switch(value)
					{	
						case 1:  { cardID=R.drawable.c1; break;  }
						case 2:  { cardID=R.drawable.c2; break;  }
						case 3:  { cardID=R.drawable.c3; break;  }
						case 4:  { cardID=R.drawable.c4; break;  }
						case 5:  { cardID=R.drawable.c5; break;  }
						case 6:  { cardID=R.drawable.c6; break;  }
						case 7:  { cardID=R.drawable.c7; break;  }
						case 8:  { cardID=R.drawable.c8; break;  }
						case 9:  { cardID=R.drawable.c9; break;  }
						case 10: { cardID=R.drawable.c10; break; }
						case 11: { cardID=R.drawable.c11; break; }
						case 12: { cardID=R.drawable.c12; break; }
						case 13: { cardID=R.drawable.c13; break; }
					}
					break;
				}
			}
		}
		

	//Accessors
	Integer getImage()
	{ return cardID; }
	
	int getSuit()
	{ return suit; }
	
	int getValue()
	{ return value; }
	
	int getScore(){
		if (value > 9){
			return 0;
		}
		return value;
	}

}
