package argo.cost.setup.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import argo.cost.common.model.UserInfo;
import argo.cost.common.model.entity.Shift;
import argo.cost.common.model.entity.ShiftTime;
import argo.cost.setup.model.SetupEntity;
import argo.cost.setup.model.SetupForm;

/**
 * 個人設定画面DAOの実現
 *
 * @author COST argo Corporation.
 */
@Repository
public class SetupDaoImpl implements SetupDao {
	
	/**
	 * 個人設定情報を取得
	 * @param UserId
	 *           ユーザＩＤ
	 * @return
	 *        個人設定情報
	 */
	@Override
	public SetupEntity getSetupInfo(String userId) {
		
		// 個人設定
		SetupEntity setup = new SetupEntity();
	
		// 代理入力者
		setup.setAgent("xiongyl");
		
		// 標準ｼﾌﾄ
		setup.setStandardShift("0900");
		
		// 勤務開始時刻
		setup.setWorkStart(900);
		
		// 勤務終了時刻
		setup.setWorkEnd(1730);
		
		// 入社日
		setup.setJoinDate("1997/4/1");
		
		// 休業開始日
		setup.setHolidayStart("2012/5/9");
		
		// 休業終了日
		setup.setHolidayEnd("2013/4/30");
		
		// 退職日
		setup.setOutDate("2013/8/30");

		return setup;
	}

	/**
	 * 代理入力者リストを取得
	 * 
	 * @return
	 *        代理入力者リスト
	 */
	@Override
	public List<UserInfo> getAgentList() {
		
		List<UserInfo> userList = new ArrayList<UserInfo>();
		UserInfo user = new UserInfo();
		
		user.setUserName("曹文艶");
		user.setUserId("caowy");
		userList.add(user);
		
		user = new UserInfo();
		user.setUserName("劉亜傑");
		user.setPassword("liuyj");
		userList.add(user);
		
		user = new UserInfo();
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
	public List<Shift> getShiftList() {
		
		List<Shift> shiftList = new ArrayList<Shift>();
		Shift shift = new Shift();
		
		shift.setShiftCd("0900");
		shiftList.add(shift);
		
		shift = new Shift();
		shift.setShiftCd("0930");
		shiftList.add(shift);
		
		shift = new Shift();
		shift.setShiftCd("0800");
		shiftList.add(shift);
		
		shift = new Shift();
		shift.setShiftCd("0830");
		shiftList.add(shift);
		
		shift = new Shift();
		shift.setShiftCd("1000");
		shiftList.add(shift);
		
		shift = new Shift();
		shift.setShiftCd("1030");
		shiftList.add(shift);
		
		shift = new Shift();
		shift.setShiftCd("1100");
		shiftList.add(shift);
		
		return shiftList;
	}
	
	/**
	 * ｼﾌﾄ時刻情報を取得
	 * 
	 * @param standardShift
	 * 					標準ｼﾌﾄ
	 * @return
	 */
	@Override
	public ShiftTime getshiftTime(String standardShift) {
		
		ShiftTime shiftTime = new ShiftTime();
		shiftTime.setStandSTime("0930");
		shiftTime.setStandEtime("1800");
		
		return shiftTime;
	}

	/**
	 * 保存処理を実行
	 * 
	 * @param setupInfo
	 *        個人設定情報
	 */
	@Override
	public void doSave(SetupForm setupInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
