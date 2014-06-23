package argo.cost.makeKyuyoFile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileForm;
import argo.cost.makeKyuyoFile.model.MakeKyuyoFileIchiranVO;
import argo.cost.makeKyuyoFile.service.MakeKyuyoFileService;
import argo.cost.monthlyReportStatusList.model.MonthlyReportStatusListForm;

/**
 * <p>
 * 給与システム用ファイル出力業務クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@RequestMapping(UrlConstant.URL_MONTHLYREPORT_STATUS_LIST)
@SessionAttributes(types = { MonthlyReportStatusListForm.class })
public class MakeKyuyoFileController extends AbstractController  {
	
	/**
	 * 給与システム用ファイル出力サービス
	 */
	@Autowired
	protected MakeKyuyoFileService makeKyuyoFileService;

	/**
	 * 月報状況一覧画面ID
	 */
	private static final String MONTHLYREPORT_STATUS_LIST = "monthlyReportStatusList";
	
	/**
	 * 作成した給与ファイル名前リンクをクリックするアクション
	 */
	private static final String MADEKYUYOFILENAMECLICK = "/madeKyuyoFileNameClick";

	/**
	 *  給与システム用ファイル出力画面初期化
	 *
	 * @param model
	 *             画面情報モデル
	 * @return  給与システム用ファイル出力画面
	 * @throws Exception 
	 */
    @RequestMapping(INIT)
    public String initMonthlyReportStatusList(Model model) throws Exception {
    	
    	// フォーム初期化
    	MakeKyuyoFileForm makeKyuyoFileForm = initForm(MakeKyuyoFileForm.class);
    	// 画面へ設定します。
    	model.addAttribute(makeKyuyoFileForm);
    	
    	String userId = makeKyuyoFileForm.getUserId();
    	
    	// 画面の作成した給与ファイルリストを取得する
    	List<MakeKyuyoFileIchiranVO>  makeKyuyoFileIchiranList = makeKyuyoFileService.getMadeFileNameList(userId);
    	
    	// 作成した給与ファイル一覧リストを設定する
    	makeKyuyoFileForm.setMakeKyuyoFileIchiranList(makeKyuyoFileIchiranList);
    	
    	// 検索条件：処理年月
    	makeKyuyoFileForm.setDealYearMonth("");
    	
    	
    	//  給与システム用ファイル出力画面を戻る
        return MONTHLYREPORT_STATUS_LIST;
    }

    /**
     * ファイル作成ボタンを押して、ファイルを作成する
     * 
     * @param form
     *            給与システム用ファイル出力画面情報
     * @return 給与システム用ファイル出力画面
     * @throws Exception 
     */
    @RequestMapping(value = SEARCH, method = RequestMethod.POST)
    public String searchMonthlyReportStatusList(MakeKyuyoFileForm makeKyuyoFileForm) throws Exception {
    	
    	// 給与システム用ファイルを作成し、画面情報でファイル情報を設定する
    	makeKyuyoFileService.createCSVFile(makeKyuyoFileForm);

    	// 給与システム用ファイル出力画面を戻り
        return MONTHLYREPORT_STATUS_LIST;
    }

    /**
     * 作成した給与ファイル名前リンクをクリックし、ファイルをダウンロードする
     * 
     * @param madeKyuyoFileName
     *               作成した給与ファイル名前
     * @return　給与システム用ファイル出力画面情報
     */
    @RequestMapping(value = MADEKYUYOFILENAMECLICK)
    public String approvalNoClick(@RequestParam("madeKyuyoFileName") String madeKyuyoFileName) {
    	
    	// 給与システム用ファイルを作成し、画面情報でファイル情報を設定する
    	
    	return "1";
    }
    	
}
