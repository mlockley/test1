package com.jpmc.ib.cdt.clearing;

import com.jpmc.ib.cdt.clearing.fixml.ExecutionReportMessageT;
import com.jpmc.ib.cdt.clearing.fixml.FIXML;
import com.jpmc.ib.cdt.clearing.fixml.ObjectFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;
import javax.xml.bind.JAXBElement;

import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
//@DirtiesContext()
public class AppTest {

    public static Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Autowired
    @Qualifier("amq.inbound")
    Destination inbound;

    @Autowired
    @Qualifier("amq.outbound")
    Destination outbound;

    @Autowired
    JmsTemplate template;

    private String createAndSendFixMessage() {
        ObjectFactory factory = new ObjectFactory();
        FIXML fixml = factory.createFIXML();

        ExecutionReportMessageT executionReportMessageT = factory.createExecutionReportMessageT();
        JAXBElement<ExecutionReportMessageT> execRpt = factory.createExecRpt(executionReportMessageT);
        fixml.setMessage(execRpt);

        StringWriter writer = new StringWriter();
        javax.xml.bind.JAXB.marshal(fixml, writer);
        String output = writer.toString();
        logger.info("output = \n{}",output);
        return output;
    }


    @Test
    public void testApp() throws InterruptedException {

        while (true) {

            final String fixMessage = createAndSendFixMessage();
            template.convertAndSend("amq.inbound", fixMessage);

            final String value = String.class.cast(template.receiveAndConvert("amq.outbound"));
            assertNotNull(value);
            assertEquals(fixMessage, value);

            TimeUnit.SECONDS.sleep(1);
        }


    }
}
