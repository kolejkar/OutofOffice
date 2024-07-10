package com.OutofOffice.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OutofOffice.Lists.Employee;
import com.OutofOffice.Repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> AllEmployees()
	{
		List<Employee> employees=new ArrayList<>();
		employeeRepository.findAll().forEach(employees::add);
		return employees;
	}
	
	/*public Adres GetAdres(Integer id)
	{
		return adresRepository.findById(id).get();
	}*/
	
	public void ChangeEmployeeStatus(Integer id, boolean status)
	{
		Employee employee = employeeRepository.getById(id);
		employee.setActive(status);
		employeeRepository.save(employee);
	}
	
	public void AddEmployee(Employee employee)
	{
		employeeRepository.save(employee);
	}

	public void UpdateEmployee(Integer id, Employee employee) {
		employeeRepository.save(employee);
	}
	
	public void DeleteEmployee(Integer id)
	{
		employeeRepository.deleteById(id);
	}
}
