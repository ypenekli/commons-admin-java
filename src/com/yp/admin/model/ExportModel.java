package com.yp.admin.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.yp.admin.Constants;
import com.yp.admin.data.Export;
import com.yp.core.AModel;
import com.yp.core.BaseConstants;
import com.yp.core.FnParam;
import com.yp.core.db.DbCommand;
import com.yp.core.db.DbConnInfo;
import com.yp.core.db.DbHandler;
import com.yp.core.entity.IDataEntity;
import com.yp.core.ref.IReference;
import com.yp.core.ref.Reference;
import com.yp.core.tools.ResourceWalker;

public class ExportModel extends AModel<Export> {

	public static final String Q_EXPORT1 = "Q_EXPORT1";
	public static final String Q_EXPORT2 = "Q_EXPORT2";

	public List<Export> getExportList(String pSourceSchemaName, String pTargetSchemaName) {
		final DbCommand query = new DbCommand(Q_EXPORT2, new FnParam("kynsema", pSourceSchemaName),
				new FnParam("hdfsema", pTargetSchemaName));
		query.setQuery(Constants.getSgl(query.getName()));

		return findAny(query);
	}

	public DbConnInfo getDefaultDb() {
		String defaultServer = BaseConstants.getConfig(BaseConstants.SERVER);
		String id = BaseConstants.getConfig(defaultServer + ".KEY");
		String def = BaseConstants.getConfig(defaultServer + ".VALUE");

		DbConnInfo connConf = new DbConnInfo(id, def);
		connConf.setDbUrl(BaseConstants.getConfig(defaultServer + DbHandler.DB_URL));
		connConf.setDbDriver(BaseConstants.getConfig(defaultServer + DbHandler.DB_DRIVER));
		connConf.setDbUser(BaseConstants.getConfig(defaultServer + DbHandler.DB_USER));
		connConf.setDbPassword(BaseConstants.getConfig(defaultServer + DbHandler.DB_PASSWORD));
		connConf.setDbSeperator(BaseConstants.getConfig(defaultServer + DbHandler.DB_SCHEMASEPERATOR));
		connConf.setDefaultDb(true);

		return connConf;
	}

	public DbConnInfo getRemoteDb() {
		String remoteServer = BaseConstants.getConfig(BaseConstants.REMSERVER);
		String id = BaseConstants.getConfig(remoteServer + ".KEY");
		String def = BaseConstants.getConfig(remoteServer + ".VALUE");

		DbConnInfo connConf = new DbConnInfo(id, def);
		connConf.setDbUrl(BaseConstants.getConfig(remoteServer + DbHandler.DB_URL));
		connConf.setDbDriver(BaseConstants.getConfig(remoteServer + DbHandler.DB_DRIVER));
		connConf.setDbUser(BaseConstants.getConfig(remoteServer + DbHandler.DB_USER));
		connConf.setDbPassword(BaseConstants.getConfig(remoteServer + DbHandler.DB_PASSWORD));
		connConf.setDbSeperator(BaseConstants.getConfig(remoteServer + DbHandler.DB_SCHEMASEPERATOR));
		connConf.setDefaultDb(true);

		return connConf;
	}

	public List<DbConnInfo> getDbList() {

		List<DbConnInfo> dbList = new ArrayList<>();
		String defaultServer = BaseConstants.getConfig(BaseConstants.SERVER);
		int i = 1;
		String server = BaseConstants.SERVER + i;
		String id = BaseConstants.getConfig(server + ".KEY");
		String def = BaseConstants.getConfig(server + ".VALUE");
		while (id != null) {
			server = BaseConstants.SERVER + i;
			DbConnInfo connConf = new DbConnInfo(id, def);
			connConf.setDbUrl(BaseConstants.getConfig(server + DbHandler.DB_URL));
			connConf.setDbDriver(BaseConstants.getConfig(server + DbHandler.DB_DRIVER));
			connConf.setDbUser(BaseConstants.getConfig(server + DbHandler.DB_USER));
			connConf.setDbPassword(BaseConstants.getConfig(server + DbHandler.DB_PASSWORD));
			connConf.setDbSeperator(BaseConstants.getConfig(server + DbHandler.DB_SCHEMASEPERATOR));
			connConf.setDefaultDb(server.equals(defaultServer));			
			dbList.add(connConf);
			i++;
			id = BaseConstants.getConfig(BaseConstants.SERVER + i + ".KEY");
			def = BaseConstants.getConfig(BaseConstants.SERVER + i + ".VALUE");
		}
		return dbList;

	}

	public List<IReference<String>> getDbResourceList() {
		List<IReference<String>> resourceList = null;
		String file = null;
		try {
			List<Path> resources = new ResourceWalker().getResourceFiles("/sql/creates", ".sql");
			if (!BaseConstants.isEmpty(resources)) {
				resourceList = new ArrayList<>();
				for (Path dPath : resources) {
					file = dPath.getFileName().toString();
					resourceList.add(new Reference<>(file, file, dPath.toString()));
				}
			} else
				resourceList = new ArrayList<>(1);

			file = "-1";
			resourceList.add(new Reference<>(file, BaseConstants.getString("FrmConfig.FindDbResource")));

		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return resourceList;
	}

	public boolean saveConfig(List<DbConnInfo> pList) {
		for (DbConnInfo vs : pList) {
			String server = vs.getKey();
			BaseConstants.setConfig(server + ".DBURL", vs.getDbUrl());
			BaseConstants.setConfig(server + ".DBDRIVER", vs.getDbDriver());
			BaseConstants.setConfig(server + ".DBUSER", vs.getDbUser());
			BaseConstants.setConfig(server + ".DBPASSWORD", vs.getDbPassword());
			BaseConstants.setConfig(server + ".DBSCHEMASEPERATOR", vs.getDbSeperator());
			if (vs.isDefaultDb())
				BaseConstants.setConfig(BaseConstants.SERVER, vs.getKey());
		}
		BaseConstants.saveConfig();
		return true;
	}

	public List<Export> findDbTables(String pSourceSchema) {
		List<IDataEntity> list = findDbTables(pSourceSchema, pSourceSchema);
		List<Export> tableList = null;
		if (!BaseConstants.isEmpty(list)) {
			String tableName = "TABLE_NAME";
			if (list.get(0).isNull(tableName))
				tableName = "NAME";
			tableList = new ArrayList<>(list.size());
			int i = 0;
			for (IDataEntity vs : list) {
				Export newVs = new Export(pSourceSchema, (String) vs.get(tableName));
				newVs.setIdx(++i);
				newVs.checkValues();
				tableList.add(newVs);
			}
		}
		return tableList;
	}

	public int findDbTableCount(String pSchemaName, String pTableName) {
		String formatedquery = String.format(Constants.getSgl(Q_EXPORT1), pSchemaName, pTableName);
		final DbCommand query = new DbCommand(Q_EXPORT1, new FnParam[] {});
		query.setQuery(formatedquery);
		Export res = findOne(query);
		if (res != null && !res.isSourceCountNull()) {
			return res.getSourceCount();
		}
		return 0;
	}

}
