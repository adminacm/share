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
import argo.cost.common.entity.KintaiInfo;
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
		Integer totleTimeQuantity = 0;
		
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("atendanceDate", yearPeriod + "%");
		// 休暇欠勤区分に「有給休暇」をセット
		String[] vals = {CommonConstant.KK_KBN_ZENKYU, CommonConstant.KK_KBN_HANKYU, CommonConstant.KK_KBN_JIKANKYU};
		condition.addConditionIn("kyukaKekinKbnMaster.code", vals);
		
		// 有給休暇情報取得
		List<KintaiInfo> kintaiList = baseDao.findResultList(condition, KintaiInfo.class);

		// 画面の有給休暇一覧
		List<PayHolidayVO> payHolidayList = new ArrayList<PayHolidayVO>();
		PayHolidayVO payHolidayInfo = null;

		if (kintaiList.size() > 0) {
		
			for (KintaiInfo kyukaInfo : kintaiList) {
				
				payHolidayInfo = new PayHolidayVO();
				//　日付
				payHolidayInfo.setPayHolidayDate(CostDateUtils.formatDate(kyukaInfo.getAtendanceDate(), CommonConstant.YYYY_MM_DD));
				// 休暇欠勤区分コード
				payHolidayInfo.setHolidayKbnCode(kyukaInfo.getKyukaKekinKbnMaster().getCode());
				//　時間数
				payHolidayInfo.setHourQuantity(kyukaInfo.getKyukaJikansu().toString());
				// 休暇欠勤区分名称
				payHolidayInfo.setHolidayKbnName(kyukaInfo.getKyukaKekinKbnMaster().getName().replace("(有給休暇)", ""));
				
				if (CommonConstant.KK_KBN_ZENKYU.equals(payHolidayInfo.getHolidayKbnCode())) {
	
					// 日数
					payHolidayInfo.setDayQuantity("1");
					// 時間数
					payHolidayInfo.setHourQuantity(null);
				} else if (CommonConstant.KK_KBN_HANKYU.equals(payHolidayInfo.getHolidayKbnCode())) {
	
					// 日数
					payHolidayInfo.setDayQuantity("0.5");
					// 時間数
					payHolidayInfo.setHourQuantity(null);
				} 
				// 有給休暇一覧リストを追加
				payHolidayList.add(payHolidayInfo);
	
				// 日数がnull以外の場合
				if (payHolidayInfo.getDayQuantity() != null) {
					// 日数合計
					totleDayQuantity += Double.valueOf(payHolidayInfo.getDayQuantity());
				}
				// 時間数がnull以外の場合
				if (payHolidayInfo.getHourQuantity() != null) {
					// 時間数合計
					totleTimeQuantity += Integer.valueOf(payHolidayInfo.getHourQuantity());
				}
			}
			
			// 合計行
			payHolidayInfo = new PayHolidayVO();
			payHolidayInfo.setPayHolidayDate("累計");
			// 日数
			if (totleDayQuantity != 0) {
				payHolidayInfo.setDayQuantity(totleDayQuantity.toString() + " 日");
			}
			// 時間数
			if (totleTimeQuantity != 0) {
				payHolidayInfo.setHourQuantity(totleTimeQuantity.toString() + " 時間");
			}
			payHolidayList.add(payHolidayInfo);
		}

		// 有給休暇一覧リストを戻り
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
		Integer totleTimeQuantity = 0;
		
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("atendanceDate", yearPeriod + "%");
		// 休暇欠勤区分に「欠勤」「遅刻・早退」をセット
		String[] vals = {CommonConstant.KK_KBN_KEKIN, CommonConstant.KK_KBN_CHIKOKU};
		condition.addConditionIn("kyukaKekinKbnMaster.code", vals);
		
		// 欠勤情報取得
		List<KintaiInfo> kekinList = baseDao.findResultList(condition, KintaiInfo.class);
		
		// 画面の欠勤一覧
		List<AbsenceVO> absenceList = new ArrayList<AbsenceVO>();
		AbsenceVO absenceInfo = null;
		
		if (kekinList.size() > 0) {
			
			for (KintaiInfo kekinInfo : kekinList) {
				
				absenceInfo = new AbsenceVO();
				// 日付
				absenceInfo.setAbsentDate(CostDateUtils.formatDate(kekinInfo.getAtendanceDate(), CommonConstant.YYYY_MM_DD));
				//　時間数
				absenceInfo.setHourQuantity(kekinInfo.getKyukaJikansu().toString());
				// 欠勤時間数が定時勤務時間以上の場合
				if (kekinInfo.getKyukaJikansu().compareTo(new BigDecimal("7.5")) >= 0) {

					//　日数
					absenceInfo.setDayQuantity("1");
					//　時間数
					absenceInfo.setHourQuantity(null);
				}
				// 欠勤一覧リストを追加
				absenceList.add(absenceInfo);
				
				// 日数がnull以外の場合
				if (absenceInfo.getDayQuantity() != null) {
					// 日数合計
					totleDayQuantity += Double.valueOf(absenceInfo.getDayQuantity());
				}
				// 時間数がnull以外の場合
				if (absenceInfo.getHourQuantity() != null) {
					// 時間数合計
					totleTimeQuantity += Integer.valueOf(absenceInfo.getHourQuantity());
				}
			}
			// 合計行
			absenceInfo = new AbsenceVO();
			absenceInfo.setAbsentDate("累計");
			// 日数
			if (totleDayQuantity != 0) {
				absenceInfo.setDayQuantity(totleDayQuantity.toString() + " 日");
			}
			// 時間数
			if (totleTimeQuantity != 0) {
				absenceInfo.setHourQuantity(totleTimeQuantity.toString() + " 時間");
			}
			absenceList.add(absenceInfo);
		}

		// 欠勤一覧リストを戻り
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
		Integer totleDayQuantity = 0;
		
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("atendanceDate", yearPeriod + "%");
		// 休暇欠勤区分に「特別休暇」をセット
		condition.addConditionEqual("kyukaKekinKbnMaster.code", CommonConstant.KK_KBN_TOKUBETUKYU);
		
		// 特別休暇情報取得
		List<KintaiInfo> kintaiList = baseDao.findResultList(condition, KintaiInfo.class);

		List<SpecialHolidayVO> specialHolidayList = new ArrayList<SpecialHolidayVO>();
		SpecialHolidayVO specialHolidayInfo = null;
		
		if (kintaiList.size() > 0) {
			
			for (KintaiInfo kintaiInfo : kintaiList) {
				
				specialHolidayInfo = new SpecialHolidayVO();
				// 日付
				specialHolidayInfo.setSpecialHolidayDate(CostDateUtils.formatDate(kintaiInfo.getAtendanceDate(), CommonConstant.YYYY_MM_DD));
				//　日数
				specialHolidayInfo.setDayQuantity("1");
				// 特別休暇一覧リストを追加
				specialHolidayList.add(specialHolidayInfo);
				
				// 日数がnull以外の場合
				if (specialHolidayInfo.getDayQuantity() != null) {
					// 日数合計
					totleDayQuantity += Integer.valueOf(specialHolidayInfo.getDayQuantity());
				}
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

		// 特別休暇一覧リストを戻り
		return specialHolidayList;
	}
}
