package argo.cost.setup.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
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
		Users setupInfo = baseDao.findById(setupForm.getUserId(), Users.class);
		
		// 画面の個人設定情報を設定
		
		// 代理入力者コード
		String strDairishaId = setupInfo.getDairishaId();
		// 代理入力者が存在する場合
		if (StringUtils.isNotEmpty(strDairishaId)) {
			setupForm.setAgentCd(strDairishaId);

			// 代理入力者名
			Users agentInfo = baseDao.findById(strDairishaId,Users.class);
			setupForm.setAgentName(agentInfo.getUserName());
		}

		// 1日の勤務時間数
		int standardShiftCdSize = setupInfo.getStandardShiftCd().length();
		// シフトコードの最後2位を設定される
		setupForm.setOneDayKinmuHours((Double.valueOf(setupInfo.getStandardShiftCd().substring(standardShiftCdSize-2, standardShiftCdSize))/10));
		
		// 1日の勤務時間数リストを設定する
		List<Double> oneDayKinmuHoursList = new ArrayList<Double>();
		// パタン7.5
		oneDayKinmuHoursList.add(7.5);
		// パタン6
		oneDayKinmuHoursList.add(6.0);
		setupForm.setOneDayMayKinmuHoursList(oneDayKinmuHoursList);
		
		// 標準ｼﾌﾄ
		setupForm.setStandardShift(setupInfo.getStandardShiftCd());
		
		// 勤務開始時刻
		setupForm.setWorkStartTime(CostDateUtils.formatTime(setupInfo.getKinmuStartTime()));
		
		// 勤務終了時刻
		setupForm.setWorkEndTime(CostDateUtils.formatTime(setupInfo.getKinmuEndTime()));
		
		// 入社日
		setupForm.setJoinDate(CostDateUtils.formatDate(setupInfo.getNyushaDate(), CommonConstant.YYYY_MM_DD));
		
		// 休業開始日
		setupForm.setHolidayStart(CostDateUtils.formatDate(setupInfo.getKyugyoStartDate(), CommonConstant.YYYY_MM_DD));
		
		// 休業終了日
		setupForm.setHolidayEnd(CostDateUtils.formatDate(setupInfo.getKyugyoEndDate(), CommonConstant.YYYY_MM_DD));
		
		// 退職日
		setupForm.setOutDate(CostDateUtils.formatDate(setupInfo.getTaisyokuDate(), CommonConstant.YYYY_MM_DD));
		
	}

	/**
	 * 個人設定変更情報を取得
	 * @param SetupForm
	 *           個人設定情報
	 */
	@Override
	public void getSetupEditInfo(SetupForm setupForm) throws ParseException {
		
		this.getSetupInfo(setupForm);
		// 代理入力者リスト
		String userId = setupForm.getUserId();
		BaseCondition personalInfoSelectCondition = new BaseCondition();
		personalInfoSelectCondition.addConditionNotEqual("id", userId);
		
		List<Users> agentList = baseDao.findResultList(personalInfoSelectCondition, Users.class);
		setupForm.setAgentList(agentList);
    	
		// 標準ｼﾌﾄリスト
		setupForm.setStandardShiftList(getShiftList());
    	
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
		String strSelectedStandardShiftCode = setupForm.getStandardShift();
		// 検索条件：選択されたシフト時刻コード
		shiftStandardJikokuSelectbaseCondition.addConditionEqual("shiftCode", strSelectedStandardShiftCode);
		ShiftJikoku shiftTime = baseDao.findSingleResult(shiftStandardJikokuSelectbaseCondition,ShiftJikoku.class);
		
    	// 勤務開始時刻
    	if (shiftTime != null && !shiftTime.getTeijiKinmuTime().isEmpty()){

        	// 勤務開始時刻
    		setupForm.setWorkStartTime(CostDateUtils.formatTime(shiftTime.getTeijiKinmuTime()));
    		
    	}
    	// 勤務終了時刻
    	if (shiftTime != null && !shiftTime.getTeijiTaikinTime().isEmpty()) {
    		
        	// 勤務終了時刻
    		setupForm.setWorkEndTime(CostDateUtils.formatTime(shiftTime.getTeijiTaikinTime()));
    	}
    	// 1日勤務時間数
    	String oneDayKinmuHours = strSelectedStandardShiftCode.substring(strSelectedStandardShiftCode.length()-2,strSelectedStandardShiftCode.length());
    	setupForm.setOneDayKinmuHours((Double.valueOf(oneDayKinmuHours)/10));
		
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
	public void doSaveCheck(SetupForm form) throws Exception {
		
		form.clearMessages();
		// 入社日のチェック
		SetupChecker.chkNyushyaDate(form);
		// 休業開始日のチェック
		SetupChecker.chkKyukaKaishiDate(form);
		// 休業終了日のチェック
		SetupChecker.chkKyukaShyuryoDate(form);
		// 退職日のチェック
		SetupChecker.chkTaishokuDate(form);
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
		user.setStandardShiftCd(setupForm.getStandardShift().concat(setupForm.getOneDayKinmuHours().toString().replaceAll(".", "")));
		
		// 勤務開始時間
		user.setKinmuStartTime(setupForm.getWorkStartTime().replaceAll(":", ""));
		// 勤務終了時間
		user.setKinmuEndTime(setupForm.getWorkEndTime().replaceAll(":", ""));
		// 入社日
		user.setNyushaDate(setupForm.getJoinDate().replaceAll(":", ""));
		// 休業開始日
		user.setKyugyoStartDate(setupForm.getHolidayStart().replaceAll("/", ""));
		// 休業終了日
		user.setKyugyoEndDate(setupForm.getHolidayEnd().replaceAll("/", ""));
		// 退職日
		user.setTaisyokuDate(setupForm.getOutDate().replaceAll("/", ""));
		baseDao.update(user);
		
	}
}
