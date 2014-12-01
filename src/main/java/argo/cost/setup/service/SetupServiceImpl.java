package argo.cost.setup.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
import argo.cost.common.exception.BusinessException;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.setup.checker.SetupChecker;
import argo.cost.setup.model.SetupForm;

/**
 * 個人設定画面サービスインタフェースの実現
 *
 * @author COST argo Corporation.
 */
@Service
public class SetupServiceImpl implements SetupService {
	/**
	 * 更新履歴番号
	 */
	private int version = 0;
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;
	
	/**
	 * 個人設定情報を取得
	 * @param UserId
	 *           ユーザＩＤ
	 * @return
	 *        SetupForm 個人設定情報
	 * @throws ParseException 
	 */
	@Override
	public void getSetupInfo(SetupForm setupForm) throws ParseException {
		
		// DBから、個人設定情報を取得
		Users userInfo = baseDao.findById(setupForm.getUserId(), Users.class);
		version = userInfo.getVersion();
		
		// 画面の個人設定情報を設定
		
		// 代理入力者コード
		String strDairishaId = userInfo.getDairishaId();
		// 代理入力者が存在する場合
		if (StringUtils.isNotEmpty(strDairishaId)) {
			setupForm.setAgentCd(strDairishaId);

			// 代理入力者名
			Users agentInfo = baseDao.findById(strDairishaId,Users.class);
			setupForm.setAgentName(agentInfo.getUserName());
		}

		// 1日の勤務時間数
		String oneDayKinmuHours = (StringUtils.endsWith(userInfo.getStandardShiftCd(), "75")) ? "7.5" : "6.0";
		setupForm.setOneDayKinmuHours(oneDayKinmuHours);
		
		// 1日の勤務時間数リストを設定する
		List<String> oneDayKinmuHoursList = new ArrayList<String>();
		// パタン7.5
		oneDayKinmuHoursList.add("7.5");
		// パタン6
		oneDayKinmuHoursList.add("6.0");
		setupForm.setOneDayMayKinmuHoursList(oneDayKinmuHoursList);
		
		// 標準ｼﾌﾄ
		setupForm.setStandardShift(StringUtils.substring(userInfo.getStandardShiftCd(), 0, 4));
		
		// 勤務開始時刻
		setupForm.setWorkStartTime(CostDateUtils.formatTime(userInfo.getKinmuStartTime()));
		
		// 勤務終了時刻
		setupForm.setWorkEndTime(CostDateUtils.formatTime(userInfo.getKinmuEndTime()));
		
		// 入社日
		setupForm.setJoinDate(CostDateUtils.formatDate(userInfo.getNyushaDate(), CommonConstant.YYYY_MM_DD));
		
		// 休業開始日
		setupForm.setHolidayStart(CostDateUtils.formatDate(userInfo.getKyugyoStartDate(), CommonConstant.YYYY_MM_DD));
		
		// 休業終了日
		setupForm.setHolidayEnd(CostDateUtils.formatDate(userInfo.getKyugyoEndDate(), CommonConstant.YYYY_MM_DD));
		
		// 退職日
		setupForm.setOutDate(CostDateUtils.formatDate(userInfo.getTaisyokuDate(), CommonConstant.YYYY_MM_DD));
		
	}

	/**
	 * 個人設定変更情報を取得
	 * @param SetupForm
	 *           個人設定情報
	 */
	@Override
	public void getSetupEditInfo(SetupForm form) throws ParseException {
		
		this.getSetupInfo(form);
		// 代理入力者リスト
		String userId = form.getUserId();
		BaseCondition condition = new BaseCondition();
		condition.addConditionNotEqual("id", userId);
		
		List<Users> agentList = baseDao.findResultList(condition, Users.class);
		form.setAgentList(agentList);
    	
		// 標準ｼﾌﾄリスト
		form.setStandardShiftList(this.getShiftList(form.getOneDayKinmuHours()));
    	
	}

	/**
	 * 標準ｼﾌﾄ変更処理
	 *
	 * @param setupInfo
	 *            個人設定情報
	 */  
	@Override
	public void changeShift(SetupForm form) {
		
		// シフトコードリストを作成
		List<ShiftJikoku> result = this.getShiftList(form.getOneDayKinmuHours());
		
		for (ShiftJikoku shift : result) {
			if (StringUtils.equals(form.getStandardShift(), shift.getShiftCode())) {
				// 定時出勤時刻
				form.setWorkStartTime(CostDateUtils.formatTime(shift.getTeijiKinmuTime()));
				// 定時退勤時刻
				form.setWorkEndTime(CostDateUtils.formatTime(shift.getTeijiTaikinTime()));
			}
		}
		
		form.setStandardShiftList(result);
		
	}

	/**
	 * 入力した個人設定情報をチェックする
	 *
	 * @param form
	 *            個人設定情報
	 * @throws Exception 
	 *            
	 */  
	@Override
	public void doSaveCheck(SetupForm form) throws BusinessException {
		
		form.clearMessages();
		SetupChecker checker = new SetupChecker(form, baseDao);
		// 保存時の入力チェック
		checker.chkSaveDate();
	}

	/**
	 * ｼﾌﾄリストを取得
	 * 
	 * @return
	 *        ｼﾌﾄリスト
	 */
	private List<ShiftJikoku> getShiftList(String hour) {

		// 一日標準出勤時間数
		String strHour = hour.replace(".", StringUtils.EMPTY);
		
		List<ShiftJikoku> shiftList = baseDao.findAll(ShiftJikoku.class);
		List<ShiftJikoku> result = new ArrayList<ShiftJikoku>();
		for (ShiftJikoku shift : shiftList) {
			// シフトコード
			String shiftCode = shift.getShiftCode();
			// シフトコードを設定する
			if (StringUtils.endsWith(shiftCode, strHour)) {
				shift.setShiftCode(shiftCode.substring(0, 4));
				result.add(shift);
			}
			
		}
		
		return result;
	}
	
	/**
	 * 保存処理を実行
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 */
	@Override
	public void doSave(SetupForm setupForm) throws BusinessException {
		
		Users user = baseDao.findById(setupForm.getUserId(), Users.class);
		// 代理者ID
		user.setDairishaId(setupForm.getAgentCd());
		// 標準シフト
		user.setStandardShiftCd(setupForm.getStandardShift().concat(setupForm.getOneDayKinmuHours().toString().replace(".", "")));
		
		// 勤務開始時間
		user.setKinmuStartTime(setupForm.getWorkStartTime().replaceAll(":", ""));
		// 勤務終了時間
		user.setKinmuEndTime(setupForm.getWorkEndTime().replaceAll(":", ""));
		// 入社日
		user.setNyushaDate(setupForm.getJoinDate().replaceAll("/", ""));
		// 休業開始日
		user.setKyugyoStartDate(setupForm.getHolidayStart().replaceAll("/", ""));
		// 休業終了日
		user.setKyugyoEndDate(setupForm.getHolidayEnd().replaceAll("/", ""));
		// 退職日
		user.setTaisyokuDate(setupForm.getOutDate().replaceAll("/", ""));
		user.setUpdatedUserId(setupForm.getUserId());               // 更新者
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));  // 更新時刻
		user.setVersion(version);
		
		try {
			// ユーザー情報を更新する
			baseDao.update(user);
			
		} catch (OptimisticLockException e) {
			// 排他エラー
			setupForm.putConfirmMsg("排他エラー！！！");
			throw new BusinessException();
		} catch (Exception ex) {
			setupForm.putConfirmMsg("ユーザー情報の更新は失敗しました");
			throw new BusinessException();
		}
	}
}
