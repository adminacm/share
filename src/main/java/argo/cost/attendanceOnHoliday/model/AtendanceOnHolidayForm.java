package argo.cost.attendanceOnHoliday.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.ListItemVO;

public class AtendanceOnHolidayForm extends AbstractForm implements Serializable {

	/**
	 * 繝代ず繝ｧ繝ｳID
	 */
	private static final long serialVersionUID = 1L;
	// 譌･莉�
	private String strAtendanceDate;
	// 蜍､蜍呎律蛹ｺ蛻�
	private List<ListItemVO> atendanceDayKbnList;
	private String selectedAtendanceDayKbn;
	// 蜍､蜍咎幕蟋区凾髢�
	private String strAtendanceTimeStat;
	// 蜍､蜍吝ｮ御ｺ�凾髢�
	private String strAtendanceTimeEnd;
	// 謖ｯ譖ｿ譌･縲�
	private String strHurikaeDate;
	// 繝励Ο繧ｸ繧ｧ繧ｯ繝医さ繝ｼ繝�
	private List<ListItemVO> projCdList;
	private String selectedProjCd;
	// 讌ｭ蜍吝�螳ｹ
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

	public List<ListItemVO> getAtendanceDayKbnList() {
		return atendanceDayKbnList;
	}

	public void setAtendanceDayKbnList(List<ListItemVO> atendanceDayKbnList) {
		this.atendanceDayKbnList = atendanceDayKbnList;
	}

	public List<ListItemVO> getProjCdList() {
		return projCdList;
	}

	public void setProjCdList(List<ListItemVO> projCdList) {
		this.projCdList = projCdList;
	}

}
