package hello.sboot.controller.page;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import ooby.workflow.activiti.entity.SendBackDefinitionEntity;
import ooby.workflow.activiti.service.OobyRuntimeService;
import ooby.workflow.activiti.service.OobyTaskService;

@Controller
@RequestMapping("/workdesk")
public class WorkDeskPageController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private OobyRuntimeService obrs;
	@Autowired
	private OobyTaskService obts;

	@RequestMapping("")
	public String workDesk() {
		return "workdesk";
	}

	@RequestMapping("/tasklist")
	public String tasklist() {
		return "tasklist";
	}

	@RequestMapping("/invitelist")
	public String inviteList() {
		return "invitelist";
	}

	@RequestMapping(value = "/executeTask/{taskId}", method = RequestMethod.GET)
	public String executeTask(@PathVariable String taskId, Map<String, Object> map,
			@SessionAttribute("userid") String userId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String formUrl = task.getFormKey();
		map.put("taskId", taskId);
		map.put("processId", task.getProcessInstanceId());
		String applicant = obrs.getApplicant(task.getProcessInstanceId());
		if (applicant == null)
			applicant = userId;
		map.put("applicant", applicant);
		map.put("applyDate", new Date());

		map.put("canSendBack", obts.canSendBack(taskId));
		if(obts.canSendBack(taskId)){
			Map<String, SendBackDefinitionEntity> a=obts.getSendBackTargets(taskId);
			System.out.println("a.size:"+a.size());
			System.out.println("a.value.size:"+a.values().size());
			for(SendBackDefinitionEntity entity:a.values()){
				System.out.println("id:"+entity.getActivitiId());
			}
			map.put("sendBackTargets", obts.getSendBackTargets(taskId).values());
		}
		return "forward:" + formUrl;
	}

	@RequestMapping("/completedTaskList")
	public String completedTaskList() {
		return "completedtasklist";
	}

}
