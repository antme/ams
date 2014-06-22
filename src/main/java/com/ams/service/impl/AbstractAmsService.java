package com.ams.service.impl;

import com.ams.bean.AmsBaseEntity;
import com.ams.bean.Notice;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.service.AbstractService;

public abstract class AbstractAmsService extends AbstractService {

	public void mergeCommonQuery(DataBaseQueryBuilder query) {

		query.orderBy(AmsBaseEntity.DISPLAY_ORDER, false);
		query.orderBy(Notice.CREATED_ON, false);

	}

	public void mergeCommonQueryForApp(DataBaseQueryBuilder query) {

		query.orderBy(AmsBaseEntity.DISPLAY_ORDER, false);
		query.orderBy(Notice.CREATED_ON, false);
		query.and(DataBaseQueryOpertion.IS_TRUE, AmsBaseEntity.DISPLAY_FOR_APP);

	}

}
