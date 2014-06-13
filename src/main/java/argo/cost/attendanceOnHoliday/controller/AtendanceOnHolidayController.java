package argo.cost.attendanceOnHoliday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.attendanceOnHoliday.service.AtendanceOnHolidayCheck;
import argo.cost.attendanceOnHoliday.service.AtendanceOnHolidayService;
import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;

/**
 * 休日勤務画面のコントローラ
 * 
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_ATT_HOLIDAY)
@SessionAttributes(types = {AtendanceOnHolidayForm.class})
public class AtendanceOnHolidayController extends AbstractController {

	/**
	 * 休日勤務入力サービス
	 */
	@Autowired
	protected AtendanceOnHolidayService atendanceOnHolidayService;

	/**
	 * 休日勤務入力情報
	 */
	private static final String ATTENDANCE_INFO = "attendanceOnHolidayInfo";

	/**
	 * 休日勤務入力画面URL
	 */
	private static final String ATTENDANCE_HOLIDAY = "attendanceOnHoliday";

	/**
	 * 休日勤務入力画面の初期化処理
	 * 
	 * @param model
	 *            画面情報設定用のインタフェース
	 * @param date
	 *            勤怠入力画面から渡した休日の日付
	 * @return 休日勤務入力画面の初期化の情報
	 * 
	 * @throws Exception 
	 */
	@RequestMapping(value = INIT)
	public String initAtendanceOnHoliday(Model model, @RequestParam(ATTDENDANCE_DATE) String currentDate) throws Exception {
		
		// 休日勤務入力画面情報初期化
		AtendanceOnHolidayForm atendanceOnHolidayForm = initForm(AtendanceOnHolidayForm.class);
		
		// 画面情報を設定する。
		atendanceOnHolidayService.setAtendanceOnHolidayInfo(atendanceOnHolidayForm, currentDate);
		// プロジェクト名プルダウンリストの取得
//		atendanceOnHolidayForm.setProjCdList(comService.getProjectNameList(atendanceOnHolidayForm.getUserId()));
		
		model.addAttribute(ATTENDANCE_INFO, atendanceOnHolidayForm);

		return ATTENDANCE_HOLIDAY;

	}

	/**
	 * 休日勤務入力画面の保存処理
	 * 
	 * @param atendanceOnHolidayInfo
	 *            休日勤務入力画面情報
	 * @param map
	 * @return 休日勤務入力画面の初期化の情報
	 */
	@RequestMapping(value = SAVE, method = RequestMethod.POST)
	public String save(@ModelAttribute(ATTENDANCE_INFO) AtendanceOnHolidayForm atendanceOnHolidayInfo,
			BindingResult result) {

		// TODO 画面から全角文字の取得不正
		// TODO チェック処理を呼ぶ
		AtendanceOnHolidayCheck atendanceOnHolidayCheck = new AtendanceOnHolidayCheck();
		atendanceOnHolidayCheck.validate(atendanceOnHolidayInfo, result);
		
		// TODO　チャック処理の結果がエラーがない場合
		if (!result.hasErrors()) {
			// TODO 保存成功の場合、勤怠入力画面に遷移する
			if ("1".equals(atendanceOnHolidayService.saveAtendanceOnHoliday(atendanceOnHolidayInfo))) {

				
				// 勤怠入力画面へ遷移する
				return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK 
						+ "attDate=" + atendanceOnHolidayInfo.getStrAtendanceDate().replace("/", "");
			} else {

				// TODO 保存失敗の場合、エラーメッセージを出力する
				return ATTENDANCE_HOLIDAY;
			}

		} else {
			return ATTENDANCE_HOLIDAY;

		}
	}

	/**
	 * 休日勤務入力画面の削除処理
	 * 
	 * @param strAtendanceDate
	 *            当前の休日日付
	 * @param map
	 * @return 休日勤務入力画面の初期化の情報
	 */
	@RequestMapping(value = DELETE)
	public String delete(@ModelAttribute(ATTENDANCE_INFO) AtendanceOnHolidayForm atendanceOnHolidayForm) {

		// 勤務日付
		String strAtendanceDate = atendanceOnHolidayForm.getStrAtendanceDate();

		// TODO 削除失敗する場合
		if (atendanceOnHolidayService.deleteAtendanceOnHoliday(atendanceOnHolidayForm.getStrAtendanceDate(), atendanceOnHolidayForm.getUserId()) == 0) {
			
			System.out.println("勤務情報を削除失敗");
			// TODO エラーメッセジーを設定し、表示する
			return ATTENDANCE_HOLIDAY;
		}
		System.out.println("削除成功する場合、勤怠入力画面に遷移する");
		// 削除成功する場合、勤怠入力画面に遷移する
		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK + "attDate=" + strAtendanceDate.replace("/", "");
		

	}

	/**
	 * 休日勤務入力画面の戻る処理
	 * 
	 * @return 勤怠入力画面の初期化の情報
	 */
	@RequestMapping(value = BACK)
	public String returnToKintaiInput(@ModelAttribute(ATTENDANCE_INFO) AtendanceOnHolidayForm atendanceOnHolidayForm) {

		// TODO 戻るボタンを押すと入力内容が破棄され、勤怠入力画面へ戻る
		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK + "attDate=" + atendanceOnHolidayForm.getStrAtendanceDate().replace("/", "");

	}

}
