package bankapp;

import java.util.Scanner;

public interface InputCaretaker {
    double getDouble();
    int getInt();
    String getString();
}

class ScannerCaretaker implements InputCaretaker {
    private Scanner scanner;

    public ScannerCaretaker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public double getDouble() {
        return scanner.nextDouble();
    }

    @Override
    public String getString() {
        return scanner.nextLine();
    }

    @Override
    public int getInt() {
        return scanner.nextInt();
    }
}
