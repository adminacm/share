package argo.cost.attendanceOnHoliday.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 休日勤務画面フォーム情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class AtendanceOnHolidayForm extends AbstractForm implements Serializable {

	/**
	 * パジョンID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *　勤務日付
	 */
	private String strAtendanceDate;
	/**
	 *　勤務日付_表示
	 */
	private String strAtendanceDateShow;
	/**
	 *　勤務日区分リスト
	 */
	private List<ListItemVO> atendanceDayKbnList;
	/**
	 *　勤務日区分
	 */
	@NotBlank(message = "勤務日区分を選択してください")
	private String selectedAtendanceDayKbn;
	/**
	 *　勤務開始時間
	 */
	private String strAtendanceTimeStat;
	/**
	 *　勤務終了時間
	 */
	private String strAtendanceTimeEnd;
	/**
	 *　振替日
	 */
	private String strHurikaeDate;
	/**
	 *　プロジェクト名リスト
	 */
	private List<ListItemVO> projCdList;
	/**
	 *　プロジェクト名区分
	 */
	private String selectedProjCd;
	/**
	 *　業務内容
	 */
	private String strCommont;

	public String getStrAtendanceDate() {
		return strAtendanceDate;
	}

	public void setStrAtendanceDate(String strAtendanceDate) {
		this.strAtendanceDate = strAtendanceDate;
	}

	public String getStrAtendanceDateShow() {
		return strAtendanceDateShow;
	}

	public void setStrAtendanceDateShow(String strAtendanceDateShow) {
		this.strAtendanceDateShow = strAtendanceDateShow;
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
