package argo.cost.makeKyuyoFile.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileForm;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileIchiranVO;
import argo.cost.monthlyReportStatusList.model.PayMagistrateCsvInfo;
@Service
public class MakeKyuyoFileServiceImpl implements MakeKyuyoFileService {
	
	/**
	 * 共通DAO
	 */	
	@Autowired
	private BaseDao baseDao;

	@Override
	public void createCSVFile(MakeKyuyoFileForm makeKyuyoFileForm)
			throws Exception {
		
		// 給与奉行向けCSVファイル情報を取得
		List<PayMagistrateCsvInfo> csvDetailList = getPayMagistrateCsvList(makeKyuyoFileForm);
		String path = "D:\\";
		
		SimpleDateFormat sdfYearM = new SimpleDateFormat("yyyyMMddHHmmss");
		// 日付設定
		String filaName = sdfYearM.format(new Date());
	 	// CSV ダウンロード
    	exportCsvfiles(path, filaName, getTitleList(), csvDetailList);
    	
    	// 勤怠情報を取得
		List<KintaiInfo> kintaiList = getKintaiList(makeKyuyoFileForm.getUserId(), makeKyuyoFileForm.getDealYearMonth());

		// 超勤管理テーブルを更新
    	updateChokinKanri(kintaiList);
		
	}

	
	/**
	 * 給与奉行向けCSVファイル情報を取得
	 * 
	 * @param form 
	 *            月報状況一覧画面情報
	 *            
	 * @return 給与奉行向けCSVファイル情報
	 */
	private List<PayMagistrateCsvInfo> getPayMagistrateCsvList(
			MakeKyuyoFileForm makeKyuyoFileForm) {

		//給与奉行向けCSVファイル情報
		List<PayMagistrateCsvInfo> payMagistrateCsvList = new ArrayList<PayMagistrateCsvInfo>();
		// CSV詳細情報
		PayMagistrateCsvInfo csvInfo = null;
		
		// 承認管理データを取得
		List<ApprovalManage> approvalList = getApprovalList(makeKyuyoFileForm);
		
		if (approvalList.size() > 0) {
			
			for (ApprovalManage approvalInfo : approvalList) {

				//　超過勤務時間数（平日_割増）
				BigDecimal hokinHeijituJikansu = new BigDecimal(0);
				//　超過勤務時間数（休日）
				BigDecimal chokinKyujituJikansu = new BigDecimal(0);
				//　超過勤務時間数（深夜）
				BigDecimal sinyaKinmuJikansu = new BigDecimal(0);
				//　欠勤時間数
				BigDecimal kyukaJikansu = new BigDecimal(0);
				//　超過勤務時間数（平日_通常）
				BigDecimal chokinHeijituTujyoJikansu = new BigDecimal(0);
				
				csvInfo = new PayMagistrateCsvInfo();
				//　社員番号
				csvInfo.setUserId(approvalInfo.getUser().getId());
				
				// 勤怠情報を取得
				List<KintaiInfo> kintaiList = getKintaiList(approvalInfo.getUser().getId(), approvalInfo.getAppYmd());
				
				if (kintaiList.size() > 0) {
					
					for (KintaiInfo kintaiInfo : kintaiList) {

						//　超過勤務時間数（平日_割増）
						hokinHeijituJikansu.add(kintaiInfo.getChokinHeijituJikansu());
						//　超過勤務時間数（休日）
						chokinKyujituJikansu.add(kintaiInfo.getChokinKyujituJikansu());
						//　超過勤務時間数（深夜）
						sinyaKinmuJikansu.add(kintaiInfo.getSinyaKinmuJikansu());
						//　欠勤時間数
						kyukaJikansu.add(kintaiInfo.getKyukaJikansu());
						//　超過勤務時間数（平日_通常）
						chokinHeijituTujyoJikansu.add(kintaiInfo.getChokinHeijituTujyoJikansu());
					}
				}

				//　超過勤務時間数（平日_割増）
				csvInfo.setOverWeekdayHours(bigDecimalToString(hokinHeijituJikansu));
				//　超過勤務時間数（休日）
				csvInfo.setOverHolidayHours(bigDecimalToString(chokinKyujituJikansu));
				//　超過勤務時間数（深夜）
				csvInfo.setOverNightHours(bigDecimalToString(sinyaKinmuJikansu));
				//　超過勤務時間数（休日出勤振替分）
				csvInfo.setOverHolidayChangeWorkHours(bigDecimalToString(getFurikaeHours(approvalInfo.getUser().getId(), approvalInfo.getAppYmd())));
				//　欠勤時間数
				csvInfo.setAbsenceHours(bigDecimalToString(kyukaJikansu));
				//　超過勤務時間数（平日_通常）
				csvInfo.setOverWeekdayNomalHours(bigDecimalToString(chokinHeijituTujyoJikansu));
				
				payMagistrateCsvList.add(csvInfo);
			}
		}
		return payMagistrateCsvList;
	}
	
	
	/**
	 * 承認管理データを取得
	 * 
	 * @param form
	 *            月報状況一覧画面情報
	 * @return　承認管理データ
	 */
	private List<ApprovalManage> getApprovalList(
			MakeKyuyoFileForm makeKyuyoFileForm) {
		
		// 検索条件
		BaseCondition condition = new BaseCondition();
		// 年月
		condition.addConditionLike("appYmd", makeKyuyoFileForm.getDealYearMonth() + "%");
//		// 状況
//		condition.addConditionLike("statusMaster.code", makeKyuyoFileForm.getStatus());
//		if (!makeKyuyoFileForm.getStatus().isEmpty()) { 
//			
//		}
//		// 所属がnull以外の場合
//		if (!makeKyuyoFileForm.getAffiliation().isEmpty()) { 
//			condition.addConditionLike("users.affiliationMaster.code", makeKyuyoFileForm.getAffiliation());
//		}
		
		// 月報状況一覧リスト
		List<ApprovalManage> approvalList = baseDao.findResultList(condition, ApprovalManage.class);
		
		return approvalList;
	}
	
	/**
	 * ヘッダ部データ設定
	 * 
	 * @return ヘッダ部データ
	 */
	private List<String> getTitleList(){
        List<String> list = new ArrayList<String>();
        list.add("MK01");
        list.add("KN09");
        list.add("KN10");
        list.add("KN11");
        list.add("KN12");
        list.add("KN08");
        list.add("KN13");
        return list;
    }
	
	
	/**
	 *  ダウンロード実行
	 *  
	 * @param path
	 *            ＣＳＶファイルが保存されたパス
	 * @param fileName
	 *                ＣＳＶファイルの名前    
	 * @param titleList
	 * 			               ヘッダ部表示するタイトルリスト
	 * @param csvDetailList
	 * 	     	                          ＣＳＶファイル詳細データリスト
	 * @param response
	 *                レスポンス
	 * @throws Exception
	 *                  異常
	 */
	 private void exportCsvfiles(String path, String fileName, List<String> titleList, List<PayMagistrateCsvInfo> csvDetailList) throws Exception {
		 
		OutputStream out = null;
		PrintWriter pw = null;

		// 社員番号
		String employeeNo = "";
		// 超過勤務時間数（平日_割増）
		String overWeekdayHours = "";
		// 超過勤務時間数（休日）
		String overHolidayHours = "";
		// 超過勤務時間数（深夜）
		String overNightHours = "";
		// 超過勤務時間数（休日出勤振替分）
		String overHolidayChangeWorkHours = "";
		// 欠勤時間数
		String absenceHours = "";
		// 超過勤務時間数（平日_通常）
		String overWeekdayNomalHours = "";
		
		try {
			File file = new File(path + fileName + ".csv");
			out = new FileOutputStream(file);
			pw = new PrintWriter(out);
			for (int j = 0; j < titleList.size(); j++) {
				pw.append(titleList.get(j) + ",");
			}
			for (int i = 0; i < csvDetailList.size(); i++) {

				// 社員番号
				employeeNo = (csvDetailList.get(i).getUserId());
				// 超過勤務時間数（平日_割増）
				overWeekdayHours = (csvDetailList.get(i).getOverWeekdayHours());
				// 超過勤務時間数（休日）
				overHolidayHours = (csvDetailList.get(i).getOverHolidayHours());
				// 超過勤務時間数（深夜）
				overNightHours = (csvDetailList.get(i).getOverNightHours());
				// 超過勤務時間数（休日出勤振替分）
				overHolidayChangeWorkHours = (csvDetailList.get(i).getOverHolidayChangeWorkHours());
				// 欠勤時間数
				absenceHours = (csvDetailList.get(i).getAbsenceHours());
				// 超過勤務時間数（平日_通常）
				overWeekdayNomalHours = (csvDetailList.get(i).getOverWeekdayNomalHours());
				
				pw.append("\n");
				pw.append(employeeNo + ",");
				pw.append(overWeekdayHours + ",");
				pw.append(overWeekdayNomalHours + ",");
				pw.append(overHolidayChangeWorkHours + ",");
				pw.append(overHolidayHours + ",");
				pw.append(overNightHours + ",");
				pw.append(absenceHours + ",");
			}
		} catch (Exception e) {

			System.out.println(fileName + ".csv生成失敗");
		} finally {
			pw.flush();
			pw.close();
			out.flush();
			out.close();
		}
	}

	 /**
	  * データ型変換(BigDecimal→String)
	  * 
	  * @param hours　
	  *             時間数
	  * 
	  * @return　変換後の時間数
	  */
	 private String bigDecimalToString(BigDecimal hours) {
		
		if (hours == null) {
			
			return "0.0";
		} else {

			return hours.toString();
		}
	}
	 
	/**
	 * 勤怠管理テーブルを更新
	 * 
	 * @param kintaiList
	 *               勤怠管理データ
	 */
	private void updateChokinKanri(List<KintaiInfo> kintaiList) {
		
		if (kintaiList.size() > 0) {
			for (KintaiInfo kintaiInfo : kintaiList) {

				// CSV出力フラグに「１」を更新
				kintaiInfo.setCsvOutputFlg("1");
				// CSV出力日に「現在日付」を更新
				kintaiInfo.setCsvOutDate(CostDateUtils.getNowDate());
				// 勤怠テーブルを更新
				baseDao.update(kintaiInfo);
			}
		}
	}

	/**
	 * 勤怠管理データを取得
	 * 
	 * @param userId
	 *              ユーザＩＤ
	 * @param applyYmd
	 *               申請年月日
	 * @return 勤怠管理データ
	 */
	private List<KintaiInfo> getKintaiList(String userId, String applyYmd) {

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("atendanceDate", applyYmd.substring(0, 6) + "%");
		// 勤怠情報を取得
		List<KintaiInfo> kintaiList = baseDao.findResultList(condition, KintaiInfo.class);
		
		return kintaiList;
	}

	/**
	 * 超過勤務時間数（休日出勤振替分）を取得
	 * @param applyYmd 
	 * @param userId 
	 * 
	 * @return 超過勤務時間数（休日出勤振替分）
	 */
	private BigDecimal getFurikaeHours(String userId, String applyYmd) {
		
		BigDecimal furikaeHours = new BigDecimal(7.5);

		// 検索条件
		BaseCondition condition = new BaseCondition();
		// ユーザＩＤ
		condition.addConditionEqual("users.id", userId);
		// 日付
		condition.addConditionLike("atendanceDate", applyYmd.substring(0, 6) + "%");
		// 勤務日区分「休日振替勤務」
		condition.addConditionEqual("workDayKbnMaster.code", CommonConstant.WORKDAY_KBN_KYUJITU_FURIKAE);
		// 振替日があり
		condition.addConditionIsNotNull("furikaeDate");
		// 勤怠情報を取得
		List<KintaiInfo> kintaiList = baseDao.findResultList(condition, KintaiInfo.class);
		
		furikaeHours.multiply(new BigDecimal(kintaiList.size()));
		
		return furikaeHours;
	}

	/**
	 * 作成した給与システム用ファイル一覧リストを取得
	 * 
	 * @param userId
	 *            ユーザーID
     * @param response
     *                レスポンス
	 * @throws Exception 
	 *                  異常
	 */
	@Override
	public List<MakeKyuyoFileIchiranVO> getMadeFileNameList(String userId) {
		
		
		return null;
	}

}
