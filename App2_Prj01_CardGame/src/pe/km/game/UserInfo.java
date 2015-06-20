package pe.km.game;

import java.io.Serializable;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int score;
	private int clearTime;
	private long dbId;
	private int min;
	private int sec;

	public UserInfo(String name, int score, int clearTime) {
		this.setName(name);
		this.setScore(score);
		this.setClearTime(clearTime);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if(score < 0) {
			this.score = 0;
		} else {
			this.score += score;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.equals("")) {
			name = "noName";
		} else {
			this.name = name;
		}
	}

	public int getClearTime() {
		return clearTime;
	}

	public void setClearTime(int clearTime) {
		this.clearTime = clearTime;
	}
	
	public void setClearTime(int min, int sec) {
		this.setMin(min);
		this.setSec(sec);
		clearTime = (min * 100) + sec;
	}

	public long getDbId() {
		return dbId;
	}

	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}

}
