package de.techstack.example;


import com.thomas_bayer.blz.BLZService;
import com.thomas_bayer.blz.BLZServicePortType;
import com.thomas_bayer.blz.DetailsType;

import javax.xml.ws.soap.SOAPFaultException;
import java.sql.SQLException;

/**
 * Created by basti on 27.11.15.
 */
public class Checker {

    final BLZService service = new BLZService();
    final BLZServicePortType port;

    public Checker() {
        port = service.getBLZServiceSOAP12PortHttp();

    }

    public void checkNumberAndWriteResultInDatabase(String pBLZ) throws SQLException {
        try {
            DetailsType lcType = port.getBank(pBLZ);
            Environment.instance.getDbManager().writeResult(lcType, pBLZ);
        } catch (SOAPFaultException e) {
            Environment.instance.getDbManager().writeResult(null, pBLZ);
        }
    }
}
