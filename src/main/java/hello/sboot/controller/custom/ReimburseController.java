package hello.sboot.controller.custom;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import ooby.workflow.activiti.service.OobyTaskService;

@Controller
@RequestMapping("/reimburse")
public class ReimburseController {

	@Autowired
	private ReimburseDao dao;
	@Autowired
	private TaskService taskService;
	@Autowired
	private OobyTaskService obts;

	@RequestMapping("/applyForm")
	public String applyForm(HttpServletRequest request, Map<String, Object> map,
			@RequestParam("page") String pageType) {
		String processId = (String) request.getAttribute("processId");
		Reimburse reim = dao.findOne(processId);
		if (reim == null) {
			reim = new Reimburse();
			reim.setAmount("0");
		}
		map.put("reimburse", reim);

		boolean readonly = false;
		if ("approve".equals(pageType))
			readonly = true;
		map.put("isReadOnly", readonly);
		map.put("pageType", pageType);
		return "/custom/applyForm";
	}

	@RequestMapping("/apply")
	@ResponseBody
	public String apply(String taskId, Reimburse reim, @SessionAttribute("userid") String userId, String depId,
			String comment, @RequestParam(defaultValue = "false") boolean temporary) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		reim.setProcessId(task.getProcessInstanceId());
		dao.save(reim);
		if (!temporary) {
			taskService.addComment(taskId, null, comment);
			obts.apply(taskId, userId, depId);
		}
		return "true";
	}

	@RequestMapping("/approve")
	@ResponseBody
	public String approve(String taskId,@SessionAttribute("userid") String userId, String depId,
			String comment) {

		taskService.addComment(taskId, null, comment);
		obts.approve(taskId, userId, depId);
		return "true";
	}
	@RequestMapping("/sendback")
	@ResponseBody
	public String sendBack(String taskId,@SessionAttribute("userid") String userId, String sendbackTarget,
			String comment) {

		taskService.addComment(taskId, null, comment);
		obts.sendBack(taskId, userId, sendbackTarget);
		return "true";
	}
}
