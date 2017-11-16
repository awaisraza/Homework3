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

public class studentTest {

    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
    }
    
    // Test to see if student successfully registers for a class that has not met its enrollment capacity.
    @Test
    public void testregisterForClass1() {
    		this.admin.createClass("English", 2017, "Billy", 2);
    		this.student.registerForClass("Awais", "English", 2017);
    		assertTrue(student.isRegisteredFor("Awais", "English", 2017));
    }
    
    // Test to see if student successfully registers for a class that has exceeded its enrollment capacity. (Bug)
    @Test
    public void testregisterForClass2() {
    		this.admin.createClass("English", 2017, "Billy", 1);
    		this.student.registerForClass("Awais", "English", 2017);
    		this.student.registerForClass("Raza", "English", 2017);
    		assertFalse(student.isRegisteredFor("Raza", "English", 2017));
    }
    
    // Test to see if student is registered for a class that does not exist.
    @Test
    public void testregisterForClass3() {
    		this.student.registerForClass("Awais", "TheEasyAClass", 2017);
    		assertFalse(student.isRegisteredFor("Awais", "TheEasyAClass", 2017));
    }
    
    // Test to see registered student is able to drop class.
    @Test
    public void testdropClass1() {
    		this.admin.createClass("English", 2017, "Billy", 2);
    		this.student.registerForClass("Awais", "English", 2017);
    		this.student.dropClass("Awais", "English", 2017);
    		assertFalse(student.isRegisteredFor("Awais", "English", 2017));
    }
    
    // Test to see registered student is able to drop class that is ended.
    @Test
    public void testdropClass2() {
    		this.admin.createClass("English", 2016, "Billy", 2);
    		this.student.registerForClass("Awais", "English", 2016);
    		this.student.dropClass("Awais", "English", 2016);
    		assertFalse(student.isRegisteredFor("Awais", "English", 2016));
    }
    
    // Test to see if student submits homework but is not registered for class. (Bug)
    @Test
    public void testsubmitHomework1() {
    		this.admin.createClass("English", 2017, "Billy", 1);
    		this.instructor.addHomework("Billy", "English", 2017, "Hw1");
    		this.student.submitHomework("Awais", "Hw1", "Answers", "English", 2017);
    		assertFalse(student.hasSubmitted("Awais", "Hw1", "English", 2017));
    }
    
    // Test to see if the class is a future class not current. (Bug)
    @Test
    public void testsubmitHomework2() {
    		this.admin.createClass("English", 2020, "Billy", 1);
    		this.student.registerForClass("Awais", "English", 2020);
    		this.instructor.addHomework("Billy", "English", 2020, "Hw1");
    		this.student.submitHomework("Awais", "Hw1", "Answers", "English", 2020);
    		assertFalse(student.hasSubmitted("Awais", "Hw1", "English", 2020));
    }
    
    // Test to see if student submits homework that does not exist.
    @Test
    public void testsubmitHomework3() {
    		this.admin.createClass("English", 2017, "Billy", 1);
    		this.student.registerForClass("Awais", "English", 2017);
    		this.student.submitHomework("Awais", "Hw1", "Answers", "English", 2017);
    		assertFalse(student.hasSubmitted("Awais", "Hw1", "English", 2017));
    }
 
}
