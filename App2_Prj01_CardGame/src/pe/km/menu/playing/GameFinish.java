package pe.km.menu.playing;

import pe.km.game.CardMaster;
import pe.km.game.PublicConstDefine;
import pe.km.game.R;
import pe.km.game.UserInfo;
import pe.km.menu.RankingActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class GameFinish extends Activity {
	private UserInfo user;
	private boolean bHomeKeyHit;
	private boolean changedActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_finish);

		user = (UserInfo) getIntent().getSerializableExtra(
				PublicConstDefine.USERINFO_EXTRA_KEY);
	}

	public void onBtnInputName(View v) {
		user.setName(((EditText) findViewById(R.id.gameFinish_inputName))
				.getText().toString());
		user.setScore(10000 - user.getClearTime());
		
		changedActivity = true;
		Intent intent = new Intent(this, RankingActivity.class);
		intent.putExtra(PublicConstDefine.USERINFO_EXTRA_KEY, user);
		intent.putExtra(PublicConstDefine.BOOLEAN_GAMESTARTPAGE_KEY, true);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void onRestart() {
		bHomeKeyHit = false;
		changedActivity = false;
		if(!CardMaster.backSoundMedia.isPlaying()) {
			CardMaster.backSoundMedia.start();
		}
		super.onRestart();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		bHomeKeyHit = true;
	}
	
	@Override
	protected void onPause() {
		if(bHomeKeyHit && !changedActivity) {
			CardMaster.backSoundMedia.pause();
		}
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	@Override
	public void onBackPressed() {
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
