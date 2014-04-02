package argo.cost.common.utils;


import org.apache.commons.lang3.StringUtils;

import com.ibm.icu.text.Transliterator;


/**
 * 文字列操作ライブラリ
 * 
 * @author cost
 * 
 */
public class CostStringUtils extends StringUtils {

	// 定数 ：Transliterator 全角→半角
	private static final String FULL_HALF = "Fullwidth-Halfwidth";
	
	/**
	 * 
	 *　数字の前ゼロを追加する
	 * 
	 * @param  str
	 * 				数字
	 * @param strLength 
	 * 				数字長度
	 * 
	 * @return ユーザの月報情報
	 */
	 public static String addZeroForNum(String str, int strLength) {
	     int strLen = str.length();
	     StringBuffer sb = null;
	     while (strLen < strLength) {
	           sb = new StringBuffer();
	           sb.append("0").append(str);
	           str = sb.toString();
	           strLen = str.length();
	     }
	     return str;
	 }

		/**
		 * 数値変換の可否を判定する<BR>
		 * 　1. 引数の文字列を半角に変換する。<BR>
		 * 　2. 文字列を数値(小数点込み)に変換<BR>
		 * 　2-1. 2.の結果、変換できた場合はtrueを返却する。<BR>
		 * 　2-2. 2.の結果、変換できなかった場合はfalseを返却する。<BR>
		 * 
		 * @param String チェック対象の文字列
		 * @return boolean true :数値変換可能、 false :数値変換不可能
		 */
		public static boolean isNumber(String str) {
			try {
				// 半角変換した文字列を数値(小数点込み)に変換
				Double.parseDouble(toCommaRemoval(str));
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		}

		/**
		 * 受け取った文字列を半角にし","を除去する<BR>
		 * 　1. 引数の文字列を半角にし、","を除去して返却する。<BR>
		 * 
		 * @param str 編集対象となる文字列
		 * @return
		 */
		public static String toCommaRemoval(String str) {
			// 文字列を半角に変換、","を除去する。
			return toHan(str).replaceAll(",", "");
		}
		/**
		 * 半角に変換する<BR>
		 * 　1. 引数の文字列をICU4Jライブラリ（Transliterator.getInstance）に<BR>
		 * 　定数ZEN_TO_HANを指定し半角文字に変換して返却する<BR>
		 * 
		 * @param String 半角文字に変換する文字列
		 * @return String 半角文字列
		 */
		public static String toHan(String str) {
			// 全角文字列→半角文字列
			Transliterator trans = Transliterator.getInstance(FULL_HALF);
			return trans.transliterate(str);

		}
}