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
import argo.cost.attendanceInput.model.AttendanceProject;
import argo.cost.attendanceInput.service.AttendanceInputService;
import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;

@Controller
@RequestMapping(UrlConstant.URL_ATTENDANCE_INPUT)
@SessionAttributes(types = { AttendanceInputForm.class })
public class AttendanceInputController extends AbstractController {

	@Autowired
	AttendanceInputService attService;

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
	public String init(Model model, @RequestParam("attDate") String newDate)
			throws Exception {

		// ユーザID
		String userId = getSession().getUserInfo().getId();

		// フォーム初期化
		AttendanceInputForm form = initForm(AttendanceInputForm.class);
		// セッション情報設定
		getSession().setUrl("attendanceInput.jsp");
		// 画面へ設定します。
		model.addAttribute(form);
		
		// 画面情報を設定
		attService.setAttForm(form, newDate, userId);

		return "attendanceInput";
	}

	/**
	 * 明日の日付を取得
	 * 
	 * @param form
	 *            フォーム
	 * @return
	 */
	@RequestMapping(value = "/nextDay", method = RequestMethod.POST)
	public String getNextMonth(AttendanceInputForm form) throws Exception {

		// 明日の日付を取得
		String nextMonth = attService.changeDate("next", form.getAttDate());

		return "redirect:/attendanceInput/init?attDate=" + nextMonth;
	}

	/**
	 * 昨日の日付を取得
	 * 
	 * @param form
	 *            フォーム
	 * @return
	 */
	@RequestMapping(value = "/lastDay", method = RequestMethod.POST)
	public String getLastMonth(AttendanceInputForm form) throws Exception {

		// 明日の日付を取得
		String nextMonth = attService.changeDate("last", form.getAttDate());

		return "redirect:/attendanceInput/init?attDate=" + nextMonth;
	}

	/**
	 * 保存処理
	 * 
	 * @param model
	 *            モデル
	 * @return
	 */
	@RequestMapping("/save")
	public String doSave(AttendanceInputForm form) {

		// 就業データを更新する
		Integer result = attService.updateAttdendanceInfo(form);
		
		// 更新成功
		if (1 == result) {
			return "redirect:/attendanceInput/back";
		} else {
			// errorMessageを追加
			return "attendanceInput";
		}
		
	}

	/**
	 * 戻る処理
	 * 
	 * @param model
	 *            モデル
	 * @return
	 */
	@RequestMapping("/back")
	public String doBack(AttendanceInputForm form) {

		// 画面ID
		String gameId = getSession().getForm();

		if (StringUtils.equals("Menu", gameId)) {

			return "redirect:/menu/init";
		} else {
			return "redirect:/monthlyReport/init?newMonth=";
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
	public String doAddLine(AttendanceInputForm form) throws ParseException {

		AttendanceProject pro = new AttendanceProject();
		pro.setProjectItemList(comService.getProjectNameList(form.getUserId()));
		pro.setWorkItemList(attService.getWorkItemList());
		form.getProjectList().add(pro);
		return "attendanceInput";
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
	public String doCount(AttendanceInputForm form) throws ParseException {

		// チェックを実行する。TODO
		// 勤務情報を計算する。
		attService.calcWorkingRec(form);
		
		return "attendanceInput";
	}

	/**
	 * 休日勤務入力画面へ
	 * 
	 * @param model
	 *            モデル
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/attendanceOnHoliday")
	public String goAttendanceOnHoliday(AttendanceInputForm form) throws ParseException {

		// 勤務日付を取得
		String attendanceDate = form.getAttDate();
		
		return REDIRECT + UrlConstant.URL_ATT_HOLIDAY + INIT 
				+ QUESTION_MARK + ATTDENDANCE_DATE + "=" + attendanceDate;
	}
}
