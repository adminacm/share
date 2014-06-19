package argo.cost.attendanceOnHolidayRecordDetail.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.attendanceOnHolidayRecordDetail.model.AttendanceOnHolidayRecordDetailForm;
import argo.cost.attendanceOnHolidayRecordDetail.service.AttendanceOnHolidayRecordDetailService;
import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;

/**
 * <p>
 * 休日出勤管理詳細画面業務クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_ATTENDANCE_ONHOLIDAY_RECORD_DETAIL)
@SessionAttributes(types = {AttendanceOnHolidayRecordDetailForm.class})
public class AttendanceOnHolidayRecordDetailController extends AbstractController {

	/**
	 * 休日出勤管理サービス
	 */
	@Autowired
	protected AttendanceOnHolidayRecordDetailService service;

	/**
	 * 休日出勤管理画面ID
	 */
	private static final String ATTENDANCE_ONHOLIDAY_RECORD_DETAIL = "attendanceOnHolidayRecordDetail";

	/**
	 * 超勤に振替えるボタンを押下アクション
	 */
	private static final String  OVERWORKEXCHANGE = "/overWorkExchange";

	/**
	 * 休日出勤管理詳細画面の初期化処理
	 * 
	 * @param model
	 *             画面情報モデル
	 * @param date
	 *            日付
	 * @param workKbn
	 *               勤務区分
	 * @return
	 *        休日出勤管理詳細画面
	 * @throws Exception
	 *                  異常
	 */
	@RequestMapping(value = INIT)
	public String initAttendanceOnHolidayRecordDetail(Model model, @RequestParam("date") String date, @RequestParam("workKbn") String workKbn) throws Exception {
		
		// 休日勤務入力画面情報初期化
		AttendanceOnHolidayRecordDetailForm form = initForm(AttendanceOnHolidayRecordDetailForm.class);

		// 休日出勤管理詳細画面情報取得
		AttendanceOnHolidayRecordDetailForm detailForm = service.getAttendanceOnHolidayRecordDetail(form.getUserId(), date, workKbn);
		
		// 代休未取得
		if (detailForm.getTurnedHolidayDate() == null) {
			
			// 超勤振替フラグ
			detailForm.setOverWorkFlg(true);
		}
		
		// 休日出勤管理詳細画面情報設定
		model.addAttribute(detailForm);

		// 休日出勤管理詳細画面を戻り
		return ATTENDANCE_ONHOLIDAY_RECORD_DETAIL;
	}

	/**
	 * 超勤に振替えるボタンを押下
	 * 
	 * @param form
	 *            休日出勤管理詳細画面情報
	 * @return
	 *        休日出勤管理詳細画面
	 */
	@RequestMapping(value = OVERWORKEXCHANGE, method = RequestMethod.POST)
	public String overworkExchange(AttendanceOnHolidayRecordDetailForm form) {
		
		// 超勤振替申請を提出
		try {
			service.overWorkPayRequest(form);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 超勤振替申請が提出、休日出勤管理画面へ戻る
		return REDIRECT + UrlConstant.URL_ATTENDANCE_ONHOLIDAY_RECORD + INIT;
	}

	/**
	 * 戻るボタンを押下
	 */
	@RequestMapping(value = BACK)
	public String doBack() {

		// 戻るボタンを押すと入力内容が破棄され、休日出勤管理画面へ戻る
		return REDIRECT + UrlConstant.URL_ATTENDANCE_ONHOLIDAY_RECORD + INIT;
	}
}
