package argo.cost.holidayRecord.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.common.constant.UrlConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.utils.CostDateUtils;
import argo.cost.holidayRecord.model.HolidayRecordForm;
import argo.cost.holidayRecord.service.HolidayRecordService;

/**
 * <p>
 * 休暇管理画面業務クラス
 * </p>
 *
 * @author COST argo Corporation.
 */
@Controller
@RequestMapping(UrlConstant.URL_HOLIDAY_RECORD)
@SessionAttributes(types = {HolidayRecordForm.class})
public class HolidayRecordController extends AbstractController {

	/**
	 * 休暇管理サービス
	 */
	@Autowired
	protected HolidayRecordService holidayRecordService;

	/**
	 * 休暇管理画面ID
	 */
	private static final String HOLIDAY_RECORD = "holidayRecord";

	/**
	 * 休暇管理画面の初期化処理
	 * 
	 * @param model
	 *             画面情報モデル
	 * @return 休暇管理画面
	 * 
	 * @throws Exception
	 *                  異常
	 */
	@RequestMapping(value = INIT)
	public String initHolidayRecord(Model model) throws Exception {
		
		// 休日勤務入力画面情報初期化
		HolidayRecordForm form = initForm(HolidayRecordForm.class);
		// 年度リストをセット
		form.setYearPeriodList(comService.getYearList(new Date()));
		// 初期値に当年度をセット
		form.setYearPeriod(CostDateUtils.getNowDate().substring(0, 4));
		
		// 画面情報を設定
		holidayRecordService.setHolidayRecordInfo(form);
		model.addAttribute(form);

		// 休暇管理画面を戻り
		return HOLIDAY_RECORD;
	}

	/**
	 * 休暇管理画面の検索処理
	 * 
	 * @param form
	 *            休暇管理画面情報
	 * @return 休暇管理画面
	 * @throws ParseException 
	 */
	@RequestMapping(value = SEARCH)
	public String searchHolidayRecord(HolidayRecordForm form) throws ParseException {
		
		// 画面情報を設定する。
		holidayRecordService.setHolidayRecordInfo(form);

		// 休暇管理画面を戻り
		return HOLIDAY_RECORD;
	}
}
