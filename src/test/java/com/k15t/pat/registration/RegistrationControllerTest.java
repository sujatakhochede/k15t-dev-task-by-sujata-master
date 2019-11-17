package com.k15t.pat.registration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.k15t.pat.util.RegistrationValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.k15t.pat.domain.Registration;
import com.k15t.pat.service.RegistrationService;


/**
 * RegistrationControllerTest tests the RegistrationController.
 * 
 * @author Sujata
 * 
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = RegistrationController.class)
public class RegistrationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RegistrationService registrationService;
	
	@MockBean
	private RegistrationValidator registrationValidator;
	
	
	/**
	 * getRegistration retrieves the specific registration and asserts the value
	 * @throws Exception
	 */
	
	
	@Test
	public void getRegistration() throws Exception {
		Registration mockRegistration = new Registration("Sujata","Sujata","Dubai","Sujata@gmail.com",new BigDecimal("0567882265"));
		
		Mockito.when(
				registrationService.get(Mockito.anyLong())).thenReturn(mockRegistration);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/registerforms/edit/1").accept(
				MediaType.ALL);

		MvcResult result = mockMvc.perform(requestBuilder)
								  .andExpect(status().isOk())
								  .andExpect(view().name("registration/registrationForm"))
				                   .andReturn();
		
		Map<String,Object> resultMap =  result.getModelAndView().getModel();
		Registration registration = (Registration) resultMap.get("registration");
		
		assertEquals("Sujata@gmail.com", registration.getEmail());
		assertEquals("Sujata", registration.getName());
		
	}
	
	/**
	 * getAllRegistration  retrieves  all the registrations and asserts the values
	 * @throws Exception
	 */
	
	@Test
	public void getAllRegistration() throws Exception {
		Registration mockRegistration = new Registration("Sujata","Sujata","Dubai","Sujata@gmail.com",new BigDecimal("0567882265"));
		Registration mockRegistration2 = new Registration("Ironman","Ironman","Berlin","Ironman@gmail.com",new BigDecimal("9657701539"));
		List<Registration> registrationList = new ArrayList<Registration>();
		registrationList.add(mockRegistration);
		registrationList.add(mockRegistration2);
		Mockito.when(
				registrationService.list()).thenReturn(registrationList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/registerforms/list").accept(
				MediaType.ALL);

		MvcResult result = mockMvc.perform(requestBuilder)
								  .andExpect(status().isOk())
								  .andExpect(view().name("registration/list"))
				                   .andReturn();
		
		Map<String,Object> resultMap =  result.getModelAndView().getModel();
		List<Registration> registrationNewList = (List<Registration>) resultMap.get("registrations");
		
		
		assertEquals("Sujata@gmail.com", registrationNewList.get(0).getEmail());
		assertEquals("Sujata", registrationNewList.get(0).getName());
		
	}
	
	/**
	 * saveRegistration creates a new Registration and asserts the values
	 * @throws Exception
	 */
	@Test
	public void saveRegistration() throws Exception {
		Registration mockRegistration = new Registration("Sujata","Sujata","Dubai","Sujata@gmail.com",new BigDecimal("0567882265"));
		mockRegistration.setConfirmPassword("Sujata");
		Mockito.when(
				registrationValidator.matchPasswords(Mockito.anyObject())).thenReturn(true);
		Mockito.when(
				registrationService.create(Mockito.anyObject())).thenReturn(mockRegistration);
	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/registerforms/save")
				                                               .accept(MediaType.ALL);

		   mockMvc.perform(requestBuilder)
				  .andExpect(status().isOk())
				  .andReturn();	
	}
	
	/**
	 * deleteRegistration deletes the given Registration instance and asserts the values
	 * @throws Exception
	 */
	@Test
	public void deleteRegistration() throws Exception {
		Registration mockRegistration = new Registration("Sujata","Sujata","Dubai","Sujata@gmail.com",new BigDecimal("0567882265"));
		mockRegistration.setConfirmPassword("Sujata");
		
		Mockito.when(
				registrationService.delete(Mockito.anyLong())).thenReturn(mockRegistration);
	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/registerforms/delete/1")
				.accept(MediaType.ALL);

				mockMvc.perform(requestBuilder)
					   .andExpect(view().name("redirect:/registerforms/list"))
					   .andExpect(status().is3xxRedirection())
				       .andReturn();
	}
	
	
	
}
