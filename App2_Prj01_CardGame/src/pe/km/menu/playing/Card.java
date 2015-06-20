package pe.km.menu.playing;

import pe.km.game.R;
import pe.km.menu.GameStartActivity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class Card extends ImageView implements OnTouchListener {
	private GameStartActivity gsa;
	private int card_id;
	private boolean bCardOverTurn;
	private boolean bEnable;
	

	public Card(Context context) {
		super(context);
		gsa = (GameStartActivity)context;
		this.setImageResource(R.drawable.cardbackimage);
	}

	public Card(Context context, AttributeSet attrs) {
		super(context, attrs);
		gsa = (GameStartActivity)context;
		this.setImageResource(R.drawable.cardbackimage);
	}

	public Card(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		gsa = (GameStartActivity)context;
		this.setImageResource(R.drawable.cardbackimage);
	}

	public int getCard_id() {
		return card_id;
	}

	public void setCardId(int cardId) {
		this.card_id = cardId;
	}
	
	public boolean isCardOverTurn() {
		return bCardOverTurn;
	}

	public void setCardOverTurn(boolean bCardOverTurn) {
		this.bCardOverTurn = bCardOverTurn;
		if(!bCardOverTurn) {
			this.post(new Runnable() {
				public void run() {
					Card.this.setImageResource(R.drawable.cardbackimage);
				}
			});
		}
	}

	public boolean isEnable() {
		return bEnable;
	}

	public void setEnable(boolean bEnable) {
		this.bEnable = bEnable;
	}

	public boolean onTouch(View v, MotionEvent event) {
		if(!this.bCardOverTurn && gsa.getClicked() < 2) {
			//카드가 보이도록 뒤집힌 경우.
			bCardOverTurn = true;
			this.setImageResource(this.card_id);
			gsa.setOverTurn(card_id);
		}
		return true;
	}

}
