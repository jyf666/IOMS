/* Copyright ? 2013 BEIJING ATNC Co.,Ltd.
 * All rights reserved
 *
 * created at 2013-9-2 下午2:26:26
 * author:HuangYijie E-mail:huangyijie@atnc.com.cn
 */

package cn.com.atnc.ioms.dao.tempcircuit;

import cn.com.atnc.common.dao.IBaseDao;
import cn.com.atnc.ioms.entity.tempcircuit.TempCircuitTesAudio;

/**
 * 
 * 类说明：TempCircuitTesAudio DAO
 * 
 * @author: 局域网_王鹏
 * @time: 2014年3月26日下午7:01:45
 * @version:1.0
 */
public interface ITempCircuitTesAudioDao extends IBaseDao<TempCircuitTesAudio> {
	public TempCircuitTesAudio getByTempcircuitId(String id);
}
