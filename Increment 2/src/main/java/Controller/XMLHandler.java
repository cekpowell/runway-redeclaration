package Controller;

import IO.IOHandler;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class XMLHandler {

    public static void saveObjectAsXML(Object o, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(IOHandler.resourceAbs + IOHandler.seperator + filename);
        XMLEncoder encoder = new XMLEncoder(fos);
        encoder.setExceptionListener(new ExceptionListener() {
            public void exceptionThrown(Exception e) {
                e.printStackTrace();
            }
        });
        encoder.writeObject(o);
        encoder.close();
        fos.close();

    }


    public static Object loadObject(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        XMLDecoder decoder = new XMLDecoder(fis);
        Object decoded = decoder.readObject();
        decoder.close();
        fis.close();
        return decoded;
    }

    public static Object loadObject(InputStream fis) throws IOException {
        XMLDecoder decoder = new XMLDecoder(fis);
        Object decoded = decoder.readObject();
        decoder.close();
        fis.close();
        return decoded;
    }


}
