package com.yp.admin.data;


import com.yp.core.entity.DataEntity;


public class Idx extends DataEntity {

	private static final long serialVersionUID = 8698818900269624526L;
	private static String schemaName = "COMMON";
	private static String tableName = "IDX";


	public Idx(){
		super();
		className = "Idx";
		setPrimaryKeys(IDX);
	}

	public Idx(Integer pIdx){
		this();
		set(IDX, pIdx);
	}

	protected static final String IDX = "idx";

	public Integer getIdx() {
		return (Integer) get(IDX);
	}
	
	public void setIdx(Integer pIdx){
		set(IDX, pIdx);
	}
	
	public boolean isIdxNull(){
		return isNull(IDX);
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
		checkInteger(IDX);
	}

}