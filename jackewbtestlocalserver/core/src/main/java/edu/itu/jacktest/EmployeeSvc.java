package edu.itu.jacktest;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/employee")
public class EmployeeSvc {
	@GET
	@Path("/getEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getEmployee() {
		Employee employee = new Employee();
		employee.setName("John");
		employee.setAge(25);
		employee.setDeparment("HR");
		employee.setWage(15000.00);
		Address address = new Address();
		address.setCity("Massachusetts");
		address.setState("Springfield");
		address.setStreet("Evergreen");
		address.setZip(66450);
		employee.setAddress(address);
		return employee;
	}
	@POST
	@Path("/postEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void postEmployee(Employee employee) {
			System.out.println("Output json server .... \n");
			System.out.println(employee);
		}
} 