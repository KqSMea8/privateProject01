package com.project.manager.system.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单实体
 */
public class SystemMenuEntity implements Serializable {
	private static final long serialVersionUID = -5600839452856960839L;

	private String menuId;
	private String parentId;
	private String menuName;
	private String menuCss;
	private String menuUrl;
	private Integer isButton;
	private Integer sort;
	private Integer status;
	private List<SystemMenuEntity> subMenuList;
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuCss() {
		return menuCss;
	}

	public void setMenuCss(String menuCss) {
		this.menuCss = menuCss;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getIsButton() {
		return isButton;
	}

	public void setIsButton(Integer isButton) {
		this.isButton = isButton;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<SystemMenuEntity> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<SystemMenuEntity> subMenuList) {
		this.subMenuList = subMenuList;
	}
}
