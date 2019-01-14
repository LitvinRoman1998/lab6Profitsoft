package profitsoft.service;

import profitsoft.data.Client;
import profitsoft.data.Contract;
import profitsoft.data.InsuredPerson;
import profitsoft.dict.ContractField;
import profitsoft.dict.Type;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileIS extends FileWorker {
    public List<Contract> downloadFromFile() {
        if (file != null) {
            List<Contract> contracts = new ArrayList<>();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String readLine = null;
                while ((readLine = bufferedReader.readLine()) != null) {
                    if (!(readLine.trim().isEmpty())) {
                        contracts.add(readContract(readLine));
                    }
                }
            } catch (IOException e) {
                System.err.println("Error" + e.getMessage());
            }
            return contracts;
        }
        return new ArrayList<>();
    }

    Contract readContract(String readLine) {
        int index = 0;
        Contract contract = new Contract();
        Client client = new Client();
        Set<InsuredPerson> insuredPeople = new LinkedHashSet<>();
        Scanner scanner = new Scanner(readLine);
        scanner.useDelimiter(";");
        while (scanner.hasNext()) {
            String data = scanner.next();
            if (index == ContractField.ID.ordinal()) {
                contract.setId(Integer.parseInt(data));
            } else if (index == ContractField.CONCLUSION_DATE.ordinal()) {
                contract.setConclusionDate(LocalDate.parse(data));
            } else if (index == ContractField.START_DATE.ordinal()) {
                contract.setStartDate(LocalDate.parse(data));
            } else if (index == ContractField.END_DATE.ordinal()) {
                contract.setEndDate(LocalDate.parse(data));
            } else if (index == ContractField.CLIENT_TYPE.ordinal()) {
                client.setType(Type.valueOf(data));
            } else if (client.getType().equals(Type.INDIVIDUAL) && index == ContractField.CLIENT.ordinal()) {
                client.setLastName(data);
                client.setFirstName(scanner.next());
                client.setPatronimic(scanner.next());
                client.setAdress(scanner.next());
                contract.setClient(client);
            } else if (client.getType().equals(Type.ENTITY) && index == ContractField.CLIENT.ordinal()) {
                client.setCompanyName(data);
                client.setAdress(scanner.next());
                contract.setClient(client);
            } else {
                InsuredPerson insuredPerson = new InsuredPerson();
                insuredPerson.setLastName(data);
                insuredPerson.setFirstName(scanner.next());
                insuredPerson.setPatronimic(String.valueOf(scanner.next()));
                insuredPerson.setBirthDay(LocalDate.parse(scanner.next()));
                insuredPerson.setInsurancePrice(Integer.parseInt(scanner.next()));
                insuredPerson.setTaxIdentificationNumber(Integer.parseInt(scanner.next()));
                insuredPeople.add(insuredPerson);
            }
            index++;
        }
        contract.setInsuredPeople(insuredPeople);
        scanner.close();
        return contract;
    }
}
