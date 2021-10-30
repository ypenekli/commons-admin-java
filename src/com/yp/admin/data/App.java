package com.yp.admin.data;

import java.math.BigDecimal;
import java.util.Date;

import com.yp.core.BaseConstants;
import com.yp.core.entity.DataEntity;
import com.yp.core.entity.IDataEntity;
import com.yp.core.tools.DateTime;
import com.yp.core.tools.StringTool;

public class App extends DataEntity {

	private static final long serialVersionUID = 7953203547827878233L;
	private static String schemaName = "COMMON";
	private static String tableName = "APPS";

	public App() {
		super();
		className = "App";
		setPrimaryKeys(ID);
	}

	public App(String pId) {
		this();
		set(ID, pId);
	}

	public App(IDataEntity pDe) {
		this((String) pDe.get(ID));
		set(AUTHOR, pDe.get(AUTHOR));
		set(DESCRIPTION, pDe.get(DESCRIPTION));
		set(ICON_URL, pDe.get(ICON_URL));
		set(NAME, pDe.get(NAME));
		set(ORGANIZATION, pDe.get(ORGANIZATION));
		set(STATUS, pDe.get(STATUS));
		set(TARGET, pDe.get(TARGET));
		set(URL, pDe.get(URL));
		set(VERSION, pDe.get(VERSION));
		set(VERSION_UPDATE_DATE, pDe.get(VERSION_UPDATE_DATE));
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
		set(NAME, pName);
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

	protected static final String AUTHOR = "author";

	public String getAutor() {
		return (String) get(AUTHOR);
	}

	public void setAuthor(String pAutor) {
		set(AUTHOR, pAutor);
	}

	public boolean isAutorNull() {
		return isNull(AUTHOR);
	}

	protected static final String ORGANIZATION = "organization";

	public String getOrganization() {
		return (String) get(ORGANIZATION);
	}

	public void setOrganization(String pOrganization) {
		set(ORGANIZATION, pOrganization);
	}

	public boolean isOrganizationNull() {
		return isNull(ORGANIZATION);
	}

	protected static final String VERSION = "version";

	public String getVersion() {
		return (String) get(VERSION);
	}

	public void setVersion(String pVersion) {
		set(VERSION, pVersion);
	}

	public boolean isVersionNull() {
		return isNull(VERSION);
	}

	protected static final String VERSION_UPDATE_DATE = "version_update_date";

	public BigDecimal getVersionUpdateDateDb() {
		return (BigDecimal) get(VERSION_UPDATE_DATE);
	}

	public void setVersionUpdateDateDb(BigDecimal pVersionUpdateDate) {
		mVersionUpdateDate = null;
		set(VERSION_UPDATE_DATE, pVersionUpdateDate);
	}

	protected Date mVersionUpdateDate;

	public Date getVersionUpdateDate() {
		if (mVersionUpdateDate == null)
			mVersionUpdateDate = DateTime.asDate((BigDecimal) get(VERSION_UPDATE_DATE));
		return mVersionUpdateDate;
	}

	public void setVersionUpdateDate(Date pVersionUpdateDate) {
		mVersionUpdateDate = pVersionUpdateDate;
		set(VERSION_UPDATE_DATE, DateTime.asDbDateTime(pVersionUpdateDate));
	}

	public boolean isVersionUpdateDateNull() {
		return isNull(VERSION_UPDATE_DATE);
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
		checkBigDecimal(VERSION_UPDATE_DATE);
		checkInteger(GROUP_ID);
		checkString(ID);
	}

	// **************

	public boolean isStatusEnabled() {
		return BaseConstants.ENABLED.equals(get(STATUS));
	}

	public AppFunc addFuncs(Integer pFuncSize, boolean isLeaf) {
		Integer idx = 0;
		if (pFuncSize != null) {
			idx = pFuncSize + 1;
		}
		final AppFunc de = new AppFunc(String.format("%s.%s", getId(), idx));
		de.setParentId(getId());
		de.setAppId(getId());
		de.setLevel(1);
		de.setIdx(idx);
		de.setTarget(getTarget());
		de.setHierarchy(
				StringTool.padLeft(getId().replace(".", ""), '0', 4) + StringTool.padLeft(idx.toString(), '0', 4));
		de.setLeaf(isLeaf);
		return de;
	}


	protected static final String GROUP_ID = "group_id";

	public Integer getGroupId() {
		return (Integer) get(GROUP_ID);
	}

	public void setGroupId(Integer pGroupId) {
		setField(GROUP_ID, pGroupId, false);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object pObj) {
		if (pObj != null)
			if (pObj instanceof App)
				return getId().equals(((App) pObj).getId());
			else if (pObj instanceof String)
				return getId().equals(pObj);
		return false;
	}
}