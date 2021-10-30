package com.yp.admin;

import com.yp.core.BaseConstants;
import com.yp.core.ref.IReference;
import com.yp.core.ref.Reference;

public class Test {

	public static void main(String[] args) {
		IReference<String> ref = new Reference<>("A", BaseConstants.getString("1028"));
		System.out.println("ref :" + ref.getValue());

	}

}
