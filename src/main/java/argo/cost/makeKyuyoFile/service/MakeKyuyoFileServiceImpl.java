package argo.cost.makeKyuyoFile.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argo.cost.common.constant.CommonConstant;
import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ApprovalManage;
import argo.cost.common.entity.KintaiInfo;
import argo.cost.common.entity.MadeSyskyuyofileOutput;
import argo.cost.common.entity.Users;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileForm;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileIchiranVO;
import argo.cost.makeKyuyoFile.model.PayMagistrateCsvInfo;
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
		MadeSyskyuyofileOutput madeSyskyuyofileOutputEntity = new MadeSyskyuyofileOutput();
		
		// 当前の時間を設定する
		Timestamp timestampSystemTime = new Timestamp(System.currentTimeMillis()); 
		madeSyskyuyofileOutputEntity.setFileCreatedTimestamp(timestampSystemTime);
		// 処理年月を設定する
		madeSyskyuyofileOutputEntity.setApplyDealYearMonth(makeKyuyoFileForm.getDealYearMonth());
		// 作成者ユーザーIDを設定する
		Users userInfo = baseDao.findById(makeKyuyoFileForm.getUserId(), Users.class);
		madeSyskyuyofileOutputEntity.setUsers(userInfo);
		
		String strCsvData = "";
		for (int i = 0 ;i < csvDetailList.size() ;i ++) {
			String strNo = csvDetailList.get(i).getUserId().concat(",");
			String strOverWeekdayHours = csvDetailList.get(i).getOverWeekdayHours().concat(",");
			String strOverNightHours = csvDetailList.get(i).getOverNightHours().concat(",");
			String strOverHolidayChangeWorkHours = csvDetailList.get(i).getOverHolidayChangeWorkHours().concat(",");
			String strAbsenceHours = csvDetailList.get(i).getAbsenceHours().concat(",");
			String strOverWeekdayNomalHours = csvDetailList.get(i).getOverWeekdayNomalHours().concat(",").concat("\r\n");
			
			strCsvData += strNo.concat(strOverWeekdayHours).concat(strOverNightHours).concat(strOverHolidayChangeWorkHours).concat(strAbsenceHours).concat(strOverWeekdayNomalHours);
		}
		
		byte[] sbyte = strCsvData.getBytes();
		// ファイル内容を設定する
		madeSyskyuyofileOutputEntity.setKyuyofileNaiyo(sbyte);
		SimpleDateFormat sysTimeFirst = new SimpleDateFormat("yyyyMM");
	    SimpleDateFormat sysTimeSecond = new SimpleDateFormat("yyyyMMddHHmmss");
	    String filaName = sysTimeFirst.format(new Date()).toString().concat(" ").concat(sysTimeSecond.format(new Date()).toString());
		madeSyskyuyofileOutputEntity.setMadeKyuyofileName(filaName);
		
		String strFileInsertFlg ="0";
		try {
			baseDao.insert(madeSyskyuyofileOutputEntity);
			strFileInsertFlg = "1";
		} catch (Exception e) {
			System.out.println("給与システム用ファイル作成失敗しました");
		}
		
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
		// タイトルの設定
		this.addTitle(payMagistrateCsvList);
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
		BaseCondition selectApprovalManageInfoCondition = new BaseCondition();
		// 処理年月
		selectApprovalManageInfoCondition.addConditionEqual("syoriYm", makeKyuyoFileForm.getDealYearMonth());
		// ユーザーID
		selectApprovalManageInfoCondition.addConditionEqual("users.id", makeKyuyoFileForm.getUserId());
		// 申請区分（"1" 月報）
		selectApprovalManageInfoCondition.addConditionEqual("applyKbnMaster.code", CommonConstant.APPLY_KBN_GETUHOU);
		
		// 月報状況一覧リスト
		List<ApprovalManage> approvalList = baseDao.findResultList(selectApprovalManageInfoCondition, ApprovalManage.class);
		
		return approvalList;
	}
	
	/**
	 * ヘッダ部データ設定
	 * 
	 * @return ヘッダ部データ
	 */
	private void addTitle(List<PayMagistrateCsvInfo> payMagistrateCsvList) {
		
		PayMagistrateCsvInfo csvInfo = new PayMagistrateCsvInfo();
		// MK01
		csvInfo.setUserId("MK01");
		// KN09
		csvInfo.setOverWeekdayHours("KN09");
		// KN10
		csvInfo.setOverHolidayHours("KN10");
		// KN11
		csvInfo.setOverNightHours("KN11");
		// KN12
		csvInfo.setOverHolidayChangeWorkHours("KN12");
		// KN08
		csvInfo.setAbsenceHours("KN08");
		// KN13
		csvInfo.setOverWeekdayNomalHours("KN13");
		
		payMagistrateCsvList.add(0, csvInfo);
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
	 */
	@Override
	public List<MakeKyuyoFileIchiranVO> getMadeFileNameList(String userId) {
		
		List<MakeKyuyoFileIchiranVO> makeKyuyoFileIchiranVOList = new ArrayList<MakeKyuyoFileIchiranVO>();
		BaseCondition selectMadeSyskyuyofileOutputList = new BaseCondition();
		selectMadeSyskyuyofileOutputList.addConditionEqual("users.id", userId);
		List<MadeSyskyuyofileOutput> madeSyskyuyofileOutputList = baseDao.findResultList(selectMadeSyskyuyofileOutputList,MadeSyskyuyofileOutput.class);
		for (int i = 0 ; i < madeSyskyuyofileOutputList.size() ;i++) {
			
			MakeKyuyoFileIchiranVO makeKyuyoFileIchiranVO = new MakeKyuyoFileIchiranVO();
			
			// ファイル名
			makeKyuyoFileIchiranVO.setMadeKyuyoFileName(madeSyskyuyofileOutputList.get(i).getMadeKyuyofileName());
			// 処理年月
			makeKyuyoFileIchiranVO.setDealYearMonth(madeSyskyuyofileOutputList.get(i).getApplyDealYearMonth());
			// 作成者
			makeKyuyoFileIchiranVO.setCreatedUserName(madeSyskyuyofileOutputList.get(i).getUsers().getUserName());
			// 作成日時
			makeKyuyoFileIchiranVO.setCreatedDateTime(madeSyskyuyofileOutputList.get(i).getFileCreatedTimestamp().toString().replaceAll("-", "/"));
		
			makeKyuyoFileIchiranVOList.add(makeKyuyoFileIchiranVO);
		}
		
		return makeKyuyoFileIchiranVOList;
	}

	/**
	 * 作成した給与システム用ファイル一覧リストの項目クリック処理
	 * 
	 * @param clickedKyuyoFileName
	 *            給与システム用ファイル名前
	 * @throws Exception 
	 *                  異常
	 */
	@Override
	public void createFileNameClick(String clickedKyuyoFileName,HttpServletResponse response)
			throws Exception {
		
		BaseCondition selectedKyuyoFileCondition = new BaseCondition();
		// 検索条件：クリックした給与システム用ファイル名前
		selectedKyuyoFileCondition.addConditionEqual("madeKyuyofileName", clickedKyuyoFileName);
		// クリックしたファイルを取得する
		MadeSyskyuyofileOutput createdSyskyuyofileOutputInfo = baseDao.findSingleResult(selectedKyuyoFileCondition, MadeSyskyuyofileOutput.class);
		 // CSV ダウンロード
        String csvData = new String(createdSyskyuyofileOutputInfo.getKyuyofileNaiyo());
        OutputStream outStream = response.getOutputStream();
        byte[] byteArray = csvData.getBytes("Windows-31J");
        SimpleDateFormat sysTimeFirst = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat sysTimeSecond = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sysTimeFirst.format(new Date()).concat(" ").concat(sysTimeSecond.format(new Date()));
        setResponseHeader(response, fileName, byteArray.length);
        outStream.write(byteArray);
        outStream.flush();
        outStream.close();
	}
	
	/**
	 * 給与システム用ファイルの配列によって、ファイルを作成処理
	 * 
	 * @param bKyuyofileNaiyo
	 *            ファイル内容配列
     * @param filePath
     *            ファイル保存されるパース
     * @param fileName
     *            ファイル名前
	 */
    public static void getFile(byte[] bKyuyofileNaiyo, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){
                dir.mkdirs();  
            }  
            file = new File(filePath+"\\"+fileName+".csv");  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bKyuyofileNaiyo);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }
    
    
	/**
	 * ファイルをダウンロード処理
	 * 
	 * @param response
	 *            レスポンス
     * @param fileName
     *            ファイル名
     * @param fileLength
     *            ファイル長さ
	 */  
  private void setResponseHeader(HttpServletResponse response, String fileName, int fileLength) throws Exception {
	  
      response.setContentType("application/octet-stream");              
      response.setHeader("Content-disposition", "attachment;filename=".concat(URLEncoder.encode(fileName, "UTF-8")) + ".csv");
      response.setHeader("Content-Type", "text/plain; charset=Shift_JIS");
      response.setDateHeader("Expires", 0);
      if (fileLength > 0) {
          response.setContentLength(fileLength);
      }
  }
}
