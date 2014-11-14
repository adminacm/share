package argo.cost.common.exception;

public class BusinessException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -987762660237303846L;
	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {

		 super(msg);

	}
	public BusinessException(String msg, Throwable cause) {

		super(msg, cause);

	}
	public BusinessException(Throwable cause) {

		super(cause);

	}

}
