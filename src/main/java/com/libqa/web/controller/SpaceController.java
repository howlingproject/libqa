package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.domain.Space;
import com.libqa.web.service.SpaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yion on 2015. 2. 8..
 */
@Controller
@Slf4j
public class SpaceController {

	@Value("${howling.hello.message}")
	private String message;

	@Autowired
	private SpaceService spaceService;

	@RequestMapping("/space/form")
	public ModelAndView index(Model model) {
		log.debug("##############################");
		log.info("# message : {}", message);

		ModelAndView mav = new ModelAndView("/space/form");
		mav.addObject("message", message);

		return mav;
	}

	@RequestMapping("/space/fileUpload")
	public ModelAndView fileUpload(Model model) {
		ModelAndView mav = new ModelAndView("/space/ajaxUpload");
		return mav;
	}

	@RequestMapping(value = "/space/addSpace", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData<?> addSpace(Space space) {
		log.info("## space : {}", space);

		Space result = spaceService.save(space);

		log.debug("#result : [{}]", result);

		return ResponseData.createSuccessResult(result);
	}

}

