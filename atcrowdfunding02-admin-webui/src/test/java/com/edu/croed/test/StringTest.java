package com.edu.croed.test;

import org.junit.Test;

import com.edu.croed.util.CrowdUtil;

/**
 * @author wyg_edu
 * @date 2020年5月8日 下午9:46:09
 * @version v1.0
 */
public class StringTest {
	
	@Test
	public void testMd5() {
		System.out.println(CrowdUtil.md5("123123"));
	}

}
