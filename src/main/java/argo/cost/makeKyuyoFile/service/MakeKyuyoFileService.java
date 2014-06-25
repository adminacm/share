package argo.cost.makeKyuyoFile.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import argo.cost.makeKyuyoFile.model.MakeKyuyoFileForm;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileIchiranVO;
/**
 * 給与システム用ファイル出力画面サービス
 *
 * @author COST argo Corporation.
 */
public interface MakeKyuyoFileService {
	
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
	List<MakeKyuyoFileIchiranVO> getMadeFileNameList(String userId);	
	
	/**
	 * 給与システム用ファイルを作成
	 * 
	 * @param makeKyuyoFileForm
	 *            給与システム用ファイル出力画面情報
     * @param response
     *                レスポンス
	 * @throws Exception 
	 *                  異常
	 */
	void createCSVFile(MakeKyuyoFileForm makeKyuyoFileForm) throws Exception;
	
	/**
	 * 給与システム用ファイルを作成
	 * 
	 * @param makeKyuyoFileForm
	 *            給与システム用ファイル出力画面情報
     * @param response
     *                レスポンス
	 * @throws Exception 
	 *                  異常
	 */
	void createFileNameClick(String clickedKyuyoFileName,HttpServletResponse response) throws Exception;


}
