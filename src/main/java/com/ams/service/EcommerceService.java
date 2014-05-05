package com.ams.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ams.bean.vo.SearchVo;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EweblibUtil;

public abstract class EcommerceService extends AbstractService {


	@Autowired
	protected ICategoryService cateService;

	
	public DataBaseQueryBuilder mergeCommonSearchQuery(SearchVo search, DataBaseQueryBuilder builder, String table, String closeKey, boolean defaultQuery) {
		DataBaseQueryBuilder searchBuilder = new DataBaseQueryBuilder(table);
		
	
	

		if (!EweblibUtil.isEmpty(search.getStartDate())) {
			searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, search.getStartDate());
			defaultQuery = false;
		}

		if (!EweblibUtil.isEmpty(search.getEndDate())) {
			Date endDate = getQueryEndDate(search);

			searchBuilder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, closeKey, endDate);
			defaultQuery = false;
		}
		
		if(defaultQuery && EweblibUtil.isEmpty(search.getDateType())){
			search.setDateType("0");
		}
		if (!EweblibUtil.isEmpty(search.getDateType())) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			if (search.getDateType().equalsIgnoreCase("0")) {
				c.set(Calendar.DAY_OF_MONTH, 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, closeKey, new Date());
				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());

			} else if (search.getDateType().equalsIgnoreCase("1")) {
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH, 1);

				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());
				c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH) + 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN, closeKey, c.getTime());

			} else if (search.getDateType().equalsIgnoreCase("2")) {
				c.add(Calendar.MONTH, -3);		
				c.set(Calendar.DAY_OF_MONTH, 1);
				searchBuilder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, closeKey, c.getTime());
				
				c.add(Calendar.MONTH, 2);		
				c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH) + 1);
				searchBuilder.and(DataBaseQueryOpertion.LESS_THAN, closeKey, c.getTime());
			}

		}

		if (!EweblibUtil.isEmpty(searchBuilder.getQueryStr())) {
			builder = builder.and(searchBuilder);
		}
		return builder;
	}
	
	

	public Date getQueryEndDate(SearchVo search) {
	    Date endDate = search.getEndDate();
	    if(DateUtil.getDateStringTime(endDate).endsWith("00:00:00")){
	    	endDate = new Date(endDate.getTime() + 86400000L - 1L);
	    }
	    return endDate;
    }
	
	


	

}
