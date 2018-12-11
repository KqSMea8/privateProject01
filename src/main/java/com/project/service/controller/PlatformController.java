package com.project.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.common.base.BaseController;
import com.project.service.service.PlatformService;

/**
 * 前台系统相关
 */
@RestController
@RequestMapping("/{version}/platform/")
public class PlatformController extends BaseController {

	@Autowired
	private PlatformService platformService;
}
