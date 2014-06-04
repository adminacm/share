package argo.cost.holidayRecord.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.holidayRecord.dao.HolidayRecordDao;
import argo.cost.holidayRecord.model.AbsenceVO;
import argo.cost.holidayRecord.model.HolidayRecordForm;
import argo.cost.holidayRecord.model.PayHolidayVO;
import argo.cost.holidayRecord.model.SpecialHolidayVO;

/**
 * <p>
 * 休暇管理画面サービス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Service
public class HolidayRecordServiceImpl implements HolidayRecordService {
	
	/**
	 * 休暇管理画面DAO
	 */
	@Autowired
	private HolidayRecordDao holidayRecordDao;
	
	/**
	 * 休暇管理情報をセット
	 * 
	 * @param form
	 *            休暇管理画面情報
	 */
	@Override
	public void setHolidayRecordInfo(HolidayRecordForm form) {
		
		// ユーザＩＤを取得
		String strUserId = form.getUserId();
		
		// 年度を取得
		String strYearPeriod =  form.getYearPeriod();
		
		// 有給休暇情報を取得
		List<PayHolidayVO> payHolidayList = holidayRecordDao.getPayHolidayList(strUserId, strYearPeriod);
		// 欠勤情報を取得
		List<AbsenceVO> absenceList = holidayRecordDao.getAbsenceList(strUserId, strYearPeriod);
		// 特別情報を取得
		List<SpecialHolidayVO> specialHolidayList = holidayRecordDao.getSpecialHolidayList(strUserId, strYearPeriod);

		// 有給休暇情報をセット
		form.setPayHolidayList(payHolidayList);
		// 欠勤情報をセット
		form.setAbsenceList(absenceList);
		// 特別情報をセット
		form.setSpecialHolidayList(specialHolidayList);
	}
}
