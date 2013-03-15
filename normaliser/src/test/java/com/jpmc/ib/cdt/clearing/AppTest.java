package com.jpmc.ib.cdt.clearing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
@DirtiesContext()
public class AppTest {

    @Autowired
    @Qualifier("amq.inbound")
    Destination inbound;

    @Autowired
    @Qualifier("amq.outbound")
    Destination outbound;

    @Autowired
    JmsTemplate template;

    @Test
    public void testApp() {
        final String message = "test";
        template.convertAndSend("amq.inbound", message);

        final String value = String.class.cast(template.receiveAndConvert("amq.outbound"));
        assertNotNull(value);
        assertEquals(message, value);

    }
}
