package com.yp.admin.data;


import java.math.BigDecimal;
import java.util.Date;
import com.yp.core.entity.DataEntity;
import com.yp.core.tools.DateTime;


public class LoginHistory extends DataEntity {

	private static final long serialVersionUID = 1722047788792203625L;
	private static String schemaName = "COMMON";
	private static String tableName = "LOGIN_HISTORY";


	public LoginHistory(){
		super();
		className = "LoginHistory";
		setPrimaryKeys(IDX);
	}

	public LoginHistory(Long pIdx){
		this();
		set(IDX, pIdx);
	}

	protected static final String IDX = "idx";

	public Long getIdx() {
		return (Long) get(IDX);
	}
	
	public void setIdx(Long pIdx){
		set(IDX, pIdx);
	}
	
	public boolean isIdxNull(){
		return isNull(IDX);
	}

	protected static final String APP_ID = "app_id";

	public String getAppId() {
		return (String) get(APP_ID);
	}
	
	public void setAppId(String pAppId){
		set(APP_ID, pAppId);
	}
	
	public boolean isAppIdNull(){
		return isNull(APP_ID);
	}

	protected static final String USER_ID = "user_id";

	public Integer getUserId() {
		return (Integer) get(USER_ID);
	}
	
	public void setUserId(Integer pUserId){
		set(USER_ID, pUserId);
	}
	
	public boolean isUserIdNull(){
		return isNull(USER_ID);
	}

	protected static final String LOGIN_DATETIME = "login_datetime";
		
	public BigDecimal getLoginDatetimeDb() {
		return (BigDecimal) get(LOGIN_DATETIME);
	}
	
	public void setLoginDatetimeDb(BigDecimal pLoginDatetime){
		mLoginDatetime = null;
		set(LOGIN_DATETIME, pLoginDatetime);
	}

	protected Date mLoginDatetime;

	public Date getLoginDatetime() {
		if (mLoginDatetime == null)
			mLoginDatetime = DateTime.asDate((BigDecimal) get(LOGIN_DATETIME));
		return mLoginDatetime;
	}
	
	public void setLoginDatetime(Date pLoginDatetime) {
		mLoginDatetime = pLoginDatetime;
		set(LOGIN_DATETIME, DateTime.asDbDateTime(pLoginDatetime));
	}
	
	public boolean isLoginDatetimeNull(){
		return isNull(LOGIN_DATETIME);
	}

	@Override
	public String getSchemaName() {
		return schemaName;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public void checkValues(){
		super.checkValues();
		checkLong(IDX);
		checkInteger(USER_ID);
		checkBigDecimal(LOGIN_DATETIME);
	}

}