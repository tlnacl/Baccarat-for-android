package com.tl.baccarat;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Baccarat extends Activity implements OnLongClickListener,
		OnDragListener, OnClickListener {
	static float BIG_TEXTSIZE = 30;
	static float DEFAULT_TEXTSIZE = 20;
	static String BANKER_WIN = "Banker Win";
	static String PLAYER_WIN = "Player Win";
	static String TIE = "Tie";
	
	AudioManager audio;

	static BaccarateService bs = new BaccarateServiceImp();

	Map<Integer, Event> chips;
	int chipValue;
	Map<Integer, Event> buttons;
	Button deal;
	Button clear;
	Button newGame;

	LinearLayout bankerView;
	LinearLayout playerView;
	TextView bankerValueView;
	TextView playerValueView;
	TextView walletView;
	
	private MediaPlayer bankerWin;
	private MediaPlayer playerWin;
	private MediaPlayer tie;

	public static enum Event {
		CHIP10, CHIP25, CHIP100, CHIP1K, DEAL, CLEAN, NEWGAME
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_baccarat);

		bs.init();
		walletView = (TextView) findViewById(R.id.walletView);
		walletView.setText(bs.getWallet().getBalance());

		chips = new HashMap<Integer, Event>();

		chips.put(R.chip.chips10, Event.CHIP10);
		chips.put(R.chip.chips25, Event.CHIP25);
		chips.put(R.chip.chips100, Event.CHIP100);
		chips.put(R.chip.chips1k, Event.CHIP1K);

		for (final Integer entry : chips.keySet()) {
			View chipView = findViewById(entry);
			chipView.setOnLongClickListener(this);
			chipView.setTag(entry);
		}

		// Draw button
		deal = (Button) findViewById(R.id.deal);
		deal.setOnClickListener(this);
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(this);
		newGame = (Button) findViewById(R.id.newgame);
		newGame.setOnClickListener(this);

		bankerView = (LinearLayout) ((LinearLayout) findViewById(R.id.bankerView))
				.getChildAt(0);
		playerView = (LinearLayout) ((LinearLayout) findViewById(R.id.playerView))
				.getChildAt(0);
		bankerValueView = (TextView) ((LinearLayout) findViewById(R.id.bankerView))
				.getChildAt(1);
		playerValueView = (TextView) ((LinearLayout) findViewById(R.id.playerView))
				.getChildAt(1);
		
		bankerWin = MediaPlayer.create(getBaseContext(), R.raw.bankerwin);
		playerWin = MediaPlayer.create(getBaseContext(), R.raw.playerwin);
		tie = MediaPlayer.create(getBaseContext(), R.raw.tie);

		initUI();
		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
	}

	void initUI() {
		deal.setVisibility(View.VISIBLE);
		clear.setVisibility(View.VISIBLE);
		newGame.setVisibility(View.GONE);
	}

	/**
	 * long click then can move the chips
	 */
	@Override
	public boolean onLongClick(View v) {
		final Event e = chips.get(v.getId());
		switch (e) {
		case CHIP10: {
			chipValue = 10;
			dragEvent(v);
			break;
		}
		case CHIP25: {
			chipValue = 25;
			dragEvent(v);
			break;
		}
		case CHIP100: {
			chipValue = 100;
			dragEvent(v);
			break;
		}
		case CHIP1K: {
			chipValue = 1000;
			dragEvent(v);
			break;
		}
		}
		return true;
	}

	void dragEvent(View view) {
		ClipData.Item item = new ClipData.Item(String.valueOf(view.getTag()));
		ClipData clipData = new ClipData((CharSequence) String.valueOf(view
				.getTag()),
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
				// caculateValue(bankerBet,view,chipValue);
				bs.addChipsOnBanker(chipValue);
				dragEntered();
			}
			if (view.getId() == R.id.player) {
				bs.addChipsOnPlayer(chipValue);
				;
				dragEntered();
			}
			if (view.getId() == R.id.tie) {
				bs.addChipsOnTie(chipValue);
				dragEntered();
			}
			return true;
		case DragEvent.ACTION_DRAG_EXITED:
			// Drag exited view bounds
			// Change back the value
			if (view.getId() == R.id.banker) {
				bs.addChipsOnBanker(-chipValue);
				dragExited();
			}
			if (view.getId() == R.id.player) {
				bs.addChipsOnPlayer(-chipValue);
				dragExited();
			}
			if (view.getId() == R.id.tie) {
				bs.addChipsOnTie(-chipValue);
				dragExited();
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

	private void dragEntered() {

		
		updateBetView();
	}

	private void dragExited() {
		walletView.setTextSize(DEFAULT_TEXTSIZE);
		updateBetView();
	}

	private void updateBetView() {
		walletView.setText(bs.getWallet().getBalance());
		((TextView) findViewById(R.id.banker)).setText(String.valueOf(bs
				.getBankerBet()));
		((TextView) findViewById(R.id.player)).setText(String.valueOf(bs
				.getPlayerBet()));
		((TextView) findViewById(R.id.tie)).setText(String.valueOf(bs
				.getTieBet()));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.deal) {
			if (bs.deal()) {
				for (final Integer entry : chips.keySet()) {
					View chipView = findViewById(entry);
					chipView.setVisibility(View.GONE);
				}

				deal.setVisibility(View.GONE);
				clear.setVisibility(View.GONE);
				newGame.setVisibility(View.VISIBLE);

				// Banker View
				for (Card card : bs.getBankerCards()) {
					ImageView cardImage = new ImageView(this);
					cardImage = new ImageView(this);
					cardImage.setImageResource(card.getImage());
					bankerView.addView(cardImage);
					Log.d(getLocalClassName(),"banker cardView"+card.getValue());
				}
				// player View
				for (Card card : bs.getPlayerCards()) {
					ImageView cardImage = new ImageView(this);
					cardImage = new ImageView(this);
					cardImage.setImageResource(card.getImage());
					playerView.addView(cardImage);
					Log.d(getLocalClassName(),"player cardView"+card.getValue());
				}

				//
				bankerValueView.setText(String.valueOf(bs.getBankerScore()));
				playerValueView.setText(String.valueOf(bs.getPlayerScore()));
				walletView.setText(bs.getWallet().getBalance());
				TextView resultView = (TextView) findViewById(R.id.resultView);
				if(bs.getResult() == 0) {
					resultView.setText(BANKER_WIN);
					bankerWin.start();
				} else if(bs.getResult() == 1){
					resultView.setText(PLAYER_WIN);
					playerWin.start();
				} else if(bs.getResult() == 2){
					resultView.setText(TIE);
					tie.start();
				} 
				
			}

		} else if (v.getId() == R.id.clear) {
			bs.clear();
			updateBetView();
		} else if (v.getId() == R.id.newgame) {
			newGame();
		}

	}

	private void newGame() {
		bs.init();
		updateBetView();

		bankerView.removeAllViews();
		playerView.removeAllViews();
		bankerValueView.setText("");
		playerValueView.setText("");
		for (final Integer entry : chips.keySet()) {
			View chipView = findViewById(entry);
			chipView.setVisibility(View.VISIBLE);
		}

		initUI();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	    case KeyEvent.KEYCODE_VOLUME_UP:
	        audio.adjustStreamVolume(
	            AudioManager.STREAM_MUSIC,
	            AudioManager.ADJUST_RAISE,
	            AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
	        return true;
	    case KeyEvent.KEYCODE_VOLUME_DOWN:
	        audio.adjustStreamVolume(
	            AudioManager.STREAM_MUSIC,
	            AudioManager.ADJUST_LOWER,
	            AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
	        return true;
	    default:
	        break;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
