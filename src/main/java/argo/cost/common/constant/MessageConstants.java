package argo.cost.common.constant;

/**
 * 原価管理メッセージ共通定数 <br />
 * 
 * @author COST argo Corporation.
 */
public class MessageConstants {
	
	// ############
	// ### 定数 ###
	// ############
	
	/**
	 *　｛0｝が未入力です！
	 */
	public static final String COSE_E_001 = "｛0｝が未入力です";
	/**
	 *　｛0｝を正しく入力してください
	 */
	public static final String COSE_E_002 = "｛0｝を正しく入力してください";
	/**
	 *　終日休暇の日は勤務できません
	 */
	public static final String COSE_E_003 = "終日休暇の日は勤務できません";
	/**
	 *　｛0｝を入力してください
	 */
	public static final String COSE_E_004 = "｛0｝を入力してください";
	/**
	 *　｛0｝が設定されていません
	 */
	public static final String COSE_E_005 = "｛0｝が設定されていません";
	/**
	 *　勤務開始時刻・終了時刻は両方入力してください
	 */
	public static final String COSE_E_006 = "勤務開始時刻・終了時刻は両方入力してください";
	/**
	 *　｛0｝は30分単位で入力してください
	 */
	public static final String COSE_E_007 = "｛0｝は30分単位で入力してください";
	/**
	 *　勤務開始時刻は定時勤務時間内の時刻を入力してください
	 */
	public static final String COSE_E_008 = "勤務開始時刻は定時勤務時間内の時刻を入力してください";
	/**
	 *　勤務終了時刻は勤務開始時刻より後の時刻を入力してください
	 */
	public static final String COSE_E_009 = "勤務終了時刻は勤務開始時刻より後の時刻を入力してください";
	/**
	 *　1日に24時間を超える勤務は入力できません
	 */
	public static final String COSE_E_010 = "1日に24時間を超える勤務は入力できません";
	/**
	 *　定時時間帯の勤務時間数が7.5h未満です。休暇区分も入力してください
	 */
	public static final String COSE_E_011 = "定時時間帯の勤務時間数が｛0｝未満です。休暇区分も入力してください";
	/**
	 *　正しい｛0｝を入力してください
	 */
	public static final String COSE_E_012 = "正しい｛0｝を入力してください";
	/**
	 *　休暇欠勤区分が入力されています
	 */
	public static final String COSE_E_013 = "休暇欠勤区分が入力されています";
	/**
	 *　休日の勤務開始は定時出勤時刻を入力してください
	 */
	public static final String COSE_E_014 = "休日の勤務開始は定時出勤時刻を入力してください";
	/**
	 *　取得できる代休はありません
	 */
	public static final String COSE_E_015 = "取得できる代休はありません";
	/**
	 *　有給休暇の取得限度を超えています
	 */
	public static final String COSE_E_016 = "有給休暇の取得限度を超えています";
	/**
	 *　｛0｝には日付を入力してください
	 */
	public static final String COSE_E_017 = "｛0｝には日付を入力してください";
	/**
	 *　｛0｝が当日の日付です
	 */
	public static final String COSE_E_018 = "｛0｝が当日の日付です";
	/**
	 *　休日振替勤務以外のときは振替休日は入力できません
	 */
	public static final String COSE_E_019 = "休日振替勤務以外のときは振替休日は入力できません";
	/**
	 *　｛0｝が入力範囲を超えています
	 */
	public static final String COSE_E_020 = "｛0｝が入力範囲を超えています";
	/**
	 *　｛0｝を選択してください
	 */
	public static final String COSE_E_021 = "｛0｝を選択してください";
	/**
	 *　｛0｝の値が不正です
	 */
	public static final String COSE_E_022 = "｛0｝の値が不正です";
	/**
	 *　日付：｛0｝で取得できる代休はありません
	 */
	public static final String COSE_E_023 = "日付：｛0｝取得できる代休はありません";
	/**
	 *　休日：｛0｝で休日勤務予定が存在する、勤怠情報を入力ください
	 */
	public static final String COSE_E_024 = "休日：｛0｝で休日勤務予定が存在する、勤怠情報を入力ください";
	/**
	 *　作業時間数の合計が勤務時間数と一致していません
	 */
	public static final String COSE_E_025 = "作業時間数の合計が勤務時間数と一致していません";
	/**
	 *　作業時間数の合計が勤務時間数と一致していません
	 */
	public static final String COSE_E_026 = "休業期間中と退職後の日付には勤怠を入力できません";
	/**
	 *　勤怠実績が存在する、休日勤務情報を削除しないてください
	 */
	public static final String COSE_E_027 = "勤怠実績が存在する、休日勤務情報を削除しないてください";
	/**
	 *　勤怠実績が存在する、振替日を指定できません
	 */
	public static final String COSE_E_028 = "勤怠実績が存在する、振替日を指定できません";
	/**
	 *　｛0｝の勤怠情報を入力ください！
	 */
	public static final String COSE_E_1103 = "｛0｝の勤怠情報を入力ください";
	
	/**
	 *　未承認の月報が存在します。処理を続けますか？
	 */
	public static final String COSE_E_1104 = "未承認の月報が存在します。処理を続けますか";
	/**
	 *　有給休暇が余分に取得されています
	 */
	public static final String COSE_W_1101 = "有給休暇が余分に取得されています";
	/**
	 *　勤務開始時刻が前日の勤務終了時刻と同時刻です
	 */
	public static final String COSE_W_1102 = "勤務開始時刻が前日の勤務終了時刻と同時刻です";
	
	/**
	 *　｛0｝は入社日よりあとの日付を入力してください
	 */
	public static final String COSE_E_1105 = "｛0｝は入社日よりあとの日付を入力してください";
	
	/**
	 *　休業終了日は休業開始日よりあとの日付を入力してください
	 */
	public static final String COSE_E_1106 = "休業終了日は休業開始日よりあとの日付を入力してください";
	
	/**
	 *　休業終了日は休業開始日よりあとの日付を入力してください
	 */
	public static final String COSE_E_1107 = "勤怠提出後の日付を休業期間や退職日には設定はできません。";

}
