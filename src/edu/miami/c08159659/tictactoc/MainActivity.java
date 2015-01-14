package edu.miami.c08159659.tictactoc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
	public static final String PLAYER_A_KEY = "edu.miami.c08159659.tictactoc.playerA";
	public static final String PLAYER_B_KEY = "edu.miami.c08159659.tictactoc.playerB";
	public static final String PLAYER_A_URI_KEY = "edu.miami.c08159659.tictactoc.playerApic";
	public static final String PLAYER_B_URI_KEY = "edu.miami.c08159659.tictactoc.playerBpic";
	public static final int ACTIVITY_PLAYER_A_PIC = 1;
	public static final int ACTIVITY_PLAYER_B_PIC = 2;
	
	private Uri playerAPic;
	private Uri playerBPic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);
	}
	
	public void pictureClick(View view) {
		Intent galleryIntent;
		
		galleryIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		
		switch(view.getId()) {
		case R.id.player_a_picture:
			startActivityForResult(galleryIntent, ACTIVITY_PLAYER_A_PIC);
			break;
		case R.id.player_b_picture:
			startActivityForResult(galleryIntent, ACTIVITY_PLAYER_B_PIC);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == RESULT_OK) {
			switch(requestCode){
			case ACTIVITY_PLAYER_A_PIC:
				playerAPic = data.getData();
				((ImageView)findViewById(R.id.player_a_picture)).setImageURI(playerAPic);
				break;
			case ACTIVITY_PLAYER_B_PIC:
				playerBPic = data.getData();
				((ImageView)findViewById(R.id.player_b_picture)).setImageURI(playerBPic);
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void toStartScreen(View view) {
		Intent toStart;
		String playerA;
		String playerB;
		
		switch(view.getId()) {
		case R.id.start_start_screen:
			toStart = new Intent();
			toStart.setClassName("edu.miami.c08159659.tictactoc", "edu.miami.c08159659.tictactoc.StartScreen");
			playerA = ((EditText)findViewById(R.id.player_a)).getText().toString();
			if (playerA.isEmpty())
				playerA = getResources().getString(R.string.default_user_name);
			playerB = ((EditText)findViewById(R.id.player_b)).getText().toString();
			if (playerB.isEmpty())
				playerB = getResources().getString(R.string.default_user_name);
			toStart.putExtra(PLAYER_A_KEY, playerA);
			toStart.putExtra(PLAYER_B_KEY, playerB);
			if (playerAPic != null)
				toStart.putExtra(PLAYER_A_URI_KEY, playerAPic.toString());
			if (playerBPic != null)
				toStart.putExtra(PLAYER_B_URI_KEY, playerBPic.toString());
			startActivity(toStart);
			break;
		default:
			break;
		
		}
	}
}
