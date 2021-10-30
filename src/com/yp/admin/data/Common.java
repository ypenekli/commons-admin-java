package com.yp.admin.data;

import com.yp.core.BaseConstants;
import com.yp.core.entity.DataEntity;
import com.yp.core.entity.IDataEntity;
import com.yp.core.ref.IReference;

public class Common extends DataEntity implements IReference<Integer> {

	private static final long serialVersionUID = 8655468033821499434L;
	private static String schemaName = "COMMON";
	private static String tableName = "COMMONS";

	public Common() {
		super();
		className = "Common";
		setPrimaryKeys(ID);
	}

	public Common(Integer pId) {
		this();
		set(ID, pId);
	}

	public Common(final Integer pId, final Integer pParentId, final Integer pLevel, final Integer pGroupCode,
			final String pHierarchy) {
		this(pId);
		setParentId(pParentId);
		setLevel(pLevel);
		setGroupCode(pGroupCode);
		setHierarchy(pHierarchy);
	}

	public Common(IDataEntity pDe) {
		this(Double.valueOf(pDe.get(ID).toString()).intValue());
		setAbrv((String) pDe.get(ABRV));
		set(DESCRIPTION, pDe.get(DESCRIPTION));
		set(GROUP_CODE, pDe.get(GROUP_CODE));
		set(HIERARCHY, pDe.get(HIERARCHY));
		set(ICON_URL, pDe.get(ICON_URL));
		set(IDX, pDe.get(IDX));
		set(LEAF, pDe.get(LEAF));
		set(LEVEL, pDe.get(LEVEL));
		set(NAME, pDe.get(NAME));
		set(PARENT_ID, pDe.get(PARENT_ID));
		set(STATUS, pDe.get(STATUS));
	}

	protected static final String ID = "id";

	public Integer getId() {
		return (Integer) get(ID);
	}

	public void setId(Integer pId) {
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

	protected static final String ABRV = "abrv";

	public String getAbrv() {
		return (String) get(ABRV);
	}

	public void setAbrv(String pAbrv) {
		set(ABRV, pAbrv, 75);
	}

	public boolean isAbrvNull() {
		return isNull(ABRV);
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

	protected static final String GROUP_CODE = "group_code";

	public Integer getGroupCode() {
		return (Integer) get(GROUP_CODE);
	}

	public void setGroupCode(Integer pGroupCode) {
		set(GROUP_CODE, pGroupCode);
	}

	public boolean isGroupCodeNull() {
		return isNull(GROUP_CODE);
	}

	protected static final String PARENT_ID = "parent_id";

	public Integer getParentId() {
		return (Integer) get(PARENT_ID);
	}

	public void setParentId(Integer pParentId) {
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
		checkInteger(ID);
		checkInteger(GROUP_CODE);
		checkInteger(PARENT_ID);
		checkInteger(IDX);
		checkInteger(LEVEL);
	}

	// ************

	public static final Integer PARENT_ID_CITY_TR = 1020000000;
	public static final Integer PARENT_ID_PROFESSION = 1030000000;
	public static final Integer PARENT_ID_TITLE = 1040000000;
	public static final Integer PARENT_ID_POSITION = 1050000000;
	public static final Common root = new Common(0, -1, -1, -1, "0");

	public boolean isStatusEnabled() {
		return BaseConstants.ENABLED.equals(get(STATUS));
	}

	// ..IReference
	@Override
	public Integer getKey() {
		return (Integer) get(ID);
	}

	@Override
	public void setKey(Integer pKey) {
		set(ID, pKey);
	}

	@Override
	public String getValue() {
		return (String) get(NAME);
	}

	@Override
	public void setValue(String pValue) {
		set(NAME, pValue);
	}

	public boolean isLeaf() {
		return BaseConstants.TRUE.equals(get(LEAF));
	}

	public void setLeaf(boolean pLeaf) {
		set(LEAF, pLeaf ? BaseConstants.TRUE.getKey() : BaseConstants.FALSE.getKey());
	}

	// ..IReference

	public Common addSubitem(Integer pSubitemSize, boolean isLeaf) {
		Integer idx = 0;
		if (pSubitemSize != null) {
			idx = pSubitemSize + 1;
		}
		Integer groupCode = getGroupCode();
		if (groupCode == null || groupCode == -1) {
			groupCode = idx;
		}
		Integer id = (100 + groupCode) * 10000000;
		if (getLevel() > 2) {
			id += 100000;
		}
		if (getLevel() > -1) {
			id += idx;
		}
		final Common aNew = new Common(id);
		aNew.setParentId(getId());
		aNew.setGroupCode(groupCode);
		aNew.setLeaf(isLeaf);
		aNew.setLevel(getLevel() + 1);
		aNew.setHierarchy(String.format("%s.%s", getHierarchy(), id));
		return aNew;
	}

	@Override
	public boolean equals(Object pObj) {
		if (pObj != null) {
			if (pObj instanceof Common || pObj instanceof IReference<?>) {
				return getId() != null && getId().equals(((Common) pObj).getId());
			} else if (pObj instanceof Integer) {
				return getId() != null && getId().equals(pObj);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return getName();
	}
}