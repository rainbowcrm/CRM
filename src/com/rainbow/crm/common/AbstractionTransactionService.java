package com.rainbow.crm.common;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;

@Transactional(rollbackFor = Exception.class)
public abstract class AbstractionTransactionService extends AbstractService  implements ITransactionService{

	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
