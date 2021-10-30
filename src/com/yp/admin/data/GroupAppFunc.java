package com.yp.admin.data;

import com.yp.core.entity.DataEntity;
import com.yp.core.entity.IDataEntity;

public class GroupAppFunc extends DataEntity {

	private static final long serialVersionUID = 7097405330140651594L;
	private static String schemaName = "COMMON";
	private static String tableName = "GROUP_APP_FUNCS";

	public GroupAppFunc() {
		super();
		className = "GroupAppFunc";
		setPrimaryKeys(GROUP_ID, APP_FUNC_ID);
	}

	public GroupAppFunc(Integer pGroupId, String pAppFuncId) {
		this();
		set(GROUP_ID, pGroupId);
		set(APP_FUNC_ID, pAppFuncId);
	}

	public GroupAppFunc(IDataEntity pDe) {
		this(Double.valueOf(pDe.get(GROUP_ID).toString()).intValue(), (String) pDe.get(APP_FUNC_ID));
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

	protected static final String APP_FUNC_ID = "app_func_id";

	public String getAppFuncId() {
		return (String) get(APP_FUNC_ID);
	}

	public void setAppFuncId(String pAppFuncId) {
		set(APP_FUNC_ID, pAppFuncId);
	}

	public boolean isAppFuncIdNull() {
		return isNull(APP_FUNC_ID);
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
		checkString(APP_FUNC_ID);		
	}

}