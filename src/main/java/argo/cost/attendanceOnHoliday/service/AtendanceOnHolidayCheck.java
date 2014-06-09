package argo.cost.attendanceOnHoliday.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.constant.CommonConstant;
import argo.cost.common.utils.CostDateUtils;

public class AtendanceOnHolidayCheck implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(AtendanceOnHolidayForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		// 必須チェック
	    ValidationUtils.rejectIfEmpty(
	      errors, "strAtendanceTimeStat", "E001",new Object[]{"勤務開始時刻"}, "{0}が未入力です！");
	    ValidationUtils.rejectIfEmpty(
	  	      errors, "strAtendanceTimeEnd", "E001",new Object[]{"勤務終了時刻"}, "{0}が未入力です！");
	    ValidationUtils.rejectIfEmpty(
	  	      errors, "selectedProjCd", "E002",new Object[]{"プロジェクト名"}, "{0}を選択してください！");
	    ValidationUtils.rejectIfEmpty(
		  	      errors, "strCommont", "E001",new Object[]{"業務内容"}, "{0}が未入力です！");
	    AtendanceOnHolidayForm atendanceOnHoliday = (AtendanceOnHolidayForm) target;
	    
	    // 必須チェックでエラーはないの場合
	    if (!errors.hasErrors()) {
	    	// 勤務開始時刻チェック　
	    	// TODO:時刻のチェックメソッドはチェック不能「09:3a」
		    String strAtendanceTimeStat = atendanceOnHoliday.getStrAtendanceTimeStat();
		    if (!CostDateUtils.isTimeHHnn(strAtendanceTimeStat)) {
		    	errors.rejectValue("strAtendanceTimeStat", "E003", new Object[]{"勤務開始時刻"}, "{0}を正しく入力してください");
		    } else if (!CostDateUtils.isHalfHour(strAtendanceTimeStat)) {
		    	errors.rejectValue("strAtendanceTimeStat", "E004", new Object[]{"勤務開始時刻"}, "{0}は30分単位で入力してください");
		    }
		    // 勤務終了時刻チェック
		    String strAtendanceTimeEnd = atendanceOnHoliday.getStrAtendanceTimeEnd();
		    if (!CostDateUtils.isTimeHHnn(strAtendanceTimeEnd)) {
		    	errors.rejectValue("strAtendanceTimeEnd", "E003", new Object[]{"勤務終了時刻"}, "{0}を正しく入力してください");
		    } else if (!CostDateUtils.isHalfHour(strAtendanceTimeEnd)) {
		    	errors.rejectValue("strAtendanceTimeEnd", "E004", new Object[]{"勤務終了時刻"}, "{0}は30分単位で入力してください");
		    }
		    
		    // 他の相関チェック
		    // 勤務終了時刻が勤務開始時刻以前の時刻
		    if (strAtendanceTimeEnd.compareTo(strAtendanceTimeStat) < 0) {
		    	errors.rejectValue("strAtendanceTimeEnd", "er1111", new Object[]{"勤務終了時刻","勤務開始時刻"}, "{0}は{1}より後の時刻を入力してください");
		    }
		    
			// 勤務区分が「休日振替勤務」で
	    	String hurikaeDate = atendanceOnHoliday.getStrHurikaeDate();
		    if (StringUtils.equals("03", atendanceOnHoliday.getSelectedAtendanceDayKbn())) {
		    	// 振替休日が未入力
		    	if (StringUtils.isEmpty(hurikaeDate)) {
		    		errors.rejectValue("strHurikaeDate", "er1118", new Object[]{"振替休日"}, "{0}を入力してください");
		    	// 振替休日に日付以外が入力されている
		    	} else if (!CostDateUtils.isValidDate(hurikaeDate, CommonConstant.YYYY_MM_DD)) {
		    		errors.rejectValue("strAtendanceTimeStat", "E003", new Object[]{"振替休日"}, "{0}を正しく入力してください");
		    	// 振替休日が当日の日付
		    	} else if (StringUtils.equals(hurikaeDate, atendanceOnHoliday.getStrAtendanceDate())) {
		    		errors.rejectValue("strAtendanceTimeStat", "E006", new Object[]{"振替休日"}, "{0}が当日の日付です");
		    	}
		    	
		    } else {
		    	if (!StringUtils.isEmpty(hurikaeDate)) {
		    		errors.rejectValue("strHurikaeDate", "E007", new Object[]{"休日振替勤務","振替休日"}, "{0}以外のときは{1}は入力できません");
		    	// 振替休日に日付以外が入力されている
		    	}
		    }
	    }
	   
	    
		
	}
		

}
