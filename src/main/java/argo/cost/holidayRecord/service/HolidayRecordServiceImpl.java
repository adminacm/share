package argo.cost.holidayRecord.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.KyukaKekin;
import argo.cost.common.utils.CostDateUtils;
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
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 休暇管理情報をセット
	 * 
	 * @param form
	 *            休暇管理画面情報
	 * @throws ParseException 
	 */
	@Override
	public void setHolidayRecordInfo(HolidayRecordForm form) throws ParseException {

		// 有給休暇一覧取得
		List<PayHolidayVO> payHolidayList = getPayHolidayList(form.getUserId(), form.getYearPeriod());
		// 有給休暇一覧をセット
		form.setPayHolidayList(payHolidayList);

		// 欠勤一覧取得
		List<AbsenceVO> absenceList = getAbsenceList(form.getUserId(), form.getYearPeriod());
		// 欠勤一覧をセット
		form.setAbsenceList(absenceList);
		
		// 特別休暇一覧取得
		List<SpecialHolidayVO> specialHolidayList = getSpecialHolidayList(form.getUserId(), form.getYearPeriod());
		// 特別休暇一覧をセット
		form.setSpecialHolidayList(specialHolidayList);
	}

	/**
	 * 有給休暇一覧取得
	 * 
	 * @param userId
	 *              ユーザID
	 * @param yearPeriod
	 *                  年度
	 * @return 有給休暇一覧
	 * @throws ParseException 
	 */
	private List<PayHolidayVO> getPayHolidayList(String userId,
			String yearPeriod) throws ParseException {
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// 合計日数
		Double totleDayQuantity = 0.0;
		// 合計時間数
		Double totleTimeQuantity = 0.0;
		
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("kyukaDate", yearPeriod + "%");
		// 休暇欠勤区分に「有給休暇」をセット
		String[] vals = {CommonConstant.KK_KBN_ZENKYU, CommonConstant.KK_KBN_HANKYU, CommonConstant.KK_KBN_JIKANKYU};
		condition.addConditionIn("kyukaKekinKbnMaster.code", vals);
		
		// 有給休暇情報取得
		List<KyukaKekin> kyukaList = baseDao.findResultList(condition, KyukaKekin.class);

		// 画面の有給休暇一覧
		List<PayHolidayVO> payHolidayList = new ArrayList<PayHolidayVO>();
		PayHolidayVO payHolidayInfo = null;

		if (kyukaList.size() > 0) {
		
			for (KyukaKekin kyukaInfo : kyukaList) {
				
				payHolidayInfo = new PayHolidayVO();
				//　日付
				payHolidayInfo.setPayHolidayDate(CostDateUtils.formatDate(kyukaInfo.getKyukaDate(), CommonConstant.YYYY_MM_DD));
				// 休暇欠勤区分コード
				payHolidayInfo.setHolidayKbnCode(kyukaInfo.getKyukaKekinKbnMaster().getCode());
				//　時間数
				payHolidayInfo.setHourQuantity(kyukaInfo.getKyukaJikansu().toString());
				// 休暇欠勤区分名称
				payHolidayInfo.setHolidayKbnName(kyukaInfo.getKyukaKekinKbnMaster().getName());
				
				if (CommonConstant.KK_KBN_ZENKYU.equals(payHolidayInfo.getHolidayKbnCode())) {
	
					// 日数
					payHolidayInfo.setDayQuantity("1.0");
					// 時間数
					payHolidayInfo.setHourQuantity(null);
				} else if (CommonConstant.KK_KBN_HANKYU.equals(payHolidayInfo.getHolidayKbnCode())) {
	
					// 日数
					payHolidayInfo.setDayQuantity("0.5");
					// 時間数
					payHolidayInfo.setHourQuantity(null);
				} 
	
				// 日数がnull以外の場合
				if (payHolidayInfo.getDayQuantity() != null) {
	
					totleDayQuantity += Double.valueOf(payHolidayInfo.getDayQuantity());
				}
				
				// 時間数がnull以外の場合
				if (payHolidayInfo.getHourQuantity() != null) {
					totleTimeQuantity += Double.valueOf(payHolidayInfo.getHourQuantity());
				}
				
				payHolidayList.add(payHolidayInfo);
			}
			
			// 合計行
			payHolidayInfo = new PayHolidayVO();
			payHolidayInfo.setPayHolidayDate("累計");
			// 日数
			if (totleDayQuantity != 0) {
				payHolidayInfo.setDayQuantity(totleDayQuantity.toString() + "日");
			}
			// 時間数
			if (totleTimeQuantity != 0) {
				payHolidayInfo.setHourQuantity(totleTimeQuantity.toString() + "時間");
			}
			payHolidayList.add(payHolidayInfo);
		}

		return payHolidayList;
	}

	/**
	 * 欠勤一覧取得
	 * 
	 * @param userId
	 *              ユーザID
	 * @param yearPeriod
	 *                  年度
	 * @return 欠勤一覧
	 * @throws ParseException 
	 */
	private List<AbsenceVO> getAbsenceList(String userId, String yearPeriod) throws ParseException {
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// 合計日数
		Double totleDayQuantity = 0.0;
		// 合計時間数
		Double totleTimeQuantity = 0.0;
		
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("kyukaDate", yearPeriod + "%");
		// 休暇欠勤区分に「欠勤」をセット
		condition.addConditionEqual("kyukaKekinKbnMaster.code", CommonConstant.KK_KBN_KEKIN);
		
		// 欠勤情報取得
		List<KyukaKekin> kekinList = baseDao.findResultList(condition, KyukaKekin.class);
		
		// 画面の欠勤一覧
		List<AbsenceVO> absenceList = new ArrayList<AbsenceVO>();
		AbsenceVO absenceInfo = null;
		
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
		
		return absenceList;
	}
	/**
	 * 特別休暇一覧取得
	 * 
	 * @param userId
	 *              ユーザID
	 * @param yearPeriod
	 *                  年度
	 * @return 特別休暇一覧
	 * @throws ParseException 
	 */
	private List<SpecialHolidayVO> getSpecialHolidayList(String userId,
			String yearPeriod) throws ParseException {
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// 合計日数
		Double totleDayQuantity = 0.0;
		
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("kyukaDate", yearPeriod + "%");
		// 休暇欠勤区分に「特別休暇」をセット
		condition.addConditionEqual("kyukaKekinKbnMaster.code", CommonConstant.KK_KBN_TOKUBETUKYU);
		
		// 特別休暇情報取得
		List<KyukaKekin> tokubetukyuList = baseDao.findResultList(condition, KyukaKekin.class);

		List<SpecialHolidayVO> specialHolidayList = new ArrayList<SpecialHolidayVO>();
		SpecialHolidayVO specialHolidayInfo = null;
		
		if (tokubetukyuList.size() > 0) {
			
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

		return specialHolidayList;
	}
}
