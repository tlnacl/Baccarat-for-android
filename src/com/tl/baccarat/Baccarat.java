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
import android.widget.TextView;

import com.tl.baccarat.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Baccarat extends Activity implements OnLongClickListener,OnDragListener{
	
	Map<Integer,Event> events;
	int chipValue;
	int bankerBet;
	int playerBet;
	int tieBet;
	
	public static enum Event{
		CHIP10,CHIP25,CHIP100,CHIP1K,DEAL,CLEAN
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		playerBet = 0;
		bankerBet = 0;
		tieBet = 0;
		
		
		setContentView(R.layout.activity_baccarat);
		
		events = new HashMap<Integer,Event>();
		
		events.put(R.chip.chips10, Event.CHIP10);
		events.put(R.chip.chips25, Event.CHIP25);
		events.put(R.chip.chips100, Event.CHIP100);
		events.put(R.chip.chips1k, Event.CHIP1K);
		
		for(final Integer entry: events.keySet()) {
			View chipView = findViewById(entry);
			chipView.setOnLongClickListener(this);
			chipView.setTag(entry);
		}
		
		
	}
	
	@Override
	public boolean onLongClick(View v) {
		final Event e= events.get(v.getId());
		switch(e){
			case CHIP10:
			{
				chipValue = 10;
				Log.d(getLocalClassName(), "10!!"+String.valueOf(chipValue));
				dragEvent(v);
				break;
			}
			case CHIP25:
			{
				chipValue = 25;
				Log.d(getLocalClassName(), "25!!"+String.valueOf(chipValue));
				dragEvent(v);
				break;
			}
			case CHIP100:
			{
				chipValue = 100;
				Log.d(getLocalClassName(), "100!!"+String.valueOf(chipValue));
				dragEvent(v);
				break;
			}
			case CHIP1K:
			{
				chipValue = 1000;
				Log.d(getLocalClassName(), "1000!!"+String.valueOf(chipValue));
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
			// If called for trash can then scale it.
			if (view.getId() == R.id.banker || view.getId() == R.id.player || view.getId() == R.id.tie ) {
				int value = Integer.parseInt(((TextView)view).getText().toString());
				((TextView)view).setText(String.valueOf(value+chipValue));
			}
			return true;
		case DragEvent.ACTION_DRAG_EXITED:
			// Drag exited view bounds
			// If called for trash can then reset it.
			if (view.getId() == R.id.banker || view.getId() == R.id.player || view.getId() == R.id.tie ) {
				int value = Integer.parseInt(((TextView)view).getText().toString());
				((TextView)view).setText(String.valueOf(value-chipValue));
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

	
}
