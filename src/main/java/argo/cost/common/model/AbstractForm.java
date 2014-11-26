package argo.cost.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 画面共通情報を記載します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public class AbstractForm implements Serializable {

	private static final long serialVersionUID = 1;

	// ********************
	// *****フィールド*****
	// ********************
	/**
	 * ユーザIDです。
	 */
	private String userId;
	/**
	 * 対象社員番号です。
	 */
	private String taishoUserId;
	/**
	 * 対象社員氏名です。
	 */
	private String taishoUserName;
	/**
	 * カンパニーコードです。
	 */
	private String cmpCd;

	/**
	 * アップロードファイルです。
	 */
	private MultipartFile file;

	/**
	 * 確認ダイアローグメッセージ内容リスト
	 */
	List<String> confirmMsgList;

	/**
	 * 完了区分です。
	 */
	private String finKbn;

	// ********************
	// **アクセスメッソド**
	// ********************
	/**
	 * ユーザIDを取得します。
	 *
	 * @return ユーザID
	 */
	public String getUserId() {

		return userId;
	}

	/**
	 * ユーザIDを設定します。
	 *
	 * @param ユーザID
	 */
	public void setUserId(String userId) {

		this.userId = userId;
	}
	/**
	 * 対象ユーザIDを取得します。
	 *
	 * @return 対象ユーザID
	 */
	public String getTaishoUserId() {
		return taishoUserId;
	}
	/**
	 * 対象ユーザIDを設定します。
	 *
	 * @param 対象ユーザID
	 */
	public void setTaishoUserId(String taishoUserId) {
		this.taishoUserId = taishoUserId;
	}
	/**
	 * 対象ユーザ氏名を取得します。
	 *
	 * @return 対象ユーザ氏名
	 */
	public String getTaishoUserName() {
		return taishoUserName;
	}
	/**
	 * 対象ユーザ氏名を設定します。
	 *
	 * @param 対象ユーザ氏名
	 */
	public void setTaishoUserName(String taishoUserName) {
		this.taishoUserName = taishoUserName;
	}

	/**
	 * カンパニーコードを取得します。
	 *
	 * @return カンパニーコード
	 */
	public String getCmpCd() {

		return cmpCd;
	}

	/**
	 * カンパニーコードを設定します。
	 *
	 * @param カンパニーコード
	 */
	public void setCmpCd(String cmpCd) {

		this.cmpCd = cmpCd;
	}

	/**
	 * 完了区分を取得します。
	 *
	 * @return 完了区分
	 */
	public String getFinKbn() {

		return finKbn;
	}

	/**
	 * 完了区分を設定します。
	 *
	 * @param 完了区分
	 */
	public void setFinKbn(String finKbn) {

		this.finKbn = finKbn;
	}

	/**
	 * アップロードファイルを取得します。
	 *
	 * @return アップロードファイル
	 */
	public MultipartFile getFile() {

		return file;
	}

	/**
	 * アップロードファイルを設定します。
	 *
	 * @param アップロードファイル
	 */
	public void setFile(MultipartFile file) {

		this.file = file;
	}

	/**
	 * 確認ダイアローグメッセージ内容リストを取得します。
	 *
	 * @return 確認ダイアローグメッセージ内容リスト
	 */
	public List<String> getConfirmMsgList() {

		return confirmMsgList;
	}

	/**
	 * 確認ダイアローグメッセージ内容リストを設定します。
	 *
	 * @param confirmMsgList
	 *            確認ダイアローグメッセージ内容リスト
	 */
	public void setConfirmMsgList(List<String> confirmMsgList) {

		this.confirmMsgList = confirmMsgList;
	}

	// ********************
	// ******共通処理******
	// ********************
	/**
	 * 確認メッセージを追加します。
	 *
	 * @param messageId
	 *            メッセージID
	 */
	public void putConfirmMsg(String messageId) {

		this.putConfirmMsg(messageId, null);
	}

	/**
	 * 確認メッセージを追加します。
	 *
	 * @param messageId
	 *            メッセージID
	 */
	public void putConfirmMsg(String messageId, String[] replacementValues) {

		if (this.confirmMsgList == null) {

			this.confirmMsgList = new ArrayList<String>();
		}

		Matcher m = Pattern.compile("\\｛(\\d)\\｝").matcher(messageId);
		while (m.find()) {
			messageId = messageId.replace(m.group(),
					replacementValues[Integer.parseInt(m.group(1))]);
		}

		this.confirmMsgList.add(messageId);
	}

	/**
	 * 確認メッセージを取得します。
	 *
	 * @return 確認メッセージ`内容
	 */
	public String getConfirmMsg() {

		if (this.confirmMsgList == null) {

			return "";
		}

		return this.confirmMsgList.get(0);
	}

	/**
	 * 確認メッセージをクリアします。
	 *
	 * @return 確認メッセージ`内容
	 */
	public void clearMessages() {

		this.confirmMsgList = null;
	}
	
}