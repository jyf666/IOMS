/**
 * 
 */
package cn.com.atnc.ioms.dao.operstat.atm.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.atnc.common.dao.hibernate.BaseDaoHibernateImpl;
import cn.com.atnc.common.entity.Pagination;
import cn.com.atnc.ioms.dao.MyBaseDao;
import cn.com.atnc.ioms.dao.operstat.atm.IPortCESMDao;
import cn.com.atnc.ioms.entity.operstat.atm.PortCESM;
import cn.com.atnc.ioms.enums.basedata.Bureau;
import cn.com.atnc.ioms.enums.operstat.atm.EnumCollections.CardType;
import cn.com.atnc.ioms.model.operstat.atm.NetworkStatQueryModel;

/**
 * Atm端口详情Dao接口实现
 * @author：KuYonggang
 * @date：2014-6-23上午10:19:54
 * @version：1.0
 */
@Repository
public class PortCESMDaoImpl extends MyBaseDao<PortCESM> implements IPortCESMDao {

	/**
	 * @see cn.com.atnc.ioms.dao.operstat.atm.IPortAXSMXGDao#queryAXSMEPageByModel(cn.com.atnc.ioms.model.operstat.atm.NetworkStatQueryModel)
	 * PortAXSMXGDaoImpl.java
	 */
	@Override
	public Pagination queryCESMPageByModel(NetworkStatQueryModel queryModel) {
		StringBuffer hql = new StringBuffer();
		hql.append("from PortCESM where 1=1 ");
		List<Object> queryParams = new ArrayList<Object>();
		//管局查询条件
		if(!StringUtils.isEmpty(queryModel.getBureau())){
            hql.append("and port.node.bureau = ? ");
			queryParams.add(Bureau.getBureauByValue(queryModel.getBureau()));
		}
		//节点代码查询条件
		if(!StringUtils.isEmpty(queryModel.getNodeCode())){
            hql.append("and port.node.atmNodeCode = ? ");
			queryParams.add(queryModel.getNodeCode());
		}
		//端口类型
		if(!StringUtils.isEmpty(queryModel.getPortType())){
            hql.append("and port.cardType = ? ");
			queryParams.add(CardType.getCardTypeByValue(queryModel.getPortType()));
		}
		String countHql = BaseDaoHibernateImpl.COUNT_ID + hql.toString();
		return  super.pageQuery(hql.toString(), queryParams.toArray(), countHql,
				queryModel.getPageNo(), queryModel.getPageSize());
	}
}
