package eurekademo.service;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountService {
	private List<Account> list = new ArrayList<Account>();

    public void insertAccount(Account acc) {
    	list.add(acc);
    }

    public List<Account> getAccounts(String name) {
        return list;
    }

}
