package core.test;
import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;
import core.api.IStudent;
import core.api.impl.Student;
import core.api.IInstructor;
import core.api.impl.Instructor;

import static org.junit.Assert.*;

public class instructorTest {

    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
    }
    
    // Check to see if homework gets assigned when the right professor assigns it.
    @Test
    public void testaddHomework1() {
    		this.admin.createClass("Math", 2017, "Billy", 2);
        this.instructor.addHomework("Billy", "Math", 2017, "MathHw1");
        assertTrue(this.instructor.homeworkExists("Math", 2017, "MathHw1"));
    }
    
    // Check to see if homework is assigned by the right instructor. (Bug)
    @Test
    public void testaddHomework2() {
    		this.admin.createClass("Math", 2017, "Billy", 2);
    		this.admin.createClass("English", 2017, "Jerry", 2);
        this.instructor.addHomework("Jerry", "Math", 2017, "MathHw1");
        assertFalse(this.instructor.homeworkExists("Math", 2017, "MathHw1"));
    }
    
    // Test to make sure student was assigned to class in which the homework was submitted.
    @Test
    public void testassignGrade1() {
		this.admin.createClass("Math", 2017, "Billy", 2);
		this.instructor.addHomework("Billy", "Math", 2017, "MathHw1");
		this.student.registerForClass("Awais", "Math", 2017);
		this.student.submitHomework("Awais", "MathHw1", "Answers", "Math", 2017);
		this.instructor.assignGrade("Billy", "Math", 2017, "MathHw1", "Awais", 100);
		assertTrue(this.instructor.getGrade("Math", 2017, "MathHw1", "Awais") == 100);
    }
    
    // Test to see if homework grade is assigned to a student who hasn't been assigned to the class. (Bug)
    @Test
    public void testassignGrade2() {
		this.admin.createClass("Math", 2017, "Billy", 2);
		this.instructor.addHomework("Billy", "Math", 2017, "MathHw1");
		this.student.submitHomework("Bob", "MathHw1", "Answer", "Math", 2017);
		this.instructor.assignGrade("Billy", "Math", 2017, "MathHw1", "Bob", 100);
		assertFalse(this.instructor.getGrade("Math", 2017, "MathHw1", "Bob") == 100);
    }
    
    // Test to see if homework grade is less than 0. (Bug)
    @Test
    public void testassignGrade3() {
    		this.admin.createClass("Math", 2017, "Billy", 2);
		this.instructor.addHomework("Billy", "Math", 2017, "MathHw1");
		this.student.registerForClass("Awais", "Math", 2017);
		this.student.submitHomework("Awais", "MathHw1", "Answers", "Math", 2017);
		this.instructor.assignGrade("Billy", "Math", 2017, "MathHw1", "Awais", -100);
		assertFalse(this.instructor.getGrade("Math", 2017, "MathHw1", "Awais") == -100);
    }
    
    // Test to see if student receives a grade although they did not submit their homework. (Bug)
    @Test
    public void testassignGrade4() {
    		this.admin.createClass("Math", 2017, "Billy", 2);
		this.instructor.addHomework("Billy", "Math", 2017, "MathHw1");
		this.student.registerForClass("Awais", "Math", 2017);
		this.instructor.assignGrade("Billy", "Math", 2017, "MathHw1", "Awais", 100);
		assertFalse(this.instructor.getGrade("Math", 2017, "MathHw1", "Awais") == 100);
    }
    
}
