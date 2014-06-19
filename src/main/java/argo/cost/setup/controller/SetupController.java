package argo.cost.setup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.setup.model.SetupForm;
import argo.cost.setup.service.SetupService;

/**
 * 個人設定画面のコントローラ
 * 
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_PERSONAL_SETUP)
@SessionAttributes(types = { SetupForm.class })
public class SetupController extends AbstractController {

	/**
	 * 個人設定画面サービス
	 */
	@Autowired
	private SetupService setupService;

	/**
	 * 個人設定画面ID
	 */
	private static final String SETUP_GAMENID = "setup";

	/**
	 * 個人設定画面ID
	 */
	private static final String SETUPEDIT_GAMENID = "setupEdit";

	/**
	 * 個人設定画面の編集画面
	 */
	public static final String INITSETUPEDIT = "/initSetupEdit";

	/**
	 * 個人設定画面の標準ｼﾌﾄ変更画面
	 */
	public static final String SHIFTCHANGE = "/shiftChange";

	/**
	 * 個人設定初期化
	 * 
	 * @param model
	 *            モデル
	 * @param loginId
	 *            ユーザID
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(INIT)
	public String initSetup(Model model) throws Exception {

		SetupForm form = initForm(SetupForm.class);
		// 画面情報を作成
		setupService.getSetupInfo(form);
		model.addAttribute(form);

		return SETUP_GAMENID;
	}

	/**
	 * 編集ボタンを押下
	 * 
	 * @param setupInfo
	 *            個人設定情報
	 * @return
	 */
	@RequestMapping(value = EDIT, method = RequestMethod.POST)
	public String editSetup(Model model,SetupForm setupForm) {
		// 画面情報を作成
		setupService.getSetupEditInfo(setupForm);
		// 画面へ設定します。
		model.addAttribute(setupForm);
		return SETUPEDIT_GAMENID;
	}

//	/**
//	 * 個人設定変更初期化
//	 * 
//	 * @param model
//	 *            モデル
//	 * @param setupInfo
//	 *            個人設定情報
//	 * @return
//	 * @throws Exception 
//	 */
//	@RequestMapping(INITSETUPEDIT)
//	public String initSetupEdit(Model model, SetupForm setupInfo) throws Exception {
//
//		setupInfo = initForm(SetupForm.class);
//		// 画面情報を作成
//		setupService.getSetupEditInfo(setupInfo);
//		// 画面へ設定します。
//		model.addAttribute(setupInfo);
//		return SETUPEDIT_GAMENID;
//	}

	/**
	 * 標準ｼﾌﾄ変更の場合
	 * 
	 * @param setupInfo
	 *            個人設定情報
	 * @return
	 */
	@RequestMapping(value = SHIFTCHANGE, method = RequestMethod.POST)
	public String doShiftChange(SetupForm setupInfo) {

		// 画面情報を作成
		setupService.changeShift(setupInfo);

		return SETUPEDIT_GAMENID;
	}

	/**
	 * 個人設定変更「保存」ボッタン押下
	 * 
	 * @param setupInfo
	 *            個人設定情報
	 * @return 個人設定画面
	 */
	@RequestMapping(value = SAVE, method = RequestMethod.POST)
	public String doSave(SetupForm setupInfo) {

		// チェックを実行
		Boolean chkFlg = setupService.doSaveCheck(setupInfo);

		if (chkFlg) {

			// 入力チェックOKの場合は入力された内容が保存されて、個人設定画面に遷移する
			setupService.doSave(setupInfo);
			return SETUP_GAMENID;
		} else {

			// エラーが発生した場合はエラーメッセージが表示され個人設定変更画面に戻る
			return SETUPEDIT_GAMENID;
		}

	}

	/**
	 * 個人設定変更「戻る」ボッタン押下
	 * 
	 * @return 個人設定画面
	 */
	@RequestMapping(value = CANCEL, method = RequestMethod.POST)
	public String doCancel() {

		return SETUP_GAMENID;
	}
}
