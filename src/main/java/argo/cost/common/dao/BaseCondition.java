package argo.cost.common.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * 単一テーブル操作の条件設定クラス <br />
 * 単一テーブル操作の条件を設定する。
 * 
 */
public class BaseCondition {


	// ##################
	// ### フィールド ###
	// ##################
	/**
	 * 検索条件
	 */
	private StringBuilder conditonSql = null;
	/**
	 * オーダー条件
	 */
	private StringBuilder orderSql = null;
	/**
	 * 検索条件項目
	 */
	private Map<String, Object> conditionMap = null;
	

	// ########################
	// ### アクセサメソッド ###
	// ########################
	/**
	 * @return 検索条件を取得する
	 */
	public StringBuilder getConditonSql() {

		return conditonSql;
	}

	/**
	 * @return オーダー条件を取得する
	 */
	public StringBuilder getOrderSql() {

		return orderSql;
	}

	/**
	 * @return 検索条件項目を取得する
	 */
	public Map<String, Object> getConditionMap() {

		return conditionMap;
	}
	

	// ######################
	// ### コンストラクタ ###
	// ######################
	/**
	 * コンストラクタ
	 */
	public BaseCondition() {

		conditonSql = new StringBuilder();
		orderSql = new StringBuilder();
		conditionMap = new HashMap<String, Object>();
	}
	

	// ######################
	// ### Publicメソッド ###
	// ######################
	/**
	 * 検索条件形式
	 * 
	 * @author fast
	 * 
	 */
	public enum Joken {
		EQUAL, NOT_EQAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL_THAN, LESS_EQUAL_THAN, IS_NULL, IS_NOT_NULL, IS_EMPTY, IS_NOT_EMPTY
	}
	

	/**
	 * 検索条件設定する（等しい）。
	 * 
	 * @param column
	 *            条件列名
	 * @param val
	 *            条件値
	 */
	public void addConditionEqual(String column, Object val) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		String paramKey = "PARAM_KEY_EQUAL_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKey, val);
		conditonSql.append("t.").append(column).append(" = :").append(paramKey);
	}

	/**
	 * 検索条件設定する（等しい以外）。
	 * 
	 * @param column
	 *            条件列名
	 * @param val
	 *            条件値
	 */
	public void addConditionNotEqual(String column, Object val) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		String paramKey = "PARAM_KEY_NOT_EQUAL_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKey, val);
		conditonSql.append("t.").append(column).append(" <> :").append(paramKey);
	}

	/**
	 * 検索条件設定する（大きい）。
	 * 
	 * @param column
	 *            条件列名
	 * @param val
	 *            条件値
	 */
	public void addConditionGreaterThan(String column, Object val) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		String paramKey = "PARAM_KEY_GREATER_THAN_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKey, val);
		conditonSql.append("t.").append(column).append(" > :").append(paramKey);
	}

	/**
	 * 検索条件設定する（小さい）。
	 * 
	 * @param column
	 *            条件列名
	 * @param val
	 *            条件値
	 */
	public void addConditionLessThan(String column, Object val) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		String paramKey = "PARAM_KEY_LESS_THAN_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKey, val);
		conditonSql.append("t.").append(column).append(" < :").append(paramKey);
	}

	/**
	 * 検索条件設定する（大きい　または　等しい）。
	 * 
	 * @param column
	 *            条件列名
	 * @param val
	 *            条件値
	 */
	public void addConditionGreaterEqualThan(String column, Object val) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		String paramKey = "PARAM_KEY_GREATER_EQUAL_THAN_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKey, val);
		conditonSql.append("t.").append(column).append(" >= :").append(paramKey);
	}

	/**
	 * 検索条件設定する（小さい　または　等しい）。
	 * 
	 * @param column
	 *            条件列名
	 * @param val
	 *            条件値
	 */
	public void addConditionLessEqualThan(String column, Object val) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		String paramKey = "PARAM_KEY_LESS_EQUAL_THAN_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKey, val);
		conditonSql.append("t.").append(column).append(" <= :").append(paramKey);
	}

	/**
	 * 検索条件設定する（NULL）。
	 * 
	 * @param column
	 *            条件列名
	 */
	public void addConditionIsNull(String column) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		conditonSql.append("t.").append(column).append(" IS NULL ");
	}

	/**
	 * 検索条件設定する（非NULL）。
	 * 
	 * @param column
	 *            条件列名
	 */
	public void addConditionIsNotNull(String column) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		conditonSql.append("t.").append(column).append(" IS NOT NULL ");
	}

	/**
	 * 検索条件設定する（区間内）。
	 * 
	 * @param column
	 *            条件列名
	 * @param valStart
	 *            条件値開始
	 * @param valEnd
	 *            条件値終了
	 */
	public void addConditionBetween(String column, Object valStart, Object valEnd) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		String paramKeyStart = "PARAM_KEY_BETWEEN_START_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKeyStart, valStart);
		conditonSql.append("t.").append(column).append(" BETWEEN :").append(paramKeyStart);

		String paramKeyEnd = "PARAM_KEY_BETWEEN_END_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKeyEnd, valEnd);
		conditonSql.append(" AND :").append(paramKeyEnd);
	}

	/**
	 * 検索条件設定する（範囲内）。
	 * 
	 * @param column
	 *            条件列名
	 * @param vals
	 *            条件値
	 */
	public void addConditionIn(String column, Object[] vals) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		int index = 0;
		conditonSql.append("t.").append(column).append(" IN ( ");
		for (int i = 0; i < vals.length; i++) {
			if (index > 0) {
				conditonSql.append(",");
			}

			String paramKey = "PARAM_KEY_IN_" + column.replaceAll("\\.", "_") + i;
			conditionMap.put(paramKey, vals[i]);
			conditonSql.append(":").append(paramKey);

			index++;
		}
		conditonSql.append(" )");
	}

	/**
	 * 検索条件設定する（範囲外）。
	 * 
	 * @param column
	 *            条件列名
	 * @param vals
	 *            条件値
	 */
	public void addConditionNotIn(String column, Object[] vals) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		int index = 0;
		conditonSql.append("t.").append(column).append(" NOT IN ( ");
		for (int i = 0; i < vals.length; i++) {
			if (index > 0) {
				conditonSql.append(",");
			}

			String paramKey = "PARAM_KEY_NOT_IN_" + column.replaceAll("\\.", "_") + i;
			conditionMap.put(paramKey, vals[i]);
			conditonSql.append(":").append(paramKey);

			index++;
		}
		conditonSql.append(" )");
	}

	/**
	 * 検索条件設定する（または）。
	 * 
	 * @param columns
	 *            条件列名
	 * @param vals
	 *            条件値
	 * @param ken
	 *            条件形式
	 */
	public void addConditionOr(String[] columns, Object[] vals, Joken[] kens) {

		if (columns == null || columns.length == 0) {
			return;
		}

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}
		conditonSql.append(" (");

		int index = 0;
		for (int i = 0; i < columns.length; i++) {

			if (index > 0) {
				conditonSql.append(" OR ");
			}

			String paramKey = "PARAM_KEY_OR_" + columns[i].replaceAll("\\.", "_") + i;

			conditonSql.append("t.").append(columns[i]);

			switch (kens[i]) {
				case EQUAL:
					conditionMap.put(paramKey, vals[i]);
					conditonSql.append(" = :").append(paramKey);
					break;
				case NOT_EQAL:
					conditionMap.put(paramKey, vals[i]);
					conditonSql.append(" <> :").append(paramKey);
					break;
				case GREATER_THAN:
					conditionMap.put(paramKey, vals[i]);
					conditonSql.append(" > :").append(paramKey);
					break;
				case LESS_THAN:
					conditionMap.put(paramKey, vals[i]);
					conditonSql.append(" < :").append(paramKey);
					break;
				case GREATER_EQUAL_THAN:
					conditionMap.put(paramKey, vals[i]);
					conditonSql.append(" >= :").append(paramKey);
					break;
				case LESS_EQUAL_THAN:
					conditionMap.put(paramKey, vals[i]);
					conditonSql.append(" <= :").append(paramKey);
					break;
				case IS_NULL:
					conditonSql.append(" IS NULL");
					break;
				case IS_NOT_NULL:
					conditonSql.append(" IS NOT NULL");
					break;
				case IS_EMPTY:
					conditonSql.append(" IS EMPTY");
					break;
				case IS_NOT_EMPTY:
					conditonSql.append(" IS NOT EMPTY");
					break;
				default:
					break;
			}

			index++;
		}
		conditonSql.append(" ) ");
	}

	/**
	 * 検索条件設定する（LIKE）。
	 * 
	 * @param column
	 *            条件列名
	 * @param val
	 *            条件値
	 */
	public void addConditionLike(String column, Object val) {

		if (conditonSql.length() > 0) {
			conditonSql.append(" AND ");
		}

		String paramKey = "PARAM_KEY_LESS_EQUAL_THAN_" + column.replaceAll("\\.", "_");
		conditionMap.put(paramKey, val);
		conditonSql.append("t.").append(column).append(" LIKE :").append(paramKey);
	}

	/**
	 * オーダー条件設定する（昇順）。
	 * 
	 * @param column
	 *            オーダー列名
	 */
	public void addOrderAsc(String column) {

		if (orderSql.length() > 0) {
			orderSql.append(", ");
		}
		orderSql.append("t.").append(column).append(" ASC");
	}

	/**
	 * オーダー条件設定する（降順）。
	 * 
	 * @param column
	 *            オーダー列名
	 */
	public void addOrderDesc(String column) {

		if (orderSql.length() > 0) {
			orderSql.append(", ");
		}
		orderSql.append("t.").append(column).append(" DESC");
	}


}
