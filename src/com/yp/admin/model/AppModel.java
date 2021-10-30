package com.yp.admin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.yp.admin.Constants;
import com.yp.admin.data.GroupUser;
import com.yp.admin.data.GroupUsersHistory;
import com.yp.admin.data.Group;
import com.yp.admin.data.App;
import com.yp.admin.data.User;
import com.yp.core.AModel;
import com.yp.core.BaseConstants;
import com.yp.core.FnParam;
import com.yp.core.db.DbCommand;
import com.yp.core.entity.IResult;
import com.yp.core.entity.Result;
import com.yp.core.log.MyLogger;
import com.yp.core.ref.IReference;
import com.yp.core.ref.RefContainer;
import com.yp.core.ref.Reference;
import com.yp.core.tools.StringTool;
import com.yp.core.user.IUser;

public class AppModel extends AModel<App> {
	public static final String Q_APP1 = "SRGPRJKOD1";
	public static final String Q_APPS6 = "Q_APPS6";
	public static final String Q_VERSION_NOTES = "Version_Notes";
	public static final String Q_VERSIONS = "Versions";

	public static final IReference<String> TARGET_WEB = new Reference<>("targetWEB", BaseConstants.getString("TARGET.WEB"));
	public static final IReference<String> TARGET_WIN = new Reference<>("targetWIN", BaseConstants.getString("TARGET.WIN"));
	public static final IReference<String> TARGET_MOBILE = new Reference<>("targetMOBILE", BaseConstants.getString("TARGET.MOBILE"));

	public static final RefContainer<String> TARGET = new RefContainer<>(TARGET_WEB, TARGET_WIN, TARGET_MOBILE);

	public AppModel() {
		super();
	}

	public AppModel(String pServer) {
		super(pServer);
	}

	public List<IReference<String>> getTargetList() {
		List<IReference<String>> targetList = new ArrayList<>(3);
		targetList.add(TARGET_WIN);
		targetList.add(TARGET_WEB);
		targetList.add(TARGET_MOBILE);
		return targetList;
	}

	public List<App> findAll() {
		DbCommand query = new DbCommand(Q_APP1, new FnParam[] {});
		query.setQuery(Constants.getSgl(query.getName()));

		return findAny(query);
	}

	public List<App> findApps(Integer pUserId) {
		DbCommand query = new DbCommand(Q_APPS6,  new FnParam("userid", pUserId));
		query.setQuery(Constants.getSgl(query.getName()));

		return findAny(query);
	}

	public List<App> findAppVersionNotes(final String pAppId, final String pVersion) {
		final DbCommand query = new DbCommand(Q_VERSION_NOTES, new FnParam("appid", pAppId),
				new FnParam("version", pVersion));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query);
	}

	public List<App> findAppVersions(final String pAppId) {
		final DbCommand query = new DbCommand(Q_VERSIONS, new FnParam("appid", pAppId));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query);
	}

	private IResult<App> validateFields(App pApp) {
		IResult<App> res = new Result<>(true, "");
		StringBuilder dSb = new StringBuilder();
		String mString = pApp.getId();
		if (StringTool.isNull(mString) || mString.length() < 3) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("FrmAppAUL.Warning.Id"));
			dSb.append(BaseConstants.EOL);
		}

		mString = pApp.getName();
		if (StringTool.isNull(mString) || mString.length() < 3) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("FrmAppAUL.Warning.Name"));
			dSb.append(BaseConstants.EOL);
		}
		res.setMessage(dSb.toString());
		return res;
	}

	public synchronized IResult<App> save(App pApp, IUser pUser) {
		IResult<App> result = validateFields(pApp);
		if (result.isSuccess()) {
			try {
				GroupModel groupModel = new GroupModel();
				setLastClientInfo(pApp, pUser);

				Group group = null;
				GroupUser groupUser = null;
				GroupUsersHistory history = null;

				if (pApp.isNew()) {					
					group = new Group(groupModel.findGroupId());
					group.setAppId(pApp.getId());
					group.setName(String.format("%s->%s", Constants.getString("FrmGroup.Admin"), pApp.getName()));
					group.setHierarchy(group.getId().toString());
					group.setGroupType(Group.GROUP_TYPE_ADMIN);
					group.setLastClientInfo(pApp);

					groupUser = new GroupUser(group.getId(), pUser.getId());
					groupUser.setLastClientInfo(group);

					history = new GroupUsersHistory(groupModel.findGroupUsersHistoryId(), groupUser);
					history.setUpdateUser((User) pUser, GroupUsersHistory.UPDATE_MODE_ADD);
					history.setClientInfo(pUser);
				}

				final IResult<String> temp = saveAtomic(pApp, group, groupUser, history);
				if (temp != null) {
					if (temp.isSuccess())
						pApp.accept();
					result.setSuccess(temp.isSuccess());
					result.setMessage(temp.getMessage());
				} else
					result.setMessage(BaseConstants.MESSAGE_SAVE_ERROR);
				result.setData(pApp);

			} catch (Exception e) {
				result.setSuccess(false);
				result.setMessage(BaseConstants.MESSAGE_SAVE_ERROR);
				result.setData(pApp);
				Logger.getLogger(MyLogger.NAME).log(Level.SEVERE, e.getMessage(), e);
			}

		}
		return result;

	}
}
