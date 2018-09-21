package pl.hycom.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Employee findEmployeeByEmail(String email){
        return employeeRepository.findByEmail(email);
    }

    public void saveEmployee(Employee employee){
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employee.setRole(RoleEnum.ROLE_EMPLOYEE);

        employeeRepository.save(employee);
    }
}
