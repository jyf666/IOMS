package cn.com.atnc.ioms.mng.business.signoutnet;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import cn.com.atnc.ioms.entity.business.formalcircuit.FormalCircuit;
import cn.com.atnc.ioms.entity.business.signoutnet.SignoutNet;

/**
 * 通信网退网申请申请工作流服务接口
 *
 * @author 段衍林
 * @2017年1月10日 上午11:21:47
 */
public interface ISignoutNetWorkflowService {

	/**
	 * 
	 * 方法说明：启动流程
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午2:42:42
	 * @param entity
	 * @param variables
	 * @return ProcessInstance
	 * 
	 */
	public abstract ProcessInstance startWorkflow(SignoutNet entity,
			Map<String, Object> variables);

	/**
	 * 
	 * 方法说明：根据用户查询该用户的待办任务
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午2:43:01
	 * @param userId
	 * @return List<KuSatellite>
	 * 
	 */
	public abstract List<SignoutNet> findTodoTasks(String userId);

	/**
	 * 
	 * 方法说明：读取所有运行中的流程
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午2:43:48
	 * @return List<KuSatellite>
	 * 
	 */
	public abstract List<FormalCircuit> findRunningProcessInstaces();

	/**
	 * 
	 * 方法说明：读取特定申请用户所有运行中的流程
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午2:43:48
	 * @return List<KuSatellite>
	 * 
	 */
	public abstract List<FormalCircuit> findRunningProcessInstaces(String userId);

	/**
	 * 
	 * 方法说明：读取所有已结束中的流程
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午2:44:01
	 * @return List<KuSatellite>
	 * 
	 */
	public abstract List<FormalCircuit> findFinishedProcessInstaces();

	/**
	 * 
	 * 方法说明：读取特定申请用户所有已结束中的流程
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午2:44:01
	 * @return List<KuSatellite>
	 * 
	 */
	public abstract List<FormalCircuit> findFinishedProcessInstaces(
			String userId);

	/**
	 * 
	 * 方法说明：完成流程或流程中的一步
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午2:53:18
	 * @param taskID
	 * @param taskVariables
	 *            void
	 * 
	 */
	public abstract void complete(String taskID,
			Map<String, Object> taskVariables);

	/**
	 * 
	 * 方法说明：签收
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午4:19:32 void
	 * 
	 */
	public abstract void claim(String taskId, String userId);

	/**
	 * 
	 * 方法说明：删除task
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月17日下午6:06:04
	 * @param taskId
	 *            void
	 * 
	 */
	public abstract void delete(String taskId);

	/**
	 * 
	 * 方法说明：根据processInstanceId获取当前Task
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月17日下午5:41:22
	 * @param id
	 * @return Task
	 * 
	 */
	public Task getTaskByProcessInstaceId(String processInstanceId);

}