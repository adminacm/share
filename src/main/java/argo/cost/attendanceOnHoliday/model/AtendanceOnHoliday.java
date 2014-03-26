package argo.cost.attendanceOnHoliday.model;

import java.util.List;

public class AtendanceOnHoliday {

	// 日付
	private String strAtendanceDate;
	// 勤務日区分
	private List<CodeNameMap> atendanceDayKbnList;
	private String selectedAtendanceDayKbn;
	// 勤務開始時間
	private String strAtendanceTimeStat;
	// 勤務完了時間
	private String strAtendanceTimeEnd;
	// 振替日　
	private String strHurikaeDate;
	// プロジェクトコード
	private List<CodeNameMap> projCdList;
	private String selectedProjCd;
	// 業務内容
	private String strCommont;

	public String getStrAtendanceDate() {
		return strAtendanceDate;
	}

	public void setStrAtendanceDate(String strAtendanceDate) {
		this.strAtendanceDate = strAtendanceDate;
	}

	public String getStrAtendanceTimeStat() {
		return strAtendanceTimeStat;
	}

	public void setStrAtendanceTimeStat(String strAtendanceTimeStat) {
		this.strAtendanceTimeStat = strAtendanceTimeStat;
	}

	public String getStrAtendanceTimeEnd() {
		return strAtendanceTimeEnd;
	}

	public void setStrAtendanceTimeEnd(String strAtendanceTimeEnd) {
		this.strAtendanceTimeEnd = strAtendanceTimeEnd;
	}

	public String getStrHurikaeDate() {
		return strHurikaeDate;
	}

	public void setStrHurikaeDate(String strHurikaeDate) {
		this.strHurikaeDate = strHurikaeDate;
	}

	public String getStrCommont() {
		return strCommont;
	}

	public void setStrCommont(String strCommont) {
		this.strCommont = strCommont;
	}

	public String getSelectedAtendanceDayKbn() {
		return selectedAtendanceDayKbn;
	}

	public void setSelectedAtendanceDayKbn(String selectedAtendanceDayKbn) {
		this.selectedAtendanceDayKbn = selectedAtendanceDayKbn;
	}

	public String getSelectedProjCd() {
		return selectedProjCd;
	}

	public void setSelectedProjCd(String selectedProjCd) {
		this.selectedProjCd = selectedProjCd;
	}

	public List<CodeNameMap> getAtendanceDayKbnList() {
		return atendanceDayKbnList;
	}

	public void setAtendanceDayKbnList(List<CodeNameMap> atendanceDayKbnList) {
		this.atendanceDayKbnList = atendanceDayKbnList;
	}

	public List<CodeNameMap> getProjCdList() {
		return projCdList;
	}

	public void setProjCdList(List<CodeNameMap> projCdList) {
		this.projCdList = projCdList;
	}

}
