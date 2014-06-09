package argo.cost.holidayRecord.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 休暇管理画面フォーム情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class HolidayRecordForm extends AbstractForm implements Serializable {

	/**
	 * パジョンID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 年度
	 */
	private String yearPeriod;
	
	/**
	 * 年度リスト
	 */
	private List<ListItemVO> yearPeriodList;
	
	/**
	 * 有給休暇リスト
	 */
	private List<PayHolidayVO> payHolidayList;
	
	/**
	 * 欠勤リスト
	 */
	private List<AbsenceVO> absenceList;
	
	/**
	 * 特別休暇リスト
	 */
	private List<SpecialHolidayVO> specialHolidayList;

	public String getYearPeriod() {
		return yearPeriod;
	}

	public void setYearPeriod(String yearPeriod) {
		this.yearPeriod = yearPeriod;
	}

	public List<ListItemVO> getYearPeriodList() {
		return yearPeriodList;
	}

	public void setYearPeriodList(List<ListItemVO> yearPeriodList) {
		this.yearPeriodList = yearPeriodList;
	}

	public List<PayHolidayVO> getPayHolidayList() {
		return payHolidayList;
	}

	public void setPayHolidayList(List<PayHolidayVO> payHolidayList) {
		this.payHolidayList = payHolidayList;
	}

	public List<AbsenceVO> getAbsenceList() {
		return absenceList;
	}

	public void setAbsenceList(List<AbsenceVO> absenceList) {
		this.absenceList = absenceList;
	}

	public List<SpecialHolidayVO> getSpecialHolidayList() {
		return specialHolidayList;
	}

	public void setSpecialHolidayList(List<SpecialHolidayVO> specialHolidayList) {
		this.specialHolidayList = specialHolidayList;
	}
}
