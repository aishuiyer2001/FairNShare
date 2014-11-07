

import java.util.List;

import models.Person;
import models.TaskInfo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import play.twirl.api.Content;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import play.mvc.Result;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

	@Test
	  public void sumTest() {     //Sample unit test to check and understand the basics of JUnit 
	    int a = 1 + 1;            
	    assertEquals(2, a);       //Check whether a=2 or not
	  }
	    
	  @Test
	  public void stringTest() {   //Sample unit test to check and understand the basics of JUnit
	    String str = "Hello world"; 
	    assertFalse(str.isEmpty()); //Asserts that string is not empty
	  }
    
	//Unit tests to check whether the html pages are being rendered properly or not
	  
    @Test
    public void renderIndexTemplateTest() {           //Unit test to check whether index page is rendered properly or not
        Content html = views.html.index.render("Sign Up.");//Giving Sign Up. as content to be displayed on index page
        assertThat(contentType(html)).isEqualTo("text/html");//Stating that input content given is either text or html
        assertThat(contentAsString(html)).contains("Sign Up.");//Checking whether the input given is being rendered on index page or not
    }

    @Test
    public void renderDashboardTemplateTest() {          //Unit test to check whether dashboard page is rendered properly or not
        Content html = views.html.dashboard.render("Welcome");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Welcome");//Checking whether the input given is being rendered on dashboard page or not
    }

   }
