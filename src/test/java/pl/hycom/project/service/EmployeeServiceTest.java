package pl.hycom.project.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    private Employee employee;
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Before
    public void setup() {
        employeeService = new EmployeeService(employeeRepository, bCryptPasswordEncoder);

        employee = new Employee();
        employee.setEmail("test@test.pl");

        Mockito.when(employeeRepository.findByEmail(Mockito.anyString())).thenReturn(employee);
    }

    @Test
    public void findEmployeeByEmail() {
        // given
        final String email = "test@test.pl";

        // when
        final Employee employee = employeeService.findEmployeeByEmail(email);

        // then
        assertEquals(email, employee.getEmail());
    }

    @Test
    public void saveEmployee() {
        // given
        final String email = "test@test.pl";

        // when
        employeeService.saveEmployee(employee);
        final Employee savedEmployee = employeeService.findEmployeeByEmail(email);

        // then
        assertEquals(email, savedEmployee.getEmail());
    }
}