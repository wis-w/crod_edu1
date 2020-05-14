package com.edu.croed.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.edu.crowd.entity.Admin;
import com.edu.crowd.mapper.AdminMapper;
import com.edu.crowd.service.api.AdminService;

/**
 * @author wyg_edu
 * @version v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {
	
	@Autowired
	private DataSource daraSource;
	
	@Autowired
	private AdminMapper adminMapper; 
	
	@Autowired
	private AdminService adminService;
	
	@Test
	public void testLog() {
		Logger log = LoggerFactory.getLogger(CrowdTest.class);
//		log.debug("debug");
		log.info("info");
		log.error("error");
	}
	 
	@Test
	public void testTxN() throws Exception {
		for(int i=0;i<151;i++) {
			testTx(String.valueOf(i));
		}
	}
	public void testTx(String s) throws Exception{
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		Admin admin = new Admin(null, "路飞"+s, "11111", "asd", "163.com", sdf.format(new Date()));
		adminService.saveAdmin(admin);
	}
	
	@Test
	public void testInsertAdmin() {
		Admin admin = new Admin(null, "tom", "321312", "��ķ", "qq.com", null);
		System.out.println(adminMapper.insertSelective(admin));
	}
	
	@Test 
	public void testConnection() throws Exception {
		System.out.println(daraSource.getConnection());
	}

}
