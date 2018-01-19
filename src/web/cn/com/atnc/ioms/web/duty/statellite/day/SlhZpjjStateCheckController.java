package cn.com.atnc.ioms.web.duty.statellite.day;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.atnc.common.model.ResultModel;
import cn.com.atnc.common.model.SessionNames;
import cn.com.atnc.ioms.entity.acl.User;
import cn.com.atnc.ioms.entity.duty.environment.EnvironmentEquipCheck;
import cn.com.atnc.ioms.entity.duty.satellite.SlhZpjjState;
import cn.com.atnc.ioms.enums.duty.environment.CheckResult;
import cn.com.atnc.ioms.enums.duty.satellite.Dec;
import cn.com.atnc.ioms.mng.duty.environment.EnvironmentEquipCheckService;
import cn.com.atnc.ioms.mng.duty.statellite.SlhZpjjStateService;
import cn.com.atnc.ioms.model.duty.statellite.day.SlhZpjjStateModel;
import cn.com.atnc.ioms.web.MyBaseController;

/**
 * TES-网管主备状态
 * 
 * @author shijiyong
 * @date 2016年10月31日 上午9:13:02
 * @version 1.0, 2016年10月14日 上午9:13:02
 */
@Controller
@RequestMapping("/duty/statelliteday/slhzpjjstate/")
public class SlhZpjjStateCheckController extends MyBaseController {

	@Autowired
	private SlhZpjjStateService slhzpjjstateservice;
	@Autowired
	private EnvironmentEquipCheckService envirEquipCheckService;

	/**
	 * 网控中心添加记录跳转
	 * 
	 * @author shijiyong
	 * @time:2016年9月28日
	 * @param queryModel
	 *            查询条件
	 * @param optType
	 *            tab窗口名称
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String addJump(@ModelAttribute("pageModel") SlhZpjjState queryModel,
			@RequestParam("optType") String optType, Model model) {
		// 检查结果枚举类
		List<CheckResult> checkResult = Arrays.asList(CheckResult.values());
		model.addAttribute("checkresult", checkResult);
		model.addAttribute("optType", optType);
		return "duty/statelliteday/slhzpjjstate/add";
	}

	/**
	 * 添加巡检记录
	 * 
	 * @author shijiyong
	 * @time:2016年9月28日
	 * @param atmday
	 *            pageModel
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            查询条件
	 * @param optType
	 *            tab窗口名称
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	public String add(
			@ModelAttribute("pageModel") SlhZpjjStateModel slhzpjjstatemodel,
			HttpServletRequest request, Model model) throws IOException {
		try {
			// 设置当前用户为操作人
			User user = (User) request.getSession().getAttribute(
					SessionNames.LOGIN_USER);
			slhzpjjstatemodel.setOperator(user);
			model.addAttribute("currentUser", user);
			// 将记录保存至数据库
			slhzpjjstateservice.save(slhzpjjstatemodel);
			model.addAttribute("returnStr",
					new ResultModel(true, "操作成功", null).toJson());
		} catch (Exception e) {
			model.addAttribute("returnStr",
					new ResultModel(false, "操作失败", null).toJson());
		}
		return "returnnote";
	}

	/**
	 * 删除
	 * 
	 * @author shijiyong
	 * @time:2016年10月24日
	 * @param id
	 *            ID
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            查询条件
	 * @param txjddatestate
	 *            pageModel
	 * @return String
	 */
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	public String delete(
			@ModelAttribute("pageModel") SlhZpjjStateModel querymodel,
			@RequestParam("id") String id, HttpServletRequest request,
			Model model) {
		try {
			// 删除
			SlhZpjjState prostate = slhzpjjstateservice.findById(id);
			String checkid = prostate.getId();
			EnvironmentEquipCheck equipcheck = envirEquipCheckService
					.findById(checkid);
			slhzpjjstateservice.delete(prostate, equipcheck);
			// 返回
			addSuccess(model, "删除记录成功");
			// 记录操作日志
			super.addLog(request, "删除记录成功");
		} catch (RuntimeException e) {
			addError(model, "删除记录失败");
			super.addLog(request, "删除记录失败");
		}
		return "/duty/statelliteday/slhzpjjstate/result";
	}

	/**
	 * 查看
	 * 
	 * @author shijiyong
	 * @time:2016年10月24日
	 * @param id
	 *            ID
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            查询条件
	 * @param txjddatestate
	 *            pageModel
	 * @return String
	 */
	@RequestMapping(value = "view.do", method = RequestMethod.GET)
	public String view(@RequestParam("id") String id,
			HttpServletRequest request, Model model) {
		SlhZpjjState mainstate = slhzpjjstateservice.findById(id);
		model.addAttribute("vo", mainstate);
		return "/duty/statelliteday/slhzpjjstate/view";
	}

	/**
	 * 更新跳转
	 * 
	 * @author shijiyong
	 * @time:2016年9月28日
	 * @param id
	 *            ID
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            查询条件
	 * @param txjddatestate
	 *            pageModel
	 * @return String
	 */
	@RequestMapping(value = "update.do", method = RequestMethod.GET)
	public String updateJump(
			@RequestParam(value = "id", required = true) String id,
			@ModelAttribute("pageModel") SlhZpjjState slhzpjjstate,
			HttpServletRequest request, Model model) {
		List<Dec> dec = Arrays.asList(Dec.values());
		SlhZpjjState zpjjstate = slhzpjjstateservice.findById(id);
		model.addAttribute("dec", dec);
		model.addAttribute("zpjjstate", zpjjstate);
		return "/duty/statelliteday/slhzpjjstate/update";
	}

	/**
	 * 更新
	 * 
	 * @author shijiyong
	 * @time:2016年10月20日
	 * @param id
	 *            ID
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            查询条件
	 * @param txjddatestate
	 *            pageModel
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String update(
			@ModelAttribute("pageModel") SlhZpjjStateModel slhzpjjstatemodel,
			HttpServletRequest request, Model model,
			@RequestParam("id") String id) throws IOException {
		try {
			SlhZpjjState slhequstate = slhzpjjstateservice.findById(id);
			String checkid = slhequstate.getCheckid().getId();
			EnvironmentEquipCheck enviroment = slhzpjjstateservice
					.findByIden(checkid);
			// 设置当前用户为操作人
			User user = (User) request.getSession().getAttribute(
					SessionNames.LOGIN_USER);
			slhzpjjstatemodel.setOperator(user);
			slhzpjjstateservice.update(slhequstate, enviroment,
					slhzpjjstatemodel);
			model.addAttribute("returnStr",
					new ResultModel(true, "操作成功", null).toJson());
		} catch (Exception e) {
			model.addAttribute("returnStr",
					new ResultModel(false, "操作失败", null).toJson());
		}
		return "returnnote";
	}

}
