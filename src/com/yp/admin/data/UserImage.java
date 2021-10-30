package com.yp.admin.data;


import com.yp.core.entity.DataEntity;
import java.sql.Blob;


public class UserImage extends DataEntity {

	private static final long serialVersionUID = 553319198399236230L;
	private static String schemaName = "COMMON";
	private static String tableName = "USER_IMAGES";


	public UserImage(){
		super();
		className = "UserImage";
		setPrimaryKeys(USER_ID, IDX);
	}

	public UserImage(Integer pUserId, Integer pIdx){
		this();
		set(USER_ID, pUserId);
		set(IDX, pIdx);
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

	protected static final String IMAGE = "image";

	public Blob getImage() {
		return (Blob) get(IMAGE);
	}
	
	public void setImage(Blob pImage){
		set(IMAGE, pImage);
	}
	
	public boolean isImageNull(){
		return isNull(IMAGE);
	}

	protected static final String IMAGE_TYPE = "image_type";

	public String getImageType() {
		return (String) get(IMAGE_TYPE);
	}
	
	public void setImageType(String pImageType){
		set(IMAGE_TYPE, pImageType);
	}
	
	public boolean isImageTypeNull(){
		return isNull(IMAGE_TYPE);
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
		checkInteger(USER_ID);
		checkInteger(IDX);
		checkBlob(IMAGE);
	}

}