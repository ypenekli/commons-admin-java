package com.yp.admin.data;

import java.math.BigDecimal;
import java.util.Date;

import com.yp.core.BaseConstants;
import com.yp.core.entity.DataEntity;
import com.yp.core.entity.IDataEntity;
import com.yp.core.ref.IReference;
import com.yp.core.tools.DateTime;
import com.yp.core.user.IUser;

public class User extends DataEntity implements IUser {

	private static final long serialVersionUID = 6685001995998534606L;
	private static String schemaName = "COMMON";
	private static String tableName = "USERS";

	public User() {
		super();
		className = "User";
		setPrimaryKeys(ID);
	}

	public User(Integer pId) {
		this();
		set(ID, pId);
		set(CITIZENSHIP_NU, BigDecimal.ZERO);
		set(LOGIN_ERROR_COUNT, 0);
		setStatusActive(false);
	}

	public User(IDataEntity pDe) {
		this(Double.valueOf(pDe.get(ID).toString()).intValue());
		set(BIRTH_CITY, pDe.get(BIRTH_CITY));
		set(BIRTH_DATE, pDe.get(BIRTH_DATE));
		set(CHECKIN_DATE, pDe.get(CHECKIN_DATE));
		set(CHECKOUT_DATE, pDe.get(CHECKOUT_DATE));
		set(CITIZENSHIP_NU, pDe.get(CITIZENSHIP_NU));
		setEmail((String) pDe.get(EMAIL));
		set(GENDER, pDe.get(GENDER));
		set(HOME_ADDRESS, pDe.get(HOME_ADDRESS));
		set(HOME_COUNTRY, pDe.get(HOME_COUNTRY));
		set(HOME_CITY, pDe.get(HOME_CITY));
		set(HOME_DISTRICT, pDe.get(HOME_DISTRICT));
		set(IBAN, pDe.get(IBAN));
		set(INVOICE_ADDRESS, pDe.get(INVOICE_ADDRESS));
		set(INVOICE_COUNTRY, pDe.get(INVOICE_COUNTRY));
		set(INVOICE_CITY, pDe.get(INVOICE_CITY));
		set(INVOICE_DISTRICT, pDe.get(INVOICE_DISTRICT));
		set(LOGIN_ERROR_COUNT, pDe.get(LOGIN_ERROR_COUNT));
		set(NAME, pDe.get(NAME));
		set(PASSWORD, pDe.get(PASSWORD));
		set(PAYCARD_NU, pDe.get(PAYCARD_NU));
		set(PAYCARD_TYPE, pDe.get(PAYCARD_TYPE));
		set(PHONE_NU1, pDe.get(PHONE_NU1));
		set(PHONE_NU2, pDe.get(PHONE_NU2));
		set(PHONE_NU3, pDe.get(PHONE_NU3));
		set(POSITION, pDe.get(POSITION));
		set(PROFESSION, pDe.get(PROFESSION));
		set(PWD_UPDATE_DATETIME, pDe.get(PWD_UPDATE_DATETIME));
		set(STATUS, pDe.get(STATUS));
		set(SURNAME, pDe.get(SURNAME));
		set(TITLE, pDe.get(TITLE));
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

	protected static final String CITIZENSHIP_NU = "citizenship_nu";

	public BigDecimal getCitizenshipNu() {
		return (BigDecimal) get(CITIZENSHIP_NU);
	}

	public void setCitizenshipNu(BigDecimal pCitizenshipNu) {
		set(CITIZENSHIP_NU, pCitizenshipNu);
	}

	public boolean isCitizenshipNuNull() {
		return isNull(CITIZENSHIP_NU);
	}

	public static final String NAME = "name";

	public String getName() {
		return (String) get(NAME);
	}

	public void setName(String pName) {
		set(NAME, pName);
	}

	public boolean isNameNull() {
		return isNull(NAME);
	}

	public static final String SURNAME = "surname";

	public String getSurname() {
		return (String) get(SURNAME);
	}

	public void setSurname(String pSurname) {
		set(SURNAME, pSurname);
	}

	public boolean isSurnameNull() {
		return isNull(SURNAME);
	}

	public static final String BIRTH_DATE = "birth_date";

	public BigDecimal getBirthDateDb() {
		return (BigDecimal) get(BIRTH_DATE);
	}

	public void setBirthDateDb(BigDecimal pBirthDate) {
		mBirthDate = null;
		set(BIRTH_DATE, pBirthDate);
	}

	protected transient Date mBirthDate;

	public Date getBirthDate() {
		if (mBirthDate == null)
			mBirthDate = DateTime.asDate((BigDecimal) get(BIRTH_DATE));
		return mBirthDate;
	}

	public void setBirthDate(Date pBirthDate) {
		mBirthDate = pBirthDate;
		set(BIRTH_DATE, DateTime.asDbDate(pBirthDate));
	}

	public boolean isBirthDateNull() {
		return isNull(BIRTH_DATE);
	}

	protected static final String BIRTH_CITY = "birth_city";

	public Integer getBirthCity() {
		return (Integer) get(BIRTH_CITY);
	}

	public void setBirthCity(Integer pBirthCity) {
		set(BIRTH_CITY, pBirthCity);
	}

	public boolean isBirthCityNull() {
		return isNull(BIRTH_CITY);
	}

	protected static final String GENDER = "gender";

	public String getGender() {
		return (String) get(GENDER);
	}

	public void setGender(String pGender) {
		set(GENDER, pGender);
	}

	public boolean isGenderNull() {
		return isNull(GENDER);
	}

	protected static final String TITLE = "title";

	public Integer getTitle() {
		return (Integer) get(TITLE);
	}

	public void setTitle(Integer pTitle) {
		set(TITLE, pTitle);
	}

	public boolean isTitleNull() {
		return isNull(TITLE);
	}

	protected static final String PROFESSION = "profession";

	public Integer getProfession() {
		return (Integer) get(PROFESSION);
	}

	public void setProfession(Integer pProfession) {
		set(PROFESSION, pProfession);
	}

	public boolean isProfessionNull() {
		return isNull(PROFESSION);
	}

	protected static final String POSITION = "position";

	public Integer getPosition() {
		return (Integer) get(POSITION);
	}

	public void setPosition(Integer pPosition) {
		set(POSITION, pPosition);
	}

	public boolean isPositionNull() {
		return isNull(POSITION);
	}

	protected static final String CHECKIN_DATE = "checkin_date";

	public BigDecimal getCheckinDateDb() {
		return (BigDecimal) get(CHECKIN_DATE);
	}

	public void setCheckinDateDb(BigDecimal pCheckinDate) {
		mCheckinDate = null;
		set(CHECKIN_DATE, pCheckinDate);
	}

	protected transient Date mCheckinDate;

	public Date getCheckinDate() {
		if (mCheckinDate == null)
			mCheckinDate = DateTime.asDate((BigDecimal) get(CHECKIN_DATE));
		return mCheckinDate;
	}

	public void setCheckinDate(Date pCheckinDate) {
		mCheckinDate = pCheckinDate;
		set(CHECKIN_DATE, DateTime.asDbDate(pCheckinDate));
	}

	public boolean isCheckinDateNull() {
		return isNull(CHECKIN_DATE);
	}

	protected static final String CHECKOUT_DATE = "checkout_date";

	public BigDecimal getCheckoutDateDb() {
		return (BigDecimal) get(CHECKOUT_DATE);
	}

	public void setCheckoutDateDb(BigDecimal pCheckoutDate) {
		mCheckoutDate = null;
		set(CHECKOUT_DATE, pCheckoutDate);
	}

	protected transient Date mCheckoutDate;

	public Date getCheckoutDate() {
		if (mCheckoutDate == null)
			mCheckoutDate = DateTime.asDate((BigDecimal) get(CHECKOUT_DATE));
		return mCheckoutDate;
	}

	public void setCheckoutDate(Date pCheckoutDate) {
		mCheckoutDate = pCheckoutDate;
		set(CHECKOUT_DATE, DateTime.asDbDate(pCheckoutDate));
	}

	public boolean isCheckoutDateNull() {
		return isNull(CHECKOUT_DATE);
	}

	public static final String EMAIL = "email";

	public String getEmail() {
		return (String) get(EMAIL);
	}

	public void setEmail(String pEmail) {
		set(EMAIL, pEmail);
	}

	public boolean isEmailNull() {
		return isNull(EMAIL);
	}

	protected static final String PHONE_NU1 = "phone_nu1";

	public String getPhoneno1() {
		return (String) get(PHONE_NU1);
	}

	public void setPhoneno1(String pPhoneno1) {
		set(PHONE_NU1, pPhoneno1);
	}

	public boolean isPhoneno1Null() {
		return isNull(PHONE_NU1);
	}

	public static final String PHONE_NU2 = "phone_nu2";

	public String getPhoneno2() {
		return (String) get(PHONE_NU2);
	}

	public void setPhoneno2(String pPhoneno2) {
		set(PHONE_NU2, pPhoneno2);
	}

	public boolean isPhoneno2Null() {
		return isNull(PHONE_NU2);
	}

	protected static final String PHONE_NU3 = "phone_nu3";

	public String getPhoneno3() {
		return (String) get(PHONE_NU3);
	}

	public void setPhoneno3(String pPhoneno3) {
		set(PHONE_NU3, pPhoneno3);
	}

	public boolean isPhoneno3Null() {
		return isNull(PHONE_NU3);
	}

	public static final String PASSWORD = "password";

	public String getPassword() {
		return (String) get(PASSWORD);
	}

	public void setPassword(String pPassword) {
		set(PASSWORD, pPassword);
	}

	public boolean isPasswordNull() {
		return isNull(PASSWORD);
	}

	protected static final String LOGIN_ERROR_COUNT = "login_error_count";

	public Integer getLoginErrorCount() {
		return (Integer) get(LOGIN_ERROR_COUNT);
	}

	public void setLoginErrorCount(Integer pLoginErrorCount) {
		set(LOGIN_ERROR_COUNT, pLoginErrorCount);
	}

	public boolean isLoginErrorCountNull() {
		return isNull(LOGIN_ERROR_COUNT);
	}

	protected static final String PWD_UPDATE_DATETIME = "pwd_update_datetime";

	public BigDecimal getPwdUpdateDatetimeDb() {
		return (BigDecimal) get(PWD_UPDATE_DATETIME);
	}

	public void setPwdUpdateDatetimeDb(BigDecimal pPwdUpdateDatetime) {
		mPwdUpdateDatetime = null;
		set(PWD_UPDATE_DATETIME, pPwdUpdateDatetime);
	}

	protected transient Date mPwdUpdateDatetime;

	public Date getPwdUpdateDatetime() {
		if (mPwdUpdateDatetime == null)
			mPwdUpdateDatetime = DateTime.asDate((BigDecimal) get(PWD_UPDATE_DATETIME));
		return mPwdUpdateDatetime;
	}

	public void setPwdUpdateDatetime(Date pPwdUpdateDatetime) {
		mPwdUpdateDatetime = pPwdUpdateDatetime;
		set(PWD_UPDATE_DATETIME, DateTime.asDbDateTime(pPwdUpdateDatetime));
	}

	public boolean isPwdUpdateDatetimeNull() {
		return isNull(PWD_UPDATE_DATETIME);
	}

	protected static final String IBAN = "iban";

	public String getIban() {
		return (String) get(IBAN);
	}

	public void setIban(String pIban) {
		set(IBAN, pIban);
	}

	public boolean isIbanNull() {
		return isNull(IBAN);
	}

	protected static final String PAYCARD_NU = "paycard_nu";

	public String getPaycardNu() {
		return (String) get(PAYCARD_NU);
	}

	public void setPaycardNu(String pPaycardNo) {
		set(PAYCARD_NU, pPaycardNo);
	}

	public boolean isPaycardNuNull() {
		return isNull(PAYCARD_NU);
	}

	protected static final String PAYCARD_TYPE = "paycard_type";

	public String getPaycardType() {
		return (String) get(PAYCARD_TYPE);
	}

	public void setPaycardType(String pPaycardType) {
		set(PAYCARD_TYPE, pPaycardType);
	}

	public boolean isPaycardTypeNull() {
		return isNull(PAYCARD_TYPE);
	}

	public static final String HOME_CITY = "home_city";

	public Integer getHomeCity() {
		return (Integer) get(HOME_CITY);
	}

	public void setHomeCity(Integer pHomeCity) {
		set(HOME_CITY, pHomeCity);
	}

	public boolean isHomeCityNull() {
		return isNull(HOME_CITY);
	}

	public static final String HOME_COUNTRY = "home_country";

	public Integer getHomeCountry() {
		return (Integer) get(HOME_COUNTRY);
	}

	public void setHomeCountry(Integer pHomeCountry) {
		set(HOME_COUNTRY, pHomeCountry);
	}

	public boolean isHomeCountryNull() {
		return isNull(HOME_COUNTRY);
	}

	protected static final String HOME_DISTRICT = "home_district";

	public Integer getHomeDistrict() {
		return (Integer) get(HOME_DISTRICT);
	}

	public void setHomeDistrict(Integer pHomeDistrict) {
		set(HOME_DISTRICT, pHomeDistrict);
	}

	public boolean isHomeDistrictNull() {
		return isNull(HOME_DISTRICT);
	}

	public static final String HOME_ADDRESS = "home_address";

	public String getHomeAddress() {
		return (String) get(HOME_ADDRESS);
	}

	public void setHomeAddress(String pHomeAddress) {
		set(HOME_ADDRESS, pHomeAddress);
	}

	public boolean isHomeAddressNull() {
		return isNull(HOME_ADDRESS);
	}

	public static final String INVOICE_COUNTRY = "invoice_country";

	public Integer getInvoiceCountry() {
		return (Integer) get(INVOICE_COUNTRY);
	}

	public void setInvoiceCountry(Integer pInvoiceCountry) {
		set(INVOICE_COUNTRY, pInvoiceCountry);
	}

	public boolean isInvoiceCountryNull() {
		return isNull(INVOICE_COUNTRY);
	}

	protected static final String INVOICE_CITY = "invoice_city";

	public Integer getInvoiceCity() {
		return (Integer) get(INVOICE_CITY);
	}

	public void setInvoiceCity(Integer pInvoiceCity) {
		set(INVOICE_CITY, pInvoiceCity);
	}

	public boolean isInvoiceCityNull() {
		return isNull(INVOICE_CITY);
	}

	protected static final String INVOICE_DISTRICT = "invoice_district";

	public Integer getInvoiceDistrict() {
		return (Integer) get(INVOICE_DISTRICT);
	}

	public void setInvoiceDistrict(Integer pInvoiceDistrict) {
		set(INVOICE_DISTRICT, pInvoiceDistrict);
	}

	public boolean isInvoiceDistrictNull() {
		return isNull(INVOICE_DISTRICT);
	}

	protected static final String INVOICE_ADDRESS = "invoice_address";

	public String getInvoiceAddress() {
		return (String) get(INVOICE_ADDRESS);
	}

	public void setInvoiceAddress(String pInvoiceAddress) {
		set(INVOICE_ADDRESS, pInvoiceAddress);
	}

	public boolean isInvoiceAddressNull() {
		return isNull(INVOICE_ADDRESS);
	}

	protected static final String LANGUAGE = "language";

	public String getLanguage() {
		return (String) get(LANGUAGE);
	}

	public void setLanguage(String pLanguage) {
		set(LANGUAGE, pLanguage);
	}

	public boolean isLanguageNull() {
		return isNull(LANGUAGE);
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
		checkBigDecimal(CITIZENSHIP_NU);
		checkBigDecimal(BIRTH_DATE);
		checkInteger(BIRTH_CITY);
		checkInteger(TITLE);
		checkInteger(PROFESSION);
		checkInteger(POSITION);
		checkBigDecimal(CHECKIN_DATE);
		checkBigDecimal(CHECKOUT_DATE);
		checkInteger(LOGIN_ERROR_COUNT);
		checkBigDecimal(PWD_UPDATE_DATETIME);
		checkInteger(HOME_COUNTRY);
		checkInteger(HOME_CITY);
		checkInteger(HOME_DISTRICT);
		checkInteger(INVOICE_COUNTRY);
		checkInteger(INVOICE_CITY);
		checkInteger(INVOICE_DISTRICT);
	}

	/***********/

	protected static final String TITLE_NAME = "title_name";

	public String getTitleName() {
		return (String) get(TITLE_NAME);
	}

	protected static final String PROFESSION_NAME = "profession_name";

	public String getProfessionName() {
		return (String) get(PROFESSION_NAME);
	}

	protected static final String POSITION_NAME = "position_name";

	public String getPositionName() {
		return (String) get(POSITION_NAME);
	}

	protected static final String FULL_NAME = "full_name";

	public String getFullName() {
		if (isNull(FULL_NAME)) {
			String name = getName() + " " + getSurname();

			if (!isNull(PROFESSION_NAME))
				name = get(PROFESSION_NAME) + " " + name;
			setField(FULL_NAME, name, false);
		}
		return (String) get(FULL_NAME);
	}

	public void incrementLoginErrorCount() {
		Integer count = getLoginErrorCount();
		if (count == null)
			count = 0;
		count += 1;
		setLoginErrorCount(count);
	}

	protected static final String GROUP_ID = "group_id";

	public Integer getGroupId() {
		return (Integer) get(GROUP_ID);
	}

	public void setGroupId(Integer pGroupId) {
		setField(GROUP_ID, pGroupId, false);
	}

	public void setStatusActive(boolean pStatus) {
		set(STATUS, pStatus ? BaseConstants.ACTIVE.getKey() : BaseConstants.PASSIVE.getKey());
	}

	public boolean isStatusActive() {
		return BaseConstants.ACTIVE.equals(get(STATUS));
	}

	public String getMobilePhoneNu() {
		return (String) get(PHONE_NU2);
	}

	protected static final String HOME_CITY_NAME = "home_city_name";

	public String getHomeCityName() {
		return (String) get(HOME_CITY_NAME);
	}
	
	private transient Common homeCityRef;
	public IReference<Integer>getHomeCityRef(){
		if(homeCityRef == null) {
			homeCityRef = new Common(getHomeCity());
			homeCityRef.setName(getHomeCityName());
		}
		return homeCityRef;
	}
	
	private transient Common positionRef;
	public IReference<Integer>getPositionRef(){
		if(positionRef == null) {
			positionRef = new Common(getPosition());
			positionRef.setName(getPositionName());
		}
		return positionRef;
	}
	
	private transient Common professionRef;
	public IReference<Integer>getProfessionRef(){
		if(professionRef == null) {
			professionRef = new Common(getProfession());
			professionRef.setName(getProfessionName());
		}
		return professionRef;
	}
	
	private transient Common titleRef;
	public IReference<Integer>getTitleRef(){
		if(titleRef == null) {
			titleRef = new Common(getTitle());
			titleRef.setName(getTitleName());
		}
		return titleRef;
	}

}