package profitsoft.data;

import org.springframework.stereotype.Component;
import profitsoft.dict.Type;

import java.io.Serializable;
import java.util.Objects;
@Component
public class Client extends Person implements Serializable {
    private int clientId;
    private Type type;
    private String companyName;
    private String adress;

    public String getClient() {
        return this.type + ";" + (type == Type.INDIVIDUAL ? getPerson() + ";" : companyName + ";") + this.adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return (type == Type.INDIVIDUAL ? (Objects.equals(lastName, client.lastName) && Objects.equals(firstName, client.firstName) &&
                Objects.equals(patronimic, client.patronimic) &&
                Objects.equals(adress, client.adress)) : (type == client.type &&
                Objects.equals(companyName, client.companyName) &&
                Objects.equals(adress, client.adress)));
    }

    @Override
    public int hashCode() {
        return (type == Type.INDIVIDUAL ? Objects.hash(type, lastName, firstName, patronimic, adress) : Objects.hash(type, companyName, adress));
    }

    public Type getType() {
        return type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAdress() {
        return adress;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }


}
