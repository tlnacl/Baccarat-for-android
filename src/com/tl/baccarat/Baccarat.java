package com.tl.baccarat;

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
	LinearLayout actionButtons;
	Map<Integer,Event> buttons;
	Button deal;
	Button clear;
	Button play;
	
	public static enum Event{
		CHIP10,CHIP25,CHIP100,CHIP1K,DEAL,CLEAN,NEWGAME
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		playerBet = 0;
		bankerBet = 0;
		tieBet = 0;
		
		
		setContentView(R.layout.activity_baccarat);
		
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
		
		//deal, clear, play buttons
		actionButtons = (LinearLayout)findViewById(R.id.actionButtons);
		
		buttons = new HashMap<Integer,Event>();
		buttons.put(R.id.deal, Event.DEAL);
		buttons.put(R.id.clear, Event.CLEAN);
		buttons.put(R.id.newgame, Event.NEWGAME);
		
		for(final Integer entry: buttons.keySet()){
			Button button = (Button)findViewById(entry);
			button.setOnClickListener(this);
			if(entry.equals(R.id.newgame)) button.setVisibility(View.GONE);
		}
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
		final Event e= buttons.get(v.getId());
		switch(e){
		case DEAL:
			if(bankerBet+playerBet+tieBet>0){
				//deal card
			}
			break;
		}
		
	}

	
}
