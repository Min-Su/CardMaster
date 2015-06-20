package pe.km.menu;

import pe.km.game.CardMaster;
import pe.km.game.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HelpActivity extends Activity {

    private boolean bHomeKeyHit;
	private boolean changedActivity;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
    
    public void onBtnGotoMain(View v) {
    	this.finish();
    }
    
    public void onBtnSourceSiteToast(View v) {
    	Toast.makeText(this, "이미지 출처 - www.freedigitalphotos.net, 사운드 출처 - www.freesound.org", Toast.LENGTH_LONG).show();
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

}
