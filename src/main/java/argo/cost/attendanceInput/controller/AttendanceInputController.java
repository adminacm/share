package argo.cost.attendanceInput.controller;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProjectVO;
import argo.cost.attendanceInput.service.AttendanceInputService;
import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;

/**
 * 勤怠入力画面業務クラス
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_ATTENDANCE_INPUT)
@SessionAttributes(types = { AttendanceInputForm.class })
public class AttendanceInputController extends AbstractController {

	/**
	 * 勤怠入力サービス
	 */
	@Autowired
	AttendanceInputService attendanceInputService;
	
	/**
	 *　勤怠入力
	 */
	private static final String ATTDENDANCE_INPUT = "attendanceInput";

	/**
	 *　休日勤務入力画面へ
	 */
	private static final String URL_ATT_HOLIDAY = "/attendanceOnHoliday";

	/**
	 * 初期化
	 * 
	 * @param map
	 *            マップ
	 * @param loginId
	 *            ユーザID
	 * @return
	 * @throws Exception
	 *             Exception
	 */
	@RequestMapping(value = INIT)
	@Secured({"ROLE_USER"})
	public String initAttendanceInput(Model model, @RequestParam(ATTDENDANCE_DATE) String newDate)
			throws Exception {


		// フォーム初期化
		AttendanceInputForm form = initForm(AttendanceInputForm.class);
		// 画面へ設定します。
		model.addAttribute(form);
		
		// 画面情報を設定
		attendanceInputService.setAttForm(form, newDate);

		return ATTDENDANCE_INPUT;
	}

	/**
	 * 明日の日付を取得
	 * 
	 * @param form
	 *            フォーム
	 * @return
	 */
	@RequestMapping(value = "/nextDay", method = RequestMethod.POST)
	public String getNextDay(AttendanceInputForm form) throws Exception {

		// 明日の日付を取得
		String nextMonth = attendanceInputService.changeDate("next", form.getAttDate());

		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK +
				ATTDENDANCE_DATE + EQUAL_SIGN + nextMonth;
	}

	/**
	 * 昨日の日付を取得
	 * 
	 * @param form
	 *            フォーム
	 * @return
	 */
	@RequestMapping(value = "/lastDay", method = RequestMethod.POST)
	public String getLastDay(AttendanceInputForm form) throws Exception {

		// 明日の日付を取得
		String nextMonth = attendanceInputService.changeDate("last", form.getAttDate());

		return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + INIT + QUESTION_MARK + 
				ATTDENDANCE_DATE + EQUAL_SIGN + nextMonth;
	}

	/**
	 * 保存処理
	 * 
	 * @param model
	 *            モデル
	 * @return
	 */
	@RequestMapping(SAVE)
	public String save(AttendanceInputForm form) {

		// 就業データを更新する
		Integer result = attendanceInputService.updateAttdendanceInfo(form);
		
		// 更新成功
		if (1 == result) {
			return REDIRECT + UrlConstant.URL_ATTENDANCE_INPUT + BACK;
		} else {
			// errorMessageを追加
			return ATTDENDANCE_INPUT;
		}
		
	}

	/**
	 * 戻る処理
	 * 
	 * @param model
	 *            モデル
	 * @return
	 */
	@RequestMapping(BACK)
	public String back(AttendanceInputForm form) {

		// 画面ID
		String gameId = getSession().getForm();

		if (StringUtils.equals("Menu", gameId)) {

			return REDIRECT + UrlConstant.URL_MENU + INIT;
		} else {
			return REDIRECT + UrlConstant.URL_MONTHLYREPORT + INIT + QUESTION_MARK + "newMonth=" + EQUAL_SIGN;
		}
	}

	/**
	 * 行追加処理
	 * 
	 * @param model
	 *            モデル
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/add")
	public String addLine(AttendanceInputForm form) throws ParseException {

		AttendanceProjectVO pro = new AttendanceProjectVO();
		// プロジェクト情報を取得
		pro.setProjectItemList(comService.getProjectNameList());
		pro.setWorkItemList(comService.getWorkItemList());
		form.getProjectList().add(pro);
		return ATTDENDANCE_INPUT;
	}

	/**
	 * 計算処理
	 * 
	 * @param model
	 *            モデル
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/count")
	public String count(AttendanceInputForm form) throws Exception {

		form.clearMessages();
		// 勤務情報を計算する。
		attendanceInputService.calcWorkingRec(form);

		return ATTDENDANCE_INPUT;
	}

	/**
	 * 休日勤務入力画面へ
	 * 
	 * @param model
	 *            モデル
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(URL_ATT_HOLIDAY)
	public String goAttendanceOnHoliday(AttendanceInputForm form) throws ParseException {

		// 勤務日付を取得
		String attendanceDate = form.getAttDate();
		
		return REDIRECT + UrlConstant.URL_ATT_HOLIDAY + INIT 
				+ QUESTION_MARK + ATTDENDANCE_DATE + EQUAL_SIGN + attendanceDate;
	}
}
