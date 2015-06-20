package pe.km.menu;

import java.io.Serializable;
import java.util.ArrayList;

import pe.km.game.PublicConstDefine;
import pe.km.game.R;
import pe.km.game.UserInfo;
import pe.km.menu.playing.Card;
import pe.km.menu.playing.GameFinish;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class GameStartActivity extends Activity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static boolean bSameCard;
	private static boolean efs_onoff;
	// 카드를 클릭한 회수 기록. 지나치게 빠른 경우 카드가 여러장이 동시에 뒤집히므로...
	private static int clicked;
	private static int gameClear_CardCnt; // 이 변수의 값이 카드 종류 개수와 일치하면 게임 끝.
	private static int overTurnedCardId1;
	private static int overTurnedCardId2;
	private static int combo;
	private static int min;
	private static int sec;
	private static TextView textView_playTime;
	private static TextView textView_score;
	private static Card[] card;
	private static UserInfo user;
	private static MediaPlayer effectSoundMedia1;
	private static MediaPlayer effectSoundMedia2;
	private static SameCardCheckThread sameCardCheck_thread;
	private static boolean alreadyAct;
	private PlayTimer playTimer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_start);
		
		textView_playTime = (TextView) findViewById(R.id.textView_playTime);
		textView_score = (TextView) findViewById(R.id.textView_score);
		
		if (!alreadyAct) {
			card = new Card[PublicConstDefine.CARD_MAX_COUNT];
			user = new UserInfo("noName", 0, 0);
			textView_score.setText("0");
			textView_playTime.setText("00 : 00");

			bSameCard = true;
			combo = 1;
			min = 0;
			sec = 0;
			overTurnedCardId1 = 0;
			overTurnedCardId2 = 0;
			clicked = 0;
			gameClear_CardCnt = 0;
			playTimer = new PlayTimer();
			playTimer.start();
			sameCardCheck_thread = new SameCardCheckThread();
			sameCardCheck_thread.start();
			initGame();
		}
	}

	@Override
	protected void onPause() {
		alreadyAct = true;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (efs_onoff) {
			effectSoundMedia1.release();
			effectSoundMedia2.release();
		}
		alreadyAct = false;
		sameCardCheck_thread.interrupt();
		playTimer.interrupt();
	}

	public void setOverTurn(int overTurnedCardId) {
		if (efs_onoff) {
			if (effectSoundMedia1.isPlaying()) {
				effectSoundMedia2.seekTo(400);
				effectSoundMedia2.start();
			} else {
				effectSoundMedia1.seekTo(400);
				effectSoundMedia1.start();
			}
		}

		/* 카드가 처음 뒤집힌 경우 */
		if (overTurnedCardId1 == 0) {
			overTurnedCardId1 = overTurnedCardId;
			clicked++;
		}
		/* 카드가 이미 한 장 뒤집힌 경우. 2번째 카드 ID를 저장. */
		else {
			clicked++;
			/* 두 번째 카드의 ID를 저장 */
			overTurnedCardId2 = overTurnedCardId;
			if (overTurnedCardId1 == overTurnedCardId2) {
				// 점수 획득
				user.setScore(50 + ((combo - 1) * 10));
				combo++;
				textView_score.setText(Integer.toString(user.getScore()));

				bSameCard = true;
				overTurnedCardId1 = 0;
				overTurnedCardId2 = 0;
				clicked = 0;
				gameClear_CardCnt++;
				if (gameClear_CardCnt == PublicConstDefine.KIND_OF_CARD) {
					// 게임 종료.
					user.setClearTime(min, sec);
					Intent intent = new Intent(this, GameFinish.class);
					intent.putExtra(PublicConstDefine.USERINFO_EXTRA_KEY, user);
					startActivity(intent);
					this.finish();
				}
			} else {
				combo = 1;
				bSameCard = false;
			}
			sameCardCheck_thread.sameCardCheckNotify();
		}
	}// END setOverTurn

	public int getClicked() {
		return clicked;
	}

	private void initGame() {
		card[0] = (Card) findViewById(R.id.card_f1);
		card[1] = (Card) findViewById(R.id.card_f2);
		card[2] = (Card) findViewById(R.id.card_f3);
		card[3] = (Card) findViewById(R.id.card_f4);
		card[4] = (Card) findViewById(R.id.card_f5);
		card[5] = (Card) findViewById(R.id.card_f6);

		card[6] = (Card) findViewById(R.id.card_s1);
		card[7] = (Card) findViewById(R.id.card_s2);
		card[8] = (Card) findViewById(R.id.card_s3);
		card[9] = (Card) findViewById(R.id.card_s4);
		card[10] = (Card) findViewById(R.id.card_s5);
		card[11] = (Card) findViewById(R.id.card_s6);

		card[12] = (Card) findViewById(R.id.card_t1);
		card[13] = (Card) findViewById(R.id.card_t2);
		card[14] = (Card) findViewById(R.id.card_t3);
		card[15] = (Card) findViewById(R.id.card_t4);
		card[16] = (Card) findViewById(R.id.card_t5);
		card[17] = (Card) findViewById(R.id.card_t6);

		card[18] = (Card) findViewById(R.id.card_fo1);
		card[19] = (Card) findViewById(R.id.card_fo2);
		card[20] = (Card) findViewById(R.id.card_fo3);
		card[21] = (Card) findViewById(R.id.card_fo4);
		card[22] = (Card) findViewById(R.id.card_fo5);
		card[23] = (Card) findViewById(R.id.card_fo6);

		card[24] = (Card) findViewById(R.id.card_fiv1);
		card[25] = (Card) findViewById(R.id.card_fiv2);
		card[26] = (Card) findViewById(R.id.card_fiv3);
		card[27] = (Card) findViewById(R.id.card_fiv4);
		card[28] = (Card) findViewById(R.id.card_fiv5);
		card[29] = (Card) findViewById(R.id.card_fiv6);

		card[30] = (Card) findViewById(R.id.card_six1);
		card[31] = (Card) findViewById(R.id.card_six2);

		for (int i = 0; i < PublicConstDefine.CARD_MAX_COUNT; i++) {
			card[i].setCardId(i);
			card[i].setOnTouchListener((OnTouchListener) card[i]);
		}

		efs_onoff = (boolean) getIntent().getBooleanExtra(
				PublicConstDefine.EFFECT_SOUND_ONOFF_KEY, true);
		if (efs_onoff) {
			effectSoundMedia1 = MediaPlayer.create(this, R.raw.cardsound);
			effectSoundMedia2 = MediaPlayer.create(this, R.raw.cardsound);
		}
		setCardImg();
	}

	private void setCardImg() {
		int cnt = PublicConstDefine.CARD_MAX_COUNT;
		int rnd;
		ArrayList<Integer> randImgIdList = new ArrayList<Integer>();

		for (int i = 0; i < 2; i++) {
			randImgIdList.add(R.drawable.cardimage1);
			randImgIdList.add(R.drawable.cardimage2);
			randImgIdList.add(R.drawable.cardimage3);
			randImgIdList.add(R.drawable.cardimage4);
			randImgIdList.add(R.drawable.cardimage5);
			randImgIdList.add(R.drawable.cardimage6);
			randImgIdList.add(R.drawable.cardimage7);
			randImgIdList.add(R.drawable.cardimage8);
			randImgIdList.add(R.drawable.cardimage9);
			randImgIdList.add(R.drawable.cardimage10);
			randImgIdList.add(R.drawable.cardimage11);
			randImgIdList.add(R.drawable.cardimage12);
			randImgIdList.add(R.drawable.cardimage13);
			randImgIdList.add(R.drawable.cardimage14);
			randImgIdList.add(R.drawable.cardimage15);
			randImgIdList.add(R.drawable.cardimage16);
		}

		while (--cnt >= 0) {
			rnd = (int) (Math.random() * (cnt + 1));
			card[cnt].setCardId(randImgIdList.get(rnd));
			randImgIdList.remove(rnd);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected class SameCardCheckThread extends Thread {
		public synchronized void run() {
			while (true) {
				try {
					if (!bSameCard) {
						sleep(700);
						if (overTurnedCardId1 != 0 && overTurnedCardId2 != 0) {
							for (int i = 0; i < PublicConstDefine.CARD_MAX_COUNT; i++) {
								if ((card[i].getCard_id() == overTurnedCardId1)
										|| (card[i].getCard_id() == overTurnedCardId2)) {
									card[i].setCardOverTurn(false);
								}
							}// END FOR
						}
						clicked = 0;
						overTurnedCardId1 = 0;
						overTurnedCardId2 = 0;
						bSameCard = true;
					} else {
						wait();
					}

				} catch (Exception e) {
					return;
				}
			}
		}

		public synchronized void sameCardCheckNotify() {
			this.notify();
		}
	}// END INNER CLASS_THREAD

	protected class PlayTimer extends Thread {
		public void run() {
			while (true) {
				try {
					sleep(1000);
					if (sec < 59) {
						sec++;
					} else {
						if (sec == 59) {
							min++;
						} else {
							// 1시간 플레이 한 경우. 강종 ㄱㄱ
							// 게임을 실패로 처리해야함.
							System.exit(-1);
						}
						sec = 0;
					}
					textView_playTime.post(new Runnable() {
						public void run() {
							if (sec < 10) {
								if (min < 10) {
									textView_playTime.setText("0"
											+ Integer.toString(min) + " : 0"
											+ Integer.toString(sec));
								} else {
									textView_playTime.setText(Integer
											.toString(min)
											+ " : 0"
											+ Integer.toString(sec));
								}
							} else {
								if (min < 10) {
									textView_playTime.setText("0"
											+ Integer.toString(min) + " : "
											+ Integer.toString(sec));
								} else {
									textView_playTime.setText(Integer
											.toString(min)
											+ " : "
											+ Integer.toString(sec));
								}
							}
						}
					});
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}// END INNER CLASS_THREAD

}// END CLASS
