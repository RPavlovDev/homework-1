package ru.otus.spring.dao;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDaoImpl implements QuestionDao {

    private final String csvFile;
    private List<CSVRecord> records;


    public QuestionDaoImpl(String csvFile) {
        this.csvFile = csvFile;

        try (InputStream is = getClass().getResourceAsStream(csvFile)) {
            if (is == null)
                throw new IOException("Can't get resource: " + csvFile);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("id", "question", "answer")
                    .setSkipHeaderRecord(true)
                    .build();
            this.records = IteratorUtils.toList(CSVParser.parse(is, StandardCharsets.UTF_8, csvFormat).iterator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Question> getAll() {
        return records.stream()
                .map(rec -> new Question(Integer.parseInt(rec.get("id")), rec.get("question"), rec.get("answer")))
                .collect(Collectors.toList());
    }

    @Override
    public Question findById(int id) {
        return records.stream()
                .filter(x -> Integer.parseInt(x.get("id")) == id)
                .map(rec -> new Question(Integer.parseInt(rec.get("id")), rec.get("question"), rec.get("answer")))
                .findFirst()
                .orElse(null);
    }
}
