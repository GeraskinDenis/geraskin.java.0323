package geraskindenis.hw4.filesort;

import geraskindenis.hw3.filesort.Generator;
import geraskindenis.hw4.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class FileSorterTestNoBatch {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("=== Testing  FileSortImplNoBatch ===");
        System.out.println("Start : " + new Date());
        DataSource dataSource = initDb();
//        File data = new File("data.txt");
        File data = new Generator().generate("src/geraskindenis/hw4/filesort/data.txt", 1_000_000);
        System.out.println("File sorted in descending order: " + new Validator(data).isSortedDescending()); // false
        FileSorter fileSorter = new FileSortImplNoBatch(dataSource);
        System.out.println("Time before sorting: " + new Date());
        File res = fileSorter.sort(data);
        System.out.println("Time after sorting: " + new Date());
        System.out.println("File sorted in descending order: " + new Validator(res).isSortedDescending()); // true
    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = ""
                + "drop table if exists numbers;"
                + "CREATE TABLE if not exists numbers (\n"
                + "\tval bigint\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
