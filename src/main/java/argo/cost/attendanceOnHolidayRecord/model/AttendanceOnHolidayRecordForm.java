package argo.cost.attendanceOnHolidayRecord.model;

import java.io.Serializable;
import java.util.List;

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
	 * 氏名
	 */
	private String userName;
	
	/**
	 * 氏名リスト
	 */
	private List<ListItemVO> userNameList;
	
	/**
	 * 休日振替勤務リスト
	 */
	private List<HolidayExchangeWorkVO> holidayExchangeWorkList;
	
	/**
	 * 休日勤務リスト
	 */
	private List<HolidayOverWorkVO> holidayOverWorkList;
	
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<ListItemVO> getUserNameList() {
		return userNameList;
	}
	public void setUserNameList(List<ListItemVO> userNameList) {
		this.userNameList = userNameList;
	}
	public List<HolidayExchangeWorkVO> getHolidayExchangeWorkList() {
		return holidayExchangeWorkList;
	}
	public void setHolidayExchangeWorkList(
			List<HolidayExchangeWorkVO> holidayExchangeWorkList) {
		this.holidayExchangeWorkList = holidayExchangeWorkList;
	}
	public List<HolidayOverWorkVO> getHolidayOverWorkList() {
		return holidayOverWorkList;
	}
	public void setHolidayOverWorkList(List<HolidayOverWorkVO> holidayOverWorkList) {
		this.holidayOverWorkList = holidayOverWorkList;
	}
	
}
