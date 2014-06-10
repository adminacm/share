package argo.cost.monthlyReportApproval.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.ComDao;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.ProjWorkTimeManage;
import argo.cost.monthlyReportApproval.dao.MonthlyReportApprovalDao;
import argo.cost.monthlyReportApproval.model.MonthlyReportApprovalVo;

@Service
public class MonthlyReportApprovalServiceImpl implements MonthlyReportApprovalService {

	/**
	 * 月報承認DAO
	 */
	@Autowired
	private MonthlyReportApprovalDao monApprovalDao;
	
	/**
	 * 共通DAO
	 */	
	@Autowired
	private ComDao comDao;

	/**
	 * 処理状況名を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        処理状況表示名
	 */
	@Override
	public String getStatus(String applyNo) {

		// 申請番号による、処理状況値を取得
		String statusValue = monApprovalDao.getStatus(applyNo);
		
		// 処理状況名
		String statusName = comDao.findStatusName(statusValue);
		
		return statusName;
	}

	/**
	 * 月報承認データを取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        月報承認データ一覧リスト
	 */
	@Override
	public List<MonthlyReportApprovalVo> getMonReApprovalList(String applyNo) {

		
		List<MonthlyReportApprovalVo> monthlyReportApprovalList = new ArrayList<MonthlyReportApprovalVo>();
		// 月報承認データを取得
		List<KintaiInfo> kintaiInfoList = monApprovalDao.searchMonthReportApprovalList(applyNo);
		
		for(int i = 0; i<kintaiInfoList.size(); i++) {
			
			for (int j = 0;j<monthlyReportApprovalList.size();j++) {
				// 区分
				monthlyReportApprovalList.get(i).setWorkKbn(kintaiInfoList.get(j).getKyukaKekinKbnMaster().getCode());
				// ｼﾌﾄ
				monthlyReportApprovalList.get(i).setShift(kintaiInfoList.get(j).getShiftJikoku().getShiftCode());
				// 出勤
				monthlyReportApprovalList.get(i).setWorkSTime(kintaiInfoList.get(j).getKinmuStartTime());
				// 退勤
				monthlyReportApprovalList.get(i).setWorkETime(kintaiInfoList.get(j).getKinmuEndTime());
				// 休暇
				monthlyReportApprovalList.get(i).setRestHours(kintaiInfoList.get(j).getKyukaJikansu().doubleValue());
				// 勤務時間数
				monthlyReportApprovalList.get(i).setWorkHours(kintaiInfoList.get(j).getKinmuJikansu().doubleValue());
				// 超勤開始
				monthlyReportApprovalList.get(i).setChoSTime(kintaiInfoList.get(j).getChokinStartTime());
				// 超勤終了
				monthlyReportApprovalList.get(i).setChoETime(kintaiInfoList.get(j).getChokinEndTime());
				// 超勤平増
				monthlyReportApprovalList.get(i).setChoWeekday(kintaiInfoList.get(j).getChokinHeijituJikansu().doubleValue());
				// 超勤平常
				monthlyReportApprovalList.get(i).setChoWeekdayNomal(kintaiInfoList.get(j).getChokinHeijituTujyoJikansu().doubleValue());
				// 超勤休日
				monthlyReportApprovalList.get(i).setChoHoliday(kintaiInfoList.get(j).getChokinKyujituJikansu().doubleValue());
				// 超勤深夜
				monthlyReportApprovalList.get(i).setmNHours(kintaiInfoList.get(j).getSinyaKinmuJikansu().doubleValue());
				// ﾛｹｰｼｮﾝコード
				monthlyReportApprovalList.get(i).setLocationCode(kintaiInfoList.get(j).getLocation().getCode());
				// ﾛｹｰｼｮﾝ名前
				monthlyReportApprovalList.get(i).setLocationName(kintaiInfoList.get(j).getLocation().getName());
			}
			
		}
		
		// 合計休暇時間数
		Double totleRestHours = 0.0;
		
		// 合計勤務時間数
		Double totleWorkHours = 0.0;

		// 合計超勤平増
		Double totleChoWeekday = 0.0;

		// 合計超勤平常
		Double totleChoWeekdayNomal = 0.0;

		// 合計超勤休日
		Double totleChoHoliday = 0.0;

		// 合計超勤深夜
		Double totleMNHours = 0.0;
		
		// 月報承認明細情報合計行作成
		for (MonthlyReportApprovalVo itemInfo : monthlyReportApprovalList) {
			
			if (itemInfo.getRestHours() != null) {

				totleRestHours += itemInfo.getRestHours();
				
			}
			if (itemInfo.getWorkHours() != null) {

				totleWorkHours += itemInfo.getWorkHours();
				
			}
			if (itemInfo.getChoWeekday() != null) {

				totleChoWeekday += itemInfo.getChoWeekday();
				
			}
			if (itemInfo.getChoWeekdayNomal() != null) {

				totleChoWeekdayNomal += itemInfo.getChoWeekdayNomal();
				
			}
			if (itemInfo.getChoHoliday() != null) {

				totleChoHoliday += itemInfo.getChoHoliday();
				
			}
			if (itemInfo.getmNHours() != null) {

				totleMNHours += itemInfo.getmNHours();
				
			}
		}
		
		MonthlyReportApprovalVo totleInfo = new MonthlyReportApprovalVo();
		// 合計フラグ
		totleInfo.setTotleFlg(true);
		totleInfo.setRestHours(totleRestHours);
		totleInfo.setWorkHours(totleWorkHours);
		totleInfo.setChoWeekday(totleChoWeekday);
		totleInfo.setChoWeekdayNomal(totleChoWeekdayNomal);
		totleInfo.setChoHoliday(totleChoHoliday);
		totleInfo.setmNHours(totleMNHours);
		
		monthlyReportApprovalList.add(totleInfo);
		
		return monthlyReportApprovalList;
	}

	/**
	 * 【PJ別作業時間集計】情報を取得
	 * 
	 * @param applyNo
	 *               申請番号
	 * @return
	 *        プロジェクト情報
	 */
	@Override
	public List<ProjWorkTimeManage> getProjectList(String applyNo) {

		// プロジェクト情報を取得
		List<ProjWorkTimeManage> projectList = monApprovalDao.searchProjectList(applyNo);
		
		return projectList;
		
	}

	/**
	 * 申請状況更新
	 * 
	 * @param applyNo
	 *               申請番号
	 * @param proStatus
	 *                 申請状況
	 * @return
	 *        更新フラグ
	 */
	@Override
	public String updateProStatus(String applyNo, String proStatus) {

		// 更新実行
		String updateFlg = monApprovalDao.updateProStatus(applyNo, proStatus);
		
		// 更新フラグ
		return updateFlg;
	}
}
