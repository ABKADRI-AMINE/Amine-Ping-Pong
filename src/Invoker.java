public class Invoker {
    private ICommand command; // Déclaration de la variable privée command de type ICommand

    public void setCommand(ICommand command) {
        this.command = command; // Méthode pour définir la commande li ghadi n executewha
    }

    public void executeCommand() {
        if (command != null) {
            command.execute(); // Méthode pour exécuter la commande si elle est définie
        }
    }
}
