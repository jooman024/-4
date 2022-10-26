package sever;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import sever.beans.AccessHistoryBean;
import sever.beans.MemberBean;

/* 로그인, 로그아웃, 접속로그기록 */
public class Auth {

	public Auth() {
		
	}
	
	/* job : 로그인 [2][6]
	 * 1. param : id, password
	 * 2. id가 DB에 존재여부 check
	 *    -->DAO가 MEMBERS 전체 레코드를 전달 --> 비교
	 *     2-1. true --> p3
	 *     2-2. false --> client
	 * 3. id와 passwerd를 DB외 비교
	 *     3.1 true --> p4
	 * 4.접속기록(로그기록) 생성
	 * 5. client 결과
	 * */
	public boolean accessCtl(String clientData) {
		/* serviceCode=1&id=hoonzzang&[asswprd=1234 -->
		 * split("&") --> {"serviceCode=1","id=hoonzzang","password=1234"}
		 * [1].split("=") --> {"id","hoonzzang"}[1] --> MemberBean.setAccessCode
		 * [2].split("=") --> {"pw","1234"}[1] --> MemberBean.setSecretCode
		 * */
		MemberBean member = this.setMemberBean(clientData);
		DataAccessObject dao = new DataAccessObject();
		ArrayList<MemberBean> memberList = dao.readDatabase(0);
		AccessHistoryBean historyBean;
		boolean accessResult = false;
		
		if(this.compareAccessCode(member.getAccessCode(), memberList)) {
			if(this.isAuth(member, memberList)) {
				/* (fileIdx=1)accessCode, (data=> yyyyMMddHHmmss),(accessType=1)*/   
				/*백엔드에는 시간이없어서 추가*/
				historyBean = new AccessHistoryBean();
				historyBean.setFileIdx(1);
				historyBean.setAccessCode(member.getAccessCode());
				historyBean.setAccessDatadate(this.getDate(false));
				historyBean.setAccessType(1);
				
				accessResult= dao.writeAccessHistory(historyBean);
			}
		}
		return accessResult;
		
	}/*현재날짜를 선택하는것*/
	private String getDate(boolean isDate) {
		String pattern = (isDate)? "yyyyMMdd" : "yyyyMMddHHmmss";
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}
	private MemberBean setMemberBean(String clientData) {     //변수지정
		MemberBean member = new MemberBean();
		
		String[] splitData = clientData.split("&");
		member.setAccessCode(splitData[1].split("=")[1]);
		member.setSecretCode(splitData[2].split("=")[1]);
		
		return member;
		
	} //DAO로 가야하는 부분
	/* AccessCode 존재여부 판단 */
	private boolean compareAccessCode(String code, ArrayList<MemberBean> memberList){
		boolean result = false;
		for(MemberBean member : memberList) {
			if(code.equals(member.getAccessCode())) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	/* AccessCode와 SecretCode의 비교  아이디 비번 비교*/
	private boolean isAuth(MemberBean member, ArrayList<MemberBean> memberList) {
		boolean result = false;
		for(MemberBean memberInfo : memberList) {
			if(member.getAccessCode().equals(memberInfo.getAccessCode())) {
				if(member.getAccessCode().equals(memberInfo.getAccessCode())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}//여기까지
}

