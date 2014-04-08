package argo.cost.holidayRecord.service;

import argo.cost.holidayRecord.model.HolidayRecordForm;


public interface HolidayRecordService {

	/**
	 * 休暇管理情報取得
	 * 
	 * @param form
	 *            休暇管理画面情報
	 */
	void searchHolidayRecord(HolidayRecordForm form);
}
