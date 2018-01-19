package cn.com.atnc.ioms.web.duty.environment.ups.year;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
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
import cn.com.atnc.ioms.enums.duty.environment.CheckCatagory;
import cn.com.atnc.ioms.enums.duty.environment.CheckResult;
import cn.com.atnc.ioms.enums.duty.environment.ContentCheckResult;
import cn.com.atnc.ioms.enums.duty.environment.EquipType;
import cn.com.atnc.ioms.enums.duty.environment.ups.TxjdUpsName;
import cn.com.atnc.ioms.mng.duty.environment.EnvironmentEquipCheckService;
import cn.com.atnc.ioms.model.duty.environment.ups.UpsMonthAddForm;
import cn.com.atnc.ioms.web.MyBaseController;

@Controller
@RequestMapping("/duty/upsdutyyear/txjdups/")
public class TxjdUpsYearController extends MyBaseController {

	@Autowired
	private EnvironmentEquipCheckService upsCheckService;
	
	@RequestMapping(value="add.do", method=RequestMethod.GET)
	public String addJump(@ModelAttribute("pageModel") UpsMonthAddForm upsMonthAddForm,
			HttpServletRequest request, Model model){
		//设备名称枚举类
		List<TxjdUpsName> txjdUpsName=Arrays.asList(TxjdUpsName.values());
		model.addAttribute("txjdUpsName",txjdUpsName);
		//检查结果枚举类
		List<CheckResult> checkResult=Arrays.asList(CheckResult.values());
		model.addAttribute("checkResult",checkResult);
		//设备类型枚举类
		List<EquipType> equipType=Arrays.asList(EquipType.values());
		model.addAttribute("equipType", equipType);
		List<CheckCatagory> checkType=Arrays.asList(CheckCatagory.values());
		model.addAttribute("checkType", checkType);
		//内容检查结果枚举类
		List<ContentCheckResult> contentCheckResult=Arrays.asList(ContentCheckResult.values());
		model.addAttribute("contentCheckResult", contentCheckResult);
		return "duty/upsdutyyear/txjdups/add";
	}
	
	/**
	 * 添加ups年检记录
	 * @author renyujuan
	 * @date 2016年8月8日上午9:05:34
	 * @param upsMonthAddForm
	 * @param dsTime
	 * @param batTotalV
	 * @param dsCurrent
	 * @param singleMaxV
	 * @param singleMinV
	 * @param remark
	 * @param number
	 * @param termialV
	 * @param interR
	 * @param remarks
	 * @param equipType
	 * @param checkType
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="add.do", method=RequestMethod.POST)
	public String add(@ModelAttribute("pageModel") UpsMonthAddForm upsMonthAddForm,
			@RequestParam("dsTime") List<String> dsTime,
			@RequestParam("batTotalV") List<String> batTotalV,
			@RequestParam("dsCurrent") List<String> dsCurrent,
			@RequestParam("singleMaxV") List<String> singleMaxV,
			@RequestParam("singleMinV") List<String> singleMinV,
			@RequestParam("remark") List<String> remark,
			@RequestParam("number") List<Integer> number,
			@RequestParam("termialV") List<String> termialV,
			@RequestParam("interR") List<String> interR,
			@RequestParam("remarks") List<String> remarks,
			@RequestParam("equipType") EquipType equipType,
			@RequestParam("checkType") CheckCatagory checkType,
			HttpServletRequest request, Model model) throws IOException{
		try {
			//记录填写人
			User user = (User) request.getSession().getAttribute(SessionNames.LOGIN_USER);
			upsMonthAddForm.setOperator(user);
			//记录填写时间（系统时间）
			upsMonthAddForm.setCheckTime(new Date().toLocaleString());
			upsMonthAddForm.setCheckDate(upsMonthAddForm.getCheckTime());
			upsMonthAddForm.setEquipType(equipType);
			upsMonthAddForm.setCheckType(checkType);
			upsCheckService.saveQuarter(upsMonthAddForm, number, termialV, interR, remarks, dsTime, batTotalV, dsCurrent, singleMaxV, singleMinV, remark);
			model.addAttribute("currentUser", user);
			model.addAttribute("returnStr",
					new ResultModel(true, "添加ups季检记录成功", upsMonthAddForm).toJson());
		} catch (Exception e) {
			logger.error("添加记录失败!", e);
			model.addAttribute("returnStr", new ResultModel(false, "添加ups季检记录失败",
					upsMonthAddForm).toJson());
			return "returnnote";
		}
		return "returnnote";
	}
	
	/**
	 * 删除ups年检记录
	 * @author renyujuan
	 * @date 2016年8月8日上午9:05:20
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="delete.do", method=RequestMethod.POST)
	public String delete(@RequestParam("id") String id,
			HttpServletRequest request, Model model){
		try {
			EnvironmentEquipCheck upsCheck=upsCheckService.findById(id);
			upsCheckService.delete(upsCheck);
			addSuccess(model, "删除记录成功");
			super.addLog(request, "删除记录成功");
		} catch (Exception e) {
			addError(model, "删除记录失败：" + e.getMessage());
		}
		return "duty/upsdutyquarter/slhups/result";
	}
	
	@RequestMapping(value="view.do", method=RequestMethod.GET)
	public String view(@RequestParam(value="id", required = true) String id,
			HttpServletRequest request, Model model){
		EnvironmentEquipCheck upsCheck=upsCheckService.findById(id);
		model.addAttribute("upsCheck",upsCheck);
		model.addAttribute("view", true);
		return "duty/upsdutyyear/txjdups/view";
	}
	
	/**
	 * 更新ups年检记录跳转
	 * @author renyujuan
	 * @date 2016年8月8日上午9:04:33
	 * @param upsMonthAddForm
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="update.do" , method=RequestMethod.GET)
	public String update(@ModelAttribute("pageModel") UpsMonthAddForm upsMonthAddForm,
			@RequestParam(value="id", required= true) String id,
			HttpServletRequest request, Model model){
		EnvironmentEquipCheck upsCheck=upsCheckService.findById(id);
		//巡检结果枚举类
		List<CheckResult> checkResult=Arrays.asList(CheckResult.values());
		model.addAttribute("checkresult",checkResult);
		//内容检查结果枚举类
		List<ContentCheckResult> contentCheckResult=Arrays.asList(ContentCheckResult.values());
		model.addAttribute("contentCheckResult", contentCheckResult);
		model.addAttribute("upsCheck", upsCheck);
		return "duty/upsdutyyear/txjdups/update";
	}
	
	/**
	 * 更新ups年检记录
	 * @author renyujuan
	 * @date 2016年8月8日上午9:04:57
	 * @param upsMonthAddForm
	 * @param dsTime
	 * @param batTotalV
	 * @param dsCurrent
	 * @param singleMaxV
	 * @param singleMinV
	 * @param remark
	 * @param termialV
	 * @param interR
	 * @param remarks
	 * @param upsCheckId
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="update.do" , method=RequestMethod.POST)
	public String updateUpsCheck(@ModelAttribute("pageModel") UpsMonthAddForm upsMonthAddForm,
			@RequestParam("dsTime") List<String> dsTime,
			@RequestParam("batTotalV") List<String> batTotalV,
			@RequestParam("dsCurrent") List<String> dsCurrent,
			@RequestParam("singleMaxV") List<String> singleMaxV,
			@RequestParam("singleMinV") List<String> singleMinV,
			@RequestParam("remark") List<String> remark,
			@RequestParam("termialV") List<String> termialV,
			@RequestParam("interR") List<String> interR,
			@RequestParam("remarks") List<String> remarks,
			@RequestParam("upsCheckId") String upsCheckId,
			HttpServletRequest request, Model model) throws IOException{
		try {
			EnvironmentEquipCheck upsCheck=upsCheckService.findById(upsCheckId);
			upsCheckService.updateQuarter(upsCheck, upsMonthAddForm, termialV, interR, remarks, dsTime, batTotalV, dsCurrent, singleMaxV, singleMinV, remark);
			model.addAttribute("returnStr",
					new ResultModel(true, "更新ups季检记录成功", upsMonthAddForm).toJson());
		} catch (Exception e) {
			logger.error("更新记录失败!", e);
			model.addAttribute("returnStr", new ResultModel(false, "更新ups季检记录失败",
					upsMonthAddForm).toJson());
			return "returnnote";
		}
		return "returnnote";
	}
}
