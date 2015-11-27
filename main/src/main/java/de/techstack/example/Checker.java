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

    public void start() {
        String[] lcBLZs = {"40060000", "49050101", "49050102", "49050103", "10050000"};

        for (String lcBLZ : lcBLZs) {
            try {
                checkNumberAndWriteResultInDatabase(lcBLZ);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkNumberAndWriteResultInDatabase(String pBLZ) throws SQLException {
        try {
            DetailsType lcType = port.getBank(pBLZ);
            Environment.instance.getDbManager().writeResult(lcType, pBLZ);
            // System.out.printf("Name %s, BIC %s, BLZ %s\n", lcType.getBezeichnung(), lcType.getBic(), pBLZ);
        } catch (SOAPFaultException e) {
            Environment.instance.getDbManager().writeResult(null, pBLZ);
            //System.out.printf("Looks like BLZ %s is not a valid BLZ \n", pBLZ);
            // System.out.println(e.getMessage());
        }
    }
}
