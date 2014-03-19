package argo.cost.common.model;

import java.io.Serializable;

import argo.cost.common.model.entity.Users;

/**
 * <p>
 * セッション情報を表現します。
 * </p>
 *
 * @author COST argo Corporation.
 */
public class AppSession implements Serializable {

	private static final long serialVersionUID = 1;

	// ********************
	// ** フィールド **
	// ********************
	/**
	 * ユーザ情報
	 */
	private Users userInfo;

	/**
	 * 権限持てるフラグ
	 */
	private boolean authFlg;

	/**
	 * 画面更新フラグ
	 */
	private boolean updFlg;

	/**
	 * 当画面URL
	 */
	private String url;

	/**
	 * 当画面フォーム名
	 */
	private String form;

	/**
	 * ファイル名称
	 */
	private String fileName;

	/**
	 * エラーファイルパース
	 */
	private String errorFilePath;

	// ********************
	// **アクセスメッソド**
	// ********************
	/**
	 * ユーザ情報を取得します。
	 *
	 * @return ユーザ情報
	 */
	public Users getUserInfo() {

		return userInfo;
	}

	/**
	 * ユーザ情報を設定します。
	 *
	 * @param userInfo
	 *            ユーザ情報
	 */
	public void setUserInfo(Users userInfo) {

		this.userInfo = userInfo;
	}

	/**
	 * updFlgを取得します。
	 *
	 * @return 画面更新フラグ
	 */
	public boolean isUpdFlg() {

		return updFlg;
	}

	/**
	 * 画面更新フラグを設定します。
	 *
	 * @param updFlg
	 *            画面更新フラグ
	 */
	public void setUpdFlg(boolean updFlg) {

		this.updFlg = updFlg;
	}

	/**
	 * 権限持てるフラグを取得します。
	 *
	 * @return 権限持てるフラグ
	 */
	public boolean isAuthFlg() {

		return authFlg;
	}

	/**
	 * 権限持てるフラグを設定します。
	 *
	 * @param authFlg
	 *            権限持てるフラグ
	 */
	public void setAuthFlg(boolean authFlg) {

		this.authFlg = authFlg;
	}

	/**
	 * 当画面URLを取得します。
	 *
	 * @return 当画面URL
	 */
	public String getUrl() {

		return url;
	}

	/**
	 * 当画面URLを設定します。
	 *
	 * @param url
	 *            当画面URL
	 */
	public void setUrl(String url) {

		this.url = url;
	}

	/**
	 * 当画面フォーム名を取得します。
	 *
	 * @return 当画面フォーム名
	 */
	public String getForm() {

		return form;
	}

	/**
	 * 当画面フォーム名を設定します。
	 *
	 * @param form
	 *            当画面フォーム名
	 */
	public void setForm(String form) {

		this.form = form;
	}

	/**
	 * ファイル名称を取得します。
	 *
	 * @return ファイル名称
	 */
	public String getFileName() {

		return fileName;
	}

	/**
	 * ファイル名称を設定します。
	 *
	 * @return ファイル名称
	 */
	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	/**
	 * エラーファイルパースを取得します。
	 *
	 * @return エラーファイルパース
	 */
	public String getErrorFilePath() {

		return errorFilePath;
	}

	/**
	 * エラーファイルパースを設定します。
	 *
	 * @param errorFilePath
	 *            エラーファイルパース
	 */
	public void setErrorFilePath(String errorFilePath) {

		this.errorFilePath = errorFilePath;
	}

}
