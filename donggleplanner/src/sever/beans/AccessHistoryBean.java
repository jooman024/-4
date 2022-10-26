package sever.beans;

/* bean은 생성자가 없음 */
public class AccessHistoryBean {
	private int fileIdx;
	private String accessCode;
	private String accessDatadate;
	private int accessType;
	public String getAccessCode() {
		return accessCode;
	}
	public int getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getAccessDatadate() {
		return accessDatadate;
	}
	public void setAccessDatadate(String accessDatadate) {
		this.accessDatadate = accessDatadate;
	}
	public int getAccessType() {
		return accessType;
	}
	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}
}
