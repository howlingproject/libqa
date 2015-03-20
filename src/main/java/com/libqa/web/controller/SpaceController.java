package com.libqa.web.controller;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.application.framework.ResponseData;
import com.libqa.domain.Space;
import com.libqa.web.service.KeywordService;
import com.libqa.web.service.SpaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by yion on 2015. 2. 8..
 */
@Slf4j
@Controller
public class SpaceController {

	@Value("${howling.hello.message}")
	private String message;

	@Autowired
	private SpaceService spaceService;

	@Autowired
	private KeywordService keywordService;

	@RequestMapping("/space")
	public String space() {
		log.debug("##############################");
		return "redirect:/space/main";
	}

	@RequestMapping("/space/main")
	public ModelAndView spaceMain(Model model) {
		log.debug("##############################");
		ModelAndView mav = new ModelAndView("/space/main");
		return mav;
	}

	@RequestMapping("/space/form")
	public ModelAndView form(Model model) {
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

	@RequestMapping(value = "/space/addSpace", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData<Space> save(@ModelAttribute Space space) {

		//HttpServletRequest request, HttpServletResponse response) {

		log.info("## space : {}", space);
		// 여기서 request 에 대한 사용자 정보 조회함 (권한관리에 이미 필요)
		space.setInsertDate(new Date());
		space.setInsertUserId(1);
		Space result = spaceService.save(space);

		String [] keywordArrays = space.getKeywords().split(",");
		log.info(" keywordArrays : {}", keywordArrays.length);
		if (keywordArrays.length > 0) {
			keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.SPACE, result.getSpaceId());
		}
		log.debug("#result : [{}]", result);
		return ResponseData.createSuccessResult(result);
	}

}

