package com.internspace.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.internspace.ejb.abstraction.PaymentEJBLocal;

@Path ("payment")
@Stateless

public class PaymentService {
@Inject
PaymentEJBLocal payp;
@POST
@Path("/add/{totale}/{Idu}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public Response Payment(
		@PathParam(value="totale")long Name,@PathParam(value="Idu")long idU)
		
{

	payp.PaymentPayPalAPI(Name,idU);
	return Response.status(Status.OK).entity("OK").build();
}
}
