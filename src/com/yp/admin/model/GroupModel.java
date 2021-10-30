package com.yp.admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yp.admin.Constants;
import com.yp.admin.data.GroupAppFunc;
import com.yp.admin.data.GroupAppFuncsHistory;
import com.yp.admin.data.GroupUser;
import com.yp.admin.data.GroupUsersHistory;
import com.yp.admin.data.Group;
import com.yp.admin.data.User;
import com.yp.core.AModel;
import com.yp.core.BaseConstants;
import com.yp.core.FnParam;
import com.yp.core.db.DbCommand;
import com.yp.core.entity.IDataEntity;
import com.yp.core.entity.IResult;
import com.yp.core.entity.Result;
import com.yp.core.tools.StringTool;
import com.yp.core.user.IUser;

public class GroupModel extends AModel<Group> {

	public static final String Q_GROUPS1 = "Q_GROUPS1";
	public static final String Q_GROUPS2 = "Q_GROUPS2";
	public static final String Q_GROUPS5 = "Q_GROUPS5";
	public static final String Q_GROUP_USERS1 = "Q_GROUP_USERS1";
	public static final String Q_GROUP_FUNCS1 = "Q_GROUP_FUNCS1";
	public static final String Q_GROUP_USERS_HISTORY1= "Q_GROUP_USERS_HISTORY1";
	public static final String Q_GROUP_APP_FUNCS_HISTORY1= "Q_GROUP_APP_FUNCS_HISTORY1";
	

	public synchronized Integer findGroupId() {
		final DbCommand query = new DbCommand(Q_GROUPS2, new FnParam[0]);
		query.setQuery(Constants.getSgl(query.getName()));
		final Group de = findOne(query);
		Integer res = null;
		if (de != null) {
			res = de.getId();
		}
		if (res != null) {
			return res + 1;
		}
		return 0;
	}

	public List<Group> findUserGroupList(final Integer pUserId) {
		final DbCommand query = new DbCommand(Q_GROUPS1, 
				new FnParam("user_id", pUserId));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query);
	}

	public List<Group> findAppGroupList(final Integer pUserId, final String pAppId) {
		final DbCommand query = new DbCommand(Q_GROUPS5, 
				new FnParam("app_id", pAppId),
				new FnParam("user_id", pUserId));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query);
	}

	public Long findGroupUsersHistoryId() {
		final DbCommand query = new DbCommand(Q_GROUP_USERS_HISTORY1, new FnParam[0]);
		query.setQuery(Constants.getSgl(query.getName()));
		final GroupUsersHistory de = (GroupUsersHistory) this.findOne(query, GroupUsersHistory.class);
		Long idx = null;
		if (de != null) {
			idx = de.getIdx();
		}
		if (idx != null) {
			return idx + 1L;
		}
		return 0L;
	}

	public synchronized Long findGroupAppFuncsHistoryId() {
		final DbCommand query = new DbCommand(Q_GROUP_APP_FUNCS_HISTORY1, new FnParam[0]);
		query.setQuery(Constants.getSgl(query.getName()));
		final GroupAppFuncsHistory de = (GroupAppFuncsHistory) this.findOne(query,
				GroupAppFuncsHistory.class);
		Long idx = null;
		if (de != null) {
			idx = de.getIdx();
		}
		if (idx != null) {
			return idx + 1L;
		}
		return 0L;
	}

	public synchronized IResult<String> deleteUserFromGroup(final Integer pGroupId, final Integer[] pUserIds,
			final IUser pUser, final String pClientIP) {
		IResult<String> result = null;
		Long idx = findGroupUsersHistoryId();
		final Date datetime = new Date();
		final Integer self = pUser.getId();
		final String email = pUser.getEmail();
		final List<IDataEntity> deleteList = new ArrayList<>();
		final List<IDataEntity> addHistoryList = new ArrayList<>();
		for (int dI = 0; dI < pUserIds.length; ++dI) {
			if (!self.equals(pUserIds[dI])) {
				final GroupUser delete = new GroupUser(pGroupId, pUserIds[dI]);
				delete.setLastClientInfo(email, pClientIP, datetime);
				delete.accept();
				delete.delete();
				deleteList.add(delete);

				final GroupUsersHistory history = new GroupUsersHistory(idx, delete);
				history.setUpdateUser((User) pUser, GroupUsersHistory.UPDATE_MODE_DELETE);
				history.setClientInfo(email, pClientIP, datetime);
				addHistoryList.add(history);
				++idx;
			}
		}
		if (!deleteList.isEmpty()) {
			return saveAtomic(deleteList, addHistoryList);
		}
		return result;
	}

	public synchronized IResult<String> deleteUserFromGroup(final String pAppId, final Integer pUserId,
			final IUser pUser, final String pClientIP) {
		IResult<String> result = null;
		final Date datetime = new Date();
		final Integer self = pUser.getId();
		final String email = pUser.getEmail();
		if (!self.equals(pUserId)) {
			final List<Group> groupList = findAppGroupList(pUserId, pAppId);
			if (groupList != null && !groupList.isEmpty()) {
				Long idx = this.findGroupUsersHistoryId();
				final List<IDataEntity> deleteList = new ArrayList<>();
				final List<IDataEntity> addHistoryList = new ArrayList<>();
				for (final Group v : groupList) {
					final GroupUser delete = new GroupUser(v.getId(), pUserId);
					delete.setLastClientInfo(email, pClientIP, datetime);
					delete.accept();
					delete.delete();
					deleteList.add(delete);

					final GroupUsersHistory history = new GroupUsersHistory(idx, delete);
					history.setUpdateUser((User) pUser, GroupUsersHistory.UPDATE_MODE_DELETE);
					history.setClientInfo(email, pClientIP, datetime);
					addHistoryList.add(history);
					++idx;
				}
				if (!deleteList.isEmpty()) {
					return saveAtomic(deleteList, addHistoryList);
				}
			}
		}
		return result;
	}

	public IResult<String> addUserToGroup(final Integer pGroupId, final Integer[] pUserIds, final IUser pUser,
			final String pClientIP) {
		IResult<String> result = null;
		Long idx = findGroupUsersHistoryId();
		final Date datetime = new Date();
		final String email = pUser.getEmail();
		final List<IDataEntity> addList = new ArrayList<>();
		final List<IDataEntity> addHistoryList = new ArrayList<>();
		final List<IDataEntity> groupUsersList = findGroupUsers(pGroupId);
		for (int dI = 0; dI < pUserIds.length; ++dI) {
			if (!containsUserInGroup(groupUsersList, pUserIds[dI])) {
				final GroupUser groupuser = new GroupUser(pGroupId, pUserIds[dI]);
				groupuser.setLastClientInfo(email, pClientIP, datetime);
				addList.add(groupuser);

				final GroupUsersHistory history = new GroupUsersHistory(idx, groupuser);
				history.setUpdateUser((User) pUser, GroupUsersHistory.UPDATE_MODE_ADD);
				history.setClientInfo(email, pClientIP, datetime);
				addHistoryList.add(history);
				++idx;
			}
		}
		if (!addList.isEmpty()) {
			return saveAtomic(addList, addHistoryList);
		}
		return result;
	}

	public synchronized IResult<Group> save(final Group pGroup, final IUser pUser) {
		final IResult<Group> result = checkFields(pGroup);
		if (result.isSuccess()) {
			GroupUser groupUser = null;
			GroupUsersHistory groupUserHistory = null;
			if (pGroup.isNew()) {
				final Integer groupId = findGroupId();
				pGroup.setId(groupId);
				pGroup.setHierarchy(groupId.toString());
				pGroup.setGroupType(Group.GROUP_TYPE_USER);
				groupUser = new GroupUser(groupId, pUser.getId());
				setLastClientInfo(groupUser, pUser);

				groupUserHistory = new GroupUsersHistory(findGroupUsersHistoryId(), groupUser);
				groupUserHistory.setUpdateUser((User) pUser, GroupUsersHistory.UPDATE_MODE_ADD);
				groupUserHistory.setClientInfo(pUser);
			}
			setLastClientInfo(pGroup, pUser);
			final IResult<String> temp = saveAtomic(pGroup, groupUser, groupUserHistory);
			if (temp != null) {
				if (temp.isSuccess())
					pGroup.accept();
				result.setSuccess(temp.isSuccess());
				result.setMessage(temp.getMessage());
			} else
				result.setMessage(BaseConstants.MESSAGE_SAVE_ERROR);
			result.setData(pGroup);
		}
		return result;
	}

	private boolean containsUserInGroup(final List<IDataEntity> pGroupUsersList, final Integer pUserId) {
		if (!BaseConstants.isEmpty(pGroupUsersList)) {
			for (final IDataEntity groupUser : pGroupUsersList) {
				if (pUserId.equals(((GroupUser) groupUser).getUserId())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean containsFnInGroup(final List<IDataEntity> pGroupFnList, final String pFuncId) {
		if (!BaseConstants.isEmpty(pGroupFnList)) {
			for (final IDataEntity groupFunc : pGroupFnList) {
				if (pFuncId.equals(((GroupAppFunc) groupFunc).getAppFuncId())) {
					return true;
				}
			}
		}
		return false;
	}

	public List<IDataEntity> findGroupUsers(final Integer pGroupId) {
		final DbCommand query = new DbCommand(Q_GROUP_USERS1, new FnParam("groupid", pGroupId));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query, GroupUser.class);
	}

	public List<IDataEntity> findGroupFuncs(final Integer pGroupId) {
		final DbCommand query = new DbCommand(Q_GROUP_FUNCS1, new FnParam("groupid", pGroupId));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query, GroupAppFunc.class);
	}

	private IResult<Group> checkFields(final Group pGroup) {
		final IResult<Group> res = new Result<>(true, "");
		final StringBuilder dSb = new StringBuilder();
		String mString = pGroup.getName();
		if (StringTool.isNull(mString) || mString.length() < 3) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("FrmGroup.Warning.Name"));
			dSb.append(BaseConstants.EOL);
		}
		mString = pGroup.getAppId();
		if (StringTool.isNull(mString) || mString.length() < 3) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("FrmAppAUL.Warning.Name"));
			dSb.append(BaseConstants.EOL);
		}
		res.setMessage(dSb.toString());
		return res;
	}

	public IResult<String> addFnToGroup(final Integer pGroupId, final String[] pFuncs, final IUser pUser,
			final String pClientIP) {
		IResult<String> result = null;
		Long idx = findGroupAppFuncsHistoryId();
		final Date datetime = new Date();
		final String email = pUser.getEmail();
		final List<IDataEntity> addList = new ArrayList<>();
		final List<IDataEntity> addHistoryList = new ArrayList<>();
		final List<IDataEntity> groupFnList = findGroupFuncs(pGroupId);
		for (int dI = 0; dI < pFuncs.length; ++dI) {
			if (!containsFnInGroup(groupFnList, pFuncs[dI])) {
				final GroupAppFunc groupFuncs = new GroupAppFunc(pGroupId, pFuncs[dI]);
				groupFuncs.setLastClientInfo(email, pClientIP, datetime);
				addList.add(groupFuncs);
				final GroupAppFuncsHistory history = new GroupAppFuncsHistory(idx, groupFuncs);
				history.setUpdateUser((User) pUser, GroupAppFuncsHistory.UPDATE_MODE_ADD);
				history.setClientInfo(email, pClientIP, datetime);
				addHistoryList.add(history);
				++idx;
			}
		}
		if (!addList.isEmpty()) {
			return saveAtomic(addList, addHistoryList);
		}
		return result;
	}

	public synchronized IResult<String> deleteFnFromGroup(final Integer pGroupId, final String[] pFuncs,
			final IUser pUser, final String pClientIP) {
		IResult<String> dSnc = null;
		Long idx = findGroupAppFuncsHistoryId();
		final Date datetime = new Date();
		final String email = pUser.getEmail();
		final List<IDataEntity> deleteList = new ArrayList<>();
		final List<IDataEntity> addHistoryList = new ArrayList<>();
		for (int dI = 0; dI < pFuncs.length; ++dI) {
			final GroupAppFunc delete = new GroupAppFunc(pGroupId, pFuncs[dI]);
			delete.setLastClientInfo(email, pClientIP, datetime);
			delete.accept();
			delete.delete();
			deleteList.add(delete);
			final GroupAppFuncsHistory history = new GroupAppFuncsHistory(idx, delete);
			history.setUpdateUser((User) pUser, GroupAppFuncsHistory.UPDATE_MODE_DELETE);
			history.setClientInfo(email, pClientIP, datetime);
			addHistoryList.add(history);
			++idx;
		}
		if (!deleteList.isEmpty()) {
			dSnc = saveAtomic(deleteList, addHistoryList);
		}
		return dSnc;
	}
}
