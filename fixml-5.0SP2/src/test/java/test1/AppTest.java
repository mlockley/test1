package test1;


import uk.co.thelockleys.fixml.ExecutionReportMessageT;
import uk.co.thelockleys.fixml.FIXML;
import uk.co.thelockleys.fixml.ObjectFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;

import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    Logger logger = LoggerFactory.getLogger(AppTest.class);
    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp() {
        assertTrue(true);
    }

    @Test
    public void testFixML () {
        ObjectFactory factory = new ObjectFactory();
        FIXML fixml = factory.createFIXML();

        ExecutionReportMessageT executionReportMessageT = factory.createExecutionReportMessageT();
        JAXBElement<ExecutionReportMessageT> execRpt = factory.createExecRpt(executionReportMessageT);
        fixml.setMessage(execRpt);

        String output = serializeUsingJAXB(fixml);
        logger.info("output = \n{}",output);
    }


    public static <T> String serializeUsingJAXB(T entity) {
        StringWriter writer = new StringWriter();
        javax.xml.bind.JAXB.marshal(entity, writer);
        return writer.toString();
    }

}


