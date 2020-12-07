package csc.zerofoureightnine.conferencemanager.users.specialrequest;
import java.util.UUID;

@Deprecated
public class SpecialRequest {
    private String requestingUser;
    private UUID requestID;
    private String header;
    private String description;
    private boolean addressed;

    /**
     * Creates a new SpecialRequest.
     * @param requestingUser username of the requesting user.
     * @param header header of this SpecialRequest.
     * @param description description of this SpecialRequest.
     * @param addressed Whether or not this SpecialRequest has been addressed.
     */
    public SpecialRequest(String requestingUser, String header, String description, boolean addressed) {
        this.requestingUser = requestingUser;
        this.header = header;
        this.description = description;
        this.addressed = addressed;
        this.requestID = UUID.randomUUID();
    }

    /**
     * Returns the UUID of this SpecialRequest.
     * @return UUID of this SpecialRequest.
     */
    public UUID getRequestID() {
        return requestID;
    }

    /**
     * Returns the username of the user making this SpecialRequest.
     * @return username of requesting user.
     */
    public String getRequestingUser() {
        return requestingUser;
    }

    /**
     * Returns the header of this SpecialRequest.
     * @return header of this SpecialRequest.
     */
    public String getHeader() {
        return header;
    }

    /**
     * Returns the description of this SpecialRequest.
     * @return description of this SpecialRequest.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns true if this SpecialRequest has been addressed, false if it hasn't.
     * @return if this SpecialRequest has been addressed.
     */
    public boolean isAddressed() {
        return addressed;
    }

    /**
     * Sets the addressed state of this SpecialRequest to addressed.
     * @param addressed if this SpecialRequest has been addressed.
     */
    public void setAddressed(boolean addressed) {
        this.addressed = addressed;
    }
}
