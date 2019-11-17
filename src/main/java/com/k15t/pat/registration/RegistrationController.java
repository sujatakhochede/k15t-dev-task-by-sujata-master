package com.k15t.pat.registration;

import com.k15t.pat.domain.Registration;
import com.k15t.pat.service.RegistrationService;
import com.k15t.pat.util.RegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * This controller is used to return and manipulate views to and from Thymeleaf
 * templates
 * 
 * @author Sujata
 *
 */

@Controller
@RequestMapping("/registerforms")
public class RegistrationController {

	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private RegistrationValidator registrationValidator;

	/**
	 * This method returns the list of all registrations
	 * 
	 * @param model
	 * @return view path name in the form of String
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("registrations", registrationService.list());
		return "registration/list";
	}

	/**
	 * This method takes the id and deletes the matching instance from the
	 * database
	 * 
	 * @param id id
	 * @param redirectAttrs model redirect attributes
	 * @param model model
	 * @return redirection String view path
	 */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs, Model model) {

		Registration registration = registrationService.delete(id);
		model.addAttribute("registration", registration);
		redirectAttrs.addFlashAttribute("message", "Registration has been deleted from records!");
		return "redirect:/registerforms/list";
	}

	/**
	 * This method takes the id and returns the matching instance from the
	 * database
	 * 
	 * @param id id
	 * @param model data model
	 * @return view path String as per the success of finding the exact instance
	 */

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {

		Registration registration = registrationService.get(id);
		if (registration != null) {
			model.addAttribute("registration", registration);
			return "registration/registrationForm";
		} else {
			return "redirect:/registerforms/list";
		}

	}

	/**
	 * This method saves the creation of a new registration.BindingResult checks
	 * for any errors in the fields and form is returned to the user if errors
	 * are present.matchPasswords() method of registrationValidator is called and
	 * appropriate error is added to BindingResult
	 * 
	 * @param registration registration row
	 * @param bindingResult bindign result
	 * @param model data model
	 * @return view path String as per the success or failure of the 'save' operation
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@Valid Registration registration, BindingResult bindingResult, Model model) {

		boolean passwordMatched = registrationValidator.matchPasswords(registration);
		if (!passwordMatched) {
			FieldError error = new FieldError("confirmPassword", "confirmPassword", "Given passwords are not matching");
			bindingResult.addError(error);
		}

		if (bindingResult.hasErrors()) {
			log.info("Binding Result has Errors ..." + bindingResult.getErrorCount());
			return "registration/registrationForm";
		} else {
			log.info("Binding Result has no Errors ...");
			Registration savedRegistration = registrationService.create(registration);
			model.addAttribute("registration", savedRegistration);

			return "registration/success";
		}

	}

	/**
	 * Thus method takes the id and returns the matching instance from the database 
	 * @param id is
	 * @param model data model
	 * @return view path String
	 * 
	 */

	@RequestMapping("/{id}")
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("registration", registrationService.get(id));
		return "registration/view";
	}

	/**
	 * This method redirects to the registration form for creating new registration
	 * 
	 * @param model data model
	 * @return view path String
	 */
	@RequestMapping("/create")
	public String create(Model model) {
		model.addAttribute("registration", new Registration());

		return "registration/registrationForm";
	}

}
