package argo.cost.attendanceInput.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import argo.cost.attendanceInput.model.AttendanceInputForm;
import argo.cost.attendanceInput.model.AttendanceProject;
import argo.cost.attendanceInput.service.AttendanceInputService;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.controller.AbstractController;
import argo.cost.common.model.ListItem;
import argo.cost.common.utils.CostDateUtils;

@Controller
@RequestMapping("/attendanceInput")
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
    @RequestMapping("/init")
    public String init(Model model) throws Exception {
    	
    	// フォーム初期化
    	AttendanceInputForm form = initForm(AttendanceInputForm.class);
    	
		// セッション情報設定
		getSession().setUrl("attendanceInput.jsp");
		
    	// 画面へ設定します。
    	model.addAttribute(form);
    	
		// システム日付を取得
		String date = CostDateUtils.getNowDate();

		// 勤怠日付を設定
		form.setAttDate(date);
		
		String attDate = CostDateUtils.formatDate(date, CommonConstant.YYYYMMDD_KANJI);
		
		// 曜日名
		String week = CostDateUtils.getWeekOfDate(CostDateUtils.toDate(date));
		
		// 勤怠日付（表示）を設定
		form.setAttDate(attDate.concat("（").concat(week).concat("）"));
		
		// 休暇欠勤区分リストを取得
		List<ListItem> kyukakbList = attService.getHolidayLackingItem();
		form.setKyukakbList(kyukakbList);
		// ユーザID
		String userId = getSession().getUserInfo().getId();
		// 個人倦怠プロジェクト情報リストを作成
		List<AttendanceProject> prjList = new ArrayList<AttendanceProject>();
		AttendanceProject prj = null;
		for (int i = 0; i < 5; i++) {
			
			prj = new AttendanceProject();
			
			// プロジェクト情報
			prj.setProjectItemList(comService.getProjectNameList(userId, CostDateUtils.toDate(date)));
			// 作業リスト情報
			prj.setWorkItemList(attService.getWorkItemList());
			prjList.add(prj);
		}
		
		form.setProjectList(prjList);
    	
		// ロケーション情報設定
		form.setLocationId("02");
		form.setLocationItemList(attService.getLocationItemList());
        return "attendanceInput";
    }

}
