package argo.cost.attendanceOnHolidayRecord.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.entity.Users;
import argo.cost.common.model.AbstractForm;
import argo.cost.common.model.ListItemVO;

/**
 * <p>
 * 休日出勤管理画面フォーム情報クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
public class AttendanceOnHolidayRecordForm extends AbstractForm implements Serializable {

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
	 * 氏名（リスト用）
	 */
	private String name;
	
	/**
	 * 氏名リスト
	 */
	private List<Users> nameList;
	
	/**
	 * 休日振替勤務リスト
	 */
	private List<HolidayExchangeWorkVO> holidayExchangeWorkList;
	
	/**
	 * 休日勤務リスト
	 */
	private List<HolidayWorkVO> holidayOverWorkList;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Users> getNameList() {
		return nameList;
	}

	public void setNameList(List<Users> nameList) {
		this.nameList = nameList;
	}

	public List<HolidayExchangeWorkVO> getHolidayExchangeWorkList() {
		return holidayExchangeWorkList;
	}

	public void setHolidayExchangeWorkList(
			List<HolidayExchangeWorkVO> holidayExchangeWorkList) {
		this.holidayExchangeWorkList = holidayExchangeWorkList;
	}

	public List<HolidayWorkVO> getHolidayOverWorkList() {
		return holidayOverWorkList;
	}

	public void setHolidayOverWorkList(List<HolidayWorkVO> holidayOverWorkList) {
		this.holidayOverWorkList = holidayOverWorkList;
	}
}
