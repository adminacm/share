package argo.cost.monthlyReportStatusList.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.model.ListItemVO;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListVo;

/**
 * {@inheritDoc}
 */
@Service
public class MonthlyReportStatusListServiceImpl implements MonthlyReportStatusListService {
	
	/**
	 * 単一テーブル操作DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	/**
	 * 年プルダウンリストを取得
	 * 
	 * @param date
	 * 	      　　　　　　 日付
	 * @return
	 * 	             年プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getYearList(Date date) {

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// ドロップダウンリスト設定
		for (int i = 0; i < 3; i++) {
			
			item = new ListItemVO();

			// 年を取得
			if (i != 0){
				
				cal.add(Calendar.YEAR, -1); 
			}
			int year = cal.get(Calendar.YEAR);

			// データを設定する
			// 区分値 
			item.setValue(String.valueOf(year));
			// 区分名称
			item.setName(year + "年");

			// リストに追加
			resultList.add(item);
		}
		
		// 年ドロップダウンリストを返却する。
		return resultList;
	
	}

	/**
	 * 月プルダウンリストを取得
	 * 
	 * @return
	 * 	             月プルダウンリスト
	 */
	@Override
	public List<ListItemVO> getMonthList() {

		// ドロップダウンリスト
		List<ListItemVO> resultList = new ArrayList<ListItemVO>();
		// ドロップダウン項目
		ListItemVO item = null;

		// ドロップダウンリスト設定
		for (int i = 1; i <= 12; i++) {
			
			item = new ListItemVO();

			// データを設定する
			if (i < 10) {
				// 区分値 
				item.setValue("0" + i);
				// 区分名称
				item.setName("0" + i + "月");
			} else {
				
				// 区分値 
				item.setValue(String.valueOf(i));
				// 区分名称
				item.setName(String.valueOf(i) + "月");
			}

			// リストに追加
			resultList.add(item);
		}
		
		// 月ドロップダウンリストを返却する。
		return resultList;
	
	}

	/**
	 * 月報状況一覧リストを取得
	 * 
	 * @param form
	 *            月報状況一覧情報
	 * @return 
	 *        月報状況一覧リスト
	 */
	@Override
	public List<MonthlyReportStatusListVo> getMonthlyReportStatusList(MonthlyReportStatusListForm form) {

		// 月報状況一覧リスト
		List<MonthlyReportStatusListVo> monthlyReportStatusList = new ArrayList<MonthlyReportStatusListVo>();
		MonthlyReportStatusListVo monthlyReportStatusInfo = null;
		
		// 承認管理データを取得
		List<ApprovalManage> approvalList = getApprovalList(form);
		
		// 承認管理データあり
		if (approvalList.size() > 0) {
			
			for (ApprovalManage approvalInfo : approvalList) {
				
				monthlyReportStatusInfo = new MonthlyReportStatusListVo();
				
				//　申請番号
				monthlyReportStatusInfo.setApplyNo(approvalInfo.getApplyNo());
				// 所属
				monthlyReportStatusInfo.setAffiliationName(approvalInfo.getUser().getAffiliationMaster().getName());
				// ユーザID	
				monthlyReportStatusInfo.setUserId(approvalInfo.getUser().getId());
				// 申請者氏名	
				monthlyReportStatusInfo.setAppliedUserName(approvalInfo.getUser().getUserName());
				// 申請区分
				monthlyReportStatusInfo.setApplyKbnCd(approvalInfo.getApplyKbnMaster().getCode());
				monthlyReportStatusInfo.setApplyKbnName(approvalInfo.getApplyKbnMaster().getName());
				// 申請内容
				monthlyReportStatusInfo.setApplyDetail(approvalInfo.getApplyDetail());
				// 状況
				monthlyReportStatusInfo.setStatusName(approvalInfo.getStatusMaster().getName());
				// 申請者名前
				monthlyReportStatusInfo.setAppliedUserName(approvalInfo.getApplicantUser().getUserName());
				// 承認日付
				if (!StringUtils.isEmpty(approvalInfo.getApprovedYmd())) {
					monthlyReportStatusInfo.setApprovedYmd(approvalInfo.getApprovedYmd());
				}
				// 承認者名前
				if (approvalInfo.getApproveUser() != null) {
					monthlyReportStatusInfo.setApproverName(approvalInfo.getApproveUser().getUserName());
				}
				
				monthlyReportStatusList.add(monthlyReportStatusInfo);
			}
		}

		// 月報状況一覧リストを戻り
		return monthlyReportStatusList;
	}

	/**
	 * 承認管理データを取得
	 * 
	 * @param form
	 *            月報状況一覧画面情報
	 * @return　承認管理データ
	 */
	private List<ApprovalManage> getApprovalList(
			MonthlyReportStatusListForm form) {
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// 処理年月
		condition.addConditionEqual("syoriYm", form.getYear() + form.getMonth());
		// 所属がnull以外の場合
		if (StringUtils.isNotEmpty(form.getAffiliation())) { 
			condition.addConditionEqual("users.affiliationMaster.code", form.getAffiliation());
		}
		
		// 月報状況一覧リスト
		List<ApprovalManage> approvalList = baseDao.findResultList(condition, ApprovalManage.class);
		
		return approvalList;
	}
}
