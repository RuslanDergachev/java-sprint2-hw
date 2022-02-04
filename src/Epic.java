

public class Epic{
    String name;
    String description;
    String status;

    public Epic(String name, String descriptionTask, String status){

        this.name = name;
        this.status = status;
        this.description = descriptionTask;
        }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
