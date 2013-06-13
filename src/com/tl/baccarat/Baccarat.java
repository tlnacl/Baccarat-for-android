package com.tl.baccarat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tl.baccarat.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Baccarat extends Activity implements OnLongClickListener,OnDragListener,OnClickListener{
	
	Map<Integer,Event> chips;
	int chipValue;
	int bankerBet;
	int playerBet;
	int tieBet;
	Map<Integer,Event> buttons;
	Button deal;
	Button clear;
	Button newGame;
	private Deck shoe; 
	ArrayList<Card> banker;
	ArrayList<Card> player;
	LinearLayout bankerView;
	LinearLayout playerView;
	TextView bankerValueView;
	TextView playerValueView;
	
	
	int bankerValue = 0;
	int playerValue = 0;
	
	public static enum Event{
		CHIP10,CHIP25,CHIP100,CHIP1K,DEAL,CLEAN,NEWGAME
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_baccarat);
		playerBet = 0;
		bankerBet = 0;
		tieBet = 0;
		
		
		//deal, clear, play buttons
//		buttons = new HashMap<Integer,Event>();
//		buttons.put(R.id.deal, Event.DEAL);
//		buttons.put(R.id.clear, Event.CLEAN);
//		buttons.put(R.id.newgame, Event.NEWGAME);
//		
//		for(final Integer entry: buttons.keySet()){
//			Button button = (Button)findViewById(entry);
//			button.setOnClickListener(this);
//			if(entry.equals(R.id.newgame)) button.setVisibility(View.GONE);
//		}
		
		//chips
				chips = new HashMap<Integer,Event>();
				
				chips.put(R.chip.chips10, Event.CHIP10);
				chips.put(R.chip.chips25, Event.CHIP25);
				chips.put(R.chip.chips100, Event.CHIP100);
				chips.put(R.chip.chips1k, Event.CHIP1K);
				
				for(final Integer entry: chips.keySet()) {
					View chipView = findViewById(entry);
					chipView.setOnLongClickListener(this);
					chipView.setTag(entry);
				}
				
				deal = (Button)findViewById(R.id.deal);
				deal.setOnClickListener(this);
				clear = (Button)findViewById(R.id.clear);
				clear.setOnClickListener(this);
				newGame = (Button)findViewById(R.id.newgame);
				newGame.setOnClickListener(this);
				
				
				bankerView = (LinearLayout)((LinearLayout) findViewById(R.id.bankerView)).getChildAt(0);
				playerView = (LinearLayout)((LinearLayout) findViewById(R.id.playerView)).getChildAt(0);
				bankerValueView = (TextView)((LinearLayout) findViewById(R.id.bankerView)).getChildAt(1);
				playerValueView = (TextView)((LinearLayout) findViewById(R.id.playerView)).getChildAt(1);
		
		initUI();
	}
	
	void initUI(){
		deal.setVisibility(View.VISIBLE);
		clear.setVisibility(View.VISIBLE);
		newGame.setVisibility(View.GONE);
		
		//game init
//		decks=1; splits=1; burns=(byte)(5.0*Math.random()); shuffleTimes = 3;
		shoe = new Deck(1,1,3);
		
		banker = new ArrayList<Card>();
		player = new ArrayList<Card>();
	}
	
	@Override
	public boolean onLongClick(View v) {
		final Event e= chips.get(v.getId());
		switch(e){
			case CHIP10:
			{
				chipValue = 10;
				dragEvent(v);
				break;
			}
			case CHIP25:
			{
				chipValue = 25;
				dragEvent(v);
				break;
			}
			case CHIP100:
			{
				chipValue = 100;
				dragEvent(v);
				break;
			}
			case CHIP1K:
			{
				chipValue = 1000;
				dragEvent(v);
				break;
			}
		}
		return true;
	}
	
	void dragEvent(View view){
		ClipData.Item item = new ClipData.Item(String.valueOf(view.getTag()) );
		ClipData clipData = new ClipData((CharSequence) String.valueOf(view.getTag()) ,
				new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }, item);
		view.startDrag(clipData, new View.DragShadowBuilder(view), null, 0);
		
		findViewById(R.id.banker).setOnDragListener(this);
		findViewById(R.id.player).setOnDragListener(this);
		findViewById(R.id.tie).setOnDragListener(this);
	}
	
	@Override
	public boolean onDrag(View view, DragEvent dragEvent) {
		switch (dragEvent.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED:
			Log.d(getLocalClassName(), "drag started");
			return true;
		case DragEvent.ACTION_DRAG_ENTERED:
			// Drag has entered view bounds
			// Change the value of each option
			if (view.getId() == R.id.banker) {
				bankerBet += chipValue;
				((TextView) view).setText(String.valueOf(bankerBet));
			}
			if (view.getId() == R.id.player) {
				playerBet += chipValue;
				((TextView) view).setText(String.valueOf(playerBet));
			}
			if (view.getId() == R.id.tie) {
				tieBet += chipValue;
				((TextView) view).setText(String.valueOf(tieBet));
			}
			return true;
		case DragEvent.ACTION_DRAG_EXITED:
			// Drag exited view bounds
			// Change back the value
			if (view.getId() == R.id.banker) {
				bankerBet -= chipValue;
				((TextView) view).setText(String.valueOf(bankerBet));
			}
			if (view.getId() == R.id.player) {
				playerBet -= chipValue;
				((TextView) view).setText(String.valueOf(playerBet));
			}
			if (view.getId() == R.id.tie) {
				tieBet -= chipValue;
				((TextView) view).setText(String.valueOf(tieBet));
			}
			return true;
		case DragEvent.ACTION_DRAG_LOCATION:
			// Ignore this event
			return true;
		case DragEvent.ACTION_DROP:
			
		case DragEvent.ACTION_DRAG_ENDED:
			// remove drag listeners
			findViewById(R.id.banker).setOnDragListener(null);
			findViewById(R.id.player).setOnDragListener(null);
			findViewById(R.id.tie).setOnDragListener(null);
			return true;

		}
		return false;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.deal){
			if(bankerBet+playerBet+tieBet>0){
				//deal card
				initBaccarate();
			}
		} else if(v.getId() == R.id.clear){ 
			bankerBet = 0;
			playerBet = 0;
			tieBet = 0;
			
			((TextView) findViewById(R.id.banker)).setText(String.valueOf(bankerBet));
			((TextView) findViewById(R.id.player)).setText(String.valueOf(playerBet));
			((TextView) findViewById(R.id.tie)).setText(String.valueOf(tieBet));
		} else if(v.getId() == R.id.newgame){
			newGame();
		}
		
	}
	
	private void newGame() {
		//clear player and Banker
		banker.clear();
		player.clear();
		
		bankerBet = 0;
		playerBet = 0;
		tieBet = 0;
		
		((TextView) findViewById(R.id.banker)).setText(String.valueOf(bankerBet));
		((TextView) findViewById(R.id.player)).setText(String.valueOf(playerBet));
		((TextView) findViewById(R.id.tie)).setText(String.valueOf(tieBet));
		
		bankerView.removeAllViews();
		playerView.removeAllViews();
		bankerValueView.setText("");
		playerValueView.setText("");
		for(final Integer entry: chips.keySet()) {
			View chipView = findViewById(entry);
			chipView.setVisibility(View.VISIBLE);
		}
		
		initUI();
		
	}

	public void initBaccarate(){
		for(final Integer entry: chips.keySet()) {
			View chipView = findViewById(entry);
			chipView.setVisibility(View.GONE);
		}
		
		
	    bankerDraw();	    
	    playerDraw();
	    bankerDraw();	    
	    playerDraw();
	    
	    int bankerScore = bankerValue%10;
	    int playerScore = playerValue%10;
	    bankerValueView.setText(String.valueOf(bankerScore));
	    playerValueView.setText(String.valueOf(playerScore));
	    
	    deal.setVisibility(View.GONE);
	    clear.setVisibility(View.GONE);
	    newGame.setVisibility(View.VISIBLE);
//	    try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
	    Card playerThird = null;
	    if(playerScore>7 || bankerScore>7){
	    	gameend();
	    	return;
	    }
	    
	    if(playerScore<6){
	    	playerThird = playerDraw();
		    
		    if(bankerScore<3){
		    	bankerDraw();
		    }else if(bankerScore == 3){
		    	if(playerThird.getScore() !=8){
		    		bankerDraw();
		    	}
		    }else if(bankerScore == 4){
		    	if(playerThird.getScore() !=0 || playerThird.getScore() !=1 ||playerThird.getScore() !=8 ||playerThird.getScore() !=9){
		    		bankerDraw();
		    	}
		    }else if(bankerScore == 5){
		    	if(playerThird.getScore() !=0 || playerThird.getScore() !=1 ||playerThird.getScore() !=2 || playerThird.getScore() !=3 ||playerThird.getScore() !=8 ||playerThird.getScore() !=9){
		    		bankerDraw();
		    	}
		    }else if(bankerScore == 6){
		    	if(playerThird.getScore() ==6 || playerThird.getScore() ==7 ){
		    		bankerDraw();
		    	}
		    }
	    } else{
	    	if(bankerScore <4){
	    		bankerDraw();
	    	}
	    }
	    gameend();
	    
	}
	
	Card playerDraw(){
		Card card = shoe.drawCard();
		ImageView cardImage=new ImageView(this);
	    cardImage=new ImageView(this);
	    cardImage.setImageResource(card.getImage());
	    player.add(shoe.drawCard());
	    playerView.addView(cardImage);
	    playerValue += card.getScore();
	    
	    return card;
	}
	
	void bankerDraw(){
		Card card = shoe.drawCard();
		ImageView cardImage=new ImageView(this);
	    cardImage=new ImageView(this);
	    cardImage.setImageResource(card.getImage());
	    banker.add(shoe.drawCard());
	    bankerView.addView(cardImage);
	    bankerValue += card.getScore();
	}

	/**
	 * 
	 */
	private void gameend() {
		int bankerScore = bankerValue%10;
	    int playerScore = playerValue%10;
	    
	}
	

	
}
