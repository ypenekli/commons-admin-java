package com.yp.admin.data;

import com.yp.core.entity.DataEntity;
import com.yp.core.entity.IDataEntity;

public class GroupUser extends DataEntity {

	private static final long serialVersionUID = 7989574171523810300L;
	private static String schemaName = "COMMON";
	private static String tableName = "GROUP_USERS";

	public GroupUser() {
		super();
		className = "GroupUser";
		setPrimaryKeys(GROUP_ID, USER_ID);
	}

	public GroupUser(Integer pGroupId, Integer pUserId) {
		this();
		set(GROUP_ID, pGroupId);
		set(USER_ID, pUserId);
	}

	public GroupUser(IDataEntity pDe) {
		this(Double.valueOf(pDe.get(GROUP_ID).toString()).intValue(),
				Double.valueOf(pDe.get(USER_ID).toString()).intValue());
	}

	protected static final String GROUP_ID = "group_id";

	public Integer getGroupId() {
		return (Integer) get(GROUP_ID);
	}

	public void setGroupId(Integer pGroupId) {
		set(GROUP_ID, pGroupId);
	}

	public boolean isGroupIdNull() {
		return isNull(GROUP_ID);
	}

	protected static final String USER_ID = "user_id";

	public Integer getUserId() {
		return (Integer) get(USER_ID);
	}

	public void setUserId(Integer pUserId) {
		set(USER_ID, pUserId);
	}

	public boolean isUserIdNull() {
		return isNull(USER_ID);
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
	public void checkValues() {
		super.checkValues();
		checkInteger(GROUP_ID);
		checkInteger(USER_ID);
	}

	protected static final String USER_NAME = "user_name";

	public String getUserName() {
		return (String) get(USER_NAME);
	}

	protected static final String USER_SURNAME = "user_surname";

	public String getUserSurname() {
		return (String) get(USER_SURNAME);
	}

	protected static final String USER_TITLE_NAME = "user_title_name";

	public String getUserTitleName() {
		return (String) get(USER_TITLE_NAME);
	}

	protected static final String USER_FULL_NAME = "user_full_name";

	public String getUserFullName() {
		if (isNull(USER_FULL_NAME)) {
			setField(USER_FULL_NAME, getUserName() + " " + getUserSurname(), false);
		}
		return (String) get(USER_FULL_NAME);
	}

	protected static final String USER_EMAIL = "user_email";

	public String getUserEmail() {
		return (String) get(USER_EMAIL);
	}

	protected static final String USER_PHONE_NU1 = "user_phone_nu1";

	public String getUserPhoneNu1() {
		return (String) get(USER_PHONE_NU1);
	}

	protected static final String USER_PHONE_NU2 = "user_phone_nu2";

	public String getUserPhoneNu2() {
		return (String) get(USER_PHONE_NU2);
	}

	protected static final String GROUP_NAME = "group_name";

	public String getGroupName() {
		return (String) get(GROUP_NAME);
	}

}