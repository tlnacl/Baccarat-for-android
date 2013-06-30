/**
 * 
 */
package com.tl.baccarat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * @author Tom.Tang
 *
 */
public class GameHistoryController  extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);		
		GridLayout gameHistory = (GridLayout)findViewById(R.id.gameHistory);
		TextView winRecord = new TextView(this);
		winRecord.setText("6");
		winRecord.setTextColor(Color.RED);
		
		gameHistory.addView(winRecord);
		gameHistory.addView(winRecord);
		gameHistory.addView(winRecord);
	}
}
