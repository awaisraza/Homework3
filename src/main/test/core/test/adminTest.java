package core.test;
import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;
import core.api.IStudent;
import core.api.impl.Student;

import static org.junit.Assert.*;

public class adminTest {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    // Testing that Instructor cannot teach more than two classes in a given year. (Bug)
    @Test
    public void testMakeClass3() {
    		this.admin.createClass("className", 2017, "Bob", 20);
    		this.admin.createClass("className2", 2017, "Bob", 20);
    		this.admin.createClass("className3", 2017, "Bob", 20);
    		assertFalse(this.admin.classExists("className3", 2017));
    }
    
    // Testing that Instructor can teach two classes in a given year.
    @Test
    public void testMakeClass4() {
    		this.admin.createClass("className", 2017, "Bob", 20);
    		this.admin.createClass("className2", 2017, "Bob", 20);
    		assertTrue(this.admin.classExists("className2", 2017));
    }
    
    // Testing that capacity cannot be 0. (Bug)
    @Test
    public void testMakeClass5() {
    		this.admin.createClass("className", 2017, "Bob", 0);
		assertFalse(this.admin.classExists("className", 2017));	
    }
    
    // Testing that capacity must be greater than 0.
    @Test
    public void testMakeClass6() {
    		this.admin.createClass("className", 2017, "Bob", 2);
		assertTrue(this.admin.classExists("className", 2017));	
    }
    
    // Testing that capacity must cannot be less than 0. (Bug)
    @Test
    public void testMakeClass7() {
    		this.admin.createClass("className", 2017, "Bob", -1);
		assertFalse(this.admin.classExists("className", 2017));	
    }
    
    // Test to see if changeCapacity can be less than students enrolled. (Bug)
    @Test
    public void testchangeCapacity1() {
    		this.admin.createClass("English", 2017, "Bob", 1);
    		this.student.registerForClass("Awais", "English", 2017);
    		this.student.registerForClass("Raza", "English", 2017);
    		this.student.registerForClass("Friend", "English", 2017);
    		this.admin.changeCapacity("English", 2017, 2);
    		assertFalse(admin.getClassCapacity("English", 2017) == 2);
    }
    
    // Test to see if changeCapacity works if capacity is changed to the number of students enrolled.
    @Test
    public void testchangeCapacity2() {
    		this.admin.createClass("English", 2017, "Bob", 1);
    		this.student.registerForClass("Awais", "English", 2017);
    		this.student.registerForClass("Raza", "English", 2017);
    		this.student.registerForClass("Friend", "English", 2017);
    		this.admin.changeCapacity("English", 2017, 3);
    		assertTrue(admin.getClassCapacity("English", 2017) == 3);
    }
    
 // Test to see if changeCapacity works if capacity is <= 0. (Bug)
    @Test
    public void testchangeCapacity3() {
    		this.admin.createClass("English", 2017, "Bob", 1);
    		this.student.registerForClass("Awais", "English", 2017);
    		this.student.registerForClass("Raza", "English", 2017);
    		this.student.registerForClass("Friend", "English", 2017);
    		this.admin.changeCapacity("English", 2017, 0);
    		assertFalse(admin.getClassCapacity("English", 2017) == 0);
    }
    
}
