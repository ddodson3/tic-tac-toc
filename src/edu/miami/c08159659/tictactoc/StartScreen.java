package edu.miami.c08159659.tictactoc;

import edu.miami.c08159659.tictactoc.TicTacToe.Player;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;

public class StartScreen extends Activity {
	
	private static final int ACTIVITY_GAME = 1;
	public static final String TIME = "edu.miami.c08159659.tictactoc.Timer";
	public static final String FIRST_PLAYER = "edu.miami.c08159659.tictactoc.FirstPlayer";
	
	private RatingBar playerAScore;
	private RatingBar playerBScore;
	private Button start;
	private int time;
	private double divide = .5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String playerA;
		String playerB;
		String playerAPic;
		String playerBPic;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
		playerA = this.getIntent().getStringExtra(MainActivity.PLAYER_A_KEY);
		playerB = this.getIntent().getStringExtra(MainActivity.PLAYER_B_KEY);
		playerAPic = this.getIntent().getStringExtra(MainActivity.PLAYER_A_URI_KEY);
		playerBPic = this.getIntent().getStringExtra(MainActivity.PLAYER_B_URI_KEY);
		if (playerAPic != null)
			((ImageView)findViewById(R.id.player_a_picture)).setImageURI(Uri.parse(playerAPic));
		if (playerBPic != null)
			((ImageView)findViewById(R.id.player_b_picture)).setImageURI(Uri.parse(playerBPic));
		playerAScore = (RatingBar)findViewById(R.id.player_a_score);
		playerBScore = (RatingBar)findViewById(R.id.player_b_score);
		start = (Button)findViewById(R.id.start_game);
		((TextView)findViewById(R.id.player_a_name)).setText(playerA);
		((TextView)findViewById(R.id.player_b_name)).setText(playerB);
		
	}
	public void startGame(View view) {
		Intent game;
		Player nextPlayer;
		
		switch(view.getId()) {
		case R.id.start_game:
			game = new Intent(this.getIntent());
			game.setClassName("edu.miami.c08159659.tictactoc", "edu.miami.c08159659.tictactoc.Game");
			if (time != 0)
				game.putExtra(TIME, time);
			if (Math.random() < divide) {
				divide -= .1;
				nextPlayer = Player.A;
			} else {
				divide += .1;
				nextPlayer = Player.B;
			}
			game.putExtra(FIRST_PLAYER, nextPlayer.ordinal());
			if (nextPlayer == Player.A)
				nextPlayer = Player.B;
			else
				nextPlayer = Player.A;
			startActivityForResult(game, ACTIVITY_GAME);
			break;
		default:
			break;
		
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (data.getFlags() == Game.PLAYER_A) {
				playerAScore.incrementProgressBy(2);
				if (playerAScore.getMax() == playerAScore.getProgress())
					start.setVisibility(View.INVISIBLE);
			} else if (data.getFlags() == Game.PLAYER_B) {
				playerBScore.incrementProgressBy(2);
				if (playerBScore.getMax() == playerBScore.getProgress())
					start.setVisibility(View.INVISIBLE);
			}
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_settings, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.reset:
			playerAScore.setProgress(0);
			playerBScore.setProgress(0);
			start.setVisibility(View.VISIBLE);
			break;
		case R.id.onesec:
			time = 1000;
			break;
		case R.id.twosec:
			time = 2000;
			break;
		case R.id.fivesec:
			time = 5000;
			break;
		case R.id.tensec:
			time = 10000;
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
