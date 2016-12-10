package hello.sboot.controller.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import ooby.workflow.activiti.service.OobyTaskService;

@Controller
@RequestMapping("/data/workdesk")
public class WorkDeskDataController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private OobyTaskService obts;
	@Autowired
	private HistoryService historyService;

	@RequestMapping("/applylist")
	@ResponseBody
	public Map<String,Object> applyList(@SessionAttribute("userid") String userId) {
		System.out.println("userid:" + userId);
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().startableByUser(userId).list();
		List<Map<String,Object>> rows=new ArrayList<Map<String,Object>>();
		for(ProcessDefinition p:list){
			Map<String,Object> row=new HashMap<String, Object>();
			row.put("key",p.getKey());
			row.put("name",p.getName());
			row.put("description", p.getDescription());
			rows.add(row);
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("data", rows);
		return map;
	}
	
	@RequestMapping("/startProcess")
	@ResponseBody
	public Boolean startProcess(String processKey,@SessionAttribute("userid") String userId){
		identityService.setAuthenticatedUserId(userId);
		runtimeService.startProcessInstanceByKey(processKey);
		return true;
	}
	
	@RequestMapping("/taskList")
	@ResponseBody
	public Map<String,Object> taskList(@SessionAttribute("userid") String userId){
		List<Task> taskList=taskService.createTaskQuery().taskAssignee(userId).list();
		List<Map<String,Object>> rows=new ArrayList<Map<String,Object>>();
		for(Task task:taskList){
			Map<String,Object> row=new HashMap<String, Object>();
			row.put("id",task.getId());
			row.put("name",task.getName());
			row.put("description", task.getDescription());
			ProcessDefinition p=repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
			row.put("processNo", task.getProcessInstanceId());
			row.put("processName", p.getName());
			row.put("processDescription", p.getDescription());
			rows.add(row);
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("data", rows);
		return map;
	}
	@RequestMapping("/invitelist")
	@ResponseBody
	public Map<String,Object> inviteList(@SessionAttribute("userid") String userId){
		List<Task> taskList=taskService.createTaskQuery().taskCandidateUser(userId).list();
		List<Map<String,Object>> rows=new ArrayList<Map<String,Object>>();
		for(Task task:taskList){
			Map<String,Object> row=new HashMap<String, Object>();
			row.put("id",task.getId());
			row.put("name",task.getName());
			row.put("description", task.getDescription());
			ProcessDefinition p=repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
			row.put("processNo", task.getProcessInstanceId());
			row.put("processName", p.getName());
			row.put("processDescription", p.getDescription());
			rows.add(row);
			
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("data", rows);
		return map;
	}
	
	@RequestMapping("/claimTask")
	@ResponseBody
	public Boolean claimTask(String taskId,@SessionAttribute("userid") String userId){
		obts.claim(taskId, userId);
		return true;
	}
	@RequestMapping("/completedTaskList")
	@ResponseBody
	public Map<String,Object> completedTaskList(@SessionAttribute("userid") String userId){
		List<HistoricTaskInstance> tasks=historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished().list();
		tasks.addAll(historyService.createHistoricTaskInstanceQuery().taskCandidateUser(userId).finished().list());
		List<Map<String,Object>> rows=new ArrayList<Map<String,Object>>();
		for(HistoricTaskInstance task:tasks){
			Map<String,Object> row=new HashMap<String, Object>();
			row.put("id",task.getId());
			row.put("name",task.getName());
			row.put("description", task.getDescription());
			ProcessDefinition p=repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
			row.put("processNo", task.getProcessInstanceId());
			row.put("processName", p.getName());
			row.put("processDescription", p.getDescription());
			rows.add(row);
			
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("data", rows);
		return map;
	}

}
