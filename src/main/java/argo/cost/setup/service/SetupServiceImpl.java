package argo.cost.setup.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.dao.ComDao;
import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.setup.model.SetupForm;

/**
 * 個人設定画面サービスインタフェースの実現
 *
 * @author COST argo Corporation.
 */
@Service
public class SetupServiceImpl implements SetupService {
	
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;

	/**
	 * 共通処理のDAO
	 */
	@Autowired
	ComDao comDao;
	
	/**
	 * 個人設定情報を取得
	 * @param UserId
	 *           ユーザＩＤ
	 * @return
	 *        SetupForm 個人設定情報
	 */
	@Override
	public void getSetupInfo(SetupForm form) {
		
		// DBから、個人設定情報を取得
		Users setupEntity = baseDao.findById(form.getUserId(), Users.class);
		
		// 画面の個人設定情報を設定
		
		// 代理入力者コード
		String strDairishaId = setupEntity.getDairishaId();
		form.setAgentCd(strDairishaId);

		// 代理入力者名
		Users agentInfo = baseDao.findById(strDairishaId,Users.class);
		form.setAgentName(agentInfo.getUserName());

		// 標準ｼﾌﾄ
		form.setStandardShift(setupEntity.getStandardShiftCd());
		
		// 勤務開始時刻
		form.setWorkStart(CostDateUtils.formatTime(setupEntity.getKinmuStartTime()));
		
		// 勤務終了時刻
		form.setWorkEnd(CostDateUtils.formatTime(setupEntity.getKinmuEndTime()));
		
		// 入社日
		form.setJoinDate(setupEntity.getNyushaDate());
		
		// 休業開始日
		form.setHolidayStart(setupEntity.getKyugyoStartDate());
		
		// 休業終了日
		form.setHolidayEnd(setupEntity.getKyugyoEndDate());
		
		// 退職日
		form.setOutDate(setupEntity.getTaisyokuDate());
		
	}

	/**
	 * 個人設定変更情報を取得
	 * @param SetupForm
	 *           個人設定情報
	 */
	@Override
	public void getSetupEditInfo(SetupForm setupInfo) {
				
		// 代理入力者リスト
		String userId = setupInfo.getUserId();
		BaseCondition personalInfoSelectCondition = new BaseCondition();
		personalInfoSelectCondition.addConditionNotEqual("id", userId);
		
		List<Users> agentList = baseDao.findResultList(personalInfoSelectCondition, Users.class);
		setupInfo.setAgentList(agentList);
    	
		// 標準ｼﾌﾄリスト
		setupInfo.setStandardShiftList(getShiftList());
    	
    	// 勤務開始時刻
    	if (StringUtils.isNotEmpty(setupInfo.getWorkStart())){

    		String[] workStart = setupInfo.getWorkStart().split(":");
        	// 勤務開始時刻（時）
    		setupInfo.setWorkStartH(workStart[0]);
        	// 勤務開始時刻（分）
    		setupInfo.setWorkStartM(workStart[1]);
    		
    	}
    	// 勤務終了時刻
    	if (StringUtils.isNotEmpty(setupInfo.getWorkEnd())){
    		
    		String[] workEnd = setupInfo.getWorkEnd().split(":");
        	// 勤務終了時刻（時）
    		setupInfo.setWorkEndH(workEnd[0]);
        	// 勤務終了時刻（分）
    		setupInfo.setWorkEndM(workEnd[1]);
    		
    	}
	}

	/**
	 * 標準ｼﾌﾄ変更処理
	 *
	 * @param setupInfo
	 *            個人設定情報
	 */  
	@Override
	public void changeShift(SetupForm setupInfo) {
		
		// 標準ｼﾌﾄより、ｼﾌﾄ時刻情報を取得
		BaseCondition shiftStandardJikokuSelectbaseCondition = new BaseCondition();
		// 検索条件：定時出勤時刻
		shiftStandardJikokuSelectbaseCondition.addConditionEqual("teijiKinmuTime", "0900");
		// 検索条件：定時退勤時刻
		shiftStandardJikokuSelectbaseCondition.addConditionEqual("teijiTaikinTime", "0530");
		ShiftJikoku shiftTime = (ShiftJikoku) baseDao.findResultList(shiftStandardJikokuSelectbaseCondition,ShiftJikoku.class);
		
    	// 勤務開始時刻
    	if (shiftTime != null && !shiftTime.getTeijiKinmuTime().isEmpty()){

        	// 勤務開始時刻（時）
    		setupInfo.setWorkStartH(shiftTime.getTeijiTaikinTime().substring(0, 2));
        	// 勤務開始時刻（分）
    		setupInfo.setWorkStartM(shiftTime.getTeijiTaikinTime().substring(2, 4));
    		
    	}
    	// 勤務終了時刻
    	if (shiftTime != null && !shiftTime.getTeijiTaikinTime().isEmpty()) {
    		
        	// 勤務終了時刻（時）
    		setupInfo.setWorkEndH(shiftTime.getTeijiTaikinTime().substring(0, 2));
        	// 勤務終了時刻（分）
    		setupInfo.setWorkEndM(shiftTime.getTeijiTaikinTime().substring(2, 4));
    	}
		
	}

	/**
	 * 入力した個人設定情報をチェックする
	 *
	 * @param setupInfo
	 *            個人設定情報
	 * @return
	 *        Booleanチェック結果(true:エラーがない； false:エラーがある)
	 *            
	 */  
	@Override
	public Boolean doSaveCheck(SetupForm setupInfo) {
		
		String strWholeHours = "00";
		
		String strHalfHours = "30";

		// 勤務開始時刻は30分単位で入力
		if (!StringUtils.equals(strWholeHours, setupInfo.getWorkStartM()) && !StringUtils.equals(strHalfHours, setupInfo.getWorkStartM())) {
			setupInfo.putConfirmMsg("勤務開始時刻は30分単位で入力してください");
			// エラー(勤務開始時刻は30分単位で入力してください)
			return false;
			
		}
		
		// 勤務終了時刻は30分単位で入力
		if (!StringUtils.equals(strWholeHours, setupInfo.getWorkEndM()) && !StringUtils.equals(strHalfHours, setupInfo.getWorkEndM())) {
			
			// エラー(勤務終了時刻は30分単位で入力してください)
			return false;
		}
		
		// 休業開始日、休業終了日、退職日は日付の値であること
		if (!CostDateUtils.isValidDate(setupInfo.getHolidayStart(), CommonConstant.YYYY_MM_DD) && !CostDateUtils.isValidDate(setupInfo.getHolidayEnd(), CommonConstant.YYYY_MM_DD)
				&& !CostDateUtils.isValidDate(setupInfo.getOutDate(), CommonConstant.YYYY_MM_DD)) {
			
			// エラー
			return false;
		}
		
		setupInfo.setWorkStart(setupInfo.getWorkStartH().concat(setupInfo.getWorkStartM()));
		setupInfo.setWorkEnd(setupInfo.getWorkEndH().concat(setupInfo.getWorkEndM()));
		return true;
	}

	/**
	 * ｼﾌﾄリストを取得
	 * 
	 * @return
	 *        ｼﾌﾄリスト
	 */
	private List<ShiftJikoku> getShiftList() {

		// ｼﾌﾄリストを取得
		List<ShiftJikoku> shiftList = baseDao.findAll(ShiftJikoku.class);
		
		return shiftList;
	}
	
	/**
	 * 保存処理を実行
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 */
	@Override
	public void doSave(SetupForm setupInfo) {
		
		Users user = baseDao.findById(setupInfo.getUserId(), Users.class);
		// 代理者ID
		user.setDairishaId(setupInfo.getAgentCd());
		// 標準シフト
		user.setStandardShiftCd(setupInfo.getStandardShift());
		// 勤務開始時間
		user.setKinmuStartTime(setupInfo.getWorkStart());
		// 勤務終了時間
		user.setKinmuEndTime(setupInfo.getWorkEnd());
		// 休業開始日
		user.setKyugyoStartDate(setupInfo.getHolidayStart().replaceAll("/", ""));
		// 休業終了日
		user.setKyugyoEndDate(setupInfo.getHolidayEnd().replaceAll("/", ""));
		// 退職日
		user.setTaisyokuDate(setupInfo.getOutDate().replaceAll("/", ""));
		baseDao.update(user);

		
	}
}
