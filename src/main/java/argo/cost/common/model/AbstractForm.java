package argo.cost.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public void putConfirmMsg(String messageId, Map<String, ?> paramMap) {

		if (this.confirmMsgList == null) {

			this.confirmMsgList = new ArrayList<String>();
		}

		// TODO
		this.confirmMsgList.add("");
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
}