package argo.cost.holidayRecord.service;

import argo.cost.holidayRecord.model.HolidayRecordForm;

public interface HolidayRecordService {

	/**
	 * 休暇管理情報セット
	 * 
	 * @param form
	 *            休暇管理画面情報
	 */
	void setHolidayRecordInfo(HolidayRecordForm form);
}
