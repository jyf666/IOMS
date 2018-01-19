package cn.com.atnc.ioms.mng.business.telegraph.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.atnc.ioms.entity.business.telegraph.TeleGraph;
import cn.com.atnc.ioms.mng.business.telegraph.TeleGraphCircuitWorkflowService;
import cn.com.atnc.ioms.mng.business.telegraph.TeleGraphService;

/**
 * 
 * 类说明：
 * 
 * @author: 局域网_王鹏
 * @time: 2014年3月27日下午7:34:47
 * @version:1.0
 */
@Transactional
@Service
public class TeleGraphCircuitWorkflowServiceImpl implements
	TeleGraphCircuitWorkflowService {

	private static Logger logger = LoggerFactory.getLogger(TeleGraphCircuitWorkflowServiceImpl.class);
	@Autowired
	private TeleGraphService teleGraphService;
	@Resource(name = "runtimeService")
	private RuntimeService runtimeService;
	@Resource(name = "taskService")
	protected TaskService taskService;
	@Resource(name = "historyService")
	protected HistoryService historyService;
	@Resource(name = "repositoryService")
	protected RepositoryService repositoryService;
	@Resource(name = "identityService")
	private IdentityService identityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.atnc.pcsp.mng.KuSatellite.ITempCircuitWorkflowService#startWorkflow
	 * (cn.com.atnc.pcsp.entity.KuSatellite.KuSatellite, java.util.Map)
	 */
	@Override
	public ProcessInstance startWorkflow(TeleGraph entity,
			Map<String, Object> variables) {
		String businessKey = entity.getId().toString();
		ProcessInstance processInstance = null;
		try {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(entity.getApplyBaseInfo().getApplyUser().getId());
			processInstance = runtimeService.startProcessInstanceByKey(
					"TelegraphCircuitProcess", businessKey, variables);
			String processInstanceId = processInstance.getId();
			entity.setProcessInstanceId(processInstanceId);
			Task task = this.getTaskByProcessInstaceId(processInstanceId);
			entity.setTaskName(task.getName());
			logger.debug("task.getAssignee():" + task.getAssignee());
			// entity.setCurrentAuditer(clientService.findByID(task.getAssignee())
			// .getRealName());
			teleGraphService.save(entity);
			logger.debug(
					"start process of {key={}, bkey={}, pid={}, variables={}}",
					new Object[] { "TeleGraph", businessKey,
							processInstanceId, variables });
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return processInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.atnc.pcsp.mng.KuSatellite.ITempCircuitWorkflowService#findTodoTasks
	 * (java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TeleGraph> findTodoTasks(String userId) {
		List<TeleGraph> results = new ArrayList<TeleGraph>();
		List<Task> tasks = new ArrayList<Task>();

		// 根据当前人的ID查询活动任务
		TaskQuery todoQuery = taskService.createTaskQuery()
				.processDefinitionKey("TelegraphCircuitProcess").taskAssignee(userId)
				.active().orderByTaskCreateTime().asc().orderByTaskCreateTime()
				.desc();
		List<Task> todoList = todoQuery.list();

		// 根据当前人的ID查询暂停（系统内用户申请撤销后置为暂停）任务
		TaskQuery suspendQuery = taskService.createTaskQuery()
				.processDefinitionKey("TelegraphCircuitProcess").taskAssignee(userId)
				.suspended().orderByTaskCreateTime().asc()
				.orderByTaskCreateTime().desc();
		List<Task> suspendList = suspendQuery.list();

		// 根据当前人未签收的任务
		TaskQuery claimQuery = taskService.createTaskQuery()
				.processDefinitionKey("TelegraphCircuitProcess").taskCandidateUser(userId)
				.active().orderByTaskCreateTime().asc().orderByTaskCreateTime()
				.desc();
		List<Task> unsignedTasks = claimQuery.list();

		// 合并
		tasks.addAll(todoList);
		tasks.addAll(unsignedTasks);
		tasks.removeAll(suspendList);
		logger.debug("todoList:" + todoList.size());
		logger.debug("unsignedTasks:" + unsignedTasks.size());

		// 根据流程的业务ID查询实体并关联
		if (tasks.size() > 0) {
			for (Task task : tasks) {
				String processInstanceId = task.getProcessInstanceId();
				ProcessInstance processInstance = runtimeService
						.createProcessInstanceQuery()
						.processInstanceId(processInstanceId).active()
						.singleResult();
				String businessKey = processInstance.getBusinessKey();
				if (businessKey == null) {
					continue;
				}
				logger.debug("new String(businessKey):"
						+ new String(businessKey));
				TeleGraph teleGraph = teleGraphService.getTeleGraphById(new String(businessKey));
				if (teleGraph != null) {
					teleGraph.setTask(task);
					teleGraph.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					teleGraph.setProcessInstance(processInstance);
					results.add(teleGraph);
				}
			}
		}
		logger.debug("results:" + results.size());
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.atnc.pcsp.mng.KuSatellite.ITempCircuitWorkflowService#
	 * findRunningProcessInstaces()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TeleGraph> findRunningProcessInstaces() {
		List<TeleGraph> results = new ArrayList<TeleGraph>();
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery()
				.processDefinitionKey("TelegraphCircuitProcess").active()
				.orderByProcessInstanceId().desc();
		List<ProcessInstance> list = query.list();

		// 关联业务实体
		for (ProcessInstance processInstance : list) {
			String businessKey = processInstance.getBusinessKey();
			if (businessKey == null) {
				continue;
			}
			TeleGraph teleGraph = teleGraphService.getTeleGraphById(new String(businessKey));
			teleGraph.setProcessInstance(processInstance);
			teleGraph.setProcessDefinition(getProcessDefinition(processInstance
							.getProcessDefinitionId()));
			results.add(teleGraph);

			// 设置当前任务信息
			List<Task> tasks = taskService.createTaskQuery()
					.processInstanceId(processInstance.getId()).active()
					.orderByTaskCreateTime().desc().listPage(0, 1);
			teleGraph.setTask(tasks.get(0));
		}
		return results;
	}

	@Override
	public List<TeleGraph> findRunningProcessInstaces(String userId) {
		// TODO Auto-generated method stub
		List<TeleGraph> results = new ArrayList<TeleGraph>();
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery()
				.processDefinitionKey("TelegraphCircuitProcess").active()
				.orderByProcessInstanceId().desc();
		query.variableValueEquals("applyUserId", userId);
		List<ProcessInstance> list = query.list();

		// 关联业务实体
		for (ProcessInstance processInstance : list) {
			String businessKey = processInstance.getBusinessKey();
			if (businessKey == null) {
				continue;
			}
			TeleGraph teleGraph = teleGraphService.getTeleGraphById(new String(businessKey));
			teleGraph.setProcessInstance(processInstance);
			teleGraph.setProcessDefinition(getProcessDefinition(processInstance
							.getProcessDefinitionId()));
			results.add(teleGraph);

			// 设置当前任务信息
			List<Task> tasks = taskService.createTaskQuery()
					.processInstanceId(processInstance.getId()).active()
					.orderByTaskCreateTime().desc().listPage(0, 1);
			teleGraph.setTask(tasks.get(0));
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.atnc.pcsp.mng.KuSatellite.ITempCircuitWorkflowService#
	 * findFinishedProcessInstaces()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TeleGraph> findFinishedProcessInstaces() {
		List<TeleGraph> results = new ArrayList<TeleGraph>();
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery()
				.processDefinitionKey("TelegraphCircuitProcess").finished()
				.orderByProcessInstanceEndTime().asc();
		List<HistoricProcessInstance> list = query.list();

		// 关联业务实体
		for (HistoricProcessInstance historicProcessInstance : list) {
			String businessKey = historicProcessInstance.getBusinessKey();
			TeleGraph teleGraph = teleGraphService.getTeleGraphById(new String(businessKey));
			teleGraph.setProcessDefinition(getProcessDefinition(historicProcessInstance
							.getProcessDefinitionId()));
			teleGraph.setHistoricProcessInstance(historicProcessInstance);
			results.add(teleGraph);
		}
		return results;
	}

	@Override
	public List<TeleGraph> findFinishedProcessInstaces(String userId) {
		// TODO Auto-generated method stub
		List<TeleGraph> results = new ArrayList<TeleGraph>();
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery()
				.processDefinitionKey("TelegraphCircuitProcess").finished()
				.orderByProcessInstanceEndTime().asc();
		query.variableValueEquals("applyUserId", userId);
		List<HistoricProcessInstance> list = query.list();

		// 关联业务实体
		for (HistoricProcessInstance historicProcessInstance : list) {
			String businessKey = historicProcessInstance.getBusinessKey();
			TeleGraph teleGraph = teleGraphService.getTeleGraphById(new String(businessKey));
			teleGraph.setProcessDefinition(getProcessDefinition(historicProcessInstance
							.getProcessDefinitionId()));
			teleGraph.setHistoricProcessInstance(historicProcessInstance);
			results.add(teleGraph);
		}
		return results;
	}

	/**
	 * 
	 * 方法说明：查询流程定义对象
	 * 
	 * @author 局域网_王鹏
	 * @date：2014年4月1日下午2:45:15
	 * @param processDefinitionId
	 * @return ProcessDefinition
	 * 
	 */
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	@Override
	public void complete(String taskID, Map<String, Object> taskVariables) {
		taskService.complete(taskID, taskVariables);
	}

	@Override
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	@Override
	public Task getTaskByProcessInstaceId(String processInstanceId) {
		List<Task> list = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public void delete(String taskId) {
		taskService.deleteTask(taskId);
	}

}
