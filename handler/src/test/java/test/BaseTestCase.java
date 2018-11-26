
package test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations="classpath:/META-INF/spring/saint-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTestCase {

}
