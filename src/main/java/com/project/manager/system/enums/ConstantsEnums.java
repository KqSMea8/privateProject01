package com.project.manager.system.enums;

public class ConstantsEnums {

	public static enum COMMON_STATUS {
		禁用("禁用", "0"), 启用("启用", "1");
		private String statusEnum;
		private String statusEnumId;

		private COMMON_STATUS(String statusEnum, String statusEnumId) {
			this.statusEnum = statusEnum;
			this.statusEnumId = statusEnumId;
		}

		public String getStatusEnum() {
			return statusEnum;
		}

		public String getStatusEnumId() {
			return statusEnumId;
		}
	}

	public static enum MEMBER_USER_STATUS_ENUM {
		未激活用户("未激活用户", "0"), 激活用户("激活用户", "1"), 已冻结用户("已冻结用户", "2");
		private String name;
		private String code;

		private MEMBER_USER_STATUS_ENUM(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public final String getName() {
			return name;
		}

		public final String getCode() {
			return code;
		}
	}

	public static enum AUDIT_STATUS_ENUM {
		待处理("待处理", "0"), 成功("成功", "1"), 失败("失败", "2");
		private String name;
		private String code;

		private AUDIT_STATUS_ENUM(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public final String getName() {
			return name;
		}

		public final String getCode() {
			return code;
		}
	}

	public static enum NOTICE_TYPE_ENUM {
		滚动("滚动", "1"), 列表("列表", "2");
		private String name;
		private String code;

		private NOTICE_TYPE_ENUM(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public final String getName() {
			return name;
		}

		public final String getCode() {
			return code;
		}
	}
	
	public static enum COMPETITION_SUB_TYPE_ENUM {
		半场("半场", "1"), 全场("全场", "2"), 全场总进球以上("全场总进球以上", "3");
		private String name;
		private String code;

		private COMPETITION_SUB_TYPE_ENUM(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public final String getName() {
			return name;
		}

		public final String getCode() {
			return code;
		}
	}

	public static enum YSE_NO_ENUM {
		是("是", "1"), 否("否", "0");
		private String name;
		private String code;

		private YSE_NO_ENUM(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public final String getName() {
			return name;
		}

		public final String getCode() {
			return code;
		}
	}
}
