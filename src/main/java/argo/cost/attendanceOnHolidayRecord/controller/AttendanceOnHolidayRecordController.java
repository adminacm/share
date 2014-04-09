package argo.cost.attendanceOnHolidayRecord.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.attendanceOnHolidayRecord.model.AttendanceOnHolidayRecordForm;
import argo.cost.attendanceOnHolidayRecord.service.AttendanceOnHolidayRecordService;
import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.utils.CostDateUtils;

/**
 * <p>
 * 休日出勤管理画面業務クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_ATTENDANCE_ONHOLIDAY_RECORD)
@SessionAttributes(types = {AttendanceOnHolidayRecordForm.class})
public class AttendanceOnHolidayRecordController extends AbstractController {

	/**
	 * 休日出勤管理サービス
	 */
	@Autowired
	protected AttendanceOnHolidayRecordService recordService;

	/**
	 * 休日出勤管理情報
	 */
	private static final String ATTENDANCE_ONHOLIDAY_RECORD_INFO = "attendanceOnHolidayRecordInfo";

	/**
	 * 休日出勤管理画面URL
	 */
	private static final String ATTENDANCE_ONHOLIDAY_RECORD = "attendanceOnHolidayRecord";

	/**
	 * 休日出勤管理画面の初期化処理
	 * 
	 * @param model
	 *            画面情報モデル
	 * @return 休日出勤管理画面の初期化の情報
	 * 
	 * @throws Exception 
	 */
	@RequestMapping(value = INIT)
	public String initAttendanceOnHolidayRecord(Model model) throws Exception {
		
		// 休日勤務入力画面情報初期化
		AttendanceOnHolidayRecordForm form = initForm(AttendanceOnHolidayRecordForm.class);
		
		// 年度リストの取得
		form.setYearPeriodList(comService.getYearList(new Date()));
		// 当年度の取得
		form.setYearPeriod(CostDateUtils.getNowDate().substring(0, 4));

		// 氏名の取得
		form.setUserNameList(comService.getUserNameList(form.getUserId()));
		// 氏名の取得
		form.setUserName(form.getUserId());
		
		// 画面情報を設定する。
		recordService.searchAttendanceOnHolidayRecord(form);
		
		model.addAttribute(ATTENDANCE_ONHOLIDAY_RECORD_INFO, form);

		return ATTENDANCE_ONHOLIDAY_RECORD;

	}

	/**
	 * 休日出勤管理画面の検索処理
	 * 
	 * @param form
	 *            休日出勤管理画面情報
	 * @return 休日出勤管理画面
	 */
	@RequestMapping(value = SEARCH)
	public String searchAttendanceOnHolidayRecord(AttendanceOnHolidayRecordForm form) {
		
		// 画面情報を設定する。
		recordService.searchAttendanceOnHolidayRecord(form);
		
		System.out.println("休日出勤管理の情報検索メッソドを実行されました");

		return ATTENDANCE_ONHOLIDAY_RECORD;

	}

}
