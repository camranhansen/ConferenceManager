package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "specialRequests")
public class SpecialRequestData implements Identifiable<String>{
    @Id
    private String requestID;

    @Column(name = "requestingUser")
    private String requestingUser;

    @Column(name = "header")
    private String header;

    @Column(name = "description")
    private String description;

    @Column(name = "addressed")
    private String addressed;

    public String getId() {
        return requestID;
    }

    public void setId(String id){ this.requestID = id;}

    public String getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(String requestingUser) {
        this.requestingUser = requestingUser;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressed() {
        return addressed;
    }

    public void setAddressed(String addressed) {
        this.addressed = addressed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialRequestData that = (SpecialRequestData) o;
        return requestID.equals(that.requestID) && requestingUser.equals(that.requestingUser) && header.equals(that.header) && description.equals(that.description) && addressed.equals(that.addressed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID);
    }
}
