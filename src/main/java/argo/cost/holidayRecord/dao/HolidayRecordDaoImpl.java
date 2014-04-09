package argo.cost.holidayRecord.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.holidayRecord.model.AbsenceVO;
import argo.cost.holidayRecord.model.PayHolidayVO;
import argo.cost.holidayRecord.model.SpecialHolidayVO;

@Repository
public class HolidayRecordDaoImpl implements HolidayRecordDao {

	/**
	 * 有給休暇情報取得
	 * 
	 * @param yearPeriod
	 *            年度
	 */
	@Override
	public List<PayHolidayVO> searchPayHolidayList(String yearPeriod) {
		// TODO ＤＢから有給休暇情報を取得
		List<PayHolidayVO> payHolidayList = new ArrayList<PayHolidayVO>();
		PayHolidayVO payHolidayVO = new PayHolidayVO();
		payHolidayVO.setPayHolidayDate("2013/5/1");
		payHolidayVO.setHolidayKbnName("全休");
		payHolidayVO.setDayQuantity("1");
		payHolidayVO.setHourQuantity("");
		payHolidayList.add(payHolidayVO);
		payHolidayVO = new PayHolidayVO();
		payHolidayVO.setPayHolidayDate("2013/5/2");
		payHolidayVO.setHolidayKbnName("全休");
		payHolidayVO.setDayQuantity("1");
		payHolidayVO.setHourQuantity("");
		payHolidayList.add(payHolidayVO);
		payHolidayVO = new PayHolidayVO();
		payHolidayVO.setPayHolidayDate("2013/5/13");
		payHolidayVO.setHolidayKbnName("半休");
		payHolidayVO.setDayQuantity("0.5");
		payHolidayVO.setHourQuantity("");
		payHolidayList.add(payHolidayVO);
		payHolidayVO = new PayHolidayVO();
		payHolidayVO.setPayHolidayDate("2013/5/30");
		payHolidayVO.setHolidayKbnName("時間");
		payHolidayVO.setDayQuantity("");
		payHolidayVO.setHourQuantity("2");
		payHolidayList.add(payHolidayVO);
		payHolidayVO = new PayHolidayVO();
		payHolidayVO.setPayHolidayDate("累計");
		payHolidayVO.setHolidayKbnName("");
		payHolidayVO.setDayQuantity("3.5日");
		payHolidayVO.setHourQuantity("2時間");
		payHolidayList.add(payHolidayVO);
		
		return payHolidayList;
	}

	/**
	 * 欠勤情報取得
	 * 
	 * @param yearPeriod
	 *            年度
	 */
	@Override
	public List<AbsenceVO> searchAbsenceList(String yearPeriod) {
		// TODO ＤＢから欠勤情報を取得
		List<AbsenceVO> absenceList = new ArrayList<AbsenceVO>();
		AbsenceVO absenceVO = new AbsenceVO();
		absenceVO.setAbsentDate("2013/4/1");
		absenceVO.setDayQuantity("1");
		absenceVO.setHourQuantity("");
		absenceList.add(absenceVO);
		absenceVO = new AbsenceVO();
		absenceVO.setAbsentDate("累計");
		absenceVO.setDayQuantity("1日");
		absenceVO.setHourQuantity("");
		absenceList.add(absenceVO);
		return absenceList;
	}
	
	/**
	 * 特別休暇情報取得
	 * 
	 * @param yearPeriod
	 *            年度
	 */
	@Override
	public List<SpecialHolidayVO> searchSpecialHolidayList(String yearPeriod) {
		// TODO 特別休暇情報を取得
		List<SpecialHolidayVO> specialHolidayList = new ArrayList<SpecialHolidayVO>();
		SpecialHolidayVO specialHolidayVO = new SpecialHolidayVO();
		specialHolidayVO.setSpecialHolidayDate("2013/4/25");
		specialHolidayVO.setDayQuantity("1");
		specialHolidayList.add(specialHolidayVO);
		specialHolidayVO = new SpecialHolidayVO();
		specialHolidayVO.setSpecialHolidayDate("2013/5/7");
		specialHolidayVO.setDayQuantity("1");
		specialHolidayList.add(specialHolidayVO);
		specialHolidayVO = new SpecialHolidayVO();
		specialHolidayVO.setSpecialHolidayDate("累計");
		specialHolidayVO.setDayQuantity("2日");
		specialHolidayList.add(specialHolidayVO);
		return specialHolidayList;
	}

}
