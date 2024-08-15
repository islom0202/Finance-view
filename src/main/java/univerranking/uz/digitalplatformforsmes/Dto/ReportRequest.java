package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportRequest {
    public enum ReportType {
        MONTHLY, QUARTERLY, ANNUAL, CUSTOM
    }

    private ReportType reportType;        // The type of report to generate
    private String reportName;            // Custom name for the report
    private String format;                // Report format (e.g., PDF, Excel)
    private String startDate;             // Start date for the report data
    private String endDate;               // End date for the report data
    private List<String> filters;         // List of filters to apply (e.g., by department, category)
    private List<String> columns;         // Specific columns to include in the report
    private Long dataId;
}
