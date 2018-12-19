package com.project.common.sms.entity;

import java.util.ArrayList;
import java.util.List;

public class SMSParameter {
	private static final String VALID_CODE = "code";

	private List<P> pl = new ArrayList<P>();

	public List<P> getParamsList() {
		return pl;
	}

	public SMSParameter addValidCode(String validCode) {
		P p = new P(VALID_CODE, validCode);
		pl.add(p);

		return this;
	}

	public P get(int index) {
		return pl.get(index);
	}

	public int size() {
		return pl.size();
	}

	public class P {
		private String key;
		private String value;

		public P(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
