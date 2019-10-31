package com.internspace.ejb.abstraction;

import javax.ejb.Local;

@Local
public interface PaymentEJBLocal {
	 void PaymentPayPalAPI(long tot,long iD) ;

}
