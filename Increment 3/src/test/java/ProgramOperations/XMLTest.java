/*import Model.Obstacle;
import Controller.XMLHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

public class XMLTest {

    @Test
    public void XMLSaveLoad(){
        Obstacle o1 = new Obstacle("Rock",132,10.0,9.0,12.0, Optional.empty());
        Obstacle identical;
        try{
            XMLHandler.saveObjectAsXML(o1,"XMLSaveLoad.xml");
            identical = (Obstacle) XMLHandler.loadObject("XMLSaveLoad.xml");
            Assertions.assertEquals(o1, identical);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
*/
