package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.constant.RestConstant;
import guru.springframework.spring6restmvc.exception.NotFoundException;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(RestConstant.CUSTOMER_URL)
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("{customerId}")
    public ResponseEntity<CustomerDTO> patchCustomerById(@PathVariable UUID customerId, @RequestBody CustomerDTO customer) {
        if(customerService.updateCustomer(customerId, customer).isEmpty())
            throw new NotFoundException("Customer not found");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<CustomerDTO> deleteById(@PathVariable UUID customerId) {
        if(!customerService.deleteById(customerId)) {
            throw new NotFoundException("Customer not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable UUID customerId, @RequestBody CustomerDTO customer) {
        if(customerService.updateCustomer(customerId, customer).isEmpty())
            throw new NotFoundException("Customer not found");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> handlePostRequest(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<>(savedCustomer, headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping("{customerId}")
    public CustomerDTO getCustomerById(@PathVariable UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
