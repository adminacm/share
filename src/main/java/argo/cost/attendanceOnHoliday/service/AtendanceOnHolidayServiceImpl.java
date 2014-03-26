package argo.cost.attendanceOnHoliday.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import argo.cost.attendanceOnHoliday.dao.AtendanceOnHolidayDao;
import argo.cost.attendanceOnHoliday.dao.AtendanceOnHolidayDaoImpl;
import argo.cost.attendanceOnHoliday.model.AtendanceOnHoliday;
import argo.cost.attendanceOnHoliday.model.CodeNameMap;

@Service
public class AtendanceOnHolidayServiceImpl implements AtendanceOnHolidayService {
	
//	@PersistenceContext
//	EntityManager em = new EntityManagerImpl(null, null, null, false, null, null);

	@Autowired
	private AtendanceOnHolidayDao atendanceOnHolidayDao = new AtendanceOnHolidayDaoImpl();

	// 休日勤務入力画面のチェック処理
	@Override
	public boolean atendanceOnHolidayDataChk(String userId, String date) {

		return atendanceOnHolidayDao.atendanceOnHolidayDataChk(userId, date);

	}

	// 勤務日区分のプルダウンリストを取得
	@Override
	public ArrayList<CodeNameMap> atendanceDayKbnList() {
		// TODO Auto-generated method stub
		return atendanceOnHolidayDao.getAtendanceDayKbnList();
	}

	// プロジェクトのプルダウンリストを取得
	@Override
	public ArrayList<CodeNameMap> projectKbnList() {
		// TODO Auto-generated method stub
		return atendanceOnHolidayDao.getProjectKbnList();
	}

	// このユーザー当日の休日勤務情報を取得
	@Override
	public AtendanceOnHoliday atendanceOnHolidayDataGet(String userId,
			String date) {
		// TODO Auto-generated method stub
		return atendanceOnHolidayDao.atendanceOnHolidayDataGet(userId, date);
	}

	// 休日勤務入力画面の保存処理
	public String saveAtendanceOnHoliday(AtendanceOnHoliday atendanceOnHoliday, String UserID) {
		
		// TODO DBに保存する		
//		em.persist(atendanceOnHoliday);
		return atendanceOnHolidayDao.saveAtendanceOnHoliday(atendanceOnHoliday,UserID);
	}

	@Transactional
	public String deleteAtendanceOnHoliday(String strAtendanceDate,String userID) {
		
		// TODO DBで削除する		
//		AtendanceOnHoliday atendanceOnHoliday = em.find(AtendanceOnHoliday.class, strAtendanceDate);
		AtendanceOnHoliday atendanceOnHoliday = new AtendanceOnHoliday();
			
		if (null != atendanceOnHoliday) {
//			em.remove(atendanceOnHoliday);
//			return atendanceOnHolidayDao.deleteAtendanceOnHoliday(strAtendanceDate,userID);
			return "1";
		} else {
			return "0";
		}
		
	}

}
