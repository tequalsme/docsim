package com.timreardon.docsim.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class IoHelper {

    public static final String DELIMITER_REGEX = "\\W+";

    public static Scanner createScanner(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        return createScanner(reader);
    }

    public static Scanner createScanner(Readable readable) {
        Scanner scanner = new Scanner(readable);
        scanner.useDelimiter(DELIMITER_REGEX);
        return scanner;
    }
}
