package org.kiwi.atom.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static java.util.Arrays.asList;

@Path("orders.atom")
public class AtomResource {
    @GET
    @Produces(MediaType.APPLICATION_ATOM_XML)
    public Feed getOrdersAtomFeed(@Context UriInfo uriInfo) throws JsonProcessingException {
        Feed f = AbderaSupport.getAbdera().getFactory().newFeed();
        f.setTitle("hahaha");
//        f.setId(...);
        f.addAuthor("kiwi");
//        f.setUpdated(...);
        URI feedLink = uriInfo.getRequestUri();
        f.addLink(feedLink.toString(), "self");

//        for (...) {
//            Entry e = f.addEntry();
//            URI entryLink = ...
//            f.addLink(entryLink.toString(),"alternate");
//            ...
//        }

        final Order order1 = new Order(1, 1);
//        order1.amount = 1;
//        order1.quantity = 1;
        final Order order2 = new Order(2, 2);
//        order2.amount = 2;
//        order2.quantity = 2;
        final List<Order> orders = asList(order1, order2);

        for (Order order : orders) {
            Entry e = f.addEntry();
//            e.setContent(order.toString());
//            e.setContentElement(new Element())
            final XmlMapper xmlMapper = new XmlMapper();
            e.setContent(xmlMapper.writeValueAsString(order), MediaType.APPLICATION_XML);
        }

        return f;
    }
}


