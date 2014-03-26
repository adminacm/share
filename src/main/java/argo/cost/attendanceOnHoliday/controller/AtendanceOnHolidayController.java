package argo.cost.attendanceOnHoliday.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHoliday;
import argo.cost.attendanceOnHoliday.service.AtendanceOnHolidayCheck;
import argo.cost.attendanceOnHoliday.service.AtendanceOnHolidayService;
import argo.cost.common.model.AppSession;

@Controller
@RequestMapping("atendanceOnHoliday")
public class AtendanceOnHolidayController {

	@Autowired
	protected AtendanceOnHolidayService service;

	protected AppSession session;

	/**
	 * 休日勤務入力画面の初期化処理
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            勤怠入力画面から渡した休日の日付
	 * @return 休日勤務入力画面の初期化の情報
	 */
	@RequestMapping("init")
	public String initAtendanceOnHoliday(Map<String, Object> map,
			String userId, String date) {
		AtendanceOnHoliday atendanceOnHoliday = new AtendanceOnHoliday();

		// 当日休日勤務情報がある場合、データベースから取得して、画面に表示する
		if (service.atendanceOnHolidayDataChk(userId, date)) {

			atendanceOnHoliday = service
					.atendanceOnHolidayDataGet(userId, date);
		}

		// 当日休日勤務情報が新規入力する場合、画面のプルダウンリスト項目を取得する
		// 勤務日区分プルダウンリスト
		atendanceOnHoliday
				.setAtendanceDayKbnList(service.atendanceDayKbnList());
		// プロジェクト名プルダウンリスト
		atendanceOnHoliday.setProjCdList(service.projectKbnList());

		map.put("atendanceOnHolidayInfo", atendanceOnHoliday);

		return "atendanceOnHoliday";

	}

	/**
	 * 休日勤務入力画面の保存処理
	 * 
	 * @param atendanceOnHolidayInfo
	 *            休日勤務入力画面情報
	 * @param map
	 * @return 休日勤務入力画面の初期化の情報
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			@ModelAttribute("atendanceOnHolidayInfo") AtendanceOnHoliday atendanceOnHolidayInfo,BindingResult result,
			Map<String, Object> map) {

		String strSaveMes = "";

		// TODO チェック処理を呼ぶ
		AtendanceOnHoliday atendanceOnHoliday = new AtendanceOnHoliday();
		AtendanceOnHolidayCheck atendanceOnHolidayCheck = new AtendanceOnHolidayCheck();
		atendanceOnHolidayCheck.validate(atendanceOnHoliday, result);
		// TODO　チャック処理の結果がエラーがない場合
		if (!result.hasErrors()) {
			// 日付
			atendanceOnHoliday.setStrAtendanceDate(atendanceOnHolidayInfo
					.getStrAtendanceDate());
			// 選択された勤務日区分
			atendanceOnHoliday
					.setSelectedAtendanceDayKbn(atendanceOnHolidayInfo
							.getSelectedAtendanceDayKbn());
			// 勤務開始時間
			atendanceOnHoliday.setStrAtendanceTimeStat(atendanceOnHolidayInfo
					.getStrAtendanceTimeStat());
			// 勤務完了時間
			atendanceOnHoliday.setStrAtendanceTimeEnd(atendanceOnHolidayInfo
					.getStrAtendanceTimeEnd());
			// 振替日
			atendanceOnHoliday.setStrHurikaeDate(atendanceOnHolidayInfo
					.getStrHurikaeDate());
			// 選択されたプロジェクト名
			atendanceOnHoliday.setSelectedProjCd(atendanceOnHolidayInfo
					.getSelectedProjCd());
			// 業務内容
			atendanceOnHoliday.setStrCommont(atendanceOnHolidayInfo
					.getStrCommont());

		

		// TODO 保存成功の場合、勤怠入力画面に遷移する
		if ("1".equals(service.saveAtendanceOnHoliday(atendanceOnHoliday,
				session.getUserInfo().getId()))) {

			strSaveMes = " save OK";

			map.put("strSaveMes", "save OK");
		// TODO 保存失敗の場合、エラーメッセージを出力する、
		} else {

			strSaveMes = "save error";

			map.put("strSaveMes", "save error");
		}
		

		map.put("atendanceOnHoliday", atendanceOnHoliday);
		return "atendanceOnHoliday";
		
		} else {
			return "atendanceOnHoliday";
			
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
	@RequestMapping("/delete/{strAtendanceDate}")
	public String delete(
			@PathVariable("strAtendanceDate") String strAtendanceDate) {

		// TODO ユーザーIDを取得する
		service.deleteAtendanceOnHoliday(strAtendanceDate, session
				.getUserInfo().getId());

		return "attendanceInput";

	}

	/**
	 * 休日勤務入力画面の戻る処理
	 * 
	 * @return 勤怠入力画面の初期化の情報
	 */
	@RequestMapping("/modoru")
	public String returnToKintaiInput() {

		// TODO 戻るボタンを押すと入力内容が破棄され、勤怠入力画面へ戻る
		return "attendanceInput";

	}

}
