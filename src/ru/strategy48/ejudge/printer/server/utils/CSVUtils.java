package ru.strategy48.ejudge.printer.server.utils;

import com.opencsv.CSVReader;
import ru.strategy48.ejudge.printer.server.exceptions.PrinterServerException;
import ru.strategy48.ejudge.printer.server.objects.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 * Some useful methods for working with CSV files
 */
public class CSVUtils {
    /**
     * Parses permitted users table from given file
     *
     * @param path {@link Path} to given CSV file
     * @return {@link Map} from login to {@link User} object
     * @throws PrinterServerException if error happened while parsing CSV file
     */
    public static Map<String, User> parseUsersTable(final Path path) throws PrinterServerException {
        List<String[]> rows;

        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new FileInputStream(path.toFile())), ';', '\"', 0)) {
            rows = reader.readAll();
        } catch (IOException e) {
            throw new PrinterServerException("Error happened while reading users table: " + e.getMessage(), e);
        }

        Map<String, User> users = new HashMap<>();
        for (int i = 1; i < rows.size(); i++) {
            User curUser = new User(rows.get(i)[0], rows.get(i)[1], rows.get(i)[2]);
            if (users.containsKey(curUser.getLogin())) {
                throw new PrinterServerException(
                        String.format("Error happened while parsing users table: user %s was set twice",
                                curUser.getLogin()));
            }
            users.put(curUser.getLogin(), curUser);
        }

        return users;
    }
}
