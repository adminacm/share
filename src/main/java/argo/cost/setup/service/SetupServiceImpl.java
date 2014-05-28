package argo.cost.setup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.ComDao;
import argo.cost.common.entity.Shift;
import argo.cost.common.entity.ShiftTime;
import argo.cost.common.model.UserVO;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.setup.dao.SetupDao;
import argo.cost.setup.model.SetupEntity;
import argo.cost.setup.model.SetupForm;

/**
 * 個人設定画面サービスインタフェースの実現
 *
 * @author COST argo Corporation.
 */
@Service
public class SetupServiceImpl implements SetupService {

	/**
	 * 個人設定画面のDAO
	 */
	@Autowired
	SetupDao setupDao;

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
	public SetupForm getSetupInfo(String userId) {
		
		// 画面の個人設定情報
		SetupForm setupInfo = new SetupForm();

		// DBから、個人設定情報を取得
		SetupEntity setupEntity = setupDao.getSetupInfo(userId);
		
		// 画面の個人設定情報を設定
		
		// 代理入力者コード
		setupInfo.setAgentCd(setupEntity.getAgent());
		
		// 代理入力者名
		UserVO agentInfo = comDao.findUserById(setupEntity.getAgent());
		setupInfo.setAgentName(agentInfo.getUserName());
		
		// 標準ｼﾌﾄ
		setupInfo.setStandardShift(setupEntity.getStandardShift());
		
		// 勤務開始時刻
		setupInfo.setWorkStart(CostDateUtils.formatIntegerToTime(setupEntity.getWorkStart()));
		
		// 勤務終了時刻
		setupInfo.setWorkEnd(CostDateUtils.formatIntegerToTime(setupEntity.getWorkEnd()));
		
		// 入社日
		setupInfo.setJoinDate(setupEntity.getJoinDate());
		
		// 休業開始日
		setupInfo.setHolidayStart(setupEntity.getHolidayStart());
		
		// 休業終了日
		setupInfo.setHolidayEnd(setupEntity.getHolidayEnd());
		
		// 退職日
		setupInfo.setOutDate(setupEntity.getOutDate());
		
		// 個人設定情報を戻る
		return setupInfo;
	}

	/**
	 * 個人設定変更情報を取得
	 * @param SetupForm
	 *           個人設定情報
	 */
	@Override
	public void getSetupEditInfo(SetupForm setupInfo) {
				
		// 代理入力者リスト
		setupInfo.setAgentList(getAgentList());
    	
		// 標準ｼﾌﾄリスト
		setupInfo.setStandardShiftList(getShiftList());
    	
    	// 勤務開始時刻
    	if (!setupInfo.getWorkStart().isEmpty()){

    		String[] workStart = setupInfo.getWorkStart().split(":");
        	// 勤務開始時刻（時）
    		setupInfo.setWorkStartH(workStart[0]);
        	// 勤務開始時刻（分）
    		setupInfo.setWorkStartM(workStart[1]);
    		
    	}
    	// 勤務終了時刻
    	if (!setupInfo.getWorkEnd().isEmpty()){
    		
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
		ShiftTime shiftTime = setupDao.getshiftTime(setupInfo.getStandardShift());
		
    	// 勤務開始時刻
    	if (shiftTime != null && !shiftTime.getStandSTime().isEmpty()){

        	// 勤務開始時刻（時）
    		setupInfo.setWorkStartH(shiftTime.getStandSTime().substring(0, 2));
        	// 勤務開始時刻（分）
    		setupInfo.setWorkStartM(shiftTime.getStandSTime().substring(2, 4));
    		
    	}
    	// 勤務終了時刻
    	if (shiftTime != null && !shiftTime.getStandEtime().isEmpty()){
    		
        	// 勤務終了時刻（時）
    		setupInfo.setWorkEndH(shiftTime.getStandEtime().substring(0, 2));
        	// 勤務終了時刻（分）
    		setupInfo.setWorkEndM(shiftTime.getStandEtime().substring(2, 4));
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
		if (strWholeHours.equals(setupInfo.getWorkStartM()) || strHalfHours.equals(setupInfo.getWorkStartM())) {
			
			// エラー(勤務開始時刻は30分単位で入力してください)
			return false;
			
		}
		
		// 勤務終了時刻は30分単位で入力
		if (strWholeHours.equals(setupInfo.getWorkEndM()) || strHalfHours.equals(setupInfo.getWorkEndM())) {
			
			// エラー(勤務終了時刻は30分単位で入力してください)
			return false;
		}
		
		// 休業開始日、休業終了日、退職日は日付の値であること
		if (!CostDateUtils.isValidDate(setupInfo.getHolidayStart()) && !CostDateUtils.isValidDate(setupInfo.getHolidayEnd()) 
				&& !CostDateUtils.isValidDate(setupInfo.getOutDate())) {
			
			// エラー
			return false;
		}
		
		return true;
	}

	/**
	 * 代理入力者リストを取得
	 * 
	 * @return
	 *        代理入力者リスト
	 */
	private List<UserVO> getAgentList() {

		// 代理入力者リストを取得
		List<UserVO> agentList = setupDao.getAgentList();
		
		return agentList;
	}

	/**
	 * ｼﾌﾄリストを取得
	 * 
	 * @return
	 *        ｼﾌﾄリスト
	 */
	private List<Shift> getShiftList() {

		// ｼﾌﾄリストを取得
		List<Shift> shiftList = setupDao.getShiftList();
		
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

		setupDao.doSave(setupInfo);
		
	}
}
