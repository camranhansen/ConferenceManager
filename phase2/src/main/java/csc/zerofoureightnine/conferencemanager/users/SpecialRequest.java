package csc.zerofoureightnine.conferencemanager.users;
import java.util.UUID;

public class SpecialRequest {
    private String requestingUser;
    private UUID requestID;
    private String header;
    private String description;
    private boolean addressed;

    public SpecialRequest(String requestingUser, String header, String description, boolean addressed) {
        this.requestingUser = requestingUser;
        this.header = header;
        this.description = description;
        this.addressed = addressed;
        this.requestID = UUID.randomUUID();
    }

    public UUID getRequestID() {
        return requestID;
    }

    public String getRequestingUser() {
        return requestingUser;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAddressed() {
        return addressed;
    }

    public void setAddressed(boolean addressed) {
        this.addressed = addressed;
    }
}
