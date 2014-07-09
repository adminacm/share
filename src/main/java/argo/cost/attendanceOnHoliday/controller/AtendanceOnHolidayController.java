package argo.cost.attendanceOnHoliday.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
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
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.utils.CostDateUtils;

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
		try {
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
			// 休日勤務情報を保存する
			atendanceOnHolidayService.saveAtendanceOnHoliday(form);
		} catch (Exception e) {
			// エラーメッセージを出力する
			return ATTENDANCE_HOLIDAY;
		}
		
		// 勤怠入力画面へ遷移する
		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK 
				+ ATTDENDANCE_DATE + EQUAL_SIGN + form.getStrAtendanceDate().replace("/", "");
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
		
		try {
			// 対象日の勤怠実績チェック
			AtendanceOnHolidayChecker.chkDelKintaiInfo(form);
			// 休日勤務情報を削除する
			atendanceOnHolidayService.deleteAtendanceOnHoliday(form.getStrAtendanceDate(), form.getTaishoUserId());
		} catch (Exception e) {
			// エラーメッセージを出力する
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
	
	/**
	 * 振替日のフォーマットの戻る処理
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/changeDate")
	public void change(HttpServletResponse response, AtendanceOnHolidayForm form) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		// 振替日
		String date = form.getStrHurikaeDate();
		String week = "";
		if (CostDateUtils.isValidDate(date, CommonConstant.YYYYMMDD)) {
			// 曜日を取得
			week = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(date));
			// 振替日をしてされたフォーマットに変更する 
			date = CostDateUtils.formatDate(date, CommonConstant.YYYY_MM_DD);
		}
		AtendanceOnHolidayForm newForm = new AtendanceOnHolidayForm();
		newForm.setStrHurikaeDate(date);
		newForm.setWeek("（".concat(week).concat("）"));
		
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject(newForm);
		out.write(json.toString());
		out.flush();
		out.close();
	}
}
