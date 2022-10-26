package sever;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sever.beans.AccessHistoryBean;
import sever.beans.MemberBean;

/* Data File과의 통신 [3][5]*/
public class DataAccessObject {
	String[] fileInfo = { "C:\\java\\data\\donggleplanner\\src\\database\\MEMBERS.txt",
			"C:\\java\\data\\donggleplanner\\src\\database\\ACCESSHISTORY.txt" };

	public DataAccessObject() {
	}

	/* 회원정보 수집 */ // 예외정보 처리
	public ArrayList<MemberBean> readDatabase(int fileIdx) {
		String line;
		ArrayList<MemberBean> memberList = null;
		MemberBean member;
		BufferedReader buffer = null;

		try {
			buffer = new BufferedReader(new FileReader(new File(fileInfo[fileIdx])));
			while ((line = buffer.readLine()) != null) {
				String[] record = line.split(",");
				memberList = new ArrayList<MemberBean>();
				member = new MemberBean();
				member.setAccessCode(record[0]);
				member.setSecretCode(record[1]);
				member.setUserName(record[2]);
				member.setPhoneNumber(record[3]);
				member.setActivation(Integer.parseInt(record[4]));
				memberList.add(member);
			}
		} catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다.");
			e.printStackTrace();
		} catch (Exception e) {
			memberList = null;
			System.out.println("파일로부터 데이터를 가져올 수 없습니다.");
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return memberList;
	}

	/* 접속기록 작성 */
	public boolean writeAccessHistory(AccessHistoryBean accessInfo) {
		boolean result = false;
		BufferedWriter writer = null;

		try { /* 버퍼라이터에다쓰고 파일라이터에 기록한다 */
			writer = new BufferedWriter(new FileWriter(new File(this.fileInfo[accessInfo.getFileIdx()]), true));
			writer.write(accessInfo.getAccessCode() + "," + accessInfo.getAccessDatadate() + ","
					+ accessInfo.getAccessType());
			writer.newLine();
			writer.flush();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {writer.close();} catch (IOException e) { e.printStackTrace();}
		}	
		return result;

		}
	}
