package com.yp.admin.data;

import com.yp.core.BaseConstants;
import com.yp.core.entity.DataEntity;
import com.yp.core.entity.IDataEntity;
import com.yp.core.tools.ITree;
import com.yp.core.tools.StringTool;

public class AppFunc extends DataEntity implements ITree<String> {

	private static final long serialVersionUID = 3417625341999168302L;
	private static String schemaName = "COMMON";
	private static String tableName = "APP_FUNCS";

	public AppFunc() {
		super();
		className = "AppFunc";
		setPrimaryKeys(ID);
	}

	public AppFunc(String pId) {
		this();
		set(ID, pId);
	}

	public AppFunc(IDataEntity pDe) {
		this((String) pDe.get(ID));
		set(DESCRIPTION, pDe.get(DESCRIPTION));
		set(HIERARCHY, pDe.get(HIERARCHY));
		set(ICON_URL, pDe.get(ICON_URL));
		set(IDX, pDe.get(IDX));
		set(LEAF, pDe.get(LEAF));
		set(LEVEL, pDe.get(LEVEL));
		set(NAME, pDe.get(NAME));
		set(PARENT_ID, pDe.get(PARENT_ID));
		set(APP_ID, pDe.get(APP_ID));
		set(STATUS, pDe.get(STATUS));
		set(TARGET, pDe.get(TARGET));
		set(URL, pDe.get(URL));
	}

	protected static final String APP_ID = "app_id";

	public String getAppId() {
		return (String) get(APP_ID);
	}

	public void setAppId(String pAppId) {
		set(APP_ID, pAppId);
	}

	public boolean isAppIdNull() {
		return isNull(APP_ID);
	}

	protected static final String ID = "id";

	public String getId() {
		return (String) get(ID);
	}

	public void setId(String pId) {
		set(ID, pId);
	}

	public boolean isIdNull() {
		return isNull(ID);
	}

	protected static final String NAME = "name";

	public String getName() {
		return (String) get(NAME);
	}

	public void setName(String pName) {
		if (pName == null)
			pName = "";
		if (!isLeaf())
			set(NAME, pName.toUpperCase(BaseConstants.LOCALE_TR));
		else
			set(NAME, StringTool.ucaseFirstCharTR(pName));
	}

	public boolean isNameNull() {
		return isNull(NAME);
	}

	protected static final String DESCRIPTION = "description";

	public String getDescription() {
		return (String) get(DESCRIPTION);
	}

	public void setDescription(String pDescription) {
		set(DESCRIPTION, pDescription);
	}

	public boolean isDescriptionNull() {
		return isNull(DESCRIPTION);
	}

	protected static final String URL = "url";

	public String getUrl() {
		return (String) get(URL);
	}

	public void setUrl(String pUrl) {
		set(URL, pUrl);
	}

	public boolean isUrlNull() {
		return isNull(URL);
	}

	protected static final String TARGET = "target";

	public String getTarget() {
		return (String) get(TARGET);
	}

	public void setTarget(String pTarget) {
		set(TARGET, pTarget);
	}

	public boolean isTargetNull() {
		return isNull(TARGET);
	}

	protected static final String PARENT_ID = "parent_id";

	public String getParentId() {
		return (String) get(PARENT_ID);
	}

	public void setParentId(String pParentId) {
		set(PARENT_ID, pParentId);
	}

	public boolean isParentIdNull() {
		return isNull(PARENT_ID);
	}

	protected static final String IDX = "idx";

	public Integer getIdx() {
		return (Integer) get(IDX);
	}

	public void setIdx(Integer pIdx) {
		set(IDX, pIdx);
	}

	public boolean isIdxNull() {
		return isNull(IDX);
	}

	protected static final String LEVEL = "level";

	public Integer getLevel() {
		return (Integer) get(LEVEL);
	}

	public void setLevel(Integer pLevel) {
		set(LEVEL, pLevel);
	}

	public boolean isLevelNull() {
		return isNull(LEVEL);
	}

	protected static final String HIERARCHY = "hierarchy";

	public String getHierarchy() {
		return (String) get(HIERARCHY);
	}

	public void setHierarchy(String pHierarchy) {
		set(HIERARCHY, pHierarchy);
	}

	public boolean isHierarchyNull() {
		return isNull(HIERARCHY);
	}

	protected static final String LEAF = "leaf";

	public String getLeaf() {
		return (String) get(LEAF);
	}

	public void setLeaf(String pLeaf) {
		set(LEAF, pLeaf);
	}

	public boolean isLeafNull() {
		return isNull(LEAF);
	}

	protected static final String ICON_URL = "icon_url";

	public String getIconUrl() {
		return (String) get(ICON_URL);
	}

	public void setIconUrl(String pIconUrl) {
		set(ICON_URL, pIconUrl);
	}

	public boolean isIconUrlNull() {
		return isNull(ICON_URL);
	}

	protected static final String STATUS = "status";

	public String getStatus() {
		return (String) get(STATUS);
	}

	public void setStatus(String pStatus) {
		set(STATUS, pStatus);
	}

	public boolean isStatusNull() {
		return isNull(STATUS);
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
		checkInteger(IDX);
		checkInteger(LEVEL);
		checkString(APP_ID);
		checkString(PARENT_ID);
		checkString(ID);
	}

	// *************

	public boolean isStatusEnabled() {
		return BaseConstants.ENABLED.equals(get(STATUS));
	}

	public boolean isLeaf() {
		return BaseConstants.TRUE.equals(get(LEAF));
	}

	public void setLeaf(boolean pLeaf) {
		set(LEAF, pLeaf ? BaseConstants.TRUE.getKey() : BaseConstants.FALSE.getKey());
	}

	public AppFunc addSubitem(Integer pSubitemSize, boolean isLeaf) {
		Integer idx = 1;
		if (pSubitemSize != null) {
			idx = pSubitemSize + 1;
		}
		final AppFunc de = new AppFunc(String.format("%s.%s", getId(), idx));
		de.setParentId(getId());
		de.setParentName(getName());
		de.setAppId(getAppId());
		de.setLevel(getLevel() + 1);
		de.setIdx(idx);
		de.setTarget(getTarget());
		de.setHierarchy(getHierarchy() + StringTool.padLeft(idx.toString(), '0', 4));
		de.setLeaf(isLeaf);
		setLeaf(false);
		return de;
	}
	
	public boolean isRoot() {
		return getAppId().equals(getParentId());
	}

	protected static final String PARENT_NAME = "parent_name";

	public String getParentName() {
		return (String) get(PARENT_NAME);
	}

	public void setParentName(String pParentName) {
		setField(PARENT_NAME, pParentName, false);
	}

	@Override
	public String getValue() {
		return (String) get(ID);
	}

	@Override
	public String getParentValue() {
		return (String) get(PARENT_ID);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object pObj) {
		if (pObj != null) {
			if (pObj instanceof AppFunc) {
				return getId().equals(((AppFunc) pObj).getId());
			} else if (pObj instanceof String) {
				return getId().equals(pObj);
			}
		}
		return false;
	}
}