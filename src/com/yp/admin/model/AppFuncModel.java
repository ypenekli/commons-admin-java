package com.yp.admin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.yp.admin.Constants;
import com.yp.admin.data.GroupAppFunc;
import com.yp.admin.data.GroupAppFuncsHistory;
import com.yp.admin.data.AppFunc;
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

public class AppFuncModel extends AModel<AppFunc> {

	public static final String Q_APPFUNCS1 = "Q_APPFUNCS1";
	public static final String Q_APPFUNCS4 = "Q_APPFUNCS4";
	public static final String Q_APPFUNCS5 = "Q_APPFUNCS5";
	public static final String Q_APPFUNCS6 = "Q_APPFUNCS6";
	public static final String Q_VERSION_NOTES = "Version_Notes";
	public static final String Q_VERSIONS = "Versions";

	public static final IReference<String> TARGET_UPDATE = new Reference<>("targetAUDL",
			BaseConstants.getString("TARGET.UPDATE"));
	public static final IReference<String> TARGET_EDIT = new Reference<>("targetAUD",
			BaseConstants.getString("TARGET.EDIT"));
	public static final IReference<String> TARGET_LIST = new Reference<>("targetL",
			BaseConstants.getString("TARGET.LIST"));
	public static final IReference<String> TARGET_VIEW = new Reference<>("targetView",
			BaseConstants.getString("TARGET.VIEW"));

	public static final RefContainer<String> TARGET = new RefContainer<>(TARGET_UPDATE, TARGET_EDIT, TARGET_LIST,
			TARGET_VIEW);

	public AppFuncModel() {
		super();
	}

	public AppFuncModel(String pServer) {
		super(pServer);
	}

	public List<IReference<String>> getTargetList() {
		List<IReference<String>> targetList = new ArrayList<>(5);
		targetList.add(TARGET_UPDATE);
		targetList.add(TARGET_LIST);
		targetList.add(TARGET_EDIT);
		targetList.add(TARGET_UPDATE);
		targetList.add(TARGET_VIEW);
		return targetList;
	}

	public List<AppFunc> findAppFuncs(String pParentId) {
		DbCommand query = new DbCommand(Q_APPFUNCS1, new FnParam("parent_id", pParentId));
		query.setQuery(Constants.getSgl(query.getName()));

		return findAny(query);
	}

	public List<AppFunc> findUserAppFuncs(final Integer pUserId, final String pAppId) {
		final DbCommand query = new DbCommand(Q_APPFUNCS5, new FnParam("app_id", pAppId), new FnParam("app_id", pAppId),
				new FnParam("user_id", pUserId));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query);
	}

	public List<AppFunc> findGroupAppFuncs(final Integer pGroupId, final String pAppId) {
		final DbCommand query = new DbCommand(Q_APPFUNCS4, new FnParam("app_id", pAppId),
				new FnParam("groupid", pGroupId));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query);
	}

	private IResult<AppFunc> validateFields(AppFunc pApp) {
		IResult<AppFunc> res = new Result<>(true, "");
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

	public synchronized IResult<AppFunc> save(AppFunc pAppFunc, Integer pGroupId, IUser pUser) {
		IResult<AppFunc> result = validateFields(pAppFunc);
		if (result.isSuccess()) {
			try {
				GroupModel groupModel = new GroupModel();
				setLastClientInfo(pAppFunc, pUser);

				AppFunc parent = null;
				GroupAppFunc groupFuncs = null;
				GroupAppFuncsHistory history = null;

				if (pAppFunc.isNew()) {
					groupFuncs = new GroupAppFunc(pGroupId, pAppFunc.getId());
					groupFuncs.setLastClientInfo(pUser);

					history = new GroupAppFuncsHistory(groupModel.findGroupAppFuncsHistoryId(), groupFuncs);
					history.setUpdateUser((User) pUser, GroupAppFuncsHistory.UPDATE_MODE_ADD);
					history.setClientInfo(pAppFunc);
					if (!pAppFunc.isRoot()) {
						parent = new AppFunc(pAppFunc.getParentId());
						parent.accept();
						parent.setLeaf(false);
						parent.setName(pAppFunc.getParentName());
						parent.setLastClientInfo(pUser);
					}
				}

				final IResult<String> temp = saveAtomic(pAppFunc, groupFuncs, history, parent);
				if (temp != null) {
					if (temp.isSuccess())
						pAppFunc.accept();
					result.setSuccess(temp.isSuccess());
					result.setMessage(temp.getMessage());
				} else
					result.setMessage(BaseConstants.MESSAGE_SAVE_ERROR);
				result.setData(pAppFunc);

			} catch (Exception e) {
				result.setSuccess(false);
				result.setMessage(BaseConstants.MESSAGE_SAVE_ERROR);
				result.setData(pAppFunc);
				Logger.getLogger(MyLogger.NAME).log(Level.SEVERE, e.getMessage(), e);
			}

		}
		return result;

	}

}
