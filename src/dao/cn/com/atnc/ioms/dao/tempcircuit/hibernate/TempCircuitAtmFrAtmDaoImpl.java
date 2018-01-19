/* Copyright ? 2013 BEIJING ATNC Co.,Ltd.
 * All rights reserved
 *
 * created at 2013-9-2 下午2:32:06
 * author:HuangYijie E-mail:huangyijie@atnc.com.cn
 */

package cn.com.atnc.ioms.dao.tempcircuit.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.atnc.ioms.dao.MyBaseDao;
import cn.com.atnc.ioms.dao.tempcircuit.ITempCircuitAtmFrAtmDao;
import cn.com.atnc.ioms.entity.tempcircuit.TempCircuitAtmFrAtm;

/**
 * 
 * 类说明：TempCircuitAtmFrAtm DAO实现
 * 
 * @author: 局域网_王鹏
 * @time: 2014年3月26日下午7:03:07
 * @version:1.0
 */
@Repository("tempCircuitAtmFrAtmDao")
public class TempCircuitAtmFrAtmDaoImpl extends MyBaseDao<TempCircuitAtmFrAtm>
		implements ITempCircuitAtmFrAtmDao {

	@SuppressWarnings("unchecked")
	@Override
	public TempCircuitAtmFrAtm getByTempcircuitId(String id) {
		// TODO Auto-generated method stub
		List<TempCircuitAtmFrAtm> list = (List<TempCircuitAtmFrAtm>) queryList(
				"from TempCircuitAtmFrAtm where tempCircuit.id = ?", id);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}