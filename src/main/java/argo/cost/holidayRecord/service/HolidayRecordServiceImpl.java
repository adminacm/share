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
	 * 休暇管理情報をセット
	 * 
	 * @param form
	 *            休暇管理画面情報
	 */
	@Override
	public void setHolidayRecordInfo(HolidayRecordForm holidayRecordForm) {
		String strYearPeriod =  holidayRecordForm.getYearPeriod();
		// 有給休暇情報を取得
		List<PayHolidayVO> payHolidayList = holidayRecordDao.getPayHolidayList(strYearPeriod);
		// 欠勤情報を取得
		List<AbsenceVO> absenceList = holidayRecordDao.getAbsenceList(strYearPeriod);
		// 特別情報を取得
		List<SpecialHolidayVO> specialHolidayList = holidayRecordDao.getSpecialHolidayList(strYearPeriod);

		// 有給休暇情報をセット
		holidayRecordForm.setPayHolidayList(payHolidayList);
		// 欠勤情報をセット
		holidayRecordForm.setAbsenceList(absenceList);
		// 特別情報をセット
		holidayRecordForm.setSpecialHolidayList(specialHolidayList);
	}
}
