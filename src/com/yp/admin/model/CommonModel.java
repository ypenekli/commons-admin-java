package com.yp.admin.model;

import java.util.List;

import com.yp.admin.Constants;
import com.yp.admin.data.Common;
import com.yp.core.AModel;
import com.yp.core.BaseConstants;
import com.yp.core.FnParam;
import com.yp.core.db.DbCommand;
import com.yp.core.db.Pager;
import com.yp.core.entity.IResult;
import com.yp.core.entity.Result;
import com.yp.core.tools.StringTool;
import com.yp.core.user.IUser;

public class CommonModel extends AModel<Common> {
	public static final String Q_COMMONS_PARENT_ID1 = "Q_COMMONS_PARENT_ID1";
	public static final String Q_COMMONS1 = "Q_COMMONS1";

	public IResult<List<Common>> findByParent(final Integer pParentId, Pager pPager) {
		final DbCommand query = new DbCommand(Q_COMMONS_PARENT_ID1, new FnParam("parent_id", pParentId));
		query.setQuery(Constants.getSgl(query.getName()));
		return this.findAny(query, pPager);
	}

	public IResult<Common> save(final Common pCommon, final IUser pUser) {
		final IResult<Common> res = this.validateFields(pCommon);
		if (res.isSuccess()) {
			setLastClientInfo(pCommon, pUser);
			return super.save(pCommon);
		}
		return res;
	}

	private IResult<Common> validateFields(final Common pCommon) {
		final IResult<Common> res = new Result<>(true, "");
		final StringBuilder dSb = new StringBuilder();
		String mString = pCommon.getName();
		if (StringTool.isNull(mString) || mString.length() < 3) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("FrmAppAUL.Warning.Id"));
			dSb.append(BaseConstants.EOL);
		}
		mString = pCommon.getAbrv();
		if (StringTool.isNull(mString) || mString.length() < 3) {
			res.setSuccess(false);
			dSb.append(BaseConstants.getString("FrmAppAUL.Warning.Name"));
			dSb.append(BaseConstants.EOL);
		}
		res.setMessage(dSb.toString());
		return res;
	}

	public Integer getCommonsId(Integer pGroupCode, Integer pParentId) {
		final DbCommand query = new DbCommand(Q_COMMONS1, new FnParam("parent_id", pGroupCode),
				new FnParam("parent_id", pParentId), new FnParam("parent_id", pParentId));
		query.setQuery(Constants.getSgl(query.getName()));
		final Common de = this.findOne(query);
		Integer res = null;
		if (de != null) {
			res = de.getId();
		}
		if (res != null) {
			return res + 1;
		}
		return 0;
	}

}
