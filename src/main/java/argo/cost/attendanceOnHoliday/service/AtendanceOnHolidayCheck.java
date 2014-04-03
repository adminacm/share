package argo.cost.attendanceOnHoliday.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import argo.cost.attendanceOnHoliday.model.AtendanceOnHolidayForm;
import argo.cost.common.utils.CostCheckUtil;

public class AtendanceOnHolidayCheck implements Validator {
	
	
//	public boolean checkAtendanceOnHoliday(AtendanceOnHoliday atendanceOnHoliday, Message message) {
		
//		// 勤務日区分が勤務日区分が「休日」「休日振替勤務」以外の場合
//		if (!("02".equals(atendanceOnHoliday.getSelectedAtendanceDayKbn())||"03".equals(atendanceOnHoliday.getSelectedAtendanceDayKbn()))) {
//			
//			message.setMessageID(messageID);
//			message.setMessgetTxt("勤務区分の値が不正です");
//			return false;
//			
//		}
//		// 勤務開始時刻が未入力の場合
//		if (CheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
//			message.setMessageID(messageID);
//		    message.setMessgetTxt("勤務開始時刻が未入力です");
//		    return false;
//		}
//		
//		// 勤務開始時刻のhhnn形式値が数値以外の場合
//		if (CheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
//			
//			
//			message.setMessageID(messageID);
//			message.setMessgetTxt("勤務開始時刻を正しく入力してください");
//			return false;
//		}
//		// 勤務開始時刻の分の値が0、30以外の場合
//		if (CheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
//			
//			message.setMessageID(messageID);
//			message.setMessgetTxt("勤務開始時刻の分の値が0、30以外");
//			return false;
//		}
//		
//		// 勤務終了時刻が未入力の場合
//		if (CheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
//			message.setMessageID(messageID);
//		    message.setMessgetTxt("勤務終了時刻が未入力です");
//		    return false;
//		}
//		
//		// 勤務終了時刻のhhnn形式値が数値以外の場合
//		if (CheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
//			
//			
//			message.setMessageID(messageID);
//			message.setMessgetTxt("勤務終了時刻を正しく入力してください");
//			return false;
//		}
//		// 勤務終了時刻の分の値が0、30以外の場合
//		if (CheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
//			
//			message.setMessageID(messageID);
//			message.setMessgetTxt("勤務終了時刻の分の値が0、30以外");
//			return false;
//		}
//		// TODO 勤務開始時刻～勤務終了時刻が２４時間を越えている場合
//		if ()
//		// 勤務区分が「休日振替勤務」で振替休日が未入力の場合
//		if ("03".equals(atendanceOnHoliday.getSelectedAtendanceDayKbn()) && CheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrHurikaeDate())) {
//			message.setMessageID(messageID);
//			message.setMessgetTxt("振替休日を入力してください");
//			return false;
//		}
//		// 勤務区分が「休日振替勤務」で振替休日に日付以外が入力されている場合
//		// 勤務区分が「休日振替勤務」で振替休日が当日の日付
//		if ("03".equals(atendanceOnHoliday.getSelectedAtendanceDayKbn()) && CheckUtil.compareToSystemDate(atendanceOnHoliday.getStrHurikaeDate())) {
//			
//			message.setMessageID(messageID);
//			message.setMessgetTxt("振替休日を入力してください");
//			return false;
//		}
//		// 勤務区分が「休日振替勤務」で振替休日の日付の就業データが処理済の場合
//		// 勤務区分が「休日振替勤務」以外で振替休日が入力されている
//		if ((!"03".equals(atendanceOnHoliday.getSelectedAtendanceDayKbn())) && CheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrHurikaeDate())) {
//			message.setMessageID(messageID);
//			message.setMessgetTxt("振替休日が当日の日付です");
//			return false;
//		}
//		// プロジェクト名未選択の場合
//		if (CheckUtil.checkNullOrBlank(atendanceOnHoliday.getSelectedProjCd())) {
//			message.setMessageID(messageID);
//			message.setMessgetTxt("プロジェクト名を選択してください");
//			return false;
//		}
//		// プロジェクト名未入力の場合
//	    if (CheckUtil.checkNullOrBlank(atendanceOnHoliday.getSelectedProjCd())) {
//			message.setMessageID(messageID);
//			message.setMessgetTxt("業務内容を入力してください");
//			return false;
//		}
//		
//		return true;
//		
//		
//	}

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(AtendanceOnHolidayForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		// 必須チェック
	    ValidationUtils.rejectIfEmpty(
	      errors, "strAtendanceTimeStat", "errors.required",new Object[]{"勤務開始時刻"}, "this field is required!");
	    ValidationUtils.rejectIfEmpty(
	  	      errors, "strAtendanceTimeEnd", "errors.required",new Object[]{"勤務終了時刻"}, "this field is required!");
	    ValidationUtils.rejectIfEmpty(
	  	      errors, "selectedProjCd", "errors.required",new Object[]{"プロジェクト名"}, "this field is required!");
	    ValidationUtils.rejectIfEmpty(
		  	      errors, "strCommont", "errors.required",new Object[]{"業務内容"}, "this field is required!");
	    AtendanceOnHolidayForm atendanceOnHoliday = (AtendanceOnHolidayForm) target;
	    
	    // 勤務開始時刻チェック
	    String strAtendanceTimeStat = atendanceOnHoliday.getStrAtendanceTimeStat();
	    if (!CostCheckUtil.checkNullOrBlank(strAtendanceTimeStat)) {
	      SimpleDateFormat formatter = new SimpleDateFormat("hhhh");
	      formatter.setLenient(false);
	      try {
	           formatter.parse(strAtendanceTimeStat);
	      } catch(ParseException e) {
	        errors.rejectValue("date", "errors.date", new Object[]{"勤務開始時刻"}, "this field is not a date");
	      }      
	    }
	    // 勤務終了時刻チェック
	    String strAtendanceTimeEnd = atendanceOnHoliday.getStrAtendanceTimeEnd();
	    if (!CostCheckUtil.checkNullOrBlank(strAtendanceTimeEnd)) {
		      SimpleDateFormat formatter = new SimpleDateFormat("hhhh");
		      formatter.setLenient(false);
		      try {
		           formatter.parse(strAtendanceTimeEnd);
		      } catch(ParseException e) {
		        errors.rejectValue("date", "errors.date", new Object[]{"勤務終了時刻"}, "this field is not a date");
		      }      
		    }
	    
	    // 他の相関チェック
		// 勤務日区分が勤務日区分が「休日」「休日振替勤務」以外の場合
		if (!("02".equals(atendanceOnHoliday.getSelectedAtendanceDayKbn())||"03".equals(atendanceOnHoliday.getSelectedAtendanceDayKbn()))) {
			errors.rejectValue("selectedAtendanceDayKbn", "errors.selectedAtendanceDayKbn", "勤務区分の値が不正です");
			
		}
	    
		// TODO　勤務開始時刻の分の値が0、30以外の場合
		if (CostCheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
			errors.rejectValue("strAtendanceTimeStat", "errors.strAtendanceTimeStat", "勤務終了時刻は30分単位で入力してください");
			
		}
		
		// TODO　勤務終了時刻が勤務開始時刻以前の時刻
		if (CostCheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
			errors.rejectValue("strAtendanceTimeStat", "errors.strAtendanceTimeStat", "勤務終了時刻は勤務開始時刻より後の時刻を入力してください");
					
		}
		// TODO　勤務終了時刻の分の値が0、30以外の場合		
		if (CostCheckUtil.checkNullOrBlank(atendanceOnHoliday.getStrAtendanceTimeStat())) {
			errors.rejectValue("strAtendanceTimeEnd", "errors.strAtendanceTimeEnd", "勤務終了時刻は30分単位で入力してください");
			
			}
	    
		
	}
		
		// TODO DBに保存する		
		
	

}
