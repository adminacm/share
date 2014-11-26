package argo.cost.makeKyuyoFile.model;

import java.io.Serializable;

import argo.cost.common.model.AbstractForm;

public class MakeKyuyoFileIchiranVO extends AbstractForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ファイル名
	 */
	String madeKyuyoFileName;
	/**
	 * 処理年月
	 */
	String dealYearMonth;
	/**
	 * 作成者
	 */
	String createdUserName;
	/**
	 * 作成日時
	 */
	String createdDateTime;
	public String getMadeKyuyoFileName() {
		return madeKyuyoFileName;
	}
	public void setMadeKyuyoFileName(String madeKyuyoFileName) {
		this.madeKyuyoFileName = madeKyuyoFileName;
	}
	public String getDealYearMonth() {
		return dealYearMonth;
	}
	public void setDealYearMonth(String dealYearMonth) {
		this.dealYearMonth = dealYearMonth;
	}
	public String getCreatedUserName() {
		return createdUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	public String getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	
	
}
