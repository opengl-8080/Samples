package sample.web;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import sample.domain.Sample;
import sample.domain.SampleValue;

@Stateless
@Path("hello")
public class HelloResource {
    @PersistenceContext(unitName="SampleUnit")
    private EntityManager em;
    
    @GET
    public String hello(@QueryParam("value") String value) {
        Sample sample = new Sample();
        sample.setValue(new SampleValue(value));
        
        System.out.println("[before persist]" + sample);
        
        this.em.persist(sample);
        
        System.out.println("[after persist]" + sample);
        
        this.em.flush();
        
        System.out.println("[after flush]" + sample);
        
        
        TypedQuery<Sample> query = this.em.createNamedQuery("Sample.findAll", Sample.class);
        return query.getResultList().stream().map(Sample::toString).collect(Collectors.joining("\n"));
    }
}
