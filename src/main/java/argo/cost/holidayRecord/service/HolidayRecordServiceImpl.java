package argo.cost.holidayRecord.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.entity.KyukaKekin;
import argo.cost.common.utils.CostDateUtils;
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
	 * @throws ParseException 
	 */
	@Override
	public void setHolidayRecordInfo(HolidayRecordForm form) throws ParseException {
		
		// ユーザＩＤを取得
		String strUserId = form.getUserId();
		
		// 年度を取得
		String strYearPeriod =  form.getYearPeriod();
		
		// 有給休暇情報を取得
		List<PayHolidayVO> payHolidayList = holidayRecordDao.getPayHolidayList(strUserId, strYearPeriod);
		// 有給休暇情報をセット
		form.setPayHolidayList(payHolidayList);
		
		// 欠勤情報を取得
		List<KyukaKekin> kekinList = holidayRecordDao.getAbsenceList(strUserId, strYearPeriod);
		// 画面の欠勤一覧
		List<AbsenceVO> absenceList = new ArrayList<AbsenceVO>();
		AbsenceVO absenceInfo = null;
		
		// 合計日数
		Double totleDayQuantity = 0.0;
		// 合計時間数
		Double totleTimeQuantity = 0.0;
		
		if (kekinList.size() > 0) {
			
			for (KyukaKekin kekinInfo : kekinList) {
				
				absenceInfo = new AbsenceVO();
				// 日付
				absenceInfo.setAbsentDate(CostDateUtils.formatDate(kekinInfo.getKyukaDate(), CommonConstant.YYYY_MM_DD));
				//　時間数
				absenceInfo.setHourQuantity(kekinInfo.getKyukaJikansu().toString());
				// 欠勤時間数が定時勤務時間以上の場合
				if (kekinInfo.getKyukaJikansu().compareTo(new BigDecimal("8")) >= 0) {

					//　日数
					absenceInfo.setDayQuantity("1.0");
					//　時間数
					absenceInfo.setHourQuantity(kekinInfo.getKyukaJikansu().subtract(new BigDecimal("8")).toString());
				}
				absenceList.add(absenceInfo);
				
				// 日数がnull以外の場合
				if (absenceInfo.getDayQuantity() != null) {

					totleDayQuantity += Double.valueOf(absenceInfo.getDayQuantity());
				}
				
				// 時間数がnull以外の場合
				if (absenceInfo.getHourQuantity() != null) {
					totleTimeQuantity += Double.valueOf(absenceInfo.getHourQuantity());
				}
				
			}
			// 合計行
			absenceInfo = new AbsenceVO();
			absenceInfo.setAbsentDate("累計");
			// 日数
			if (totleDayQuantity != 0) {
				absenceInfo.setDayQuantity(totleDayQuantity.toString() + "日");
			}
			// 時間数
			if (totleTimeQuantity != 0) {
				absenceInfo.setHourQuantity(totleTimeQuantity.toString() + "時間");
			}
			absenceList.add(absenceInfo);
		}
		// 欠勤情報をセット
		form.setAbsenceList(absenceList);
		
		// 特別情報を取得
		List<KyukaKekin> tokubetukyuList = holidayRecordDao.getSpecialHolidayList(strUserId, strYearPeriod);
		// 合計日数
		totleDayQuantity = 0.0;
		// 合計時間数
		totleTimeQuantity = 0.0;
		List<SpecialHolidayVO> specialHolidayList = new ArrayList<SpecialHolidayVO>();
		SpecialHolidayVO specialHolidayInfo = null;
		
		if (kekinList.size() > 0) {
			
			for (KyukaKekin tokubetukyuInfo : tokubetukyuList) {
				
				specialHolidayInfo = new SpecialHolidayVO();
				// 日付
				specialHolidayInfo.setSpecialHolidayDate(CostDateUtils.formatDate(tokubetukyuInfo.getKyukaDate(), CommonConstant.YYYY_MM_DD));
				//　日数
				specialHolidayInfo.setDayQuantity("1.0");
				
				// 日数がnull以外の場合
				if (specialHolidayInfo.getDayQuantity() != null) {

					totleDayQuantity += Double.valueOf(specialHolidayInfo.getDayQuantity());
				}
				
				specialHolidayList.add(specialHolidayInfo);
				
			}
			// 合計行
			specialHolidayInfo = new SpecialHolidayVO();
			specialHolidayInfo.setSpecialHolidayDate("累計");
			// 日数
			if (totleDayQuantity != 0) {
				specialHolidayInfo.setDayQuantity(totleDayQuantity.toString() + "日");
			}
			specialHolidayList.add(specialHolidayInfo);
		}

		// 特別情報をセット
		form.setSpecialHolidayList(specialHolidayList);
	}
}
