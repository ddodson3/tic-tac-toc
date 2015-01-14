package edu.miami.c08159659.tictactoc;

import edu.miami.c08159659.tictactoc.TicTacToe.Player;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView;

public class Game extends Activity {
	public static final int PLAYER_A = 1;
	public static final int PLAYER_B = 2;
	
	private ProgressBar timer;
	private int clickTime;
	private Player activePlayer;
	private String playerA;
	private String playerB;
	private Bitmap playerABitmap;
	private Bitmap playerBBitmap;
	private TicTacToe board;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Player firstPlayer;
		int timeForPlayer;
		String playerAPic;
		String playerBPic;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		board = new TicTacToe();
		playerA = this.getIntent().getStringExtra(MainActivity.PLAYER_A_KEY);
		playerB = this.getIntent().getStringExtra(MainActivity.PLAYER_B_KEY);
		playerAPic = this.getIntent().getStringExtra(MainActivity.PLAYER_A_URI_KEY);
		playerBPic = this.getIntent().getStringExtra(MainActivity.PLAYER_B_URI_KEY);
		clickTime = getResources().getInteger(R.integer.click_time);
		timeForPlayer = this.getIntent().getIntExtra(StartScreen.TIME, getResources().getInteger(R.integer.timer_max_default));
		timer = (ProgressBar)findViewById(R.id.timer);
		firstPlayer = Player.values() [this.getIntent().getIntExtra(StartScreen.FIRST_PLAYER, Player.A.ordinal())];
		
		try {
			if (playerAPic != null) {
				playerABitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(playerAPic)));
				((ImageView)findViewById(R.id.player_a_picture)).setImageBitmap(playerABitmap);
			}
			if (playerBPic != null) {
				playerBBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(playerBPic)));
				((ImageView)findViewById(R.id.player_b_picture)).setImageBitmap(playerBBitmap);
			}
		} catch (Exception e) {
			Log.e("ImageProblem", "Cannot find images");
		}

		
		timer.setMax(timeForPlayer);
		setActivePlayer(firstPlayer);
		timer.setProgress(timer.getMax());
		myProgresser.run();
	}
	
	public void playClickHandler(View view) {
		FrameLayout space = (FrameLayout)view.getParent();
		TableRow row = (TableRow)space.getParent();
		int rowNum = Integer.parseInt(row.getTag().toString());
		
		ImageView image = (ImageView)space.findViewById(R.id.player_image);
		view.setVisibility(View.INVISIBLE);
		if (activePlayer == Player.A && playerABitmap != null)
			image.setImageBitmap(playerABitmap);
		if (activePlayer == Player.B && playerBBitmap != null)
			image.setImageBitmap(playerBBitmap);
		image.setVisibility(View.VISIBLE);
		
		switch(space.getId()) {
		case R.id.one:
			board.setPlay(rowNum, 0, activePlayer);
			break;
		case R.id.two:
			board.setPlay(rowNum, 1, activePlayer);
			break;
		case R.id.three:
			board.setPlay(rowNum, 2, activePlayer);
			break;
		}
		
		if (board.isSolved()) {
			returnWinner(board.getWinner());
		} else {
			switchPlayer();
			timer.setProgress(timer.getMax());
		}
	}
	
	private void returnWinner(Player winner) {
		Intent returnIntent = new Intent();
		if (winner == Player.A)
			returnIntent.setFlags(PLAYER_A);
		else if (winner == Player.B)
			returnIntent.setFlags(PLAYER_B);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
	
	private void switchPlayer() {
		if (activePlayer == Player.A) {
			setActivePlayer(Player.B);
		} else if (activePlayer == Player.B) {
			setActivePlayer(Player.A);
		}
	}
	
	private void setActivePlayer(Player active) {
		activePlayer = active;
		if (active == Player.A) {
			((TextView)findViewById(R.id.current_player)).setText(playerA);
			((ImageView)findViewById(R.id.player_a_picture)).setVisibility(View.VISIBLE);
			((ImageView)findViewById(R.id.player_b_picture)).setVisibility(View.INVISIBLE);
			
		} else if (active == Player.B) {
			((TextView)findViewById(R.id.current_player)).setText(playerB);
			((ImageView)findViewById(R.id.player_a_picture)).setVisibility(View.INVISIBLE);
			((ImageView)findViewById(R.id.player_b_picture)).setVisibility(View.VISIBLE);
		}
	}
	
	private Handler myHandler = new Handler();
	
	private final Runnable myProgresser = new Runnable() {
		
		public void run() {
			
			timer.setProgress(timer.getProgress()-clickTime);
			if (timer.getProgress() == 0) {
				switchPlayer();
				timer.setProgress(timer.getMax());
			}
			if (!myHandler.postDelayed(myProgresser, clickTime)) {
				Log.e("postDelayed Error", "Cannot get postDelayed.");
			}
		}
	};
	
	protected void onPause() {
		if(playerABitmap != null)
			playerABitmap.recycle();
		if(playerBBitmap != null)
			playerBBitmap.recycle();
		myHandler.removeCallbacks(myProgresser);
		super.onPause();
	};
}