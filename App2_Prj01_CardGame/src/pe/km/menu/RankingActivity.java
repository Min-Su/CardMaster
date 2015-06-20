package pe.km.menu;

import java.util.ArrayList;

import pe.km.db.DataBaseManager;
import pe.km.game.CardMaster;
import pe.km.game.PublicConstDefine;
import pe.km.game.R;
import pe.km.game.UserInfo;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class RankingActivity extends Activity {
	/* Field */
	private boolean bGameStartPage;
	private boolean bHomeKeyHit;
	private boolean changedActivity;

	private ListView rankingList;

	private ArrayList<UserInfo> allUserData;
	private DataBaseManager dbm;
	private UserInfo user;


	/* Method */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);
		rankingList = (ListView) findViewById(R.id.rank_list);
		dbm = new DataBaseManager(this);

		bGameStartPage = (boolean) getIntent().getBooleanExtra(
				PublicConstDefine.BOOLEAN_GAMESTARTPAGE_KEY, false);
		if (bGameStartPage) {
			user = (UserInfo) getIntent().getSerializableExtra(
					PublicConstDefine.USERINFO_EXTRA_KEY);
			addUser();
			
		}
		
		Cursor cursor = dbm.getAllUserInfoCursor();
		allUserData = dbm.getAllUserData(cursor);
		rankingList.setAdapter(new CustomBaseAdapter(this, allUserData));
	}

	@Override
	public void onBackPressed() {
		// Back Button Block
		this.finish();
	}

	@Override
	protected void onRestart() {
		bHomeKeyHit = false;
		changedActivity = false;
		if (!CardMaster.backSoundMedia.isPlaying()) {
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
		if (bHomeKeyHit && !changedActivity) {
			CardMaster.backSoundMedia.pause();
		}
		super.onPause();
	}

	public void onBtnGoStartScreen(View v) {
		this.finish();
	}

	private void addUser() {
		UserInfo userInfo = new UserInfo(user.getName(), user.getScore(),
				user.getClearTime());
		boolean added = dbm.add(userInfo);

		if (!added) {
			Toast.makeText(this, "Not added: " + userInfo, Toast.LENGTH_SHORT)
					.show();
		}
	}

}