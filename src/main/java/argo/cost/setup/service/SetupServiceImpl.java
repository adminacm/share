package argo.cost.setup.service;

import java.util.List;

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
		form.setWorkStartTime(CostDateUtils.formatTime(setupEntity.getKinmuStartTime()));
		
		// 勤務終了時刻
		form.setWorkEndTime(CostDateUtils.formatTime(setupEntity.getKinmuEndTime()));
		
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
    	
	}

	/**
	 * 標準ｼﾌﾄ変更処理
	 *
	 * @param setupInfo
	 *            個人設定情報
	 */  
	@Override
	public void changeShift(SetupForm setupForm) {
		
		// 標準ｼﾌﾄより、ｼﾌﾄ時刻情報を取得
		BaseCondition shiftStandardJikokuSelectbaseCondition = new BaseCondition();
		// 検索条件：定時出勤時刻
		shiftStandardJikokuSelectbaseCondition.addConditionEqual("teijiKinmuTime", "0900");
		// 検索条件：定時退勤時刻
		shiftStandardJikokuSelectbaseCondition.addConditionEqual("teijiTaikinTime", "0530");
		ShiftJikoku shiftTime = (ShiftJikoku) baseDao.findResultList(shiftStandardJikokuSelectbaseCondition,ShiftJikoku.class);
		
    	// 勤務開始時刻
    	if (shiftTime != null && !shiftTime.getTeijiKinmuTime().isEmpty()){

        	// 勤務開始時刻
    		setupForm.setWorkStartTime(CostDateUtils.formatTime(shiftTime.getTeijiTaikinTime()));
    		
    	}
    	// 勤務終了時刻
    	if (shiftTime != null && !shiftTime.getTeijiTaikinTime().isEmpty()) {
    		
        	// 勤務終了時刻
    		setupForm.setWorkEndTime(CostDateUtils.formatTime(shiftTime.getTeijiTaikinTime()));
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
		
		// 休業開始日、休業終了日、退職日は日付の値であること
		if (!CostDateUtils.isValidDate(setupInfo.getHolidayStart(), CommonConstant.YYYY_MM_DD) && !CostDateUtils.isValidDate(setupInfo.getHolidayEnd(), CommonConstant.YYYY_MM_DD)
				&& !CostDateUtils.isValidDate(setupInfo.getOutDate(), CommonConstant.YYYY_MM_DD)) {
			
			// エラー
			return false;
		}
		
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
	public void doSave(SetupForm setupForm) {
		
		Users user = baseDao.findById(setupForm.getUserId(), Users.class);
		// 代理者ID
		user.setDairishaId(setupForm.getAgentCd());
		// 標準シフト
		user.setStandardShiftCd(setupForm.getStandardShift());
		// 勤務開始時間
		user.setKinmuStartTime(setupForm.getWorkStartTime().replaceAll(":", ""));
		// 勤務終了時間
		user.setKinmuEndTime(setupForm.getWorkEndTime().replaceAll(":", ""));
		// 休業開始日
		user.setKyugyoStartDate(setupForm.getHolidayStart().replaceAll("/", ""));
		// 休業終了日
		user.setKyugyoEndDate(setupForm.getHolidayEnd().replaceAll("/", ""));
		// 退職日
		user.setTaisyokuDate(setupForm.getOutDate().replaceAll("/", ""));
		baseDao.update(user);

		
	}
}
