package test;

import com.zjc.sagas.handler.SagasHandler;
import com.zjc.sagas.model.SagasContext;
import com.zjc.sagas.model.SagasDate;
import com.zjc.sagas.utils.SeqCreateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;

@ContextConfiguration(locations= "classpath:/spring/sagas.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Demo {
    @Autowired
    private SagasHandler sagasHandler;
    @Test
    public void testCommit(){
        LinkedList<SagasDate> sagasDates = new LinkedList<>();
        sagasDates.add(getSagasDate(true));
        sagasDates.add(getSagasDate(true));
        sagasDates.add(getSagasDate(true));
        sagasHandler.handler(sagasDates, SeqCreateUtil.create("ceshi"),1);

    }
    @Test
    public void  testRollBack() {
        LinkedList<SagasDate> sagasDates = new LinkedList<>();
        sagasDates.add(getSagasDate(true));
        sagasDates.add(getSagasDate(true));
        sagasDates.add(getSagasDate(false));
        sagasHandler.handler(sagasDates, SeqCreateUtil.create("ceshi"),1);
    }
    private SagasDate getSagasDate(boolean b) {
        SagasProcessorImpl1 processorImpl1 = new SagasProcessorImpl1();
        SagasContext<Boolean> context1 = new SagasContext<>();
        context1.setParam(b);

        SagasDate sagasDate1 = new SagasDate();
        sagasDate1.setContext(context1);
        sagasDate1.setProcessor(processorImpl1);
        return sagasDate1;
    }
}
