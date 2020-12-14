package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "specialRequests")
public class SpecialRequestData implements Identifiable<UUID>{
    @Id
    private UUID requestID;

    @Column(name = "requestingUser")
    private String requestingUser;

    @Column(name = "header")
    private String header;

    @Column(name = "description")
    private String description;

    @Column(name = "addressed")
    private boolean addressed;

    /**
     * Initiates a {@link SpecialRequestData}
     * @param requestingUser username of the user
     * @param header header of the request
     * @param description description of the request
     * @param addressed status of the request
     */
    public SpecialRequestData(String requestingUser, String header, String description, boolean addressed) {
        this.requestingUser = requestingUser;
        this.header = header;
        this.description = description;
        this.addressed = addressed;
        this.requestID = UUID.randomUUID();
    }

    /**
     * Sets request id to a randomly generated {@link UUID}.
     */
    public SpecialRequestData(){
        this.requestID = UUID.randomUUID();
    }

    /**
     * Returns the request id.
     * @return request id in form {@link UUID}
     */
    public UUID getId() {
        return requestID;
    }

    /**
     * Sets the request id.
     * @param id request id
     */
    public void setId(UUID id){ this.requestID = id;}

    /**
     * Returns the requesting user
     * @return a {@link String} representing username of the user
     */
    public String getRequestingUser() {
        return requestingUser;
    }

    /**
     * Sets the requesting user.
     * @param requestingUser username of the user
     */
    public void setRequestingUser(String requestingUser) {
        this.requestingUser = requestingUser;
    }

    /**
     * Returns the header of the request.
     * @return a {@link String} representing the request header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the header of the request.
     * @param header request header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Returns the description of the request.
     * @return s {@link String} representing the request description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the request.
     * @param description request description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the status of the request.
     * @return true if the request is addressed, false otherwise
     */
    public boolean getAddressed() {
        return addressed;
    }

    /**
     * Sets the status of the request.
     * @param addressed request status
     */
    public void setAddressed(boolean addressed) {
        this.addressed = addressed;
    }

    /**
     * Compares this special request with the given special request. Returns true if they are exactly the same, false otherwise.
     * @param o {@link Object} a special request
     * @return true if the two special request are exactly the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialRequestData that = (SpecialRequestData) o;
        return requestID.equals(that.requestID) && requestingUser.equals(that.requestingUser) && header.equals(that.header) && description.equals(that.description) && addressed==that.addressed;
    }

    /**
     * Returns the hash code of the special request.
     * @return int representing special request's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(requestID);
    }
}
