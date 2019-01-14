package profitsoft.service;

import profitsoft.data.Contract;
import profitsoft.data.InsuredPerson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class FileOS extends FileWorker {
    public void uploadToFile(Contract contract) {
        if (file != null) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
                bufferedWriter.write(Integer.toString(contract.getId()));
                bufferedWriter.write(";");
                bufferedWriter.write(contract.getConclusionDate().toString());
                bufferedWriter.write(";");
                bufferedWriter.write(contract.getStartDate().toString());
                bufferedWriter.write(";");
                bufferedWriter.write(contract.getEndDate().toString());
                bufferedWriter.write(";");
                bufferedWriter.write(contract.getClient().getClient());
                Iterator<InsuredPerson> iterator = contract.getInsuredPeople().iterator();
                while (iterator.hasNext()) {
                    bufferedWriter.write(";");
                    bufferedWriter.write(iterator.next().getInsuredPerson());
                }
                bufferedWriter.write(System.lineSeparator());
            } catch (IOException e) {
                System.err.println("Error" + e.getMessage());
            }
        }
    }
}
