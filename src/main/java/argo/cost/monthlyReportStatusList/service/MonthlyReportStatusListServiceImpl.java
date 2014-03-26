package argo.cost.monthlyReportStatusList.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.model.ListItem;
import argo.cost.common.model.entity.ApprovalList;
import argo.cost.monthlyReportStatusList.dao.MonthlyReportStatusListDao;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListInfo;

/**
 * {@inheritDoc}
 */
@Service
public class MonthlyReportStatusListServiceImpl implements MonthlyReportStatusListService {

	/**
	 * 月報状況一覧DAO
	 */
	@Autowired
	private MonthlyReportStatusListDao mRSDao;

	/**
	 * 月報状況一覧リストを取得
	 * 
	 * @param form
	 *           月報状況一覧情報
	 * @return 月報状況一覧リスト
	 */
	@Override
	public List<MonthlyReportStatusListInfo> getMonthlyReportStatusList(MonthlyReportStatusListForm form) {
		
		// 月報状況一覧リスト
		List<MonthlyReportStatusListInfo> mRSList = new ArrayList<MonthlyReportStatusListInfo>();
		
		// ＤＢから、月報状況一覧リストを取得
		List<ApprovalList> mRSEList = mRSDao.getMonthlyReportStatusList(form);
		
		// TODO
		if (mRSEList != null && mRSEList.size() > 0) {
			for (int i = 0; i < mRSEList.size(); i++) {
				ApprovalList mRSInfo = mRSEList.get(i);
				MonthlyReportStatusListInfo appInfo = new MonthlyReportStatusListInfo();
				// ID
				appInfo.setId(mRSInfo.getId());
				// 申請区分
				appInfo.setApplyKbn(mRSInfo.getApplyKbn());
				// 申請内容
				appInfo.setApplyDetail(mRSInfo.getApplyDetail());
				// 状況
				String statusName = mRSDao.getStatusName(mRSInfo.getStatus());
				appInfo.setStatus(statusName);
				// 所属
				appInfo.setAffiliation(mRSInfo.getAffiliation());
				// 氏名
				appInfo.setName(mRSInfo.getName());
				
				mRSList.add(appInfo);
			}
		}
		
		// 月報状況一覧リスト
		return mRSList;
	}

	/**
	 * 年月プルダウンリスト取得
	 * 
	 * @return
	 * 	年月プルダウンリスト
	 */
	@Override
	public List<ListItem> getYearMonthList(Date date) {

		// ドロップダウンリスト
		List<ListItem> resultList = new ArrayList<ListItem>();
		// ドロップダウン項目
		ListItem item = null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// ドロップダウンリスト設定
		for (int i = 0; i <= 3; i++) {
			
			item = new ListItem();

			// 年月を取得
			cal.add(Calendar.YEAR, -i); 
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);

			// データを設定する
			// 区分値 
			item.setValue(String.valueOf(year) + String.valueOf(month));
			// 区分名称
			item.setName(year + "年" + month + "月");

			// リストに追加
			resultList.add(item);
		}
		
		// 年月ドロップダウンリストを返却する。
		return resultList;
	
	}

	/**
	 * 所属プルダウンリスト取得
	 * 
	 * @return 
	 *        所属プルダウンリスト
	 */
	@Override
	public List<ListItem> getAffiliationList() {
		
		//TODO ドロップダウンリスト
		List<ListItem> resultList = new ArrayList<ListItem>();
		// ドロップダウン項目
		ListItem item = new ListItem();
		item.setValue("01");
		item.setName("ＢＳ１");
		resultList.add(item);
		
		item = new ListItem();
		item.setValue("02");
		item.setName("ＢＳ２");
		resultList.add(item);
		
		// ＤＢから、月報状況一覧リストを取得
		// List<> affiliationList = mRSDao.getAffiliationList();
		
		return resultList;
	}
}
