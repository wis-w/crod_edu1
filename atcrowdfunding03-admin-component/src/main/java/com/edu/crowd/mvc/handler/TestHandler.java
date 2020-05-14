package com.edu.crowd.mvc.handler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.croed.util.CrowdUtil;
import com.edu.croed.util.ResultEntity;
import com.edu.crowd.entity.Admin;
import com.edu.crowd.entity.ParamData;
import com.edu.crowd.entity.Student;
import com.edu.crowd.service.api.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wyg_edu
 * @date 2020年4月28日 下午9:45:11
 * @version v1.0
 */
@Controller
public class TestHandler {
	
	Logger log = LoggerFactory.getLogger(TestHandler.class);
	
	@Autowired
	private AdminService adminService;
	
	@ResponseBody
	@RequestMapping("/test/ajax/async.html")
	public String testAsync() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/send/compose/object.json")
	public ResultEntity<Student> testReceivel(@RequestBody Student student,HttpServletRequest httpServletRequest) {
		log.info(CrowdUtil.judgeReqyestType(httpServletRequest)+"\t json");
		log.info(student.toString());
		
		String s = null;
		System.out.println(s.length());
		
		return new ResultEntity<Student>().successWithData(student);
		
	}
	
	@RequestMapping("/test/ssm.html")
//	@RequestMapping("/test/ssm")
	public String testSSM(ModelMap model,HttpServletRequest httpServletRequest) {
		log.info(CrowdUtil.judgeReqyestType(httpServletRequest)+"\t json");
		List<Admin> list = adminService.getAll();
		model.addAttribute("adminList",list);
		String s = null;
//		System.out.println(s.length());
		System.out.println(10 / 0);
		return "target";
	}
	
	@ResponseBody
	@RequestMapping("/send/array/one.html")
	public String testReceiveArrayOne(@RequestParam("array[]")List<Integer> arrayList) {
		for(int i:arrayList) {
			System.out.println(i);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/send/array/two.html")
	public String testReceiveArrayTwo(ParamData paramData ) {
		List<Integer> arrayList = paramData.getArray();
		for(int i:arrayList) {
			System.out.println(i);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/send/array/three.html")
	public String testReceiveArrayThree(@RequestBody List<Integer> arrayList) {
		for(int i:arrayList) {
			log.info(i+"lalalla");
		}
		return "success";
	}
}
