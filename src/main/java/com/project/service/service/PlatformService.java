package com.project.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.base.BaseService;
import com.project.service.proxy.PlatformProxy;

@Service("platformService")
public class PlatformService extends BaseService {

	@Autowired
	private PlatformProxy platformProxy;
}
