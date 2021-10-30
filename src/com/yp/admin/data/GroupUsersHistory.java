package com.yp.admin.data;


import java.math.BigDecimal;
import java.util.Date;
import com.yp.core.entity.DataEntity;
import com.yp.core.tools.DateTime;


public class GroupUsersHistory extends DataEntity {
	
	private static final long serialVersionUID = 7976844798422445326L;
	private static String schemaName = "COMMON";
	private static String tableName = "GROUP_USERS_HISTORY";

	public static final String UPDATE_MODE_ADD = "A";
	public static final String UPDATE_MODE_DELETE = "D";



	public GroupUsersHistory(){
		super();
		className = "GroupUsersHistory";
		setPrimaryKeys(IDX);
	}

	public GroupUsersHistory(Long pIdx){
		this();
		set(IDX, pIdx);
	}

	public GroupUsersHistory(Long pIdx, GroupUser pGroupUsers){
		this(pIdx);
		setGroupId(pGroupUsers.getGroupId());
		setUserId(pGroupUsers.getUserId());
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

	protected static final String GROUP_ID = "group_id";

	public Integer getGroupId() {
		return (Integer) get(GROUP_ID);
	}
	
	public void setGroupId(Integer pGroupId){
		set(GROUP_ID, pGroupId);
	}
	
	public boolean isGroupIdNull(){
		return isNull(GROUP_ID);
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

	protected static final String UPDATE_DATETIME = "update_datetime";
		
	public BigDecimal getUpdateDatetimeDb() {
		return (BigDecimal) get(UPDATE_DATETIME);
	}
	
	public void setUpdateDatetimeDb(BigDecimal pUpdateDatetime){
		mUpdateDatetime = null;
		set(UPDATE_DATETIME, pUpdateDatetime);
	}

	protected Date mUpdateDatetime;

	public Date getUpdateDatetime() {
		if (mUpdateDatetime == null)
			mUpdateDatetime = DateTime.asDate((BigDecimal) get(UPDATE_DATETIME));
		return mUpdateDatetime;
	}
	
	public void setUpdateDatetime(Date pUpdateDatetime) {
		mUpdateDatetime = pUpdateDatetime;
		set(UPDATE_DATETIME, DateTime.asDbDateTime(pUpdateDatetime));
	}
	
	public boolean isUpdateDatetimeNull(){
		return isNull(UPDATE_DATETIME);
	}

	protected static final String UPDATE_USER_ID = "update_user_id";

	public Integer getUpdateUserId() {
		return (Integer) get(UPDATE_USER_ID);
	}
	
	public void setUpdateUserId(Integer pUpdateUserId){
		set(UPDATE_USER_ID, pUpdateUserId);
	}
	
	public boolean isUpdateUserIdNull(){
		return isNull(UPDATE_USER_ID);
	}

	protected static final String UPDATE_USER_NAME = "update_user_name";

	public String getUpdateUserName() {
		return (String) get(UPDATE_USER_NAME);
	}
	
	public void setUpdateUserName(String pUpdateUserName){
		set(UPDATE_USER_NAME, pUpdateUserName);
	}
	
	public boolean isUpdateUserNameNull(){
		return isNull(UPDATE_USER_NAME);
	}

	protected static final String UPDATE_USER_TITLE = "update_user_title";

	public String getUpdateUserTitle() {
		return (String) get(UPDATE_USER_TITLE);
	}
	
	public void setUpdateUserTitle(String pUpdateUserTitle){
		set(UPDATE_USER_TITLE, pUpdateUserTitle);
	}
	
	public boolean isUpdateUserTitleNull(){
		return isNull(UPDATE_USER_TITLE);
	}

	protected static final String UPDATE_MODE = "update_mode";

	public String getUpdateMode() {
		return (String) get(UPDATE_MODE);
	}
	
	public void setUpdateMode(String pUpdateMode){
		set(UPDATE_MODE, pUpdateMode);
	}
	
	public boolean isUpdateModeNull(){
		return isNull(UPDATE_MODE);
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
		checkInteger(GROUP_ID);
		checkInteger(USER_ID);
		checkBigDecimal(UPDATE_DATETIME);
		checkInteger(UPDATE_USER_ID);
	}

	// **************

	public void setUpdateUser(User pUser, String pUpdateMode) {
		if (pUser != null) {
			setUpdateUserId(pUser.getId());
			setUpdateUserName(pUser.getFullName());
			setUpdateUserTitle(pUser.getTitleName());
		} else {
			setUpdateUserId(0);
			setUpdateUserName("Admin");
			setUpdateUserTitle("Admin");
		}
		setUpdateMode(pUpdateMode);
		setUpdateDatetimeDb(DateTime.dbNow());
	}


}