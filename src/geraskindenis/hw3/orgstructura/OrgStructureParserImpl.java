package geraskindenis.hw3.orgstructura;

import geraskindenis.hw3.orgstructura.Exceptions.InvalidFormatCSVException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

public class OrgStructureParserImpl implements OrgStructureParser {
    private static final String formatCSV = "id;boss_id;name;position";

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        FileReader fileReader = new FileReader(csvFile);
        Scanner scanner = new Scanner(fileReader);
        boolean firstLine = true;
        Employee absolutelyBoss = null;
        Map<Long, Employee> employeeMap = new TreeMap<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (firstLine) {
                if (!line.equals(formatCSV)) {
                    throw new InvalidFormatCSVException("Invalid CSV file format.");
                }
                firstLine = false;
                continue;
            }
            Employee employee = new Employee();
            String[] subStrings = line.split(";");
            employee.setId(Long.parseLong(subStrings[0]));
            employee.setName(subStrings[2]);
            employee.setPosition(subStrings[3]);

            String strBossId = subStrings[1];
            if (strBossId.isEmpty()) {
                absolutelyBoss = employee;
            } else {
                employee.setBossId(Long.parseLong(strBossId));
            }
            employeeMap.put(employee.getId(), employee);
        }

        employeeMap.values().stream().filter(e -> Objects.nonNull(e.getBossId())).forEach(e -> {
            Employee boss = employeeMap.get(e.getBossId());
            if (Objects.nonNull(boss)) {
                e.setBoss(boss);
                boss.getSubordinate().add(e);
            }
        });

        return absolutelyBoss;
    }
}
