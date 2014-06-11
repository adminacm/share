package argo.cost.holidayRecord.service;

import java.text.ParseException;

import argo.cost.holidayRecord.model.HolidayRecordForm;

/**
 * <p>
 * 休暇管理画面サービスのインターフェイス
 * </p>
 *
 * @author COST argo Corporation.
 */
public interface HolidayRecordService {

	/**
	 * 休暇管理情報セット
	 * 
	 * @param form
	 *            休暇管理画面情報
	 */
	void setHolidayRecordInfo(HolidayRecordForm form) throws ParseException;
}
