package techit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.model.Ticket;
import techit.model.User;
import techit.model.dao.TicketDao;

@RestController
public class TicketController {

    @Autowired
    private TicketDao ticketDao;


    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public List<Ticket> getTickets()
    {
        return ticketDao.getTickets();
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.GET)
    public Ticket getTicket( @PathVariable Long id )
    {
        return ticketDao.getTicket( id );
    }
    
    @RequestMapping(value = "/tickets",method = RequestMethod.POST)
    public Ticket addTicket(@ModelAttribute("currentUser") User currentUser,@RequestBody Ticket ticket) {
    	ticket.setCreatedBy(currentUser);
    	return ticketDao.saveTicket(ticket);
    }
}
