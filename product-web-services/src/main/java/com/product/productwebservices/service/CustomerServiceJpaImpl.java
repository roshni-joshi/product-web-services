package com.product.productwebservices.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.product.productwebservices.dto.CustomerDTO;
import com.product.productwebservices.exception.CustomerNotFoundException;
import com.product.productwebservices.mapper.CustomerMapper;
import com.product.productwebservices.model.Customer;
import com.product.productwebservices.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("jpa")
@Slf4j
public class CustomerServiceJpaImpl implements CustomerService {

	private CustomerMapper customerMapper;
	private CustomerRepository customerRepository;
	
	public CustomerServiceJpaImpl(final CustomerMapper customerMapper, final CustomerRepository customerRepository) {
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		 log.debug("Customer Jpa : GetAllCustomers");
		 
		 List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
		 
		 List<Customer> customers = customerRepository.findAll();
		 
		 if(customers != null && customers.size()>0)
		 {
			 customerDTOList = customers.stream().map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());
		 }
		 
		 return customerDTOList;
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		log.debug("Customer Jpa : GetCustomerById " + id);
		
		CustomerDTO customerDTO = null;
		
		if(id != null)
		{
			Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("No customer found"));
			
			//Optional<Customer> optional = customerRepository.findById(id);
			//if(optional != null && optional.isPresent())
			//{
				//Customer customer = optional.get();
				if(customer != null)
				{
					customerDTO = customerMapper.customerToCustomerDTO(customer);
				}
			//}
		}
		
		return customerDTO;
	}

	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDTO) {
		
		log.debug("Customer Jpa : Create Customer");
		
		CustomerDTO savedCustomerDTO = null;
		
		if(customerDTO != null)
		{
			Customer customerToSave = customerMapper.customerDTOToCustomer(customerDTO);
			if(customerToSave != null)
			{
				Customer savedCustomer = customerRepository.save(customerToSave);
				savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
			}
		}
		
		return savedCustomerDTO;
	}

	@Override
	public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
		
		log.debug("Customer Jpa : Update Customer");
		return createCustomer(customerDTO);
	}

	@Override
	public void deleteCustomer(Long id) {
		
		log.debug("Customer Jpa : DeleteCustomerById " + id);
		
		if(id != null)
		{
			customerRepository.deleteById(id);
		}
	}

}
