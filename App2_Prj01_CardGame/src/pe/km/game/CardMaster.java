package pe.km.game;

import pe.km.menu.GameStartActivity;
import pe.km.menu.HelpActivity;
import pe.km.menu.RankingActivity;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

public class CardMaster extends Activity {
	public static MediaPlayer backSoundMedia;

	private Intent intent;
	private CheckBox backSoundChecked, effectSoundChecked;
	private boolean bHomeKeyHit;
	private boolean changedActivity;

	public CardMaster() {
		intent = new Intent();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		backSoundMedia = MediaPlayer.create(this, R.raw.cardgamesound);
		backSoundMedia.start();
		backSoundMedia.setLooping(true);

		Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
		Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
		Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

		backSoundChecked = (CheckBox) findViewById(R.id.backgroundsound);
		effectSoundChecked = (CheckBox) findViewById(R.id.effectsound);

		backSoundChecked.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (backSoundChecked.isChecked()) {
					backSoundMedia.start();
				} else {
					backSoundMedia.pause();
				}
			}
		});

		effectSoundChecked.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				intent.putExtra(PublicConstDefine.EFFECT_SOUND_ONOFF_KEY,
						effectSoundChecked.isChecked());
			}
		});
	}

	@Override
	protected void onRestart() {
		bHomeKeyHit = false;
		changedActivity = false;
		if (!backSoundMedia.isPlaying()) {
			backSoundMedia.start();
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
		if (bHomeKeyHit && !changedActivity) {
			backSoundMedia.pause();
		}
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		backSoundMedia.release();
		backSoundMedia = null;
		System.gc();
	}

	public void onBtnGameStart(View v) {
		intent.setClass(this, GameStartActivity.class);
		changedActivity = true;
		startActivity(intent);
	}

	public void onBtnRanking(View v) {
		intent.setClass(this, RankingActivity.class);
		changedActivity = true;
		startActivity(intent);
	}

	public void onBtnHelp(View v) {
		intent.setClass(this, HelpActivity.class);
		changedActivity = true;
		startActivity(intent);
	}

	public void onBtnExit(View v) {
		this.finish();
	}

}
