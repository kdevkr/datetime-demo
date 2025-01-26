package kr.kdev.demo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class SampleApi {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Month.class, new MonthPropertyEditor());
    }

    @GetMapping("/stat/{year:[0-9]{4}}")
    public ResponseEntity<Map<String, String>> getStatOfYear(@PathVariable("year") Year year) {
        log.info("Request for year: {}", year);

        LocalDate fromDate = year.atDay(1);
        LocalDate toDate = year.atDay(year.length());
        return ResponseEntity.ok(Map.of(
                "from", fromDate.format(DATE_FORMAT),
                "to", toDate.format(DATE_FORMAT)));
    }

    @GetMapping("/stat/{yearMonth:[0-9]{4}-[0-9]{2}}")
    public ResponseEntity<Map<String, String>> getStatOfYearMonth(
            @DateTimeFormat(pattern = "yyyy-MM")
            @PathVariable("yearMonth") YearMonth yearMonth) {
        log.info("Request for year-month: {}", yearMonth);

        LocalDate fromDate = yearMonth.atDay(1);
        LocalDate toDate = yearMonth.atEndOfMonth();
        return ResponseEntity.ok(Map.of(
                "from", fromDate.format(DATE_FORMAT),
                "to", toDate.format(DATE_FORMAT)));
    }

    @GetMapping("/stat/{year}/{month}")
    public ResponseEntity<Map<String, String>> getStatOfYearMonth(@PathVariable("year") Year year,
                                                @PathVariable("month") Month month) {
        return getStatOfYearMonth(YearMonth.of(year.getValue(), month));
    }

    private static class MonthPropertyEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text.matches("\\d+")) {
                setValue(Month.of(Integer.parseInt(text)));
                return;
            }
            setValue(Month.valueOf(text.toUpperCase()));
        }
    }
}
