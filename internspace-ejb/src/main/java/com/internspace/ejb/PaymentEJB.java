package com.internspace.ejb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.PaymentEJBLocal;
import com.internspace.entities.university.Payments;
import com.internspace.entities.university.University;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Authorization;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;


@Stateless
public class PaymentEJB implements PaymentEJBLocal {
	@PersistenceContext
	EntityManager em;
	private String cId ="AbjSMnIhwuiUhsiZcsw2zuNXyUkQRPXfvT-7iOmn4zI5ERWPU2J229dHIe3rHswtKucl7vDUVwAxniuZ";
	  private static String crunchifySecret = "EMXlrVMs0vCjcZW882fJ4QI6zZki7XyYGWAOmNnyQ9GiIc_2wUIFchu4MFrhCyrjXfa2Q4sjRSobFwHy";

	  private static String executionMode = "sandbox"; // sandbox or production
	 @Override
	public void PaymentPayPalAPI(long totale,long idU) {
		 Payments p= new Payments();
		// p.setPaymentDate(LocalDate.of(2019,11,12));
		 University u=em.find(University.class,idU);
		 System.out.println(u);
		 p.setUniversity(u);
		 em.persist(p);
		 em.flush();
		/*
		 * Flow would look like this: 
		 * 1. Create Payer object and set PaymentMethod 
		 * 2. Set RedirectUrls and set cancelURL and returnURL 
		 * 3. Set Details and Add PaymentDetails
		 * 4. Set Amount
		 * 5. Set Transaction
		 * 6. Add Payment Details and set Intent to "authorize"
		 * 7. Create APIContext by passing the clientID, secret and mode
		 * 8. Create Payment object and get paymentID
		 * 9. Set payerID to PaymentExecution object
		 * 10. Execute Payment and get Authorization
		 * 
		 */
 
		Payer crunchifyPayer = new Payer();
		crunchifyPayer.setPaymentMethod("paypal");
 
		// Redirect URLs
		
		RedirectUrls crunchifyRedirectUrls = new RedirectUrls();
		crunchifyRedirectUrls.setCancelUrl("http://localhost:3000/crunchifyCancel");
		crunchifyRedirectUrls.setReturnUrl("http://localhost:3000/crunchifyReturn");
 
		// Set Payment Details Object
		Details crunchifyDetails = new Details();
		crunchifyDetails.setShipping("2.22");
		crunchifyDetails.setSubtotal("3.33");
		crunchifyDetails.setTax("1.11");
 
		// Set Payment amount
		long tot=totale+2+1;
		Amount crunchifyAmount = new Amount();
		crunchifyAmount.setCurrency("USD");
		crunchifyAmount.setTotal("6.66");
		crunchifyAmount.setDetails(crunchifyDetails);
 
		// Set Transaction information
		Transaction crunchifyTransaction = new Transaction();
		crunchifyTransaction.setAmount(crunchifyAmount);
		crunchifyTransaction.setDescription("Crunchify Tutorial: How to Invoke PayPal REST API using Java Client?");
		List<Transaction> crunchifyTransactions = new ArrayList<Transaction>();
		crunchifyTransactions.add(crunchifyTransaction);
 
		// Add Payment details
		Payment crunchifyPayment = new Payment();
		
		// Set Payment intent to authorize
		crunchifyPayment.setIntent("authorize");
		crunchifyPayment.setPayer(crunchifyPayer);
		crunchifyPayment.setTransactions(crunchifyTransactions);
		crunchifyPayment.setRedirectUrls(crunchifyRedirectUrls);
		
 
		// Pass the clientID, secret and mode. The easiest, and most widely used option.
		APIContext crunchifyapiContext = new APIContext(cId, crunchifySecret, executionMode);
 
		try {
 
			Payment myPayment = crunchifyPayment.create(crunchifyapiContext);
 
			System.out.println("createdPayment Obejct Details ==> " + myPayment.toString());
 
			// Identifier of the payment resource created 
			crunchifyPayment.setId(myPayment.getId());
			
			PaymentExecution crunchifyPaymentExecution = new PaymentExecution();
			System.out.println("aaaaaaaaaaaaaaaaaaaa");

			// Set your PayerID. The ID of the Payer, passed in the `return_url` by PayPal.
			crunchifyPaymentExecution.setPayerId(myPayment.getPayer().getPayerInfo().getPayerId());
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			// This call will fail as user has to access Payment on UI. Programmatically
			// there is no way you can get Payer's consent.
			Payment createdAuthPayment = crunchifyPayment.execute(crunchifyapiContext, crunchifyPaymentExecution);
			System.out.println("aaaaaaaaaaaassssssssssssssssaaaaaaaa");

			// Transactional details including the amount and item details.
			Authorization crunchifyAuthorization = createdAuthPayment.getTransactions().get(0).getRelatedResources().get(0).getAuthorization();
			System.out.println("aaaaaaaaaaaa4444444444aaaaaaaa");

 
		} catch (PayPalRESTException e) {
 
			// The "standard" error output stream. This stream is already open and ready to
			// accept output data.
			System.err.println(e.getDetails());
		}
	}
		
	}
	


