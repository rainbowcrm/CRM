package com.rainbow.crm.user.service;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.user.model.User;

public interface IUserService extends IBusinessService{

	 public User getByEmail(String email);
	 public User getByPhone(String phone);
}
