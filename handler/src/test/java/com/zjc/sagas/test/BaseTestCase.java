
package com.zjc.sagas.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations="classpath:/spring/sagas.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTestCase {

}
