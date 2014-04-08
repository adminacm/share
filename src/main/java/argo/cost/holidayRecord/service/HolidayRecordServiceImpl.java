package argo.cost.holidayRecord.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.holidayRecord.dao.HolidayRecordDao;
import argo.cost.holidayRecord.model.AbsenceVO;
import argo.cost.holidayRecord.model.HolidayRecordForm;
import argo.cost.holidayRecord.model.PayHolidayVO;
import argo.cost.holidayRecord.model.SpecialHolidayVO;

@Service
public class HolidayRecordServiceImpl implements HolidayRecordService {
	
	@Autowired
	private HolidayRecordDao holidayRecordDao;
	

	/**
	 * 休暇管理情報取得
	 * 
	 * @param form
	 *            休暇管理画面情報
	 */
	public void searchHolidayRecord(HolidayRecordForm holidayRecordForm) {
		String strYearPeriod =  holidayRecordForm.getYearPeriod();
		// 有給休暇情報の取得
		List<PayHolidayVO> payHolidayList = holidayRecordDao.searchPayHolidayList(strYearPeriod);
		// 欠勤情報の取得
		List<AbsenceVO> absenceList = holidayRecordDao.searchAbsenceList(strYearPeriod);
		// 特別情報の取得
		List<SpecialHolidayVO> specialHolidayList = holidayRecordDao.searchSpecialHolidayList(strYearPeriod);
		
		holidayRecordForm.setPayHolidayList(payHolidayList);
		holidayRecordForm.setAbsenceList(absenceList);
		holidayRecordForm.setSpecialHolidayList(specialHolidayList);
		
	}

}
