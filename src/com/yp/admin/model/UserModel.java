package com.yp.admin.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.yp.admin.Constants;
import com.yp.admin.data.LoginHistory;
import com.yp.admin.data.PwdHistory;
import com.yp.admin.data.User;
import com.yp.core.AModel;
import com.yp.core.BaseConstants;
import com.yp.core.FnParam;
import com.yp.core.db.DbCommand;
import com.yp.core.entity.IResult;
import com.yp.core.entity.Result;
import com.yp.core.log.MyLogger;
import com.yp.core.mail.Address;
import com.yp.core.sec.PasswordGenerator;
import com.yp.core.tools.DateTime;
import com.yp.core.tools.StringTool;
import com.yp.core.user.IUser;
import com.yp.core.web.JsonHandler;

public class UserModel extends AModel<User> {

	public static final String Q_Principles = "Principles";
	public static final String Q_USERS1 = "Q_USERS1";
	public static final String Q_USERS2 = "Q_USERS2";
	public static final String Q_EMail = "Q_USERS3";	
	public static final String Q_USERS4 = "Q_USERS4";
	public static final String Q_Telephone = "Q_USERS5";
	public static final String Q_USERS6 = "Q_USERS6";
	public static final String Q_USERS_ALL = "Q_USERS7";
	public static final String Q_USERS7 = "Q_USERS_ALL";
	public static final String Q_LOGIN_HISTORY1= "Q_LOGIN_HISTORY1";
	public static final String Q_PWD_HISTORY1= "Q_PWD_HISTORY1";

	public UserModel() {
		super();
	}

	public UserModel(String pServer) {
		super(pServer);
	}

	public User findByEMail(String pEMail) {
		if (!StringTool.isNull(pEMail)) {
			pEMail = pEMail.toLowerCase(BaseConstants.LOCALE_EN);

			DbCommand query = new DbCommand(Q_EMail, new FnParam("email", pEMail));
			query.setQuery(Constants.getSgl(Q_EMail));

			return findOne(query);
		}
		return null;
	}

	public User findByTelphone(String pTelnu) {
		if (!StringTool.isNull(pTelnu) && StringTool.isNumber(pTelnu)) {
			DbCommand query = new DbCommand(Q_Telephone, new FnParam("phonenumber", pTelnu));
			query.setQuery(Constants.getSgl(query.getName()));

			return findOne(query);
		}
		return null;
	}

	public User findByTC(BigDecimal pCitizinShipNu) {
		if (pCitizinShipNu != null && pCitizinShipNu.intValue() > 100) {
			DbCommand query = new DbCommand(Q_USERS7, new FnParam("tcnu", pCitizinShipNu));
			query.setQuery(Constants.getSgl(query.getName()));
			return findOne(query);
		}
		return null;
	}

	public User find(Integer pUserId) {
		if (pUserId != null && pUserId > -1) {
			DbCommand query = new DbCommand(Q_USERS1, new FnParam("userid", pUserId));
			query.setQuery(Constants.getSgl(query.getName()));

			return findOne(query);
		}
		return null;
	}

	public User find(Integer pUserId, BigDecimal pCitizenShipNu) {
		if (pUserId == null)
			pUserId = -1;
		if (pCitizenShipNu == null)
			pCitizenShipNu = BaseConstants.BIGDECIMAL_MINUS_ONE;
		if (pUserId > -1 || pCitizenShipNu.compareTo(BigDecimal.ZERO) > 0) {
			DbCommand query = new DbCommand(Q_USERS2, new FnParam("userid", pUserId), new FnParam("tcnu", pCitizenShipNu));
			query.setQuery(Constants.getSgl(query.getName()));

			return findOne(query);
		}
		return null;
	}

	public User find(Integer pUserId, String pEmail) {
		if (pUserId == null)
			pUserId = -1;
		if (pEmail == null)
			pEmail = "";
		pEmail = pEmail.toLowerCase(BaseConstants.LOCALE_EN);

		if (pUserId > -1 || !StringTool.isNull(pEmail)) {
			DbCommand query = new DbCommand(Q_USERS4, new FnParam("userid", pUserId), new FnParam("email", pEmail));
			query.setQuery(Constants.getSgl(query.getName()));

			return findOne(query);
		}
		return null;
	}

	public synchronized Long findLoginHistoryId() {
		DbCommand query = new DbCommand(Q_LOGIN_HISTORY1, new FnParam[] {});
		query.setQuery(Constants.getSgl(query.getName()));
		LoginHistory de = (LoginHistory) findOne(query, LoginHistory.class);
		Long idx = null;
		if (de != null) {
			idx = de.getIdx();
		}
		if (idx != null) {
			return idx + 1L;
		}
		return 0L;
	}

	public synchronized Long findPwdHistoryId() {
		DbCommand query = new DbCommand(Q_PWD_HISTORY1, new FnParam[] {});
		query.setQuery(Constants.getSgl(query.getName()));
		PwdHistory de = (PwdHistory) findOne(query, PwdHistory.class);
		Long idx = null;
		if (de != null) {
			idx = de.getIdx();
		}
		if (idx != null) {
			return idx + 1L;
		}
		return 0L;
	}

	private IResult<IUser> checkUser(IUser pUser, String pPassword, String pAppId, String pClientIP) {
		final IResult<IUser> result = new Result<>();
		if (pUser != null) {
			LoginHistory history = null;
			if (pPassword.equals(pUser.getPassword())) {
				if (pAppId != null) {
					history = new LoginHistory(-1L);
					history.setAppId(pAppId);
					history.setUserId(pUser.getId());
					history.setLoginDatetimeDb(DateTime.dbNow());
					history.setClientInfo(pUser);
				}
				pUser.setLoginErrorCount(0);
				pUser.setLastClientInfo(pUser.getId().toString(), pClientIP, new Date());

				result.setMessage(BaseConstants.getString("1017"));
				result.setSuccess(true);
			} else {
				result.setErrorcode(BaseConstants.ERRORCODE_WRONG_PASS);
				pUser.incrementLoginErrorCount();
				result.setMessage(StringTool.format(BaseConstants.getString("1006"), pUser.getLoginErrorCount()));
			}
			this.saveAtomic(pUser, history);
		} else {
			result.setErrorcode(BaseConstants.ERRORCODE_NO_USER);
			result.setMessage(BaseConstants.getString("1009"));
		}
		result.setData(pUser);
		return result;
	}

	public synchronized IResult<IUser> logIn(BigDecimal pTckmlnmr, String pPassword, String pAppId,
			String pClientIP) {
		User user = null;

		user = findByTC(pTckmlnmr);
		return checkUser(user, pPassword, pAppId, pClientIP);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized IResult<IUser> logIn(String pEMail, String pPassword, String pAppId, String pClientIP) throws IOException {
		IResult<IUser> res;
		if(isRemotingEnabled()) {			
			FnParam eposta = new FnParam("email", pEMail);
			FnParam parola = new FnParam("pwd", pPassword);
			FnParam prjkod = new FnParam("appid", pAppId);
			FnParam remadres = new FnParam("remadres", pClientIP);			
			res = ((JsonHandler)handler).executeAtServer("login", User.class, eposta, parola, prjkod, remadres);				
		}else {
			User user =findByEMail(pEMail);
			res = checkUser(user, pPassword, pAppId, pClientIP);
		}

		return res;
	}

	private IResult<IUser> validateFields(User pNewUser) {
		IResult<IUser> res = new Result<>(true, "");
		res.setData((IUser) pNewUser);

		StringBuilder dSb = new StringBuilder();

		if (StringTool.isNull(pNewUser.getEmail())) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("AddAccount.EPostaUyari"));
			dSb.append(BaseConstants.EOL);
		}
		String mString = pNewUser.getPassword();
		if (StringTool.isNull(mString) || mString.length() < 5) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("10041"));
			dSb.append(BaseConstants.EOL);

		}
		mString = pNewUser.getName();
		if (StringTool.isNull(mString)) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("AddAccount.NameWarning"));
			dSb.append(BaseConstants.EOL);
		}
		mString = pNewUser.getSurname();
		if (StringTool.isNull(mString)) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("AddAccount.SurnameWarning"));
			dSb.append(BaseConstants.EOL);
		}
		Date date = pNewUser.getBirthDate();
		if (date == null) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("AddAccount.BirthdayWarning"));
			dSb.append(BaseConstants.EOL);
		}
		Integer mInt = pNewUser.getHomeCity();
		if (mInt == null || mInt < 0) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("AddAccount.HomecityWarning"));
		}
		res.setMessage(dSb.toString());
		return res;

	}

	public IUser checkRemoteUser(IUser pNewUser, IUser pUser) {
		User user = null;
		try {
			UserModel model = new UserModel(BaseConstants.REMSERVER);
			user = model.findByEMail(pNewUser.getEmail());

			if (user == null) {
				user = new User(pNewUser);
				user.setId(-1);
				user.setStatusActive(false);
				user.setCitizenshipNu(BigDecimal.TEN);
				AModel.setLastClientInfo(user, pUser);
				IResult<User> res = model.save(user);
				if (res.isSuccess()) {
					user.setId(res.getData().getId());
				} else
					user = null;
			}

		} catch (Exception e) {
			Logger.getLogger(MyLogger.NAME).log(Level.SEVERE, e.getMessage(), e);
			user = null;
		}
		return (IUser) user;
	}

	private String generatePassword() {
		PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true)
				.useLower(true).useUpper(true).build();
		return passwordGenerator.generate(8);
	}

	public synchronized IResult<IUser> addAccount(String pTitle, IUser pNewUser, IUser pUser) {
		IResult<IUser> res = validateFields((User) pNewUser);
		if (res.isSuccess()) {
			pNewUser.setStatusActive(false);
			int rand = ThreadLocalRandom.current().nextInt(100, 99999999 + 1);
			pNewUser.setLoginErrorCount(rand);
			pNewUser.setCheckinDate(new Date());
			if ("11111.0".equals(pNewUser.getPassword())) {
				pNewUser.setPassword(generatePassword());
			}
			AModel.setLastClientInfo(pNewUser, pUser);
			if (!isRemotingEnabled()) {
				IUser remuser = checkRemoteUser(pNewUser, pUser);
				if (remuser != null) {
					pNewUser.setId(remuser.getId());
				}
			}
			IResult<User> temp = save((User) pNewUser);
			if (temp != null && temp.isSuccess()) {
				res.setSuccess(true);
				pNewUser.setId(temp.getData().getId());
				pNewUser.accept();
				res.setData(pNewUser);
				String message = BaseConstants.getString("AddAccount.Sonuc.Basarili");
				if (!pNewUser.isStatusActive()) {
					User userFrom = new User();
					userFrom.setEmail("bilgi@yonetici.web.tr");
					userFrom.setName("yonetici.web.tr");
					IResult<String> temp2 = sendActivationMail(pTitle, pNewUser, userFrom);
					if (temp2 != null && !temp2.isSuccess())
						message += temp2.getMessage();
				}
				res.setMessage(message);
			} else {
				res.setSuccess(false);
				res.setMessage(BaseConstants.getString("AddAccount.Sonuc.Basarisiz"));
			}
		}
		return res;
	}

	public synchronized IResult<IUser> changePassword(String pEMail, String pPassword, String pNewPassword,
			String pNewPasswordConfirm, IUser pUser, String pClientIP) throws IOException{
		IResult<IUser> result = new Result<>();
		if (!StringTool.isNull(pEMail)) {
			if (pNewPassword.equals(pNewPasswordConfirm)) {
				if (pNewPassword.length() > 4) {
					IResult<IUser> login = logIn(pEMail, pPassword, null, pClientIP);
					if (login.isSuccess()) {
						IUser user = login.getData();
						IResult<IUser> temp = changePassword(user, pNewPassword, pUser, pClientIP);
						result.setSuccess(temp.isSuccess());
						result.setMessage(temp.getMessage());
						result.setData(temp.getData());
					}
				} else
					result.setMessage(BaseConstants.getString("10041"));
			} else
				result.setMessage(BaseConstants.getString("1004"));
		} else
			result.setMessage(BaseConstants.getString("1009"));

		return result;

	}

	public synchronized IResult<IUser> changePassword(BigDecimal pCitizenshipNo, String pPassword, String pNewPassword,
			String pNewPasswordConfirm, IUser pUser, String pClientIP) {
		IResult<IUser> result = new Result<>();
		if (pCitizenshipNo != null && pCitizenshipNo.compareTo(BigDecimal.ZERO) > 0) {
			if (pNewPassword.equals(pNewPasswordConfirm)) {
				if (pNewPassword.length() > 4) {
					IResult<IUser> login = logIn(pCitizenshipNo, pPassword, null, pClientIP);
					if (login.isSuccess()) {
						IUser user = login.getData();
						IResult<IUser> temp = changePassword(user, pNewPassword, pUser, pClientIP);
						result.setSuccess(temp.isSuccess());
						result.setMessage(temp.getMessage());
						result.setData(temp.getData());
					}
				} else
					result.setMessage(BaseConstants.getString("10041"));
			} else
				result.setMessage(BaseConstants.getString("1004"));
		} else
			result.setMessage(BaseConstants.getString("1009"));

		return result;
	}

	private synchronized IResult<IUser> changePassword(IUser pPwdUser, String pNewPassword, IUser pUser,
			String pClientIP) {
		IResult<IUser> result = new Result<>();
		if (!pNewPassword.equals(pPwdUser.getPassword())) {
			pPwdUser.setPassword(pNewPassword);
			pPwdUser.setLastClientInfo(pUser.getEmail(), pClientIP, new Date());
			PwdHistory history = new PwdHistory();
			history.setUserId(pPwdUser.getId());
			history.setPassword(pPwdUser.getPassword());
			history.setUpdateUser((User) pUser);
			history.setClientInfo(pUser);

			IResult<String> dGec = saveAtomic(pPwdUser, history);

			if (dGec.isSuccess()) {
				pPwdUser.setPassword(pNewPassword);
				result.setSuccess(true);
				result.setMessage(BaseConstants.getString("1003"));
			} else {
				result.setMessage(dGec.getMessage());
				result.setSuccess(false);
			}
			result.setData(pPwdUser);
		} else
			result.setMessage(BaseConstants.getString("1005"));

		return result;
	}

	public List<User> findByName(String pNameSurname) {
		List<User> list = null;
		if (!StringTool.isNull(pNameSurname)) {
			DbCommand query = new DbCommand(Q_USERS6, new FnParam("name", "%" + pNameSurname + "%"),
					new FnParam("surname", "%" + pNameSurname + "%"));
			query.setQuery(Constants.getSgl(query.getName()));
			return findAny(query);
		}
		return list;

	}

	public synchronized IResult<IUser> save(IUser user, IUser pUser) {
		IResult<IUser> res = validateFields((User) user);
		if (res.isSuccess()) {
			setLastClientInfo(user, pUser);
			IResult<User> temp = save((User) user);
			if (temp != null && temp.isSuccess()) {
				res.setSuccess(true);
				user.checkValues();
				user.accept();
				res.setMessage(BaseConstants.getString("FrmSahis.Sonuc.Basarili"));
			} else {
				res.setSuccess(false);
				res.setMessage(BaseConstants.getString("FrmSahis.Sonuc.Basarisiz"));
			}

			res.setData(user);
		}
		return res;
	}

	public IResult<String> sendActivationMail(String pTitle, IUser pUserTo, IUser pUserFrom) {
		User user = (User) pUserTo;
		Address from = new Address(pUserFrom.getEmail(), pUserFrom.getName());
		List<Address> to = new ArrayList<>();
		to.add(new Address(pUserTo.getEmail(), pUserTo.getName()));

		String subject = pTitle + ", " + BaseConstants.getString("SendMail.Activation.Subject");
		StringBuilder dBody = new StringBuilder();
		dBody.append("<b><font size='3'>");
		dBody.append("<a href='");
		dBody.append(hosturl);

		dBody.append("/UserModel/activate/");
		dBody.append(user.getId());
		dBody.append("_");
		dBody.append(user.getLoginErrorCount());
		dBody.append("/");

		dBody.append("'>").append(BaseConstants.getString("SendMail.Activation.Body")).append("</a></font></b>");

		return sendMail(from, from, subject, dBody.toString(), to, null, null, null);
	}

	public IResult<String> sendPasswordMail(String pTitle, IUser pUserTo, IUser pUserFrom) {
		User userfrom = (User) pUserFrom;
		User user = findByEMail(pUserTo.getEmail());
		if (user != null) {
			if (userfrom == null) {
				userfrom = new User();
				userfrom.setEmail("bilgi@yonetici.web.tr");
				userfrom.setName("yonetici.web.tr");
			}
			Address from = new Address(userfrom.getEmail(), userfrom.getName());
			ArrayList<Address> to = new ArrayList<>();
			to.add(new Address(pUserTo.getEmail(), pUserTo.getName()));

			String subject = pTitle + ", " + BaseConstants.getString("SendPassword.Subject");
			StringBuilder dBody = new StringBuilder();
			dBody.append("<b><font size='3'>");
			dBody.append(BaseConstants.getString("SendPassword.Subject"));
			dBody.append(" :");
			dBody.append("<br>");
			dBody.append(user.getPassword());
			dBody.append("<br></font></b>");
			if (isRemotingEnabled())
				return sendMail(from, from, subject, dBody.toString(), to, null, null, null);
			else
				return new UserModel(BaseConstants.REMSERVER).sendMail(from, from, subject, dBody.toString(), to, null,
						null, null);
		} else {
			IResult<String> res = new Result<>(false, BaseConstants.getString("1009"));
			res.setErrorcode(BaseConstants.ERRORCODE_NO_USER);
			return res;
		}
	}

}
