package geraskindenis.hw3.orgstructura;

import java.io.File;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        OrgStructureParser parser = new OrgStructureParserImpl();
        Employee employee = parser.parseStructure(new File("src\\geraskindenis\\hw3\\orgstructura\\src.csv"));
        System.out.println(employee);
    }
}
