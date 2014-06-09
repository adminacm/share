package argo.cost.setup.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.entity.ShiftInfo;
import argo.cost.common.entity.ShiftJikoku;
import argo.cost.common.entity.Users;
import argo.cost.common.model.UserVO;
import argo.cost.setup.model.SetupForm;

/**
 * 個人設定画面DAOの実現
 *
 * @author COST argo Corporation.
 */
@Repository
public class SetupDaoImpl implements SetupDao {
	
	/**
	 * 共通DAO
	 */
	@Autowired
	BaseDao baseDao;
	
	/**
	 * 個人設定情報を取得
	 * @param UserId
	 *           ユーザＩＤ
	 * @return
	 *        個人設定情報
	 */
	@Override
	public Users getSetupInfo(String userId) {
		
		BaseCondition personalInfoSelectCondition = new BaseCondition();
		personalInfoSelectCondition.addConditionEqual("users", baseDao.findById(userId, Users.class));
		
		Users users = (Users) baseDao.findResultList(personalInfoSelectCondition, Users.class);
	
		return users;
	}

	/**
	 * 代理入力者リストを取得
	 * 
	 * @return
	 *        代理入力者リスト
	 */
	@Override
	public List<UserVO> getAgentList() {
		
		// TODO 代理入力者未定、課題中
		List<UserVO> userList = new ArrayList<UserVO>();
		UserVO user = new UserVO();
		
		user.setUserName("曹文艶");
		user.setUserId("caowy");
		userList.add(user);
		
		user = new UserVO();
		user.setUserName("劉亜傑");
		user.setPassword("liuyj");
		userList.add(user);
		
		user = new UserVO();
		user.setUserName("熊燕玲");
		user.setPassword("xiongyl");
		userList.add(user);

		return userList;
	}

	/**
	 *ｼﾌﾄリストを取得
	 * 
	 * @return
	 *        ｼﾌﾄリスト
	 */
	@Override
	public List<ShiftInfo> getShiftList() {
		
		List<ShiftInfo> shiftInfoList = baseDao.findAll(ShiftInfo.class);
		
		return shiftInfoList;
	}
	
	/**
	 * ｼﾌﾄ時刻情報を取得
	 * 
	 * @param standardShift
	 * 					標準ｼﾌﾄ
	 * @return
	 */
	@Override
	public ShiftJikoku getshiftTime(String standardShift) {
		
		BaseCondition shiftStandardJikokuSelectbaseCondition = new BaseCondition();
		// 検索条件：定時出勤時刻
		shiftStandardJikokuSelectbaseCondition.addConditionEqual("teijiKinmuTime", "0900");
		// 検索条件：定時退勤時刻
		shiftStandardJikokuSelectbaseCondition.addConditionEqual("teijiTaikinTime", "0530");
		ShiftJikoku shiftStandardJikoku = (ShiftJikoku) baseDao.findResultList(shiftStandardJikokuSelectbaseCondition,ShiftJikoku.class);
		
		return shiftStandardJikoku;
	}

	/**
	 * 保存処理を実行
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 */
	@Override
	public void doSave(SetupForm setupInfo) {
		
		Users user = new Users();
		// 代理者ID
		user.setDairishaId(setupInfo.getAgentCd());
		// 標準シフト
		user.setStandardShiftCd(setupInfo.getStandardShift());
		// 勤務開始時間
		user.setKinmuStartTime(setupInfo.getWorkStart());
		// 勤務終了時間
		user.setKinmuEndTime(setupInfo.getWorkEnd());
		// 退職日
		user.setTaisyokuDate(setupInfo.getOutDate());
		baseDao.insert(user);
		
	}

}
