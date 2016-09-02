package hello.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import eurekademo.service.Account;
import eurekademo.service.AccountService;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("file:config/applicationContext.xml");  
        AccountService service = (AccountService) context.getBean("accountServiceRemote");
        Account a = new Account();
        a.setName("123");
        Account a1 = new Account();
        a1.setName("456");
        service.insertAccount(a);
        service.insertAccount(a1);
        System.out.println(service.getAccounts(""));
	}

}
