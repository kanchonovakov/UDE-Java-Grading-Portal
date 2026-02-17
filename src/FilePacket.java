import java.io.Serializable;

public class FilePacket implements Serializable {
    private static final long serialVersionUID = 1L;

    private String filename;
    private byte[] content; // The actual file data
    private String owner;   // Who sent it (Student)

    public FilePacket(String filename, byte[] content, String owner) {
        this.filename = filename;
        this.content = content;
        this.owner = owner;
    }

    public String getFilename() { return filename; }
    public byte[] getContent() { return content; }
    public String getOwner() { return owner; }
}