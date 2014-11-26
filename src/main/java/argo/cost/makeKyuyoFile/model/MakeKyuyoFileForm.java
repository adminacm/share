package argo.cost.makeKyuyoFile.model;

import java.io.Serializable;
import java.util.List;

import argo.cost.common.model.AbstractForm;

public class MakeKyuyoFileForm extends AbstractForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 検索条件：処理年月
	 */
	String dealYearMonth;
	/**
	 * 作成したファイル一覧
	 */
	List<MakeKyuyoFileIchiranVO> makeKyuyoFileIchiranList;
	public String getDealYearMonth() {
		return dealYearMonth;
	}
	public void setDealYearMonth(String dealYearMonth) {
		this.dealYearMonth = dealYearMonth;
	}
	public List<MakeKyuyoFileIchiranVO> getMakeKyuyoFileIchiranList() {
		return makeKyuyoFileIchiranList;
	}
	public void setMakeKyuyoFileIchiranList(
			List<MakeKyuyoFileIchiranVO> makeKyuyoFileIchiranList) {
		this.makeKyuyoFileIchiranList = makeKyuyoFileIchiranList;
	}
	
	
	
	
}
