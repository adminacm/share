package argo.cost.attendanceOnHoliday.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.attendanceOnHoliday.checker.AtendanceOnHolidayChecker;
import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
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
		
		model.addAttribute(atendanceOnHolidayForm);

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
	public String save(AtendanceOnHolidayForm form) {

		// チェック処理を呼ぶ
		form.clearMessages();
		// 勤務開始時刻チェック
		AtendanceOnHolidayChecker.chkStartTimeInput(form);
		// 勤務終了時刻チェック
		AtendanceOnHolidayChecker.chkEndTimeInput(form);
		// 勤務日区分チェック
		AtendanceOnHolidayChecker.chkWorkDayKbn(form);
		// プロジェクト情報チェック
		AtendanceOnHolidayChecker.chkProjectID(form);
		// 業務内容チェック
		AtendanceOnHolidayChecker.chkWorkDetail(form);
		// 振替日チェック
		AtendanceOnHolidayChecker.chkFurikaeBiInput(form);
		
		// チャック処理の結果がエラーがない場合
		if (StringUtils.isEmpty(form.getConfirmMsg())) {
			// 保存成功の場合、勤怠入力画面に遷移する
			if ("1".equals(atendanceOnHolidayService.saveAtendanceOnHoliday(form))) {

				// 勤怠入力画面へ遷移する
				return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK 
						+ ATTDENDANCE_DATE + EQUAL_SIGN + form.getStrAtendanceDate().replace("/", "");
			} else {

				// 保存失敗の場合、エラーメッセージを出力する
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
	public String delete(AtendanceOnHolidayForm form) {

		// 勤務日付
		String strAtendanceDate = form.getStrAtendanceDate();

		// 削除失敗する場合
		if (atendanceOnHolidayService.deleteAtendanceOnHoliday(form.getStrAtendanceDate(), form.getTaishoUserId()) == 0) {
			
			return ATTENDANCE_HOLIDAY;
		}
		// 削除成功する場合、勤怠入力画面に遷移する
		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK + ATTDENDANCE_DATE + "=" + strAtendanceDate.replace("/", "");

	}

	/**
	 * 休日勤務入力画面の戻る処理
	 * 
	 * @return 勤怠入力画面の初期化の情報
	 */
	@RequestMapping(value = BACK)
	public String returnToKintaiInput(AtendanceOnHolidayForm form) {

		// 戻るボタンを押すと入力内容が破棄され、勤怠入力画面へ戻る
		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK + ATTDENDANCE_DATE + "=" + form.getStrAtendanceDate().replace("/", "");

	}

}
