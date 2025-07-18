package org.zhonghe;

import org.zhonghe.http.HttpClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class SyncJob {

    // ==== 配置 =================================================================================
    private static final String JDBC_URL  = "jdbc:dm://127.0.0.1:5236/DMDB";
    private static final String JDBC_USER = "DMUSER";
    private static final String JDBC_PWD  = "DMPASS";
    private static final String STAGING_FILE = "data/c_202507.json";   // 示例：接口 C 拉取的本地文件
    // ==========================================================================================



    public static void main(String[] args) throws Exception {
        // 示例用法：可以从命令行参数、配置文件或UI获取这些值
        String yearMonth = "202504";
        Path out = Paths.get("/data/output", "zget_repeated_" + yearMonth + ".txt");

        HttpClient httpClient = new HttpClient();
        httpClient.downloadReport(yearMonth, out);
    }

    /**
     * 步骤 0：清空临时表 C_stage 并批量插入接口原始数据
     */
    private static void loadCStage(Connection conn) throws Exception {
        try (Statement st = conn.createStatement()) {
            st.execute("TRUNCATE TABLE C_stage");
        }

        String insert = "INSERT INTO C_stage (id, col1, col2) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(insert);
             BufferedReader br = new BufferedReader(new FileReader(STAGING_FILE))) {

            String line;
            int batch = 0;
            final int BATCH_SIZE = 1000;

            while ((line = br.readLine()) != null) {
                // 假设 JSON ⇢ 简单切割解析；生产场景请用 Jackson / Gson
                String[] parts = line.replace("\"", "")
                        .replace("{", "")
                        .replace("}", "")
                        .split(",");
                long   id   = Long.parseLong(parts[0].split(":")[1]);
                String col1 = parts[1].split(":")[1];
                String col2 = parts[2].split(":")[1];

                ps.setLong(1, id);
                ps.setString(2, col1);
                ps.setString(3, col2);
                ps.addBatch();

                if (++batch % BATCH_SIZE == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();   // flush
        }
        System.out.println("C_stage loaded.");
    }

    /**
     * 步骤 1：把真正新增写入 A 与 B
     */
    private static void insertNewRows(Connection conn) throws SQLException {
        // 1-a -> A
        String sqlA =
                "INSERT INTO A (id, col1, col2) \n" +
                        "SELECT c.id, c.col1, c.col2 \n" +
                        "FROM   C_stage c \n" +
                        "LEFT JOIN A a ON a.id = c.id \n" +
                        "LEFT JOIN B b ON b.id = c.id \n" +
                        "WHERE  a.id IS NULL AND b.id IS NULL";

        // 1-b -> B   (同样逻辑，可合并写两次 INSERT)
        String sqlB =
                "INSERT INTO B (id, col1, col2) \n" +
                        "SELECT c.id, c.col1, c.col2 \n" +
                        "FROM   C_stage c \n" +
                        "LEFT JOIN A a ON a.id = c.id \n" +
                        "LEFT JOIN B b ON b.id = c.id \n" +
                        "WHERE  a.id IS NULL AND b.id IS NULL";

        try (Statement st = conn.createStatement()) {
            int rowsA = st.executeUpdate(sqlA);
            int rowsB = st.executeUpdate(sqlB);
            System.out.printf("Inserted %d → A, %d → B%n", rowsA, rowsB);
        }
    }
}