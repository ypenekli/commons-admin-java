package com.yp.admin.data;

import java.math.BigDecimal;
import java.util.Date;

import com.yp.core.BaseConstants;
import com.yp.core.db.IExport;
import com.yp.core.db.OnExportListener.PHASE;
import com.yp.core.entity.DataEntity;
import com.yp.core.tools.DateTime;

public class Export extends DataEntity implements IExport {

	private static final long serialVersionUID = 783015803424147220L;
	private static String schemaName = "COMMON";
	private static String tableName = "EXPORTS";

	private boolean deleteTargetTableRows;

	public Export() {
		super();
		className = "Export";
		setPrimaryKeys(SOURCE_SCHEMA, SOURCE_TABLE, TARGET_SCHEMA, TARGET_TABLE);
		setMessages("*");
		setQuery("*");
		setSourceCount(0);
		setTargetCount(0);
		setGroupCode("A");
		deleteTargetTableRows = false;
	}

	public Export(String pSourceSchema, String pSourceTable, String pTargetSchema, String pTargetTable) {
		this();
		set(SOURCE_SCHEMA, pSourceSchema);
		set(SOURCE_TABLE, pSourceTable);
		set(TARGET_SCHEMA, pTargetSchema);
		set(TARGET_TABLE, pTargetTable);
	}

	public Export(String pSourceSchema, String pTargetSchema, String pSourceTable) {
		this(pSourceSchema, pSourceTable, pTargetSchema, pSourceTable);
	}

	public Export(String pSourceSchema, String pSourceTable) {
		this(pSourceSchema, pSourceTable, pSourceSchema, pSourceTable);
	}

	public static final String SOURCE_SCHEMA = "source_schema";

	public String getSourceSchema() {
		return (String) get(SOURCE_SCHEMA);
	}

	public void setSourceSchema(String pSourceSchema) {
		set(SOURCE_SCHEMA, pSourceSchema);
	}

	public boolean isSourceSchemaNull() {
		return isNull(SOURCE_SCHEMA);
	}

	public static final String SOURCE_TABLE = "source_table";

	public String getSourceTable() {
		return (String) get(SOURCE_TABLE);
	}

	public void setSourceTable(String pSourceTable) {
		set(SOURCE_TABLE, pSourceTable);
	}

	public boolean isSourceTableNull() {
		return isNull(SOURCE_TABLE);
	}

	public static final String TARGET_SCHEMA = "target_schema";

	public String getTargetSchema() {
		return (String) get(TARGET_SCHEMA);
	}

	public void setTargetSchema(String pTargetSchema) {
		set(TARGET_SCHEMA, pTargetSchema);
	}

	public boolean isTargetSchemaNull() {
		return isNull(TARGET_SCHEMA);
	}

	public static final String TARGET_TABLE = "target_table";

	public String getTargetTable() {
		return (String) get(TARGET_TABLE);
	}

	public void setTargetTable(String pTargetTable) {
		set(TARGET_TABLE, pTargetTable);
	}

	public boolean isTargetTableNull() {
		return isNull(TARGET_TABLE);
	}

	public static final String QUERY = "query";

	public String getQuery() {
		return (String) get(QUERY);
	}

	public void setQuery(String pQuery) {
		set(QUERY, pQuery);
	}

	public boolean isQueryNull() {
		return isNull(QUERY);
	}

	public static final String SOURCE_COUNT = "source_count";

	public Integer getSourceCount() {
		return (Integer) get(SOURCE_COUNT);
	}

	public void setSourceCount(Integer pSourceCount) {
		set(SOURCE_COUNT, pSourceCount);
	}

	public boolean isSourceCountNull() {
		return isNull(SOURCE_COUNT);
	}

	public static final String TARGET_COUNT = "target_count";

	public Integer getTargetCount() {
		return (Integer) get(TARGET_COUNT);
	}

	public void setTargetCount(Integer pTargetCount) {
		set(TARGET_COUNT, pTargetCount);
	}

	public boolean isTargetCountNull() {
		return isNull(TARGET_COUNT);
	}

	public static final String START_DATETIME = "start_datetime";

	public BigDecimal getStartDatetimeDb() {
		return (BigDecimal) get(START_DATETIME);
	}

	public void setStartDatetimeDb(BigDecimal pStartDatetime) {
		mStartDatetime = null;
		set(START_DATETIME, pStartDatetime);
	}

	protected Date mStartDatetime;

	public Date getStartDatetime() {
		if (mStartDatetime == null)
			mStartDatetime = DateTime.asDate((BigDecimal) get(START_DATETIME));
		return mStartDatetime;
	}

	public void setStartDatetime(Date pStartDatetime) {
		mStartDatetime = pStartDatetime;
		set(START_DATETIME, DateTime.asDbDateTime(pStartDatetime));
	}

	public boolean isStartDatetimeNull() {
		return isNull(START_DATETIME);
	}

	public static final String END_DATETIME = "end_datetime";

	public BigDecimal getEndDatetimeDb() {
		return (BigDecimal) get(END_DATETIME);
	}

	public void setEndDatetimeDb(BigDecimal pEndDatetime) {
		mEndDatetime = null;
		set(END_DATETIME, pEndDatetime);
	}

	protected Date mEndDatetime;

	public Date getEndDatetime() {
		if (mEndDatetime == null)
			mEndDatetime = DateTime.asDate((BigDecimal) get(END_DATETIME));
		return mEndDatetime;
	}

	public void setEndDatetime(Date pEndDatetime) {
		mEndDatetime = pEndDatetime;
		set(END_DATETIME, DateTime.asDbDateTime(pEndDatetime));
	}

	public boolean isEndDatetimeNull() {
		return isNull(END_DATETIME);
	}

	public static final String GROUP_CODE = "group_code";

	public String getGroupCode() {
		return (String) get(GROUP_CODE);
	}

	public void setGroupCode(String pGroupCode) {
		set(GROUP_CODE, pGroupCode);
	}

	public boolean isGroupCodeNull() {
		return isNull(GROUP_CODE);
	}

	public static final String IDX = "idx";

	public Integer getIdx() {
		return (Integer) get(IDX);
	}

	public void setIdx(Integer pIdx) {
		set(IDX, pIdx);
	}

	public boolean isIdxNull() {
		return isNull(IDX);
	}

	public static final String MESSAGES = "messages";

	public String getMessages() {
		return (String) get(MESSAGES);
	}

	public void setMessages(String pMessages) {
		set(MESSAGES, pMessages);
	}

	public boolean isMessagesNull() {
		return isNull(MESSAGES);
	}

	public static final String ERROR_CODE = "error_code";

	public Integer getErrorCode() {
		return (Integer) get(ERROR_CODE);
	}

	public void setErrorCode(Integer pErrorCode) {
		set(ERROR_CODE, pErrorCode);
	}

	public boolean isErrorCodeNull() {
		return isNull(ERROR_CODE);
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
		checkInteger(SOURCE_COUNT);
		checkInteger(TARGET_COUNT);
		checkBigDecimal(START_DATETIME);
		checkBigDecimal(END_DATETIME);
		checkInteger(IDX);
		checkInteger(ERROR_CODE);
	}

	protected static final String EXPORT_ID = "export_id";

	public String getExportId() {
		if (isNull(EXPORT_ID))
			setField(EXPORT_ID, String.format("%s->%s.%s", getSourceTable(), getTargetSchema(), getTargetTable()),
					false);
		return (String) get(EXPORT_ID);
	}

	public boolean isDeleteTargetTableRows() {
		return deleteTargetTableRows;
	}

	public void setDeleteTargetTableRows(boolean pDelTargetTableRows) {
		deleteTargetTableRows = pDelTargetTableRows;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Export) {
			Export vs = (Export) obj;
			String dSS1 = vs.getSourceSchema().toLowerCase(BaseConstants.LOCALE_EN);
			String dST1 = vs.getSourceTable().toLowerCase(BaseConstants.LOCALE_EN);
			String dTS1 = vs.getTargetSchema().toLowerCase(BaseConstants.LOCALE_EN);
			String dSS2 = getSourceSchema().toLowerCase(BaseConstants.LOCALE_EN);
			String dST2 = getSourceTable().toLowerCase(BaseConstants.LOCALE_EN);
			String dTS2 = getTargetSchema().toLowerCase(BaseConstants.LOCALE_EN);
			return dSS1.equals(dSS2) && dST1.equals(dST2) && dTS1.equals(dTS2);
		}
		return false;
	}

	@Override
	public int compareTo(IExport pArg0) {
		if (pArg0 == null)
			return 1;
		else {
			if (isGroupCodeNull())
				setGroupCode("A");
			if (pArg0.isGroupCodeNull())
				pArg0.setGroupCode("A");
			if (isIdxNull())
				setIdx(0);
			if (pArg0.isIdxNull())
				pArg0.setIdx(0);
			if (getGroupCode().equals(pArg0.getGroupCode()))
				return getIdx().compareTo(pArg0.getIdx());
			else return getGroupCode().compareTo(pArg0.getGroupCode());
		}
	}

	public void onProceed(PHASE phase, Double progress, int count, String message) {
		switch (phase) {
		case STARTS:
			setTargetCount(0);
			setSourceCount(count);
			setStartDatetimeDb(DateTime.dbNow());
			break;
		case PROCEED:
			setTargetCount(progress.intValue());
			break;
		case ENDS:
			setEndDatetimeDb(DateTime.dbNow());
			setTargetCount(count);
			break;
		case FAILS:
			setTargetCount(0);
			break;
		default:
			setMessages(message);
			break;
		}
	}
}