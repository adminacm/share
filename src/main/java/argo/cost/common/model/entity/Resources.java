package argo.cost.common.model.entity;

/**
 * <p>
 * 資源情報を表現します。
 * </p>
 *
 */
public class Resources {

	/**
	 * 資源ID
	 */
	private String id;

	/**
	 * 資源URL
	 */
	private String url;

	/**
	 * 資源Memo
	 */
	private String memo;

	/**
	 * 資源タイプ
	 */
	private Integer type;

	/**
	 * 資源名
	 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
