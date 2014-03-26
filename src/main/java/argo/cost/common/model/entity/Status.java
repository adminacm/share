package argo.cost.common.model.entity;

import java.io.Serializable;

/**
 * <p>
 * 状況テーブル情報を表現します。
 * </p>
 *
 */
public class Status implements Serializable {

	private static final long serialVersionUID = 1;
	
	/**
	 * 状況
	 */
	private String statusCode;
	
	/**
	 * 状況名
	 */
	private String statusName;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	
}
